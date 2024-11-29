package model.dao.implementation

import db.exception.DatabaseException
import exception.EntityNotFoundException
import exception.UniqueEmailViolationException
import model.User
import model.dao.UserDao
import java.sql.*

class UserDaoImplementation(private val connection: Connection) : UserDao {
    override fun insert(user: User): User {
        try {
            val preparedStatement: PreparedStatement = connection
                .prepareStatement(
                    "insert into usuarios (nome, email) values (?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
                )
            preparedStatement.setString(1, user.getNome())
            preparedStatement.setString(2, user.getEmail())

            preparedStatement.executeUpdate()
            preparedStatement.generatedKeys.use { result ->
                if (result.next()) {
                    user.setId(result.getInt(1))
                    return user
                }
                throw DatabaseException("Falha ao inserir usuário, resultSet sem valor")
            }
        } catch (e: SQLException) {
            throw UniqueEmailViolationException("Falha ao inserir usuário -> " + e.message)
        }
    }

    override fun getById(id: Int): User {
        val statement: PreparedStatement = connection.prepareStatement("select * from usuarios where id = ?")
        statement.setInt(1, id)
        statement.executeQuery().use { result ->
            return if (result.next()) {
                val id = result.getInt("id")
                val nome = result.getString("nome")
                val email = result.getString("email")
                User(id, nome, email)
            } else {
                throw EntityNotFoundException("Usuário com id '$id' não foi encontrado")
            }
        }
    }

    override fun getAll(): List<User> {
        val statement: Statement = connection.createStatement()
        val result: ResultSet = statement.executeQuery("select * from usuarios")
        val list: MutableList<User> = ArrayList()

        while (result.next()) {
            var user: User = User(result.getInt("id"), result.getString("nome"), result.getString("email"))
            list.add(user)
        }
        return list
    }

    override fun update(id: Int, user: User): String {
        /*val userDb: User = getById(id)

        val preparedStatement: PreparedStatement =
            connection.prepareStatement("update usuarios set nome = ?, email = ? where id = ?")
        preparedStatement.setString(1, user.getNome())
        preparedStatement.setString(2, user.getEmail())
        preparedStatement.setInt(3, id)

        val rows: Int = preparedStatement.executeUpdate()

        if (rows > 0) {
            return "Atualizado com sucesso"
        }*/
        TODO()
    }

    override fun deleteById(id: Int): String {
        TODO("Not yet implemented")
    }
}