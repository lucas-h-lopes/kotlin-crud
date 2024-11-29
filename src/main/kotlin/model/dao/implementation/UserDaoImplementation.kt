package model.dao.implementation

import db.Database
import db.exception.DatabaseException
import model.User
import model.dao.UserDao
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class UserDaoImplementation(val connection: Connection) : UserDao {
    override fun insert(user: User): User {
        try {
            val preparedStatement: PreparedStatement = Database.getConnection()
                .prepareStatement(
                    "insert into usuarios (nome, email) values (?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
                )
            preparedStatement.setString(1, user.getNome())
            preparedStatement.setString(2, user.getEmail())

            preparedStatement.executeUpdate()
            val resultSet: ResultSet = preparedStatement.generatedKeys

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1))
                return user
            }
            throw DatabaseException("Falha ao inserir usuário, resultSet sem valor")
        } catch (e: SQLException) {
            throw DatabaseException("Falha ao inserir usuário: " + e.message)
        }
    }

    override fun getById(id: Int): User {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<User> {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, user: User): String {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): String {
        TODO("Not yet implemented")
    }
}