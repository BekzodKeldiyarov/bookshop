package com.bekzodkeldiyarov.bookshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "author")
@ApiModel("data model of author entity")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "id generated automatically", example = "1")
    private Integer id;
    @ApiModelProperty(name = "firstname", example = "Bob")
    private String firstName;
    @ApiModelProperty(name = "lastname", example = "Marley")
    private String lastName;
    @ApiModelProperty(name = "photo of author", example = "photo.png")
    private String photo;
    @ApiModelProperty(name = "slug for referencing", example = "bobmarley")
    private String slug;
    @ApiModelProperty(name = "info about author", example = "some text info")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "TEXT")

    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Book2Author> book2Authors = new ArrayList<>();

    @Builder
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author() {
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
