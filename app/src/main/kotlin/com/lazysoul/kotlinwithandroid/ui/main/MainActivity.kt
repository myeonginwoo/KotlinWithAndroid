package com.lazysoul.kotlinwithandroid.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.lazysoul.kotlinwithandroid.R
import com.lazysoul.kotlinwithandroid.common.BaseActivity
import com.lazysoul.kotlinwithandroid.datas.Todo
import com.lazysoul.kotlinwithandroid.singletons.TodoManager
import com.lazysoul.kotlinwithandroid.ui.detail.DetailActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView, TodoListener {

    private lateinit var todoAdapter: TodoAdapter

    private val REQUEST_CODE_DETAIL = 100

    private val KEY_IS_FIRST = "isFirst"

    @Inject lateinit var sharedPreferences: SharedPreferences

    @Inject lateinit var editor: SharedPreferences.Editor

    @Inject lateinit var realm: Realm

    lateinit var presenter: MainMvpPresenter<MainActivity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(tb_activity_main)

        todoAdapter = TodoAdapter(this)

        with(rv_activity_main) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }

        fa_activity_main.setOnClickListener { createTodo() }

        val isFirst = sharedPreferences.getBoolean(KEY_IS_FIRST, false)
        presenter.loadTotoList(isFirst)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DETAIL) {
            data?.let {
                val resultType = it.getIntExtra(TodoManager.KEY_RESULT_TYPE, -1)
                val todoId = it.getIntExtra(TodoManager.KEY_ID, -1)
                when (resultType) {
                    TodoManager.RESULT_TYPE_CREATED -> presenter.insert(todoId)
                    TodoManager.RESULT_TYPE_UPDATED -> todoAdapter.update(todoId)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)

        setSearchView(menu.findItem(R.id.menu_activity_main_search))
        return super.onCreateOptionsMenu(menu)
    }

    override fun inject() {
        component.inject(this)
    }

    override fun initPresenter() {
        presenter = MainMvpPresenterImpl(realm)
                .apply {
                    attachView(this@MainActivity)
                }
    }

    override fun onUpdateTodoList(todoList: List<Todo>) {
        with(todoAdapter) {
            clear()
            addItems(todoList)
        }
        tv_activity_main_empty.visibility = View.GONE
    }

    override fun onCreatedTodo(todo: Todo) {
        todoAdapter.addItem(todo)
    }

    override fun showEmtpyView() {
        todoAdapter.clear()
        tv_activity_main_empty.visibility = View.VISIBLE
    }

    override fun onSuccessCreateSampes() {
        editor.putBoolean(KEY_IS_FIRST, true).apply()
    }

    override fun onChecked(id: Int, isChecked: Boolean) {
        presenter.checked(id, isChecked)
    }

    override fun onClicked(id: Int) {
        goTodo(id)
    }

    private fun createTodo() {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                .apply {
                    putExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_CREATE)
                }
        startActivityForResult(intent, REQUEST_CODE_DETAIL)
    }

    private fun goTodo(id: Int) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                .apply {
                    putExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_VIEW)
                    putExtra(TodoManager.KEY_ID, id)
                }
        startActivityForResult(intent, REQUEST_CODE_DETAIL)
    }

    private fun setSearchView(searchMenuItem: MenuItem) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        (searchMenuItem.actionView as SearchView).apply {
            queryHint = getString(R.string.hint_search)
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(true)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String) = true

                override fun onQueryTextChange(newText: String): Boolean {
                    presenter.searchQuery(newText)
                    return true
                }
            })
        }

        MenuItemCompat.setOnActionExpandListener(searchMenuItem,
                object : MenuItemCompat.OnActionExpandListener {
                    override fun onMenuItemActionExpand(item: MenuItem): Boolean = true

                    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                        presenter.searchFinish()
                        return true
                    }
                })
    }
}
