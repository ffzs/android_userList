package com.ffzs.userlist.table

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author: ffzs
 * @Date: 2020/9/11 下午5:08
 */

@Dao
interface UserDao {

    @Insert
    suspend fun insert (user: User):Long

    @Update
    suspend fun update (user: User):Int

    @Delete
    suspend fun delete (user: User):Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAll ():Int

    @Query("SELECT * FROM user_table")
    fun findAll ():LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun findById (id:Long):User

}