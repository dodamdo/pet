package com.the.pet.controller;

import com.the.pet.model.entity.PetEntity;
import com.the.pet.model.entity.SchEntity;
import com.the.pet.repository.PetRepository;
import com.the.pet.repository.SchRepository;
import com.the.pet.service.ObjectStorageService;
import com.the.pet.service.PetService;
import com.the.pet.service.SchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private ObjectStorageService objectStorageService;
    @Autowired
    private SchRepository schRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private SchService schService;


    @GetMapping("/pets/petList")
    public String getAllPets( Model model,@PageableDefault(size = 10) Pageable pageable) {
        pageable =PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("petId").descending());
        Page<PetEntity>  pets = petRepository.findAll(pageable);
        for(PetEntity pet : pets){
            if(pet.getOwnerId().toString().length()==8){
                String formattedOwnerId = "010-" + pet.getOwnerId().toString().substring(0, 4) + "-" + pet.getOwnerId().toString().substring(4);
                pet.setFormattedOwnerId(formattedOwnerId);
            }
        }


        model.addAttribute("pets", pets);
        model.addAttribute("currentPage",pets.getNumber()+1);
        model.addAttribute("totalPages",pets.getTotalPages());
        model.addAttribute("prevPage", pets.hasPrevious() ? pets.getNumber() - 1 : null);
        model.addAttribute("nextPage", pets.hasNext() ? pets.getNumber() + 1 : null);
        model.addAttribute("lastPage",pets.getTotalPages()-1);

        return "pets/petList";
    }

    @GetMapping("/pets/petSearch")
    public String getPetSearch(
            @RequestParam(value = "search", required = false) String search,
            Model model,
            @PageableDefault(size = 10) Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("petId").descending());
        Page<PetEntity> pets;
        boolean isNumeric = search != null && search.matches("\\d+"); // 숫자열 확인
        if (isNumeric) {
            model.addAttribute("searchtype", "전화번호 검색");
            pets = petRepository.findByOwnerIdLike(search, pageable);
        } else {
            model.addAttribute("searchtype", "애완동물 이름 검색");
            pets = petRepository.findByPetNameContainingIgnoreCase(search, pageable); // 펫 이름으로 검색
        }

        for(PetEntity pet : pets){
            if(pet.getOwnerId().toString().length()==8){
                String formattedOwnerId = "010-" + pet.getOwnerId().toString().substring(0, 4) + "-" + pet.getOwnerId().toString().substring(4);
                pet.setFormattedOwnerId(formattedOwnerId);
            }
        }

        model.addAttribute("pets", pets);
        model.addAttribute("currentPage", pets.getNumber() + 1);
        model.addAttribute("totalPages", pets.getTotalPages());
        model.addAttribute("prevPage", pets.hasPrevious() ? pets.getNumber() - 1 : null);
        model.addAttribute("nextPage", pets.hasNext() ? pets.getNumber() + 1 : null);
        model.addAttribute("lastPage", pets.getTotalPages() - 1);
        model.addAttribute("search", search);

        return "pets/petSearch";
    }
    @GetMapping("/pets/petadd")
    public String petadd(Model model) {
        model.addAttribute("pet", new PetEntity());
        return "pets/petadd"; // 입력 폼 템플릿
    }

    @PostMapping("/pets/petadd")
    public String petaddpost(@ModelAttribute PetEntity pet) {
        petService.savePet(pet);
        return "redirect:/pets/petList";
    }


    @GetMapping("/pets/petDetail")
    public String petDetail(@RequestParam("petId") Integer petId, Model model) {
        PetEntity pet = petService.getPetById(petId);
        if(pet.getOwnerId().toString().length()==8){
            String formattedOwnerId = "010-" + pet.getOwnerId().toString().substring(0, 4) + "-" + pet.getOwnerId().toString().substring(4);
            pet.setFormattedOwnerId(formattedOwnerId);
        }
        model.addAttribute("pet", pet);


        List<SchEntity> schList = schRepository.findByPetIdOrderBySchDateDesc(petId);
        for (SchEntity sch : schList) {
            String petName=petRepository.findPetNameById(sch.getPetId());
            String ownerId=petRepository.findOwnerIdById(sch.getPetId());
            if (ownerId.length() == 8) {
                ownerId= "010-" + ownerId.substring(0, 4) + "-" + ownerId.substring(4);
            }
            sch.setPetName(petName);
            sch.setOwnerId(ownerId);
        }
        List<String> photoUrls = schService.getPhotoUrlsByPetId(Long.valueOf(petId));
        if (photoUrls == null || photoUrls.isEmpty()) {
            photoUrls = List.of("default-pet.jpg");
        }
        System.out.println("img list :  ------------"+photoUrls);

        model.addAttribute("petsch",schList);
        model.addAttribute("photoUrls",photoUrls);
        return "pets/petDetail";
    }

    @GetMapping("/slider")
    public String slider(@RequestParam("petId") Integer petId,Model model){
        List<String> photoUrls = schService.getPhotoUrlsByPetId(Long.valueOf(petId));
        if (photoUrls == null || photoUrls.isEmpty()) {
            photoUrls = List.of("default-pet.jpg");
        }
        System.out.println("img list :  ------------"+photoUrls);
        model.addAttribute("photoUrls",photoUrls);
        return "slider";
    }








    @GetMapping("/pets/petUpdate")
    public String showUpdateForm(@RequestParam("petId") Integer petId, Model model) {
        PetEntity pet = petService.getPetById(petId);
        model.addAttribute("pet",pet );
        return "pets/petUpdate";
    }

    @PostMapping("/pets/petUpdate")
    public String updatePet(@ModelAttribute("pet") PetEntity pet) {
        petRepository.save(pet);
        return "redirect:/pets/petDetail?petId="+pet.getPetId();
    }

    @GetMapping("/pets/petinfo")
    @ResponseBody
    public List<PetEntity> petinfo(
            @RequestParam(value = "search", required = false) String search, Model model) {

        List<PetEntity> pets;
        boolean isNumeric = search != null && search.matches("\\d+");
        if (isNumeric) {
            pets = petRepository.findByOwnerIdLike(search);
        } else {
            pets = petRepository.findByPetNameContainingIgnoreCase(search);
        }


        return pets;
    }

    @PostMapping("/pets/uploadPhoto")
    public String uploadPhoto(@RequestParam("file") MultipartFile file,
                              @RequestParam("schId") Long schId,
                              @RequestParam("petId") Long petId,
                              Model model) {
        try {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = System.getProperty("java.io.tmpdir") + "/"+ file.getOriginalFilename();
            file.transferTo(Paths.get(filePath));
            objectStorageService.uploadPhoto(filePath, uniqueFileName);

            SchEntity sch = schRepository.findBySchId(schId);
            if (sch != null) {
                sch.setPhotoUrl(uniqueFileName);
                schRepository.save(sch);
                model.addAttribute("message", "파일 업로드 성공!");
            } else {
                model.addAttribute("message", "예약 정보를 찾을 수 없습니다.");
            }
            model.addAttribute("message", "파일 업로드 성공!");



        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "파일 업로드 실패: " + e.getMessage());
        }
        return "redirect:/pets/petDetail?petId="+petId;
    }


}
