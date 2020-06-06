package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.AppsMenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends JpaRepository<AppsMenu, Long> {

    @Query("select e from #{#entityName} e where e.menu_link like %:param% ")
    AppsMenu findByMenu(String param);
    
}