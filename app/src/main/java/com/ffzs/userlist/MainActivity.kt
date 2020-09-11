package com.ffzs.userlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ffzs.userlist.databinding.ActivityMainBinding
import com.ffzs.userlist.table.User
import com.ffzs.userlist.table.UserDatabase
import com.ffzs.userlist.table.UserRepository
import kotlinx.android.synthetic.main.user_list.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = UserDatabase.getInstance(application).userDao
        val repository = UserRepository(dao)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        userViewModel = ViewModelProvider(this,UserViewModelFactory(repository)).get(UserViewModel::class.java)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
        userViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

    }

    private lateinit var adapter: UserRecyclerViewAdapter
    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserRecyclerViewAdapter({ selectedItem: User ->listItemClicked(selectedItem)})
        binding.recyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        userViewModel.users.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(user: User){
        //Toast.makeText(this,"selected name is ${subscriber.name}",Toast.LENGTH_LONG).show()

        userViewModel.initUpdateAndDelete(user)
    }
}
