package com.the.pet.controller.rest;
import com.the.pet.model.entity.CommentEntity;
import com.the.pet.model.request.CommentDto;
import com.the.pet.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class CommentRestController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/rest/test")
    public String test(){
        return "hellowRest";
    }
    @PostMapping("rest/comment/insert")
    public ResponseEntity<CommentEntity> insert(@RequestBody CommentDto dto){
        System.out.println(dto);
        CommentEntity saved = commentService.save(dto.toEntity());
        if(saved==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }


    @GetMapping("rest/comment/selectOne/{id}")
    public ResponseEntity<CommentEntity> selectById(@PathVariable Integer id){
        CommentEntity entity=commentService.findById(id);
        if(entity==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }


    @GetMapping("rest/comment/selectSome/{id}")
    public ResponseEntity<List<CommentEntity>> selectByLoginId(@PathVariable
                                                               Integer id){
        List<CommentEntity> dtos=commentService.findByLoginEntityId(id);
        if(dtos==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }


    @GetMapping("rest/comment/selectAll")
    public ResponseEntity<List<CommentEntity>> selectAll(){
        List<CommentEntity> dtos=commentService.findAll();
        if(dtos==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }



    @PutMapping("rest/comment/update")
    public ResponseEntity<CommentEntity> update(@RequestBody CommentDto dto){
        System.out.println(dto);
        CommentEntity saved = commentService.save(dto.toEntity());
        if(saved==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }
    @DeleteMapping("rest/comment/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        if (commentService.delete(id)==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        };
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
