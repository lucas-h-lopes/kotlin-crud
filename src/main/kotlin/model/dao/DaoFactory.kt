package model.dao

import db.Database
import model.dao.implementation.UserDaoImplementation
import java.sql.Connection

abstract class DaoFactory {
    companion object {
        fun createUserDao(connection: Connection): UserDao {
            Database.createUserTable()
            return UserDaoImplementation(connection)
        }
    }
}