package com.javaproject.leaderboardservice.services;

import com.javaproject.leaderboardservice.model.Role;
import com.javaproject.leaderboardservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role addRole(Role role){
        return roleRepository.save(role);
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Optional<Role> getUserRoles(long role_id){
        return roleRepository.findById(role_id);
    }
}
