package com.zhj.treeview.resolution1.bean

import com.zhj.treeview.R

/**
 * Description:
 * Created by zhonghaojie on 2018/9/11.
 */
class Branch(var name:String): LayoutItem {
    override fun getLayoutId(): Int = R.layout.item_branch

    override fun getClickId(): Int = R.id.tv_branch_content

    override fun getToggleId(): Int =R.id.iv_branch_arrow
}