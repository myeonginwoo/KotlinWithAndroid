package com.lazysoul.kotlinwithandroid.ui.main

import com.lazysoul.kotlinwithandroid.common.BaseMvpView
import com.lazysoul.kotlinwithandroid.common.RxPresenter
import com.lazysoul.kotlinwithandroid.singletons.TodoManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

class MainMvpPresenterImpl<in MvpView : BaseMvpView> : RxPresenter(), MainMvpPresenter<MvpView> {

    private lateinit var view: MainMvpView

    private val searchTextChangeSubject = PublishSubject.create<String>()

    init {
        add(searchTextChangeSubject
            .throttleLast(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                val searchedList = TodoManager.search(text)
                if (searchedList.isEmpty()) {
                    view.showEmtpyView()
                } else {
                    view.onUpdateTodoList(searchedList)
                }
            })
    }

    override fun attachView(view: MvpView) {
        this.view = view as MainMvpView
    }

    override fun destroy() {
        dispose()
    }

    override fun createTodoSamples() {
        view.onCreatedSampes(TodoManager.createSamples())
    }

    override fun loadTodoList() {
        val todoList = TodoManager.getTodoList()
        if (todoList.isEmpty()) {
            view.showEmtpyView()
        } else {
            view.onUpdateTodoList(todoList)
        }
    }

    override fun insert(id: Int, body: String) {
        view.onCreatedTodo(TodoManager.insert(id, body))
    }

    override fun update(id: Int, body: String) {
        val todo = TodoManager.update(id, body)
        if (todo != null) {
            view.onUpdateTodo(todo)
        }
    }

    override fun checked(id: Int, isChecked: Boolean) {
        TodoManager.checked(id, isChecked)
    }

    override fun searchQuery(text: String) {
        searchTextChangeSubject.onNext(text)
    }

    override fun searchFinish() {
        loadTodoList()
    }
}
