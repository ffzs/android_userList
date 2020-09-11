package com.ffzs.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ffzs.userlist.table.UserRepository

/**
 * @author: ffzs
 * @Date: 2020/9/11 下午6:43
 */
class UserViewModelFactory (private val repository: UserRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel类型不匹配")
    }
}