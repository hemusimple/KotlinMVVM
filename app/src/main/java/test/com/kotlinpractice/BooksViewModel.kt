package test.com.kotlinpractice

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class BooksViewModel : ViewModel() {

    val bookobject: MutableLiveData<MutableList<Books>>  by lazy {
        MutableLiveData<MutableList<Books>>()
    }

    fun addNewBook(book: Books) {
        bookobject.value?.add(book)
    }

    fun  removeBook(book: Books?){
        bookobject.value?.remove(book)
    }
}