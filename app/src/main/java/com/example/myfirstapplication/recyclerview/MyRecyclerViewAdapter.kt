package com.example.myfirstapplication.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.databinding.ItemRecyclerViewBinding


class MyRecyclerViewAdapter(private  val onItemClick: (Int, String) -> Unit) :
    ListAdapter<String, MyRecyclerViewAdapter.ViewHolder>(DiffCallback()) {
    val sTAG = "MyRecyclerViewAdapter"
    // 创建 ViewHolder（仅当无复用 View 时调用）
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        Log.d(sTAG, "onCreateViewHolder")
        val binding = ItemRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    // 绑定数据（滑动/刷新时复用 ViewHolder，仅更新数据）
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        Log.d(sTAG, "onBindViewHolder")
        holder.bind(currentList[position])
    }

    // 提供外部修改数据的方法
    fun updateData(newList: List<String>) {
        submitList(ArrayList(newList)) // ListAdapter 内置方法，自动触发 DiffUtil 刷新
    }

    // 自定义 ViewHolder，使用 ViewBinding 避免 findViewById
    inner class ViewHolder(val binding : ItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // 绑定 Item 点击事件
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val content = currentList[position]
                    onItemClick(position, content)
                }
            }
        }

        // 绑定数据到 View
        fun bind(data: String) {
            // 直接通过 binding 访问布局中的 TextView
            binding.tvContent.text = data
        }
    }

    // DiffUtil 差异化刷新回调（对比新旧数据）
    class DiffCallback : DiffUtil.ItemCallback<String>() {
        // 判断是否是同一个 Item（实际项目建议用唯一 ID 判断）
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            Log.d("MyRecyclerViewAdapter", "areItemsTheSame")
            return oldItem == newItem
        }

        // 判断 Item 内容是否变化
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            Log.d("MyRecyclerViewAdapter", "areContentsTheSame")
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: String, newItem: String): Any? {
            Log.d("MyRecyclerViewAdapter", "getChangePayload")
            return super.getChangePayload(oldItem, newItem)
        }
    }
}