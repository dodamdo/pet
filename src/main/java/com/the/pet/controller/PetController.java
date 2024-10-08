package com.the.pet.controller;

import com.the.pet.model.entity.PetEntity;
import com.the.pet.repository.PetRepository;
import com.the.pet.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class PetController {
    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/petList")
    public String getAllPets( Model model,@PageableDefault(size = 10) Pageable pageable) {
        pageable =PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("petId").descending());
        Page<PetEntity>  pets = petRepository.findAll(pageable);
        model.addAttribute("pets", pets);
        model.addAttribute("currentPage",pets.getNumber()+1);
        model.addAttribute("totalPages",pets.getTotalPages());
        model.addAttribute("prevPage", pets.hasPrevious() ? pets.getNumber() - 1 : null);
        model.addAttribute("nextPage", pets.hasNext() ? pets.getNumber() + 1 : null);
        model.addAttribute("lastPage",pets.getTotalPages()-1);

        return "petList";
    }

    @GetMapping("/petSearch")
    public String getpetSearch(@RequestParam(value = "search") String search,
                             Model model,@PageableDefault(size = 10) Pageable pageable) {

        pageable =PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("petId").descending());
        Page<PetEntity> pets = petRepository.findByPetNameContainingIgnoreCase(search, pageable);

        model.addAttribute("pets", pets);
        model.addAttribute("currentPage",pets.getNumber()+1);
        model.addAttribute("totalPages",pets.getTotalPages());
        model.addAttribute("prevPage", pets.hasPrevious() ? pets.getNumber() - 1 : null);
        model.addAttribute("nextPage", pets.hasNext() ? pets.getNumber() + 1 : null);
        model.addAttribute("lastPage",pets.getTotalPages()-1);
        model.addAttribute("search", search);

        return "petSearch";
    }

    @GetMapping("/pets/petadd")
    public String petadd(Model model) {
        model.addAttribute("pet", new PetEntity());
        return "pets/petadd"; // 입력 폼 템플릿
    }

    @PostMapping("/pets/petadd")
    public String petaddpost(@ModelAttribute PetEntity pet) {
        petService.savePet(pet);
        return "redirect:/petList";
    }
}
