package com.meazza.instagram.ui.direct.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.R
import com.meazza.instagram.databinding.LayoutMessageReceivedBinding
import com.meazza.instagram.databinding.LayoutMessageSentBinding
import com.meazza.instagram.vo.DirectMessage


class ChatAdapter(private val userId: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val MESSAGE_SENT = 101
        private const val MESSAGE_RECEIVED = 102
    }

    private var dataList = mutableListOf<DirectMessage>()

    fun setListData(data: MutableList<DirectMessage>) {
        dataList = data
    }

    override fun getItemViewType(position: Int) =
        if (dataList[position].authorId == userId) MESSAGE_SENT else MESSAGE_RECEIVED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            MESSAGE_SENT -> {
                HolderSentMessage(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.layout_message_sent,
                        parent,
                        false
                    )
                )
            }
            else -> {
                HolderReceivedMessage(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.layout_message_received,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            MESSAGE_SENT -> {
                (holder as HolderSentMessage).itemBinding.directMessage =
                    dataList[position]
            }
            MESSAGE_RECEIVED -> {
                (holder as HolderReceivedMessage).itemBinding.directMessage =
                    dataList[position]
            }
        }
    }

    override fun getItemCount(): Int = if (dataList.size > 0) dataList.size else 0

    inner class HolderSentMessage(val itemBinding: LayoutMessageSentBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    inner class HolderReceivedMessage(val itemBinding: LayoutMessageReceivedBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
