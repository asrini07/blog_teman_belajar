package com.example.temanbelajar.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//mendeklarasikan bahwa class ini merupakan sebuah class entity yang akan di mapping oleh hibernate.
@Entity(name = "Blog")
@Table(name = "blog")
@Getter
@Setter
@ToString
public class Blog extends AuditModel {
    //mendeklarasikan bahwa property tersebut merupakan primary key.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private transient Long author_id;

    private transient Long categories_id;

    private transient List<Long> tags_id;

    private transient List<String> tags_name;

    @Column(length = 150, nullable = false)
    @Size(min = 3, max = 150, message = "Lastname min 3 and max 150 character")
    @NotBlank
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 10, message = "Content min 10 character")
    @NotBlank
    private String content;

    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categories;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "blog_tags", 
        joinColumns = { @JoinColumn(name = "blog_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "tags_id") }
    )
    private List<Tags> tag = new ArrayList<>();

    // @OneToMany(cascade = CascadeType.ALL,
    //     fetch = FetchType.LAZY,
    //     mappedBy = "post")
    // private Set<Comment> comments = new HashSet<>();

    
}