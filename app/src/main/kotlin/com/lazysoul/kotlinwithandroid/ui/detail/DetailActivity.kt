package com.lazysoul.kotlinwithandroid.ui.detail

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.lazysoul.kotlinwithandroid.R
import com.lazysoul.kotlinwithandroid.common.BaseActivity
import com.lazysoul.kotlinwithandroid.datas.Todo
import com.lazysoul.kotlinwithandroid.extensions.dialog
import com.lazysoul.kotlinwithandroid.singletons.TodoManager
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

class DetailActivity : BaseActivity(), DetailMvpView {

    @Inject lateinit var realm: Realm

    lateinit var presenter: DetailMvpPresenter<DetailMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(findViewById(R.id.tb_activity_detail) as Toolbar)

        et_activity_detail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                presenter.onTextChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        presenter.loadTodo(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onBackPressed() {
        if (!presenter.isFixed) {
            super.onBackPressed()
        } else {
            showSaveDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.activity_detail, menu)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val saveMenu = menu.findItem(R.id.menu_activity_main_save)
        saveMenu.isVisible = presenter.isFixed
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_activity_main_save -> presenter.saveTodo(et_activity_detail.text.toString())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSaveDialog() {
        dialog(R.string.msg_not_save,
                R.string.confirm,
                { dialog, _ ->
                    dialog.dismiss()
                    this@DetailActivity.onBackPressed()
                },
                R.string.cancel,
                { dialog, _ -> dialog.dismiss() }
        )
    }

    override fun inject() {
        component.inject(this)
    }

    override fun initPresenter() {
        presenter = DetailMvpPresentImpl(realm).apply {
            attachView(this@DetailActivity)
        }
    }

    override fun onUpdated(todo: Todo, editable: Boolean) {
        with(et_activity_detail) {
            setText(todo.body)
            if (editable) {
                requestFocus()
            }
        }
    }

    override fun onChagedSaveBt() {
        invalidateOptionsMenu()
    }

    override fun onSaved(requestType: Int, todoId: Int) {
        var result = -1
        when (requestType) {
            TodoManager.REQUEST_TYPE_CREATE -> result = TodoManager.RESULT_TYPE_CREATED
            TodoManager.REQUEST_TYPE_VIEW -> result = TodoManager.RESULT_TYPE_UPDATED
        }

        val resultData = Intent().apply {
            putExtra(TodoManager.KEY_RESULT_TYPE, result)
            putExtra(TodoManager.KEY_ID, todoId)
        }
        setResult(RESULT_OK, resultData)
    }
}
