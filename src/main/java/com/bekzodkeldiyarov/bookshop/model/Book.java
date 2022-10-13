package com.bekzodkeldiyarov.bookshop.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Double priceOld;
    private Double price;
    private Date pubDate;
    private Integer isBestseller;
    private String slug;
    private String image;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(columnDefinition = "text")
    @JsonIgnore
    private String description;
    private String discount;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Book2Author> book2Authors = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<BookFile> files = new ArrayList<>();


    @JsonGetter("authors")
    public String getMainAuthorsFullName() {
        Book2Author book2AuthorToReturn = book2Authors
                .stream()
                .min(Comparator.comparing(Book2Author::getSortIndex))
                .orElseThrow(NoSuchElementException::new);
        return book2AuthorToReturn.getAuthor().getFullName();
    }


    @Builder
    public Book(String title, Double priceOld, Double price) {
        this.title = title;
        this.priceOld = priceOld;
        this.price = price;
    }
}
