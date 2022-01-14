package com.javaproject.leaderboardservice.controller;

import com.javaproject.leaderboardservice.model.Role;
import com.javaproject.leaderboardservice.repositories.RoleRepository;
import com.javaproject.leaderboardservice.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/role/all")
    public ResponseEntity<?> getAllRoles(){
        //return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
        return ResponseEntity.status(200).body(roleService.getRoles());
    }

    @GetMapping(value = "/role/{role_id}")
    public ResponseEntity<?> getRoleUsers(@PathVariable("role_id") long role_id, Pageable pageable){
        return ResponseEntity.status(200).body(roleService.getUserRoles(role_id));
    }

}
