package com.ffzs.userlist


import android.util.Patterns
import androidx.core.content.ContextCompat
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ffzs.userlist.table.User
import com.ffzs.userlist.table.UserRepository
import kotlinx.coroutines.launch


/**
 * @author: ffzs
 * @Date: 2020/9/11 下午4:46
 */
class UserViewModel (private val repository: UserRepository): ViewModel(), Observable {


    val users = repository.users
    private var isUpdateOrDelete = false
    private lateinit var selectedUser: User

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()
    @Bindable
    val btnSave = MutableLiveData<String>()
    @Bindable
    val btnDel = MutableLiveData<String>()

    init {
        btnSave.value = "保存"
        btnDel.value = "清空"
    }

    private val msg = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = msg

    fun saveOrUpdate () {
        if (inputName.value == null) {
            msg.value = Event("请输入用户名")
        } else if (inputEmail.value == null) {
            msg.value = Event("请输入用户邮箱")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            msg.value = Event("请确定输入的是邮箱")
        } else {
            if (isUpdateOrDelete) {
                selectedUser.name = inputName.value!!
                selectedUser.email = inputEmail.value!!
                update(selectedUser)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insert(User(0, name, email))
                inputName.value = null
                inputEmail.value = null
            }
        }
    }

    private fun update(user: User) = viewModelScope.launch {
        val noOfRows = repository.update(user)
        if (noOfRows > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            btnSave.value = "保存"
            btnDel.value = "清空"
            msg.value = Event("$noOfRows 项成功更新")
        } else {
            msg.value = Event("出错")
        }
    }


    private fun insert(subscriber: User) = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if (newRowId > -1) {
            msg.value = Event("$newRowId 成功添加")
        } else {
            msg.value = Event("添加出错")
        }
    }

    fun clearOrDelete() {
        if (isUpdateOrDelete) {
            delete(selectedUser)
        } else {
            clearAll()
        }
    }

    fun delete(user: User) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(user)

        if (noOfRowsDeleted > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            btnSave.value = "保存"
            btnDel.value = "清除"
            msg.value = Event("$noOfRowsDeleted 项成功删除")
        } else {
            msg.value = Event("出错")
        }
    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            msg.value = Event("$noOfRowsDeleted 项成功删除")
        } else {
            msg.value = Event("出错")
        }
    }

    fun initUpdateAndDelete(user: User) {
        inputName.value = user.name
        inputEmail.value = user.email
        isUpdateOrDelete = true
        selectedUser = user
        btnSave.value = "更新"
        btnDel.value = "删除"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
