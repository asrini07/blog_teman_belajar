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

@Entity(name = "Tag")
@Table(name = "tags")
@Getter
@Setter
@ToString
public class Tags extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 20, nullable = false, unique = true)
    @Size(min = 2, max = 20)
    @NotBlank
    private String name;

    //@ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    // @ManyToMany(mappedBy = "tag")
    // private List<Blog> blog = new ArrayList<>();

}