package com.lazysoul.kotlinwithandroid.ui.detail

import android.content.Intent
import com.lazysoul.kotlinwithandroid.common.RxPresenter
import com.lazysoul.kotlinwithandroid.datas.Todo
import com.lazysoul.kotlinwithandroid.singletons.TodoManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.realm.Realm
import java.util.Calendar

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

class DetailMvpPresentImpl(private val realm: Realm)
    : RxPresenter(), DetailMvpPresenter<DetailMvpView> {

    private lateinit var view: DetailMvpView

    private lateinit var beforeTodo: Todo

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

    override fun attachView(view: DetailMvpView) {
        this.view = view
    }

    override fun destroy() {
        dispose()
        realm.close()
    }

    override fun loadTodo(intent: Intent) {
        requestType = intent
                .getIntExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_CREATE)

        when (requestType) {
            TodoManager.REQUEST_TYPE_CREATE -> {
                beforeTodo = Todo().apply {
                    id = -1
                    isChecked = false
                    createdAt = Calendar.getInstance().time
                    body = ""
                }
                view.onUpdated(beforeTodo, true)
            }
            TodoManager.REQUEST_TYPE_VIEW -> {
                val id = intent.getIntExtra(TodoManager.KEY_ID, -1)
                if (id != -1) {
                    beforeTodo = TodoManager.load(realm, id)
                    view.onUpdated(beforeTodo, false)
                }
            }
        }
    }

    override fun onTextChanged(s: String) {
        if (!beforeTodo.isFixed && isChanged(s)) {
            realm.beginTransaction()
            beforeTodo.isFixed = true
            realm.commitTransaction()
        }

        textChangeSubject.onNext(isChanged(s))
    }

    override fun saveTodo(text: String) {
        realm.beginTransaction()
        with(beforeTodo) {
            body = text
            isFixed = false
        }
        if (beforeTodo.id == -1) {
            beforeTodo.id = TodoManager.getMaxId(realm) + 1
            beforeTodo = realm.copyToRealm(beforeTodo)
        }
        realm.commitTransaction()
        view.onSaved(requestType, beforeTodo.id)

        textChangeSubject.onNext(false)
    }

    private fun isChanged(s: String): Boolean = beforeTodo.body != s

    override val isFixed: Boolean
        get() = beforeTodo.isFixed
}
