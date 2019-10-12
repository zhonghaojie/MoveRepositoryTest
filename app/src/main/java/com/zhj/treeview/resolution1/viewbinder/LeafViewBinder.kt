package com.zhj.treeview.resolution1.viewbinder

import android.util.Log
import android.view.View
import android.widget.TextView

import com.zhj.treeview.R
import com.zhj.treeview.resolution1.TreeViewBinder
import com.zhj.treeview.resolution1.bean.Leaf
import com.zhj.treeview.resolution1.bean.Root
import com.zhj.treeview.resolution1.bean.TreeNode


/**
 * Description:
 * Created by zhonghaojie on 2018/9/11.
 */

class LeafViewBinder : TreeViewBinder<LeafViewBinder.ViewHolder>() {

    override fun getLayoutId(): Int {
        return R.layout.item_leaf
    }

    override fun getToggleId(): Int {
        return R.id.iv_leaf_arrow
    }


    override fun getClickId(): Int {
        return R.id.tv_leaf_content
    }

    override fun creatViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun bindViewHolder(holder: ViewHolder, position: Int, treeNode: TreeNode<*>) {
        super.bindViewHolder(holder, position, treeNode)
        (holder.findViewById<View>(R.id.tv_leaf_content) as TextView).text = (treeNode.value as Leaf).name
        holder.findViewById<View>(R.id.iv_leaf_arrow).rotation = (if (treeNode.isExpand) 90 else 0).toFloat()
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView)
}
