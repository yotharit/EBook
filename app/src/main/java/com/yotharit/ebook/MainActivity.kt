package com.yotharit.ebook

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View


class MainActivity : AppCompatActivity(), BookView, SearchView.OnQueryTextListener {


    lateinit var recyclerView: RecyclerView
    lateinit var bookAdapter: BookAdapter
    lateinit var bookPresenter: BookPresenter
    lateinit var bookRepository: RealBookRepository
    lateinit var bookSearch: SearchView

    val bookList: ArrayList<Book> = ArrayList<Book>()

    override fun setBookList(books: ArrayList<Book>) {
        this.bookList.clear()
        this.bookList.addAll(books)
        bookAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVariable()
        initInstance()
        bookPresenter.start()
    }

    fun initVariable() {
        bookRepository = RealBookRepository()
        bookPresenter = BookPresenter(this, bookRepository)
    }

    fun initInstance() {

        bookSearch = findViewById(R.id.bookSearch)
        bookSearch.setOnQueryTextListener(this)

        recyclerView = findViewById(R.id.bookRecycleView)
        bookAdapter = BookAdapter(bookList)
        val tranLayManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = tranLayManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16))

        recyclerView.addOnItemTouchListener(RecycleTouchListener(applicationContext, recyclerView, object : RecycleTouchListener.ClickListener {

            override fun onClick(view: View, position: Int) {
                //wait for later
            }

            override fun onLongClick(view: View, position: Int) {
                //wait for later
            }
        }))
        recyclerView.adapter = bookAdapter

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        bookAdapter.filter(query!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        bookAdapter.filter(newText!!)
        return true
    }
}
