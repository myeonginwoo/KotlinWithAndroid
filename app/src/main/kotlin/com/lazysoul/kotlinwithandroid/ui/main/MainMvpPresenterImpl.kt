package com.lazysoul.kotlinwithandroid.ui.main

import com.lazysoul.kotlinwithandroid.common.RxPresenter
import com.lazysoul.kotlinwithandroid.singletons.TodoManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.realm.Realm
import java.util.concurrent.TimeUnit

/**
 * Created by Lazysoul on 2017. 7. 9..
 */
class MainMvpPresenterImpl(private val realm: Realm)
    : RxPresenter(), MainMvpPresenter<MainMvpView> {

    private lateinit var view: MainMvpView

    private val searchTextChangeSubject = PublishSubject.create<String>()

    init {
        add(searchTextChangeSubject
                .throttleLast(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { text ->
                    TodoManager.search(realm, text).run {
                        if (isEmpty()) {
                            view.showEmtpyView()
                        } else {
                            view.onUpdateTodoList(this)
                        }
                    }

                })
    }

    override fun attachView(view: MainMvpView) {
        this.view = view
    }

    override fun destroy() {
        dispose()
        realm.close()
    }

    override fun loadTotoList(isFirst: Boolean) {
        add(Single.just(isFirst)
                .filter { !it }
                .doOnSuccess { view.onSuccessCreateSampes() }
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { loadTodoList() }
                .subscribe { createdSample() })
    }

    private fun loadTodoList() {
        TodoManager.getTodoList(realm).run {
            if (isEmpty()) {
                view.showEmtpyView()
            } else {
                view.onUpdateTodoList(this)
            }
        }
    }

    private fun createdSample() {
        TodoManager.createSamleTodo(realm)
    }

    override fun insert(id: Int) {
        view.onCreatedTodo(TodoManager.load(realm, id))
    }

    override fun checked(id: Int, isChecked: Boolean) {
        realm.beginTransaction()
        TodoManager.load(realm, id).isChecked = isChecked
        realm.commitTransaction()
    }

    override fun searchQuery(text: String) {
        searchTextChangeSubject.onNext(text)
    }

    override fun searchFinish() {
        loadTodoList()
    }
}
