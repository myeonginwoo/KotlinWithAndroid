package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.datas.Todo;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

interface TodoListener {

    void onChecked(Todo todo, boolean isChecked);

    void onClicked(Todo todo);
}
