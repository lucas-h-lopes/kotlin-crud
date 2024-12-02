package model.dao

import model.Phone

interface PhoneDao {
    fun insert(phone: Phone): Phone
    fun getById(id: Int): Phone
    fun getAll(): List<Phone>
    fun updatePhone(id : Int, phone : Phone): String
    fun deleteById(id: Int): Boolean
    fun getByUserId(userId: Int) : List<Phone>
}