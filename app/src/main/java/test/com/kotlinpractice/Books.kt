package test.com.kotlinpractice

data class Books(private var bookname: String) {

    var name: String = ""
    var author: String? = null

    init {
        name = bookname
    }

    constructor(bookname: String, bookauthor: String?) : this(bookname) {
        author = bookauthor
        name = bookname
    }
}