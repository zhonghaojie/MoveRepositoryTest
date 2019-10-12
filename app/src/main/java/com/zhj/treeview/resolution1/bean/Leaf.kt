package com.zhj.treeview.resolution1.bean

import com.zhj.treeview.R

/**
 * Description:
 * Created by zhonghaojie on 2018/9/11.
 */
class Leaf(var name:String): LayoutItem {
    override fun getLayoutId(): Int = R.layout.item_leaf

    override fun getClickId(): Int = R.id.tv_leaf_content

    override fun getToggleId(): Int =R.id.iv_leaf_arrow
}