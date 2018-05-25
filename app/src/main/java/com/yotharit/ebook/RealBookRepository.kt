package com.yotharit.ebook

import android.os.AsyncTask
import org.json.JSONArray
import org.json.JSONTokener
import java.net.URI
import java.net.URL
import java.util.*


class RealBookRepository : BookRepository() {

    val bookList: ArrayList<Book> = ArrayList<Book>()

    inner class DataBookLoader : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg p0: String?): String {
            return URL("https://theory.cpe.ku.ac.th/~jittat/courses/sw-spec/ebooks/books.json").readText()
        }

        override fun onPostExecute(result: String?) {
            if (result != null) {
                var jsonData = JSONArray(result)
                for (i in 0.. jsonData.length() - 1) {
                    var tmp = jsonData.getJSONObject(i)
                    var book = Book(tmp.getString("id").toInt(), tmp.get("title").toString(),
                            tmp.getString("price").toDouble(), tmp.getString("pub_year").toInt(),
                            tmp.getString("img_url").toString())
                    bookList.add(book)
                }
                setChanged()
                notifyObservers()
            }
        }

    }

    fun sortBook(query: String) {
        when(query.toLowerCase()) {
            "name" -> bookList.sortBy { book -> book.title }
            "year" -> bookList.sortBy { book -> book.publicationYear }
        }
        setChanged()
        notifyObservers()
    }


    override fun loadAllBooks() {
        bookList.clear()
        val loadTask = DataBookLoader()
        loadTask.execute()
    }

    override fun getBooks(): ArrayList<Book> {
        return bookList
    }

}