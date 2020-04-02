package com.example.temanbelajar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Setter
@Getter
@ToString
public class Comment extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @Column(nullable=false)
    private transient Long blog_id;

    @Column(length = 80)
    @Size(min = 10, max = 80)
    @Email(message = "email not valid")
    private String guest_email;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Content can not empty")
    private String content;

    // @ManyToOne
    // @JoinColumn(name = "blog_id")
    // private Blog blog;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blog_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Blog blog;


    
}