# TreeView2
树形控件

要解决的问题：

1、如何加载不同节点布局？

2、如何获取点击的item

3、如何伸缩

针对问题1：通过RecyclerViewAdapter的getItemViewType来绘制不同节点的布局

针对问题2：通过holder.getLayoutPosition来获取布局的index（getLayoutPosition拿到的index，就是 某个item，你眼睛所看到的它的index）,然后通过index去获取item数据

针对问题3：点击箭头（控制展开和关闭的控件），展开时，将childs添加到showNodes中去，再notifyItemRangeInserted
          关闭时，将该node从showNodes中移除，再notifyItemRangeRemoved
          
          
定义一个TreeNode：

这个TreeNode需要有：

1、提供节点布局，点击控件，伸缩控件的能力

2、childNodes

3、isExpand是否展开

4、T nodeData节点数据

5、level 节点级别（几层节点）

6、parentNode
```java
interface LayoutItem {
    int getLayoutId()
    int getClickId()
    int getToggleId()
}
//初始化的时候要根据数据源设置好每个node的parentNode、level、value、childNodes
public class TreeNode<T extends LayoutItem> implements Cloneable {
    private TreeNode parentNode;//父节点
    private List<TreeNode> childNodes = new ArrayList<>();//子节点
    private T value;//节点数据
    private boolean isExpand;//是否展开
    private int level = 0;//该节点层级
}
```

节点有了，接下来就是要写adapter了

主要就是onCreateViewHolder、getItemViewType、onBindViewHolder这三个方法

1、getItemViewType。由于我们的TreeNode能够对外提供节点布局，也就是这个`private T value`，所以说，可以根据getLayoutId来确定有哪几种ViewType

2、onCreateViewHolder中，我们需要加载每种node对应的布局，根据getItemViewType来加载对应布局

3、onBindViewHolder。根据不同的节点类型给相应控件赋值、设置监听

这里为了扩展性，我加入了TreeViewBinder这个类，不管节点布局怎么变化，我都不需要改动adapter，只需要向adapter中传入不同的viewBinders，由viewBinders去onCreateViewHolder和onBindViewHolder。

```java
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
```

```java
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //因为getItemViewType返回的就是布局id，所以这里直接用viewType来创建View
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final TreeNode node = showNodes.get(holder.getLayoutPosition());


        for (TreeViewBinder viewBinder : viewBinders) {
            if (viewBinder.getLayoutId() == node.getValue().getLayoutId()) {
                viewBinder.bindViewHolder(holder, position, node);
            }
        }
    }
```

以上，就完成了数据的加载，当然，事先得写好LayoutItem、TreeViewBinder的实现类
扩展和收缩就是简单的添加和移除Node，当然这里可能有个性需求，比如展开是否要递归展开所有子项，收缩是否要保持子节点的展开状态等等






