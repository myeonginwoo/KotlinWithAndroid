package com.lazysoul.kotlinwithandroid.ui.detail;

import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

public interface DetailMvpView extends BaseMvpView {

    void onUpdated(Todo todo, boolean editable);

    void onDeleted(Todo todo);

    void onCreated(Todo todo);
}
