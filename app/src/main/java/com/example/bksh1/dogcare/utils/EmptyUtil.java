package com.example.bksh1.dogcare.utils;

import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class EmptyUtil {

    /**
     * To check if given object is null
     *
     * @param obj Object to be checked
     * @return returns given object is null
     */
    public static boolean isEmpty(@Nullable Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof CharSequence && ((CharSequence) obj).length() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0;
        }
        return false;
    }


    /**
     * To check that given object is not empty
     *
     * @param obj Object to be checked
     * @return returns if object is not empty
     */
    public static boolean isNotEmpty(@NonNull Object obj) {
        return !isEmpty(obj);
    }

    /**
     * To check that given object is null
     *
     * @param obj Object to be checked
     * @return returns if object is null
     */
    public static boolean isNull(@Nullable Object obj) {
        return obj == null;
    }

    /**
     * To check that given object is not null
     *
     * @param obj Object to be checked
     * @return returns if given object is not null
     */
    public static boolean isNotNull(@Nullable Object obj) {
        return !isNull(obj);
    }
}
