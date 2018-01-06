package com.lazysoul.kotlinwithandroid.extensions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast

/**
 * Created by Lazysoul on 2017. 8. 12..
 */

fun test(context: Context){
    context.toast("test")
}

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}

fun String.toast(context: Context, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, length).show()
}

fun Context.dialog(titleId: Int,
        posTitleId: Int,
        posListener: (DialogInterface, Int) -> Unit,
        negaTitleId: Int,
        negaListener: (DialogInterface, Int) -> Unit) {

    AlertDialog.Builder(this).apply {
        setMessage(getString(titleId))
        setPositiveButton(getString(posTitleId), posListener)
        setNegativeButton(getString(negaTitleId), negaListener)
        show()
    }
}


















