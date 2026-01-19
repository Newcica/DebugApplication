package com.example.myfirstapplication.wechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.databinding.ItemContactBinding
import com.example.myfirstapplication.databinding.ItemSectionHeaderBinding

class ContactAdapter(
    private val onContactClick: (Contact) -> Unit
) : ListAdapter<ContactItem, RecyclerView.ViewHolder>(ContactDiffCallback) {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_CONTACT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContactItem.Header -> TYPE_HEADER
            is ContactItem.ContactItemData -> TYPE_CONTACT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val binding = ItemSectionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }
            TYPE_CONTACT -> {
                val binding = ItemContactBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ContactViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = getItem(position) as ContactItem.Header
                holder.bind(item.title)
            }
            is ContactViewHolder -> {
                val item = getItem(position) as ContactItem.ContactItemData
                holder.bind(item.contact, onContactClick)
            }
        }
    }

    class HeaderViewHolder(
        private val binding: ItemSectionHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.tvSectionHeader.text = title
        }
    }

    class ContactViewHolder(
        private val binding: ItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact, onContactClick: (Contact) -> Unit) {
            binding.tvName.text = contact.name
            
            // 显示个性签名（如果存在）
            contact.signature?.let { signature ->
                binding.tvSignature.text = signature
                binding.tvSignature.visibility = View.VISIBLE
            } ?: run {
                binding.tvSignature.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onContactClick(contact)
            }
        }
    }

    object ContactDiffCallback : DiffUtil.ItemCallback<ContactItem>() {
        override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
            return when {
                oldItem is ContactItem.Header && newItem is ContactItem.Header -> {
                    oldItem.title == newItem.title
                }
                oldItem is ContactItem.ContactItemData && newItem is ContactItem.ContactItemData -> {
                    oldItem.contact.id == newItem.contact.id
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
            return oldItem == newItem
        }
    }
}

sealed class ContactItem {
    data class Header(val title: String) : ContactItem()
    data class ContactItemData(val contact: Contact) : ContactItem()
}