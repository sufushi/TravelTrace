package com.rdc.project.traveltrace.view.dynamic_grid_view;

public interface DynamicGridAdapterInterface {

    void reorderItems(int originalPosition, int newPosition);

    int getColumnCount();

    boolean canReorder(int position);

}
