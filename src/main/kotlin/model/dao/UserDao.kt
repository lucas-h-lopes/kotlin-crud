package model.dao

import model.User

interface UserDao {

    fun insert(user: User): User
    fun getById(id: Int): User
    fun getAll(): List<User>
    fun update(id: Int, user: User): String
    fun deleteById(id: Int): String
}