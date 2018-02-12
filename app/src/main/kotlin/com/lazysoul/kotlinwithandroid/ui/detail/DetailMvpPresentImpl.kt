package com.lazysoul.kotlinwithandroid.ui.detail

import android.content.Intent
import com.lazysoul.kotlinwithandroid.common.BaseMvpView
import com.lazysoul.kotlinwithandroid.common.RxPresenter
import com.lazysoul.kotlinwithandroid.datas.Todo
import com.lazysoul.kotlinwithandroid.singletons.TodoManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

class DetailMvpPresentImpl<in MvpView : BaseMvpView> : RxPresenter(), DetailMvpPresenter<MvpView> {

    override val isFixed: Boolean
        get() = beforeTodo.isFixed

    lateinit var view: DetailMvpView

    lateinit var beforeTodo: Todo

    private val textChangeSubject = PublishSubject.create<Boolean>()

    private var requestType = -1

    init {
        add(textChangeSubject
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isChanged ->
                beforeTodo.isFixed = isChanged
                view.onChagedSaveBt()
            })
    }

    override fun attachView(view: MvpView) {
        this.view = view as DetailMvpView
    }

    override fun destroy() {
        dispose()
    }

    override fun loadTodo(intent: Intent) {
        requestType = intent.getIntExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_CREATE)

        when (requestType) {
            TodoManager.REQUEST_TYPE_CREATE -> {
                beforeTodo = Todo().apply {
                    id = -1
                    body = ""
                }
                view.onUpdated(beforeTodo, true)
            }
            TodoManager.REQUEST_TYPE_VIEW -> {
                val id = intent.getIntExtra(TodoManager.KEY_ID, -1)
                if (id != -1) {
                    beforeTodo = TodoManager.getTodo(id)
                    view.onUpdated(beforeTodo, false)
                }
            }
        }
    }

    override fun onTextChanged(s: String) {
        if (!beforeTodo.isFixed && isChanged(s)) {
            beforeTodo.isFixed = true
        }

        textChangeSubject.onNext(isChanged(s))
    }

    override fun saveTodo(text: String) {
        with(beforeTodo) {
            body = text
            isFixed = false
            if (id == -1) {
                id = TodoManager.maxId + 1
            }
        }
        view.onSaved(requestType, beforeTodo)
        textChangeSubject.onNext(false)
    }

    private fun isChanged(s: String): Boolean = beforeTodo.body != s
}
