package com.the.pet.service;

import com.the.pet.model.entity.CommentEntity;
import com.the.pet.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    public CommentEntity save(CommentEntity dto){
        CommentEntity save = commentRepository.save(dto);
        return save;
    }
    public CommentEntity findById(Integer id){
        return commentRepository.findById(id).orElse(null);
    }
    public ArrayList<CommentEntity> findAll(){
        return commentRepository.findAll();
    }
    public CommentEntity delete(Integer id) {
        CommentEntity entity=commentRepository.findById(id).orElse(null);
        if(entity==null){
            return null;
        }
        commentRepository.delete(entity);
        return entity;
    }
    public ArrayList<CommentEntity> findByLoginEntityId(Integer id){
        return commentRepository.findByLoginEntityId(id);
    }
    public ArrayList<CommentEntity> findByNameContaining(String hello) {
        return commentRepository.findByNameContaining(hello);
    }
}
