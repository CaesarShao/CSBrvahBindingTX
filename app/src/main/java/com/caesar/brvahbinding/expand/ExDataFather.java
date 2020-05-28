package com.caesar.brvahbinding.expand;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExDataFather extends BaseExpandNode implements MultiItemEntity {

    private String name;
    private String title;
    private int imgRes;
    private List<BaseNode> childNode;

    public ExDataFather(String name, String title, int imgRes,List<BaseNode> childNode) {
        this.name = name;
        this.title = title;
        this.imgRes = imgRes;
        this.childNode = childNode;
        setExpanded(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

//    @Override
//    public int getLevel() {
//        return 1;
//    }

    @Override
    public int getItemType() {
        return 1;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
