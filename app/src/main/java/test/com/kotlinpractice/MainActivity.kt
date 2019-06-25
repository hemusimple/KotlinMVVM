package test.com.kotlinpractice

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import test.com.kotlinpractice.databinding.ActivityMainBinding
import test.com.kotlinpractice.databinding.ListofbooksrowBinding


class MainActivity : AppCompatActivity() {

    private lateinit var model: BooksViewModel
    private lateinit var mainactivity: ActivityMainBinding
    private var booksAdapter: BooksAdapter
    private  var booklist = mutableListOf<Books>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(BooksViewModel::class.java)
        mainactivity = DataBindingUtil.setContentView(this, R.layout.activity_main)
        model.bookobject.observe(this, bookChangeObserver())
        updateBooks()
        tempBooks()
    }

    init {
        booksAdapter = BooksAdapter()
    }

    private fun updateBooks() {
        mainactivity.listofbooks.setHasFixedSize(true)
        mainactivity.listofbooks.layoutManager = LinearLayoutManager(this)
        mainactivity.listofbooks.adapter = booksAdapter
    }

    private fun bookChangeObserver(): Observer<MutableList<Books>> {
        val oserver = Observer<MutableList<Books>> { data ->
            booksAdapter.notifyDataSetChanged()
        }
        return oserver
    }

    private fun tempBooks() {
        booklist.add(Books("Java"))
        booklist.add(Books("Kotlin", "JetBrains"))
        booklist.add(Books("Python"))
        booklist.add(Books("Php"))
        booklist.add(Books("Android"))
        model.bookobject.value = booklist
    }

    private inner class BooksAdapter : RecyclerView.Adapter<BooksAdapter.BookHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookHolder {
            return BookHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(this@MainActivity),
                    R.layout.listofbooksrow,
                    p0,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            val count = model.bookobject.value!!.size
            return if (count > 0) count else 0
        }

        override fun onBindViewHolder(bookViewHoder: BookHolder, position: Int) {
            bookViewHoder.view.bookname.text = model.bookobject.value?.get(position)?.name
            bookViewHoder.view.bookAuthor.text = "Author:"+if(model.bookobject.value?.get(position)?.author==null) "" else model.bookobject.value?.get(position)?.author
            bookViewHoder.view.delete.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    deleteUser(booklist.get(bookViewHoder.adapterPosition))
                }
            })
        }

        fun deleteUser(book: Books?) {
            booklist.remove(book)
            model.bookobject.value = booklist
        }

        inner class BookHolder(var view: ListofbooksrowBinding) : RecyclerView.ViewHolder(view.root)
    }
}