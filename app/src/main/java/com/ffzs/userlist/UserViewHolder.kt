package com.ffzs.userlist

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ffzs.userlist.databinding.UserListBinding
import com.ffzs.userlist.table.User
import kotlinx.android.synthetic.main.user_list.*

/**
 * @author: ffzs
 * @Date: 2020/9/11 下午7:12
 */
class UserViewHolder (val binding: UserListBinding, val context: Context): RecyclerView.ViewHolder(binding.root){

    fun bind(user: User, clickListener:(User)->Unit){
        binding.nameTextView.text = user.name
        binding.emailTextView.text = user.email
        binding.cardView.background = ContextCompat.getDrawable(context, R.drawable.default_card_bg)
        binding.listItemLayout.setOnClickListener{
            binding.cardView.background = ContextCompat.getDrawable(context, R.drawable.selected_card_bg)
            clickListener(user)
        }
    }
}