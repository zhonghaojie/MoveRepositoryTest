package com.zhj.treeview.resolution1;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhj.treeview.TreeViewListener;
import com.zhj.treeview.resolution1.bean.Branch;
import com.zhj.treeview.resolution1.bean.LayoutItem;
import com.zhj.treeview.resolution1.bean.Leaf;
import com.zhj.treeview.resolution1.bean.Root;
import com.zhj.treeview.resolution1.bean.TreeNode;
import com.zhj.treeview.resolution1.viewbinder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by zhonghaojie on 2018/9/11.
 */

public class TreeViewAdapter<T extends LayoutItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<? extends TreeViewBinder> viewBinders = new ArrayList<>();
    private List<TreeNode<T>> showNodes;
    private TreeViewListener listener;

    public TreeViewAdapter(List<TreeNode<T>> nodes, List<? extends TreeViewBinder> viewBinders) {
        this.viewBinders = viewBinders;
        showNodes = new ArrayList<>();
        showNodes.addAll(nodes);
    }

    /**
     * @param parent
     * @param viewType 在这里是每个item的布局id
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        RecyclerView.ViewHolder holder = null;
        //由每一级的viewBinder独自绘制布局
        for (TreeViewBinder viewBinder : viewBinders) {
            if (viewBinder.getLayoutId() == viewType) {
                holder = viewBinder.creatViewHolder(view);
            }
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return showNodes.get(position).getValue().getLayoutId();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final TreeNode node = showNodes.get(holder.getLayoutPosition());


        for (TreeViewBinder viewBinder : viewBinders) {
            if (viewBinder.getLayoutId() == node.getValue().getLayoutId()) {
                viewBinder.bindViewHolder(holder, position, node);
            }
        }
        ((BaseViewHolder) holder).findViewById(node.getValue().getClickId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick((BaseViewHolder) holder, node, holder.getLayoutPosition());
                }
            }
        });
        ((BaseViewHolder) holder).findViewById(node.getValue().getToggleId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle((BaseViewHolder) holder);
                if (listener != null) {
                    listener.afterToggle(v, node.isExpand());
                }
            }
        });
    }

    private void toggle(BaseViewHolder holder) {
        TreeNode node = showNodes.get(holder.getLayoutPosition());
        if(node.isLeaf()){
            return;
        }
        if (!node.isExpand()) {
            node.open();
            notifyItemRangeInserted(holder.getLayoutPosition() + 1, expandNode(node));
        } else {
            node.close();
            notifyItemRangeRemoved(holder.getLayoutPosition() + 1, closeNode(node));
        }
    }

    /**
     * 打开一级菜单的某个节点
     * @param index
     */
    public void expandNode(int index){
        TreeNode node = showNodes.get(index);
        if(node.isLeaf()){
            return;
        }
        List<TreeNode> childs = node.getChildNodes();
        node.open();
        int count = 1;
        for (TreeNode child : childs) {
            showNodes.add(index + count, child);
            count++;
            if (!child.isLeaf() && child.isExpand()) {
                count += expandNode(child);
            }
        }
    }
    private int expandNode(TreeNode node) {
        List<TreeNode> childs = node.getChildNodes();
        int startPosition = showNodes.indexOf(node) + 1;
        int count = 0;
        Log.e("Tree", "展开第" + showNodes.indexOf(node) + "个");
        for (TreeNode child : childs) {
            showNodes.add(startPosition + count, child);
            count++;
            if (!child.isLeaf() && child.isExpand()) {
                count += expandNode(child);
            }
        }
        return count;
    }

    private int closeNode(TreeNode node) {
        List<TreeNode> childs = node.getChildNodes();
        Log.e("Tree", "关闭第" + showNodes.indexOf(node) + "个");
        int count = 0;
        for (TreeNode child : childs) {
            if (!child.isLeaf() && child.isExpand()) {
                count += closeNode(child);
            }
            showNodes.remove(child);
            count++;
        }
        return count;
    }


    private void printNodeInfo(TreeNode node, String other) {
        if (node.getValue() instanceof Root) {
            Log.e("Tree", other + ((Root) node.getValue()).getName());
        } else if (node.getValue() instanceof Branch) {
            Log.e("Tree", other + ((Branch) node.getValue()).getName());
        } else if (node.getValue() instanceof Leaf) {
            Log.e("Tree", other + ((Leaf) node.getValue()).getName());
        }

    }


    @Override
    public int getItemCount() {
        return showNodes.size();
    }


    public void setListener(TreeViewListener listener) {
        this.listener = listener;
    }

    public List<TreeNode<T>> getShowNodes() {
        return showNodes;
    }

    public void printShowNodes() {
        Log.e("Tree", "----------------\n");
        for (TreeNode showNode : showNodes) {
            printNodeInfo(showNode, "");
        }
        Log.e("Tree", "----------------\n");
    }

}
