package com.atguigu.book.dao;

import com.atguigu.book.pojo.Book;
import com.atguigu.book.service.BookService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/2 22:29
 * @description TODO
 */
public interface BookDAO {
    List<Book> getBookList();

    Book getBook(Integer id);
}
