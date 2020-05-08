package com.meazza.instagram.ui.direct_message.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.R
import com.meazza.instagram.data.model.DirectMessage
import com.meazza.instagram.databinding.LayoutMessageReceivedBinding
import com.meazza.instagram.databinding.LayoutMessageSentBinding


class ChatAdapter(private val userId: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val MESSAGE_SENT = 101
        private const val MESSAGE_RECEIVED = 102
    }

    private var messagesList = mutableListOf<DirectMessage>()

    fun setListData(messages: MutableList<DirectMessage>) {
        messagesList = messages
    }

    override fun getItemViewType(position: Int) =
        if (messagesList[position].authorId == userId) MESSAGE_SENT else MESSAGE_RECEIVED

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
                (holder as HolderSentMessage).itemBinding.directMessage = messagesList[position]
            }
            MESSAGE_RECEIVED -> {
                (holder as HolderReceivedMessage).itemBinding.directMessage = messagesList[position]
            }
        }
    }

    override fun getItemCount(): Int = if (messagesList.size > 0) messagesList.size else 0

    inner class HolderSentMessage(val itemBinding: LayoutMessageSentBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    inner class HolderReceivedMessage(val itemBinding: LayoutMessageReceivedBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
