package com.ffzs.userlist.table

/**
 * @author: ffzs
 * @Date: 2020/9/11 下午5:12
 */


class UserRepository(private val dao: UserDao) {

    val users = dao.findAll()

    suspend fun insert(user: User):Long{
        return dao.insert(user)
    }

    suspend fun update(user: User):Int{
        return dao.update(user)
    }

    suspend fun delete(user: User) : Int{
        return dao.delete(user)
    }

    suspend fun deleteAll() : Int{
        return dao.deleteAll()
    }
}