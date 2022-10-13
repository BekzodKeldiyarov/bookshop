package com.bekzodkeldiyarov.bookshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
@Getter
@Setter
public class BookFile {
    @Id
    private Integer id;
    private String hash;

    private Integer type_id;
    private String path;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public String getBookFileExtension() {
        return BookFileType.getExtensionTypeByTypeId(type_id);
    }
}
