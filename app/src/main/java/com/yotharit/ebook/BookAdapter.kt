package com.yotharit.ebook

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class BookAdapter(bookList: ArrayList<Book>) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    private var bookList: ArrayList<Book> = bookList
    private var bookListCopy: ArrayList<Book> = ArrayList<Book>()

    init {
        bookListCopy.addAll(bookList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_list_row, parent, false)
        return MyViewHolder(itemView)
    }

    fun filter(text: String) {
        var text = text
        bookList.clear()
        if (text.isEmpty()) {
            bookList.addAll(bookListCopy)
        } else {
            text = text.toLowerCase()
            for (item in bookListCopy) {
                if (item.title.toLowerCase().contains(text) || item.publicationYear.toString().contains(text)) {
                    bookList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val book = bookList.get(position)
        holder.name.text = book.title
        holder.price.text = book.price.toString() + " Baht"
        holder.year.text = book.publicationYear.toString()
        Picasso.get().load(book.imageURL).into(holder.image)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.bookName)
        var price: TextView = view.findViewById(R.id.bookPrice)
        var year: TextView = view.findViewById(R.id.bookYear)
        var image: ImageView = view.findViewById(R.id.bookImage)
    }

}