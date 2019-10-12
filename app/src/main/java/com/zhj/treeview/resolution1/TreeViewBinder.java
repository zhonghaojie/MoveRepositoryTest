package com.zhj.treeview.resolution1;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhj.treeview.resolution1.bean.LayoutItem;
import com.zhj.treeview.resolution1.bean.TreeNode;

/**
 * Description:
 * Created by zhonghaojie on 2018/9/11.
 */
public abstract class TreeViewBinder<VH extends RecyclerView.ViewHolder> implements LayoutItem {
    /**
     * 创建视图
     * @param itemView
     * @return
     */
    public abstract VH creatViewHolder(View itemView);

    /**
     * 绑定视图
     * @param holder
     * @param position
     * @param treeNode
     */
    public  void bindViewHolder(VH holder, int position, TreeNode treeNode){
        holder.itemView.setPadding(40 * treeNode.getLevel(), 0, 0, 0);
    }
}
