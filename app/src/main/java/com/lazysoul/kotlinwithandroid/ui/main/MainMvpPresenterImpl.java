package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.common.RxPresenter;
import com.lazysoul.kotlinwithandroid.datas.Todo;
import com.lazysoul.kotlinwithandroid.singletons.TodoManager;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

class MainMvpPresenterImpl<MvpView extends BaseMvpView> extends RxPresenter
        implements MainMvpPresenter<MvpView> {

    private MainMvpView view;

    private Realm realm = Realm.getDefaultInstance();

    @Override
    public void attachView(MvpView view) {
        this.view = (MainMvpView) view;
    }

    @Override
    public void stop(boolean isFinishing) {
        if (isFinishing) {
            dispose();
            realm.close();
        }
    }

    @Override
    public void destroy() {
        dispose();
        realm.close();
    }

    @Override
    public void loadTotoList(final boolean isFirst) {
        add(Single.just(isFirst)
                .filter(new Predicate<Boolean>() {
                    @Override
                    public boolean test(Boolean isFirst) throws Exception {
                        return !isFirst;
                    }
                })
                .doOnSuccess(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isFirst) throws Exception {
                        view.onSuccessCreateSampes();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        loadTodoList();
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isfirst) throws Exception {
                        createdSample();
                    }
                }));
    }

    private void loadTodoList() {
        RealmResults<Todo> todoList = TodoManager.getTodoList(realm);
        if (null != todoList && todoList.isEmpty()) {
            view.showEmtpyView();
        } else {
            view.onUpdateTodoList(todoList);
        }
    }

    public void createdSample() {
        TodoManager.createSamleTodo(realm);
    }

    @Override
    public void search(String keyword) {

    }

    @Override
    public void update(Todo todo) {

    }

    @Override
    public void remove(Todo todo) {

    }

    @Override
    public void delete(Todo todo) {

    }

    @Override
    public void checked(Todo todo, boolean isChecked) {
        realm.beginTransaction();
        todo.setChecked(isChecked);
        realm.commitTransaction();
    }
}
