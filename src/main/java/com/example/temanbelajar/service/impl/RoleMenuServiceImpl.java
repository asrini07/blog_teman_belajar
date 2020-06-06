package com.example.temanbelajar.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.temanbelajar.model.Author;
import com.example.temanbelajar.model.Role;
import com.example.temanbelajar.model.RoleAppsMenu;
import com.example.temanbelajar.model.RoleMember;
import com.example.temanbelajar.model.AppsMenu;
import com.example.temanbelajar.repository.MenuRepository;
import com.example.temanbelajar.repository.AuthorRepository;
import com.example.temanbelajar.repository.RoleAppsMenuRepository;
import com.example.temanbelajar.repository.RoleRepository;
import com.example.temanbelajar.service.roleMenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class RoleMenuServiceImpl implements roleMenuService {

    // @Override
    // public boolean roleAccess(String url) {
    //     // TODO Auto-generated method stub
    //     return false;
    // }

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleAppsMenuRepository roleAppsMenuRepository;

    @Autowired
    private MenuRepository appsMenuRepository;

    @Override
    public boolean roleAccess(String url, String method) {
        // TODO Auto-generated method stub

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author userAuth = (Author) auth.getPrincipal();

        Author author = authorRepository.findByUserId(userAuth.getId());

        //find menu
        AppsMenu menu = appsMenuRepository.findByMenu(url);
        //find role
        Role role = roleRepository.findByTitle(author.getRole());

        RoleAppsMenu roleAccess = roleAppsMenuRepository.findByRoleIdAndMenuId(role, menu, method);

        if(roleAccess != null){
            return true;
        }

        return false;
        
        

    }
}