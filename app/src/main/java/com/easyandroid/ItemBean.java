package com.easyandroid;

import com.easytools.tools.CheckAdapter;

/**
 * package: com.easyandroid.ItemBean
 * author: gyc
 * description:
 * time: create at 2017/1/8 15:38
 */

public class ItemBean implements CheckAdapter.CheckItem{

    private String name;
    private String age;
    private boolean isChecked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }
}
