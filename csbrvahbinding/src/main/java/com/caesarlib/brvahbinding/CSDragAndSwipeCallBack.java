package com.caesarlib.brvahbinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.dragswipe.DragAndSwipeCallback;
import com.chad.library.adapter.base.module.BaseDraggableModule;

public class CSDragAndSwipeCallBack extends DragAndSwipeCallback {
    public CSDragAndSwipeCallBack(BaseDraggableModule draggableModule) {
        super(draggableModule);
    }
//    public CSDragAndSwipeCallBack(DraggableController draggableController) {
//        super(draggableController);
//    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        CSbrvahLog.Print("当前拖动了");
        return true;
    }
}
