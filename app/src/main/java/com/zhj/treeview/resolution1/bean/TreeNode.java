package com.zhj.treeview.resolution1.bean;

import android.support.annotation.LayoutRes;
import android.text.Layout;

import com.zhj.treeview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by zhonghaojie on 2018/9/11.
 */

public class TreeNode<T extends LayoutItem> implements Cloneable {

    private TreeNode parentNode;//父节点
    private List<TreeNode> childNodes = new ArrayList<>();//子节点
    private T value;//节点数据
    private boolean isExpand;//是否展开
    private int level = 0;//该节点层级
    public LayoutItem layoutItem;
    public TreeNode(T value) {
        this.value = value;
    }

    public boolean open() {
        isExpand = true;
        return isExpand;
    }

    public boolean close() {
        isExpand = false;
        return isExpand;
    }

    public boolean isRoot() {
        return parentNode == null;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public boolean toggle() {
        isExpand = !isExpand;
        return isExpand;
    }


    /**
     * 是否是最后的叶节点
     * @return
     */
    public boolean isLeaf() {
        return childNodes.isEmpty();
    }

    public TreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public List<TreeNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<TreeNode> childNodes) {
        for (TreeNode childNode : childNodes) {
            childNode.setParentNode(this);
        }
        initLevel(childNodes,this);

        this.childNodes = childNodes;
    }

    private void initLevel(List<TreeNode> list,TreeNode parent) {
        for (TreeNode treeNode : list) {
            if (parent != null) {
                treeNode.setLevel(parent.getLevel() + 1);
            }
            if (!treeNode.getChildNodes().isEmpty()) {
                initLevel(treeNode.getChildNodes(),treeNode);
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        TreeNode treeNode = (TreeNode) obj;
        return value.equals(treeNode.getValue())
                && ((parentNode != null && parentNode.equals(treeNode.getParentNode())) || (parentNode == null && treeNode.getParentNode() == null))
                && childNodes.equals(treeNode.getChildNodes())
                && isExpand == treeNode.isExpand();
    }

    @Override
    protected TreeNode<T> clone() throws CloneNotSupportedException {
        TreeNode<T> clone = new TreeNode<>(this.value);
        clone.level = this.level;
        return clone;
    }
}
