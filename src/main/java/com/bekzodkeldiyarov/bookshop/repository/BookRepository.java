package com.bekzodkeldiyarov.bookshop.repository;

import com.bekzodkeldiyarov.bookshop.model.Book;
import com.bekzodkeldiyarov.bookshop.model.Book2Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAll();


    @Query("select b from Book b inner join b.book2Authors bb where bb.author.firstName like concat('%', ?1, '%') or bb.author.lastName like concat('%', ?1, '%')")
    List<Book> findBooksByAuthorNameContaining(String name);



    List<Book> findBookByTitleContaining(String title);

    List<Book> findBooksByPriceOldBetween(Double min, Double max);

    List<Book> findBooksByPriceOldIs(Double price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM book where price=(SELECT MAX(price) from book)", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();


    Page<Book> findBookByTitleContaining(String title, Pageable nextPage);


    @Query("select b from Book b where b.slug = ?1")
    Book findBookBySlug(String slug);

    Book findBookById(Integer id);


    @Query("select b from Book b where b.slug in ?1")
    List<Book> findBooksBySlugIn(String[] slugs);
}
