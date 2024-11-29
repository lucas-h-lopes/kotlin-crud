package model.dao

import db.Database
import model.dao.implementation.PhoneDaoImplementation
import model.dao.implementation.UserDaoImplementation
import java.sql.Connection

class DaoFactory(private val connection: Connection) {

    fun createUserDao(): UserDao {
        Database.createUserTable()
        return UserDaoImplementation(connection)
    }

    fun createPhoneDao(): PhoneDao {
        Database.createPhoneTable()
        return PhoneDaoImplementation(connection, createUserDao())
    }

}