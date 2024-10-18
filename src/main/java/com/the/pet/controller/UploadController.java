package com.the.pet.controller;

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

    @Autowired
    private ObjectStorageService objectStorageService;

    @GetMapping("/upload")
    public String uploadForm() {
        return "uploadForm";
    }

    @PostMapping("/upload/photo")
    public String uploadPhoto(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = System.getProperty("java.io.tmpdir") + file.getOriginalFilename();
            file.transferTo(Paths.get(filePath));
            objectStorageService.uploadPhoto(filePath, uniqueFileName);


            model.addAttribute("message", "파일 업로드 성공!");



        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "파일 업로드 실패: " + e.getMessage());
        }
        return "uploadForm";
    }
}
