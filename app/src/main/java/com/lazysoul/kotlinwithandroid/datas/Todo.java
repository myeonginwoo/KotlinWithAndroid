package com.lazysoul.kotlinwithandroid.datas;

import java.util.Date;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

public class Todo {

    public Todo(int id, String body, boolean isChecked, Date createdAt) {
        this.id = id;
        this.body = body;
        this.isChecked = isChecked;
        this.createdAt = createdAt;
    }

    private int id;

    private String body;

    private boolean isChecked;

    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
