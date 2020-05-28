package com.caesarlib.brvahbinding;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.listener.GridSpanSizeLookup;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.listener.OnUpFetchListener;
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.module.BaseDraggableModule;

import java.util.ArrayList;

public class CSBrvahBindingAdapter {

    @BindingAdapter(
            value = {"cs_brvah_adapter", "cs_brvah_layoutManager", "cs_brvah_spansize", "cs_brvah_multiType", "cs_brvah_headBinding", "cs_brvah_footBinding", "cs_brvah_loadMoreListener", "cs_brvah_Decoration", "cs_brvah_animation_custom", "cs_brvah_loadMoreView", "cs_brvah_upFetchListener", "cs_brvah_animation", "cs_brvah_OnItemSwipeListener", "cs_brvah_OnItemDragListener", "cs_brvah_SwipeMoveFrags"},
            requireAll = false
    )
    public static <T> void setCSBravhAdapter(RecyclerView recyclerView, BaseQuickAdapter adapter, CSBrvahLayoutManager.LayoutManagerFactory layoutManager, GridSpanSizeLookup spanSizeLookup, BaseMultiTypeDelegate<T> multiTypeDelegate, ArrayList<CSBravhItemBinding> headBinding, ArrayList<CSBravhItemBinding> footBinding, OnLoadMoreListener loadMoreListener, RecyclerView.ItemDecoration itemDecoration, BaseAnimation animationCustom, BaseLoadMoreView loadMoreView, OnUpFetchListener upFetchListener, ObservableField<BaseQuickAdapter.AnimationType> animationType, OnItemSwipeListener onItemSwipeListener, OnItemDragListener onItemDragListener, ObservableInt SwipeMoveFrags) {

        Adapter oldAdapter = recyclerView.getAdapter();
        adapter = initAdapter(recyclerView, adapter, oldAdapter, spanSizeLookup, multiTypeDelegate, itemDecoration, loadMoreListener, loadMoreView, upFetchListener, animationType, animationCustom, onItemSwipeListener, onItemDragListener, SwipeMoveFrags);
        CSbrvahLog.Print("适配器是否为空:" + (adapter == null));
        Context context = recyclerView.getContext();
        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager.create(recyclerView));
        } else {
            recyclerView.setLayoutManager(CSBrvahLayoutManager.linear().create(recyclerView));
        }
        recyclerView.setAdapter(adapter);
        if (headBinding != null) {
            for (CSBravhItemBinding binding : headBinding) {
                ViewDataBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), binding.getLayoutRes(), null, false);
                viewBinding.setVariable(binding.getVariableId(), binding.getHeadAFootData());
                if (binding.getAction() != null) {
                    viewBinding.setVariable(binding.getActionId(), binding.getAction());
                }
                adapter.addHeaderView(viewBinding.getRoot());
            }
        }

        if (footBinding != null) {
            for (CSBravhItemBinding binding : footBinding) {
                ViewDataBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), binding.getLayoutRes(), null, false);
                viewBinding.setVariable(binding.getVariableId(), binding.getHeadAFootData());
                if (binding.getAction() != null) {
                    viewBinding.setVariable(binding.getActionId(), binding.getAction());
                }
                adapter.addFooterView(viewBinding.getRoot());
            }
        }
    }


    private static <T> BaseQuickAdapter initAdapter(RecyclerView recyclerView, BaseQuickAdapter adapter, Adapter oldAdapter, GridSpanSizeLookup spanSizeLookup, BaseMultiTypeDelegate<T> multiTypeDelegate, RecyclerView.ItemDecoration itemDecoration, OnLoadMoreListener loadMoreListener, BaseLoadMoreView loadMoreView, OnUpFetchListener upFetchListener, ObservableField<BaseQuickAdapter.AnimationType> animationType, BaseAnimation animationCustom, OnItemSwipeListener onItemSwipeListener, OnItemDragListener onItemDragListener, ObservableInt SwipeMoveFrags) {


        if (adapter == null) {
            if (oldAdapter != null) {
                CSbrvahLog.Print("oldAdapter为空");
                adapter = (BaseQuickAdapter) oldAdapter;
            } else {
                CSbrvahLog.Print("oldAdapter不为空");
            }
        } else {
            CSbrvahLog.Print("Adapter不为空");
        }
        if (adapter != null) {
            if (spanSizeLookup != null) {
                CSbrvahLog.Print("设置了spanSizeLookup");
                adapter.setGridSpanSizeLookup(spanSizeLookup);
            }
            if (loadMoreListener != null) {
                if (adapter instanceof CSItemBindingAdapter) {
                    ( (CSItemBindingAdapter)adapter).becomeLoadMore();
                    adapter.getLoadMoreModule().setOnLoadMoreListener(loadMoreListener);
                    if (loadMoreView != null) {
                        adapter.getLoadMoreModule().setLoadMoreView(loadMoreView);
                    }
                }

            }

            if (upFetchListener != null) {
                ( (CSItemBindingAdapter)adapter).becomeUpFetch();
                adapter.getUpFetchModule().setOnUpFetchListener(upFetchListener);
//                adapter.setUpFetchListener(upFetchListener);
            }
            if (animationType != null && animationType.get() != null) {
                adapter.setAnimationWithDefault(animationType.get());//这边3。0之后改为枚举了
//                adapter.openLoadAnimation(animationType.get());
            }
            if (recyclerView.getItemDecorationCount() == 0 && itemDecoration != null) {
                recyclerView.addItemDecoration(itemDecoration);
            }
            if (animationCustom != null) {
                adapter.setAdapterAnimation(animationCustom);//3.0之后，方法名改变
            }
            if (adapter instanceof CSItemBindingAdapter) {
                if (multiTypeDelegate != null) {
                    CSbrvahLog.Print("设置了multiTypeDelegate");
                    ((CSItemBindingAdapter) adapter).setMultiTypeDelegate(multiTypeDelegate);
                }

                if (onItemSwipeListener != null || onItemDragListener != null) {
                    CSbrvahLog.Print("开始了滑动配置");
                    ((CSItemBindingAdapter) adapter).becomeDraggable();
                    BaseDraggableModule draggableController =  adapter.getDraggableModule();
                    CSDragAndSwipeCallBack itemDragAndSwipeCallback = new CSDragAndSwipeCallBack(draggableController);
                    //这边是滑动功能,可以用系统的ItemDragAndSwipeCallback,如果是多布局拖动功能,并且想要不同type之间也可以拖动,要用CSDragAndSwipeCallBack
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
                    draggableController.itemTouchHelper = itemTouchHelper;
                    //3.0之后,这边要重新配置,要重新设置itemTouchHelper,和下面的itemTouchHelperCallback,实现多布局的拖动
//                    ItemTouchHelper itemTouchHelper = ((CSItemBindingAdapter) adapter).getItemTouchHelper(itemDragAndSwipeCallback);
//                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    if (onItemSwipeListener != null) {
                        CSbrvahLog.Print("设置了滑动删除监听");
//                        draggableController.enableSwipeItem();
                        draggableController.setSwipeEnabled(true);
                        draggableController.setOnItemSwipeListener(onItemSwipeListener);
                        if (SwipeMoveFrags != null) {
                            CSbrvahLog.Print("设置了侧滑方向");
                            itemDragAndSwipeCallback.setSwipeMoveFlags(SwipeMoveFrags.get());
                        }
                    }
                    if (onItemDragListener != null) {
                        CSbrvahLog.Print("设置了拖动监听");
                        draggableController.setOnItemDragListener(onItemDragListener);
                        draggableController.setDragEnabled(true);
                        draggableController.itemTouchHelperCallback = itemDragAndSwipeCallback;
//                        draggableController.enableDragItem(itemTouchHelper);
                    }
                }

            }
        }


        return adapter;
    }


    @BindingAdapter(value = {"cs_brvah_loadMoreEnd"})
    public static void onLoadMoreEnd(RecyclerView recyclerView, ObservableBoolean loadMoreEnd) {
        CSbrvahLog.Print("加载结束调用:" + loadMoreEnd.get());
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BaseQuickAdapter) {
            CSbrvahLog.Print("进入加载结束调用:" + loadMoreEnd.get());
            ((BaseQuickAdapter) adapter).getLoadMoreModule().loadMoreEnd(loadMoreEnd.get());
        }
    }

    @BindingAdapter(value = {"cs_brvah_loadMoreEnable"})
    public static void onLoadMoreEnable(RecyclerView recyclerView, ObservableBoolean loadMoreEnable) {
        CSbrvahLog.Print("是否加载更多:" + loadMoreEnable.get());
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BaseQuickAdapter) {
            CSbrvahLog.Print("进入是否加载更多:" + loadMoreEnable.get());
            ((BaseQuickAdapter) adapter).getLoadMoreModule().setEnableLoadMore(loadMoreEnable.get());
        }
    }

    @BindingAdapter(value = {"cs_brvah_loadMoreSuccess"})
    public static void onLoadMoreSuccess(RecyclerView recyclerView, ObservableBoolean loadMoreSuccess) {
        CSbrvahLog.Print("是否加载成功:" + loadMoreSuccess.get());
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BaseQuickAdapter) {
            CSbrvahLog.Print("进入是否加载成功:" + loadMoreSuccess.get());
            if (loadMoreSuccess.get()) {
                ((BaseQuickAdapter) adapter).getLoadMoreModule().loadMoreComplete();
            } else {
                ((BaseQuickAdapter) adapter).getLoadMoreModule().loadMoreFail();
            }
        }
    }


    @BindingAdapter(value = {"cs_brvah_emptyResId", "cs_brvah_emptyClickListener"})
    public static void onEmptyView(RecyclerView recyclerView, ObservableInt emptyResId, View.OnClickListener clickListener) {
        CSbrvahLog.Print("加载空布局调用");
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BaseQuickAdapter) {
            if (emptyResId != null) {
                CSbrvahLog.Print("进入加载空布局");
                ((BaseQuickAdapter) adapter).setEmptyView(emptyResId.get());
            }
            if (clickListener != null && ((BaseQuickAdapter) adapter).hasEmptyView()) {
                CSbrvahLog.Print("进入加载空布局监听事件");
//                ((BaseQuickAdapter) adapter).getEmptyView().setOnClickListener(clickListener);
                ((BaseQuickAdapter) adapter).getEmptyLayout().setOnClickListener(clickListener);
            }

        }
    }


}
