package com.example.temanbelajar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "author")
@Getter
@Setter
@ToString
public class Author extends AuditModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    @NotBlank(message = "Firstname must not be empty")
    @Size(min = 3, max = 45, message = "Firstname min 3 and max 45 character")
    private String first_name;
    
    @Column(length = 45)
    @NotBlank(message = "Lastname must not be empty")
    @Size(min = 3, max = 45, message = "Lastname min 3 and max 45 character")
    private String last_name;

    @Column(length = 45, unique = true, nullable = false)
    @NotBlank(message = "Username must not be empty")
    @Size(min = 3, max = 45, message = "Username min 3 and max 45 character")
    private String username;

    @Column(length = 150, nullable = false)
    @NotBlank(message = "Password must not be empty")
    @Size(min = 3, max = 150)
    private String password;
  
}