package com.zhj.treeview.resolution1.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Description:
 * Created by zhonghaojie on 2018/9/11.
 */

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun <T : View> findViewById(id: Int): T {
        return itemView.findViewById(id)
    }
}
