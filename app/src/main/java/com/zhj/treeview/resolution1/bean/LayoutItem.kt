package com.zhj.treeview.resolution1.bean

/**
 *
 * Description:具体的业务对象要实现该接口
 * Created by zhonghaojie on 2018/9/11.
 */
interface LayoutItem {
    fun getLayoutId(): Int
    fun getClickId(): Int
    fun getToggleId(): Int
}