package com.easytools.tools;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * package: com.easytools.tools.CheckAdapter
 * author: gyc
 * description:选择适配器
 * <br>准备工作：
 * <br>第一步：创建你的Item并实现CheckAdapter.CheckItem接口
 * <br>第二步：创建你的适配器并继承于CheckAdapter并同时通过泛型指定Item类型
 * <br>第三步：在构造函数中传给父类Item集合
 * <br>第四步：在getView()方法中调用handleCheckBox()方法处理CheckBox
 * <br>第五步：在ListView的OnItemClickListener中调用clickIitem()方法处理点击事件
 * <br>至此，全部准备工作已经完结
 * <br>
 * <br>接下来你可以：
 * <br>调用setSingleMode()方法修改选择模式为单选模式（默认是多选模式）
 * <br>调用enableCheckMode()方法开启选择模式
 * <br>调用cancelCheckMode()方法取消选择模式
 * <br>调用isEnableCheckMode()方法判断是否正处于选择模式
 * <br>调用getCheckedItems()方法获取全部选中的项
 * <br>调用deleteCheckedItems()方法删除全部选中的项
 * time: create at 2017/1/8 15:01
 */

public abstract class CheckAdapter<T extends CheckAdapter.CheckItem> extends BaseAdapter {

    private List<T> items;
    private boolean enabledCheckMode;//激活选择模式
    private boolean singleMode;//单选模式
    private int currentCheckedPosition = -1;

    public CheckAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    /**
     * 是否是单选模式
     *
     * @return
     */
    public boolean isSingleMode() {
        return singleMode;
    }

    /**
     * 设置单选模式，默认是复选模式
     *
     * @param singleMode
     */
    public void setSingleMode(boolean singleMode) {
        this.singleMode = singleMode;
    }

    /**
     * 处理复选框
     *
     * @param compoundButton
     * @param t
     */
    public void handleCompoundButton(CompoundButton compoundButton, T t) {
        compoundButton.setChecked(t.isChecked());
        compoundButton.setVisibility(isEnabledCheckMode() ? View.VISIBLE : View.GONE);
    }

    /**
     * 激活选择模式
     */
    public void enableCheckMode() {
        if (enabledCheckMode) {
            return;
        }
        enabledCheckMode = true;
        notifyDataSetChanged();
    }

    /**
     * 取消选择模式
     */
    public void cancelCheckMode() {
        if (!enabledCheckMode) {
            return;
        }
        enabledCheckMode = false;
        for (T item : items) {
            item.setChecked(false);
        }
        notifyDataSetChanged();
    }

    /**
     * 判定是否已激活选择模式
     *
     * @return
     */
    public boolean isEnabledCheckMode() {
        return enabledCheckMode;
    }

    /**
     * 点击了某一项
     *
     * @param position
     * @return true：已经激活了选择模式并且设置成功；false：尚未激活选择模式并且设置失败
     */
    public boolean clickItem(int position) {
        if(isEnabledCheckMode()){
            if(position < items.size()){
                if(singleMode){
                    if(currentCheckedPosition == -1 || currentCheckedPosition == position){
                        T item = items.get(position);
                        item.setChecked(!item.isChecked());
                    }else{
                        items.get(currentCheckedPosition).setChecked(false);
                        items.get(position).setChecked(true);
                    }
                    currentCheckedPosition = position;
                }else{
                    T item = items.get(position);
                    item.setChecked(!item.isChecked());
                }
                notifyDataSetChanged();
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取选中的项
     * @return
     */
    public List<T> getCheckedItem() {
        List<T> checkedItems = new ArrayList<T>();
        for (T item : items) {
            if (item.isChecked()) {
                checkedItems.add(item);
            }
        }
        return checkedItems;
    }

    /**
     * 删除选中的项
     * @return
     */
    public List<T> deleteCheckedItems() {
        List<T> checkedItems = new ArrayList<T>();
        Iterator<T> iterator = items.iterator();
        T item;
        while (iterator.hasNext()) {
            item = iterator.next();
            if (item.isChecked()) {
                checkedItems.add(item);
                iterator.remove();
            }
        }
        notifyDataSetChanged();
        return checkedItems;
    }

    public interface CheckItem{
        public boolean isChecked();
        public void setChecked(boolean checked);
    }
}
