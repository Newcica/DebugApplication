package com.example.myfirstapplication.wechat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirstapplication.databinding.ActivityMomentsBinding
import java.util.*

class MomentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMomentsBinding
    private lateinit var adapter: MomentAdapter
    private val momentsList = mutableListOf<Moment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMomentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initRecyclerView()
        loadSampleData()
        setupFab()
    }

    private fun initToolbar() {
        binding.toolbar.title = "朋友圈"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {
        adapter = MomentAdapter { position, momentId ->
            Toast.makeText(this, "点击了朋友圈: $momentId", Toast.LENGTH_SHORT).show()
        }

        binding.rvMoments.apply {
            layoutManager = LinearLayoutManager(this@MomentsActivity)
            adapter = this@MomentsActivity.adapter
            setHasFixedSize(true)
        }
    }

    private fun loadSampleData() {
        // 创建一些示例数据
        momentsList.clear()

        // 示例朋友圈1
        val comments1 = listOf(
            Comment("1", "张三", "看起来很棒！"),
            Comment("2", "李四", "在哪里拍的？")
        )
        val likes1 = listOf("王五", "赵六", "钱七")

        val moment1 = Moment(
            id = "1",
            userName = "张三",
            userAvatar = "avatar1",
            content = "今天天气真好，出去走走，心情舒畅！今天天气真好，出去走走，心情舒畅！",
            time = "2小时前",
            likes = likes1,
            comments = comments1,
            imageUrls = listOf("image1", "image2", "image3")
        )

        // 示例朋友圈2
        val comments2 = listOf(
            Comment("3", "王五", "不错的选择！")
        )
        val likes2 = listOf("张三", "李四")

        val moment2 = Moment(
            id = "2",
            userName = "李四",
            userAvatar = "avatar2",
            content = "新买的书到了，开始充实自己！",
            time = "3小时前",
            likes = likes2,
            comments = comments2,
            imageUrls = listOf("image4")
        )

        // 示例朋友圈3
        val moment3 = Moment(
            id = "3",
            userName = "王五",
            userAvatar = "avatar3",
            content = "周末和朋友聚餐，好久不见啦！",
            time = "昨天 18:30",
            likes = emptyList(),
            comments = emptyList(),
            imageUrls = listOf("image5", "image6")
        )

        // 示例朋友圈4
        val comments4 = listOf(
            Comment("4", "张三", "哈哈，不错"),
            Comment("5", "李四", "我也想吃"),
            Comment("6", "赵六", "看着就香")
        )
        val likes4 = listOf("张三", "王五", "孙七", "周八")

        val moment4 = Moment(
            id = "4",
            userName = "赵六",
            userAvatar = "avatar4",
            content = "尝试做了新菜谱，味道还不错！",
            time = "昨天 12:15",
            likes = likes4,
            comments = comments4,
            imageUrls = null // 没有图片
        )

        momentsList.addAll(listOf(moment1, moment2, moment3, moment4))
        adapter.submitList(momentsList.toList())
    }

    private fun setupFab() {
        binding.fabAddMoment.setOnClickListener {
            // 这里可以添加发布新朋友圈的功能
            Toast.makeText(this, "发布新朋友圈", Toast.LENGTH_SHORT).show()
            
            // 添加一个新的示例朋友圈
            val newMoment = Moment(
                id = (momentsList.size + 1).toString(),
                userName = "我",
                userAvatar = "my_avatar",
                content = "刚刚发布了新动态",
                time = "刚刚",
                likes = emptyList(),
                comments = emptyList(),
                imageUrls = if (Random().nextBoolean()) listOf("new_image") else null
            )
            
            momentsList.add(0, newMoment)
            adapter.submitList(momentsList.toList())
            binding.rvMoments.smoothScrollToPosition(0)
        }
    }
}