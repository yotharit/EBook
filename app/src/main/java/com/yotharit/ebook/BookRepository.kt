package com.yotharit.ebook

import java.util.*

abstract class BookRepository : Observable() {
        abstract fun loadAllBooks()
        abstract fun getBooks(): ArrayList<Book>
}