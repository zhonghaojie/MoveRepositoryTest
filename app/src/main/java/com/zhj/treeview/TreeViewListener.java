package com.zhj.treeview;

import android.view.View;

import com.zhj.treeview.resolution1.bean.TreeNode;
import com.zhj.treeview.resolution1.viewbinder.BaseViewHolder;

/**
 * Description:
 * Created by zhonghaojie on 2018/9/12.
 */

public interface TreeViewListener {
    void onItemClick(BaseViewHolder holder, TreeNode node, int position);



    void afterToggle(View view, boolean isOpen);
}
