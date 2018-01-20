package com.easytools.tools;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;

/**
 * package: com.easytools.tools.ViewUtil
 * author: gyc
 * description:
 * time: create at 2017/1/19 10:18
 */

public class ViewUtil {

    public static final void setEditTextSelectionToEnd(EditText editText) {
        Editable editable = editText.getEditableText();
        Selection.setSelection((Spannable) editable, editable.toString().length());
    }
}
