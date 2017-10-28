package com.github.batulovandrey.urbandictionarycom.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Andrey Batulov on 28/10/2017
 */

public final class Utils {

    private Utils() {
        throw new IllegalStateException("can't create the object");
    }

    public static void hideKeyboard(@NonNull View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}