package com.lazysoul.kotlinwithandroid.ui.detail;

import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

public interface DetailMvpView extends BaseMvpView {

    void onUpdated(Todo todo, boolean editable);

    void onChagedSaveBt();

    void onSaved(int requestType, Todo todo);
}
