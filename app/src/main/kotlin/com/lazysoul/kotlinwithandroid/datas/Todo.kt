package com.lazysoul.kotlinwithandroid.datas

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.Date

/**
 * Created by Lazysoul on 2017. 7. 24..
 */
open class Todo(@PrimaryKey var id: Int = -1, var body: String = "",
        var isChecked: Boolean = false,
        var createdAt: Date? = null, @Ignore var isFixed: Boolean = false) : RealmObject()