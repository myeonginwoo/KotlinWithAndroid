package com.lazysoul.kotlinwithandroid.ui.main

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.lazysoul.kotlinwithandroid.R
import com.lazysoul.kotlinwithandroid.common.BaseActivity
import com.lazysoul.kotlinwithandroid.datas.Todo
import com.lazysoul.kotlinwithandroid.singletons.TodoManager
import com.lazysoul.kotlinwithandroid.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView, TodoListener {

    companion object {
        private const val REQUEST_CODE_DETAIL = 100
        private const val KEY_IS_FIRST = "isFirst"
    }

    lateinit var todoAdapter: TodoAdapter

    lateinit var presenter: MainMvpPresenter<MainMvpView>

    @Inject lateinit var sharedPreferences: SharedPreferences

    @Inject lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<View>(R.id.tb_activity_main) as Toolbar)

        todoAdapter = TodoAdapter(this)

        with(rv_activity_main) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }

        fa_activity_main.setOnClickListener { createTodo() }

        if (sharedPreferences.getBoolean(KEY_IS_FIRST, true)) {
            presenter.createTodoSamples()
            editor.putBoolean(KEY_IS_FIRST, false).apply()
        } else {
            presenter.loadTodoList()
        }
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_DETAIL) {
            data?.let {
                val resultType = it.getIntExtra(TodoManager.KEY_RESULT_TYPE, -1)
                val id = it.getIntExtra(TodoManager.KEY_ID, -1)
                val body = it.getStringExtra(TodoManager.KEY_BODY)
                when (resultType) {
                    TodoManager.RESULT_TYPE_CREATED -> presenter.insert(id, body)
                    TodoManager.RESULT_TYPE_UPDATED -> presenter.update(id, body)
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
        presenter = MainMvpPresenterImpl()
        presenter.attachView(this)
    }

    override fun onUpdateTodoList(todoList: List<Todo>) {
        for ((id, body, isChecked) in todoList) {
            Log.d("foo", "id : $id, body : $body, checked : $isChecked")
        }
        todoAdapter.addItems(todoList)
        tv_activity_main_empty.visibility = View.GONE
    }

    override fun onUpdateTodo(todo: Todo) {
        todoAdapter.update(todo)
    }

    override fun onCreatedTodo(todo: Todo) {
        todoAdapter.addItem(todo)
    }

    override fun showEmtpyView() {
        todoAdapter.clear()
        tv_activity_main_empty.visibility = View.VISIBLE
    }

    override fun onCreatedSampes(todoList: List<Todo>) {
        todoAdapter.addItems(todoList)
    }

    override fun onClicked(id: Int) {
        goTodo(id)
    }

    override fun onChecked(id: Int, isChecked: Boolean) {

    }

    private fun createTodo() {
        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
            putExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_CREATE)
        }
        startActivityForResult(intent, REQUEST_CODE_DETAIL)
    }

    private fun goTodo(id: Int) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
            putExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_VIEW)
            putExtra(TodoManager.KEY_ID, id)
        }
        startActivityForResult(intent, REQUEST_CODE_DETAIL)
    }

    private fun setSearchView(searchMenuItem: MenuItem) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        with(searchMenuItem.actionView as SearchView) {
            queryHint = getString(R.string.hint_search)
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(true)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    presenter.searchQuery(newText)
                    return true
                }
            })
        }

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                presenter.searchFinish()
                return true
            }
        })
    }
}
