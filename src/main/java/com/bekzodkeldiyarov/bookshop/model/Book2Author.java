package com.bekzodkeldiyarov.bookshop.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book2Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;
    private Integer sortIndex;

    public void setAuthor(Author author){
        this.author=author;
        author.getBook2Authors().add(this);
    }

    public void setBook(Book book){
        this.book=book;
        book.getBook2Authors().add(this);
    }


}
