package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.AppsMenu;
import com.example.temanbelajar.model.Role;
import com.example.temanbelajar.model.RoleAppsMenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleAppsMenuRepository extends JpaRepository<RoleAppsMenu, Long> {
    
    @Query("select e from #{#entityName} e where e.roleApps=:role AND e.appsMenu=:menu AND e.requestType=:method")
    public RoleAppsMenu findByRoleIdAndMenuId(Role role, AppsMenu menu, String method);

}