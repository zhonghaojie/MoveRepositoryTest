package com.zhj.treeview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson

import com.zhj.treeview.resolution1.bean.Branch
import com.zhj.treeview.resolution1.bean.Leaf
import com.zhj.treeview.resolution1.bean.Root
import com.zhj.treeview.resolution1.bean.TreeNode
import com.zhj.treeview.resolution1.TreeViewAdapter
import com.zhj.treeview.resolution1.TreeViewBinder
import com.zhj.treeview.resolution1.viewbinder.BaseViewHolder
import com.zhj.treeview.resolution1.viewbinder.BranchViewBinder
import com.zhj.treeview.resolution1.viewbinder.LeafViewBinder
import com.zhj.treeview.resolution1.viewbinder.RootViewBinder
import kotlinx.android.synthetic.main.activity_main.*

import java.util.ArrayList
import java.util.Arrays

class MainActivity : AppCompatActivity() {
    private var adapter: TreeViewAdapter<Root>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initList()
        btn_shownodes.setOnClickListener {
            adapter?.printShowNodes()
        }
    }

    private fun initList() {
        val list = ArrayList<TreeNode<Root>>()

        //根节点
        val item1 = TreeNode(Root("三国演义"))
        //一级节点
        val childs1 = ArrayList<TreeNode<*>>()
        var branch1 = TreeNode(Branch("三国演义第一回"))
        var branch2 = TreeNode(Branch("三国演义第二回"))
        var branch3 = TreeNode(Branch("三国演义第三回"))
        childs1.add(branch1)
        childs1.add(branch2)
        childs1.add(branch3)

        //二级节点
        val leaf1 = TreeNode(Leaf("三国第一回leaf1"))
        val leaf2 = TreeNode(Leaf("三国第一回leaf2"))
        val leaf3 = TreeNode(Leaf("三国第一回leaf3"))
        val leafs = ArrayList<TreeNode<*>>()
        leafs.add(leaf1)
        leafs.add(leaf2)
        leafs.add(leaf3)
        branch1.childNodes = leafs

        //二级节点
        val leaf21 = TreeNode(Leaf("三国第二回leaf1"))
        val leaf22 = TreeNode(Leaf("三国第二回leaf2"))
        val leaf23 = TreeNode(Leaf("三国第二回leaf3"))
        val leaf2s = ArrayList<TreeNode<*>>()
        leaf2s.add(leaf21)
        leaf2s.add(leaf22)
        leaf2s.add(leaf23)
        branch2.childNodes = leaf2s

        item1.childNodes = childs1

        val item2 = TreeNode(Root("西游记"))
        val childs2 = ArrayList<TreeNode<*>>()
        branch1 = TreeNode(Branch("西游记第一回"))
        branch2 = TreeNode(Branch("西游记第二回"))
        branch3 = TreeNode(Branch("西游记第三回"))
        childs2.add(branch1)
        childs2.add(branch2)
        childs2.add(branch3)

        val leafs2 = ArrayList<TreeNode<*>>()
        val leaf31 = TreeNode(Leaf("西游第一回leaf1"))
        val leaf32 = TreeNode(Leaf("西游第一回leaf2"))
        val leaf33 = TreeNode(Leaf("西游第一回leaf3"))
        leafs2.add(leaf31)
        leafs2.add(leaf32)
        leafs2.add(leaf33)
        branch1.childNodes = leafs2

        item2.childNodes = childs2
        list.add(item1)
        list.add(item2)

        adapter = TreeViewAdapter(list, Arrays.asList<TreeViewBinder<*>>(RootViewBinder(), BranchViewBinder(), LeafViewBinder()))
        adapter!!.setListener(object : TreeViewListener {
            override fun onItemClick(holder: BaseViewHolder, node: TreeNode<*>, position: Int) {
                Log.e("Tree","holder.layoutPosition   "+holder.layoutPosition)
                if (node.value is Root) {
                    Toast.makeText(this@MainActivity, "level:" + node.level + (node.value as Root).name, Toast.LENGTH_SHORT).show()
                } else if (node.value is Branch) {
                    Toast.makeText(this@MainActivity, "level:" + node.level + (node.value as Branch).name, Toast.LENGTH_SHORT).show()
                } else if (node.value is Leaf) {
                    Toast.makeText(this@MainActivity, "level:" + node.level + (node.value as Leaf).name, Toast.LENGTH_SHORT).show()
                }

            }

            override fun afterToggle(view: View, isOpen: Boolean) {
                view.rotation = (if (isOpen) 90 else 0).toFloat()
            }
        })
        val recyclerView = findViewById<RecyclerView>(R.id.recycle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter?.expandNode(0)
    }
}
