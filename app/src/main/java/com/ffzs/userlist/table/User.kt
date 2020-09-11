package com.ffzs.userlist.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: ffzs
 * @Date: 2020/9/11 下午5:05
 */
@Entity(tableName = "user_table")
data class User (

    @PrimaryKey(autoGenerate = true)
    var id:Long,
    @ColumnInfo(name = "user_name")
    var name:String,
    @ColumnInfo(name = "email")
    var email:String
)