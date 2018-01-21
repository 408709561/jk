package com.github.wxiaoqi.security.common.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ace on 2017/6/12.
 */
public class TreeNodeVO {
    protected int id;
    protected int parentId;

    public List<TreeNodeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeVO> children) {
        this.children = children;
    }

    List<TreeNodeVO> children = new ArrayList<TreeNodeVO>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void add(TreeNodeVO node){
        children.add(node);
    }
}
