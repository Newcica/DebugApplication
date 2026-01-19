package com.example.myfirstapplication.wechat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.R
import com.example.myfirstapplication.databinding.ItemMomentBinding

class MomentAdapter(private val onItemAction: (Int, String) -> Unit) :
    ListAdapter<Moment, MomentAdapter.MomentViewHolder>(MomentDiffCallback()) {

    companion object {
        private const val TAG = "MomentAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val binding = ItemMomentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MomentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        holder.bind(getItem(position))
    }

    inner class MomentViewHolder(private val binding: ItemMomentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(moment: Moment) {
            binding.tvUserName.text = moment.userName
            binding.tvTime.text = moment.time
            binding.tvContent.text = moment.content

            // 处理图片网格
            setupImageGrid(moment)

            // 处理点赞和评论
            setupLikesAndComments(moment)

            // 设置点击事件
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemAction(position, moment.id)
                }
            }
        }

        private fun setupImageGrid(moment: Moment) {
            val gridLayout = binding.gridImages
            gridLayout.removeAllViews()
            
            moment.imageUrls?.let { imageList ->
                if (imageList.isNotEmpty()) {
                    gridLayout.visibility = View.VISIBLE
                    val imageSize = 100 // 图片大小（dp）
                    val margin = 4 // 间距（dp）
                    
                    // 转换为像素
                    val pixelSize = (imageSize * binding.root.context.resources.displayMetrics.density).toInt()
                    val pixelMargin = (margin * binding.root.context.resources.displayMetrics.density).toInt()
                    
                    for ((index, imageUrl) in imageList.withIndex()) {
                        val imageView = ImageView(binding.root.context)
                        imageView.setImageResource(R.drawable.ic_launcher_foreground) // 占位图
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                        
                        val layoutParams = ViewGroup.LayoutParams(pixelSize, pixelSize)
                        gridLayout.addView(imageView, layoutParams)
                        
                        // 设置间距
                        val marginLayoutParams = imageView.layoutParams as ViewGroup.MarginLayoutParams
                        marginLayoutParams.setMargins(
                            if (index % 3 == 0) 0 else pixelMargin,
                            if (index < 3) 0 else pixelMargin,
                            0, 0
                        )
                        imageView.layoutParams = marginLayoutParams
                    }
                } else {
                    gridLayout.visibility = View.GONE
                }
            } ?: run {
                gridLayout.visibility = View.GONE
            }
        }

        private fun setupLikesAndComments(moment: Moment) {
            val likesLayout = binding.layoutLikes
            val commentsLayout = binding.layoutComments
            val likesTextView = binding.tvLikes
            val commentsTextView = binding.tvComments

            // 处理点赞
            if (moment.likes.isNotEmpty()) {
                likesLayout.visibility = View.VISIBLE
                likesTextView.text = "👍 ${moment.likes.joinToString(", ") { it }}"
            } else {
                likesLayout.visibility = View.GONE
            }

            // 处理评论
            if (moment.comments.isNotEmpty()) {
                commentsLayout.visibility = View.VISIBLE
                val commentTexts = moment.comments.map { "${it.userName}: ${it.content}" }
                commentsTextView.text = commentTexts.joinToString("\n")
            } else {
                commentsLayout.visibility = View.GONE
            }
        }
    }

    class MomentDiffCallback : DiffUtil.ItemCallback<Moment>() {
        override fun areItemsTheSame(oldItem: Moment, newItem: Moment): Boolean {
            Log.d(TAG, "areItemsTheSame")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Moment, newItem: Moment): Boolean {
            Log.d(TAG, "areContentsTheSame")
            return oldItem == newItem
        }
    }
}