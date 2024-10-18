package com.the.pet.controller;

import com.the.pet.model.entity.SchEntity;
import com.the.pet.repository.SchRepository;
import com.the.pet.service.ObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.UUID;


@Controller
public class UploadController {



    @GetMapping("/upload")
    public String uploadForm() {
        return "uploadForm";
    }


}
