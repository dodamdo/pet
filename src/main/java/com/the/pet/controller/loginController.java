package com.the.pet.controller;

import com.the.pet.model.entity.LoginEntity;
import com.the.pet.model.request.LoginDto;
import com.the.pet.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.util.ArrayList;


@Controller
@Slf4j
public class loginController {
    @Autowired
    private LoginService loginService;
    @GetMapping("/insert")
    public String insert(){
        return "insert";
    }
    @PostMapping("/insert")
    public String insert(LoginDto dto, Model model){
        System.out.println(dto);
//DB 넣는 작업 JAP 진행
        LoginEntity entitySaved=loginService.save(dto.toEntity());
        System.out.println(entitySaved);
        log.info("log"+entitySaved.toString());
// return "insert";
        return "redirect:/selectOne/"+entitySaved.getId();
    }
    //selectOne/1 1번글 읽어오기
    @GetMapping("/selectOne/{id}")
    public String selectOne(@PathVariable Integer id,Model model){
        LoginEntity dto=loginService.findById(id);
        model.addAttribute("dto",dto);
        System.out.println(dto);
        loginService.findAll();
        return "selectOne";
    }
    @GetMapping("/selectAll")
    public String selectAll(Model model){
        ArrayList<LoginEntity> dtos=loginService.findAll();
        model.addAttribute("dtos",dtos);
        System.out.println(dtos);
        return "selectAll";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes rttr){
        LoginEntity dto=loginService.findById(id);
        System.out.println(dto);
        if(dto!=null){
            loginService.delete(dto);
            rttr.addFlashAttribute("msg","success");
        }
        return "redirect:/selectAll";
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         Model model){
        LoginEntity dto=loginService.findById(id);
        model.addAttribute("dto",dto);
        return "update";
    }
    @PostMapping("/update")
    public String update( LoginDto dto){
        LoginEntity entity=dto.toEntity();
        System.out.println(entity.toString());
        loginService.save(entity);
        return "redirect:/selectOne/"+entity.getId();
    }
}
