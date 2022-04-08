package com.atguigu.book.service;

import com.atguigu.book.pojo.Book;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2022/4/2 22:34
 * @description TODO
 */
public interface BookService {
    List<Book> getBookList();

    Book getBook(Integer id);
}
