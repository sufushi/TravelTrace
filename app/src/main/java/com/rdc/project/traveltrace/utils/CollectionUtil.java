package com.rdc.project.traveltrace.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean inRange(Collection collection, int position) {
        if (isEmpty(collection)) {
            return false;
        }
        return position < collection.size();
    }

    public static void swap(List list, int fromPos, int targetPos) {
        if (isEmpty(list) || !inRange(list, fromPos) || !inRange(list, targetPos)) {
            return;
        }
        if (fromPos < targetPos) {
            for (int i = fromPos; i < targetPos; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = fromPos; i > targetPos; i--) {
                Collections.swap(list, i, i - 1);
            }
        }
    }
}
