package com.the.pet.controller.rest;

import com.the.pet.model.entity.OwnerEntity;
import com.the.pet.model.entity.PetEntity;
import com.the.pet.model.entity.SchEntity;
import com.the.pet.model.request.PetInfoDto;
import com.the.pet.model.request.PetOwnerDTO;
import com.the.pet.model.request.PetSchDto;
import com.the.pet.repository.OwnerRepository;
import com.the.pet.repository.PetRepository;
import com.the.pet.repository.SchRepository;
import com.the.pet.service.ObjectStorageService;
import com.the.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
public class RestPetController
{
    @Autowired
    private PetService petService;

    @Autowired
    private  PetRepository petRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private SchRepository schRepository;
    @Autowired
    private ObjectStorageService objectStorageService;


    @GetMapping("/selectAll")
    public ResponseEntity<List<PetInfoDto>> selectAll(){
        System.out.println("selectAll -----------------------");
        List<PetInfoDto> petDetails = petService.getAllPetDetails();
        return ResponseEntity.ok(petDetails);

    }

    @GetMapping("/catchphone")
    public ResponseEntity<List<PetInfoDto>> searchphone(@RequestParam(value = "ownerId", required = false) String ownerId){
        System.out.println("searchphone -----------------------");
        List<PetInfoDto> petDetails = petService.catchphone(ownerId);
        return ResponseEntity.ok(petDetails);

    }


    @GetMapping("/api/flutterPetSearch")
    public ResponseEntity<PetOwnerDTO> getPetSearch(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "search", required = false) String search) {
        System.out.println("Authorization 헤더: " + authorization);
        List<PetEntity> pets;
        List<OwnerEntity> owners;
        boolean isNumeric = search != null && search.matches("\\d+"); // 숫자열 확인
        if (isNumeric) {
            pets = petRepository.findByOwnerIdLike(search);
            owners = ownerRepository.findByOwnerId(search);
        } else {
            pets = petRepository.findByPetNameContainingIgnoreCase(search);
            owners = ownerRepository.findByPetName(search);
        }

        for (PetEntity pet : pets) {
            if (pet.getOwnerId().toString().length() == 8) {
                String formattedOwnerId = "010-" + pet.getOwnerId().toString().substring(0, 4) + "-" + pet.getOwnerId().toString().substring(4);
                pet.setFormattedOwnerId(formattedOwnerId);
            }
        }

        PetOwnerDTO petOwnerDTO = new PetOwnerDTO(pets, owners);
        return ResponseEntity.status(HttpStatus.OK).body(petOwnerDTO);

    }
    @GetMapping("/api/petDetail")
    public ResponseEntity<PetSchDto> getPetDetail(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "petId", required = false) Long petId) {
        System.out.println("Authorization 헤더: " + authorization);
        System.out.println("petId : " + petId);

        PetInfoDto pet = petService.getPetDetails(petId);
        List< SchEntity > schList = schRepository.findByPetIdOrderBySchDateDesc(Math.toIntExact(petId));

        PetSchDto petSchDto =new PetSchDto();
        petSchDto.setPetInfo(pet);
        petSchDto.setSchedules(schList);

        System.out.println("petService : " + pet);
        System.out.println("SchEntity : " + schList);
        return ResponseEntity.ok(petSchDto);


    }

    @PostMapping("/api/petAdd")
    public ResponseEntity<?> petAdd(
            @RequestHeader(value="Authorization") String authoriztion,
            @RequestBody PetEntity petEntity){
        petRepository.save(petEntity);
        return ResponseEntity.ok("등록 완료");
    }


    @PostMapping("/api/uploadPhoto")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file,
                                         @RequestParam("schId") Long schId,
                                         @RequestParam("petId") Long petId) {
        try {
            // 파일 저장 및 업로드 처리
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
            file.transferTo(Paths.get(filePath));
            objectStorageService.uploadPhoto(filePath, uniqueFileName);

            // DB에 업로드 정보 저장
            SchEntity sch = schRepository.findBySchId(schId);
            if (sch != null) {
                sch.setPhotoUrl(uniqueFileName);
                schRepository.save(sch);
                return ResponseEntity.ok().body("파일 업로드 성공!"); // 성공 응답
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("예약 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 업로드 실패: " + e.getMessage());
        }
    }





}
