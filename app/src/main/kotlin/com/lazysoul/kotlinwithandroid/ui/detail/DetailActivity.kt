package com.lazysoul.kotlinwithandroid.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.lazysoul.kotlinwithandroid.R
import com.lazysoul.kotlinwithandroid.common.BaseActivity
import com.lazysoul.kotlinwithandroid.datas.Todo
import com.lazysoul.kotlinwithandroid.singletons.TodoManager
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

class DetailActivity : BaseActivity(), DetailMvpView {

    lateinit var presenter: DetailMvpPresenter<DetailMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(tb_activity_detail)

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
        presenter.destroy()
        super.onDestroy()
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
        menu.findItem(R.id.menu_activity_main_save).isVisible = presenter.isFixed
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_activity_main_save -> presenter.saveTodo(et_activity_detail.text.toString())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSaveDialog() {
        AlertDialog.Builder(this).apply {
            setMessage(R.string.msg_not_save)
            setPositiveButton(R.string.confirm) { dialog, _ ->
                dialog.dismiss()
                super@DetailActivity.onBackPressed()
            }
            setNegativeButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
        }.show()
    }

    override fun inject() {
        component.inject(this)
    }

    override fun initPresenter() {
        presenter = DetailMvpPresentImpl()
        presenter.attachView(this)
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

    override fun onSaved(requestType: Int, todo: Todo) {
        var result = -1
        when (requestType) {
            TodoManager.REQUEST_TYPE_CREATE -> result = TodoManager.RESULT_TYPE_CREATED
            TodoManager.REQUEST_TYPE_VIEW -> result = TodoManager.RESULT_TYPE_UPDATED
        }

        val resultData = Intent().apply {
            putExtra(TodoManager.KEY_RESULT_TYPE, result)
            putExtra(TodoManager.KEY_ID, todo.id)
            putExtra(TodoManager.KEY_BODY, todo.body)
        }
        setResult(Activity.RESULT_OK, resultData)
    }
}
