package com.lazysoul.kotlinwithandroid.ui.main;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

interface TodoListener {

    void onChecked(int id, boolean isChecked);

    void onClicked(int id);
}
