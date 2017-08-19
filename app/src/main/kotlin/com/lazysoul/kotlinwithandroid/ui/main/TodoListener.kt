package com.lazysoul.kotlinwithandroid.ui.main

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

interface TodoListener {

    fun onChecked(id: Int, isChecked: Boolean)

    fun onClicked(id: Int)
}
