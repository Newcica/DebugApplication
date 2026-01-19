package com.example.myfirstapplication.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirstapplication.databinding.ActivityRecyclerViewBinding
import java.util.ArrayList

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecyclerViewBinding
    private val dataList = mutableListOf<String>()
    private lateinit var adapter : MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.test_recyclerview)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        initData()
        initRecyclerView()
        initClickEvent()

    }

    private fun initData() {
        for (i in 0..100) {
            dataList.add("item $i")
        }
//        adapter.submitList(dataList)
    }

    private fun initRecyclerView() {
        // 创建适配器
        adapter = MyRecyclerViewAdapter { position, item ->
            // Item 点击事件：修改内容并局部刷新
            dataList[position] = "已点击 - 第 ${position + 1} 条"
            adapter.updateData(dataList) // 差异化刷新
            Toast.makeText(this, "点击了：$item", Toast.LENGTH_SHORT).show()
        }

        // 配置 RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity) // 线性布局
            adapter = this@RecyclerViewActivity.adapter // 设置适配器
            // 添加自定义分割线（灰色，高度 1dp）
//            addItemDecoration(DividerItemDecoration(this@RecyclerViewActivity,
//                R.color.graty, 1f))
            // 优化：设置固定高度，减少测量次数
            setHasFixedSize(true)
        }

        // 初始加载数据
        adapter.updateData(dataList)
    }

    private fun initClickEvent() {
        binding.btnAdd.setOnClickListener {
            val newData = "新增数据 - ${System.currentTimeMillis() % 1000}"
            dataList.add(0, newData) // 插入到第一条
//            dataList.add("item ${dataList.size + 1}")
            adapter.updateData(dataList)
            binding.recyclerView.scrollToPosition(0) // 滚动到顶部
        }
        binding.btnRemove.setOnClickListener {
            if (dataList.isEmpty()) {
                Toast.makeText(this, "数据为空！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val size = dataList.size
            dataList.removeAt(size - 1) // 删除最后一条
            adapter.updateData(dataList) // 局部刷新
//            if (dataList.isNotEmpty()) {
//                dataList.removeLast()
//                adapter.updateData(dataList)
//            }
        }
    }
}