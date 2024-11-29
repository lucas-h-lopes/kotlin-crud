package model.dao.implementation

import db.Database
import db.exception.DatabaseException
import model.Phone
import model.dao.PhoneDao
import model.dao.UserDao
import java.sql.Connection
import java.sql.PreparedStatement

class PhoneDaoImplementation(
    private val connection: Connection,
    private val userDaoImplementation: UserDao
) : PhoneDao {

    override fun insert(phone: Phone): Phone {
        try {
            userDaoImplementation.getById(phone.getUser().getId())

            val preparedStatement: PreparedStatement =
                connection.prepareStatement(
                    "insert into telefones (ddd, numero, user_id) values (?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
                )
            preparedStatement.setString(1, phone.getDdd())
            preparedStatement.setString(2, phone.getNumber())
            preparedStatement.setInt(3, phone.getUser().getId())

            preparedStatement.executeUpdate()
            preparedStatement.generatedKeys.use { result ->
                if (!result.next()) {
                    Database.closeStatement(preparedStatement)
                    throw DatabaseException("Não foi possível inserir o telefone: $phone")
                }
                phone.setId(result.getInt(1))
                Database.closeStatement(preparedStatement)
                return phone
            }
        } catch (e: Exception) {
            throw DatabaseException("Falha ao inserir telefone: ${e.message}")
        }
    }

    override fun getById(id: Int): Phone {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Phone> {
        TODO("Not yet implemented")
    }

    override fun updatePhone(id: Int, phone: Phone): String {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): String {
        TODO("Not yet implemented")
    }
}