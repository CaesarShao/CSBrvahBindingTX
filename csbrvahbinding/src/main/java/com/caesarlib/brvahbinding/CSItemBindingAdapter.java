package com.caesarlib.brvahbinding;

import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class CSItemBindingAdapter<T, V extends BaseViewHolder> extends BaseQuickAdapter<T, V> implements LoadMoreModule, DraggableModule, UpFetchModule {
    private Map<Integer, CSBravhItemBinding> bravhItemBinding;
    private BaseMultiTypeDelegate multiTypeDelegate;
    //    private DraggableController mDraggableController;
    private ItemTouchHelper itemTouchHelper;

    private CSBindingListChangedCallBack bindingListChangedCallBack;

    public CSItemBindingAdapter(Map<Integer, CSBravhItemBinding> itemBinding, List<T> data) {
        super(0, data);
        this.bravhItemBinding = itemBinding;
        this.bindingListChangedCallBack = new CSBindingListChangedCallBack(this);
        if (getData() instanceof ObservableList) {
            ((ObservableList) getData()).addOnListChangedCallback(bindingListChangedCallBack);
        }
    }


    @Override
    protected void convert(V helper, T item) {
//        if (mDraggableController != null) {
//            mDraggableController.initView(helper);
//        }
        ViewDataBinding binding = DataBindingUtil.bind(helper.itemView);
        if (bravhItemBinding.size() > 1) {
            binding.setVariable(bravhItemBinding.get(helper.getItemViewType()).getVariableId(), item);
            if (bravhItemBinding.get(helper.getItemViewType()).getAction() != null) {
                binding.setVariable(bravhItemBinding.get(helper.getItemViewType()).getActionId(), bravhItemBinding.get(helper.getItemViewType()).getAction());
            }

        } else {
            binding.setVariable(bravhItemBinding.get(0).getVariableId(), item);
            if (bravhItemBinding.get(0).getAction() != null) {
                binding.setVariable(bravhItemBinding.get(0).getActionId(), bravhItemBinding.get(0).getAction());
            }
        }
        binding.executePendingBindings();

    }

    @Override
    protected int getDefItemViewType(int position) {
        if (bravhItemBinding != null && bravhItemBinding.size() > 1 && getItem(position) instanceof MultiItemEntity) {
            return ((MultiItemEntity) getItem(position)).getItemType();
        } else if (multiTypeDelegate != null) {
            return multiTypeDelegate.getItemType(getData(), position);
        }
        return super.getDefItemViewType(position);
    }

    protected void setMultiTypeDelegate(BaseMultiTypeDelegate multiTypeDelegate) {
        this.multiTypeDelegate = multiTypeDelegate;
    }

    @Override
    protected V onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, bravhItemBinding.get(viewType).getLayoutRes());
    }


    //    @Override
//    protected View getItemView(int layoutResId, ViewGroup parent) {
//        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutResId, parent, false);
//        if (binding != null) {
//            return binding.getRoot();
//        } else {
//            //在使用加载更多布局的时候,如果不是databinding的布局,binding会空,所以直接判断
//            View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
//            return view;
//
//        }
//    }

//    public DraggableController getDraggableController() {
//        if (mData instanceof ObservableList) {
//            ((ObservableList) mData).removeOnListChangedCallback(bindingListChangedCallBack);
//        }
//        if (mDraggableController == null) {
//            mDraggableController = new DraggableController(this);
//        }
//        return mDraggableController;
//    }

    public ItemTouchHelper getItemTouchHelper(ItemTouchHelper.Callback callback) {
        if (itemTouchHelper == null) {
            itemTouchHelper = new ItemTouchHelper(callback);
        }
        return itemTouchHelper;
    }

    public void becomeLoadMore() {//3.0之后,添加成能够上拉加载的
//        addLoadMoreModule(this);
    }

    public void becomeDraggable() {//3.0之后,添加成为拖动和侧滑删除
        if (getData() instanceof ObservableList) {
            ((ObservableList) getData()).removeOnListChangedCallback(bindingListChangedCallBack);
        }
//        CSDragAndSwipeCallBack back  = new CSDragAndSwipeCallBack(getDraggableModule());
//        getDraggableModule().itemTouchHelperCallback = back;
//        addDraggableModule(this);
    }

    public void becomeUpFetch() {//3.0之后,添加变成向上加载
//        addUpFetchModule(this);
    }


//    /**
//     * Expand an expandable item with animation.
//     *
//     * @param position position of the item, which includes the header layout count.
//     * @return the number of items that have been added.
//     */
//    public int expand(@IntRange(from = 0) int position) {
//        return expand(position, true, true);
//    }


    public void expand(int position) {
        T node = getData().get(position);
        if (node instanceof BaseExpandNode && !((BaseExpandNode) node).isExpanded()) {
            BaseExpandNode nodeGp = (BaseExpandNode) node;
            nodeGp.setExpanded(true);
            if (nodeGp.getChildNode() == null || nodeGp.getChildNode().isEmpty()) {
                notifyDataSetChanged();
            } else {
                getData().addAll(position + 1, (Collection<? extends T>) nodeGp.getChildNode());
                notifyItemChanged(position);
//                notifyDataSetChanged();
            }
        }
    }


    public void collapse(int position){
        T node = getData().get(position);
        if (node instanceof BaseExpandNode && ((BaseExpandNode) node).isExpanded()) {
            BaseExpandNode nodeGp = (BaseExpandNode) node;
            nodeGp.setExpanded(false);
            if (nodeGp.getChildNode() == null || nodeGp.getChildNode().isEmpty()) {
                notifyDataSetChanged();
            } else {
                getData().removeAll( nodeGp.getChildNode());
                notifyItemChanged(position);
                notifyItemRangeRemoved(position+1,nodeGp.getChildNode().size());
            }
        }
    }


}
