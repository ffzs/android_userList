package com.ffzs.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ffzs.userlist.databinding.UserListBinding
import com.ffzs.userlist.table.User

/**
 * @author: ffzs
 * @Date: 2020/9/11 下午7:11
 */
class UserRecyclerViewAdapter(private val clickListener: (User) -> Unit) : RecyclerView.Adapter<UserViewHolder>() {

    private val userList = ArrayList<User>()
    private var preHolder:UserViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: UserListBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.user_list, parent, false)
        return UserViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setList(users: List<User>) {
        userList.clear()
        userList.addAll(users)
    }
}

