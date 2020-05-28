package com.caesar.brvahbinding.expand;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExDataChild extends BaseNode implements MultiItemEntity {
    private String name;
    private int imgRes;

    public ExDataChild(String name, int imgRes) {
        this.name = name;
        this.imgRes = imgRes;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    @Override
    public int getItemType() {
        return 2;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
