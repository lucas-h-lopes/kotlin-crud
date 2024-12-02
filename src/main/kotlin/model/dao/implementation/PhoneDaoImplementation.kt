package model.dao.implementation

import db.Database
import db.exception.DatabaseException
import exception.EntityNotFoundException
import model.Phone
import model.dao.PhoneDao
import model.dao.UserDao
import java.sql.*

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
        val statement: PreparedStatement = connection.prepareStatement("select * from telefones where id = ?")
        statement.setInt(1, id)
        statement.executeQuery().use { result ->
            return if (result.next()) {
                val id = result.getInt("id")
                val ddd = result.getString("ddd")
                val numero = result.getString("numero")
                Phone(id, ddd, numero, userDaoImplementation.getById(result.getInt("user_id")))
            } else {
                throw EntityNotFoundException("Telefone com id '$id' não foi encontrado")
            }
        }
    }

    override fun getAll(): List<Phone> {
        val statement: Statement = connection.createStatement()
        val result: ResultSet = statement.executeQuery("select * from telefones")
        val list: MutableList<Phone> = ArrayList()

        while (result.next()) {
            val phone: Phone = Phone(
                result.getInt("id"),
                result.getString("ddd"),
                result.getString("numero"),
                userDaoImplementation.getById(result.getInt("user_id"))
            )
            list.add(phone)
        }
        return list
    }

    override fun updatePhone(id: Int, phone: Phone): String {
        val preparedStatement: PreparedStatement =
            connection.prepareStatement("update telefones set ddd = ?, numero = ? where id = ?")
        preparedStatement.setString(1, phone.getDdd())
        preparedStatement.setString(2, phone.getNumber())
        preparedStatement.setInt(3, id)

        val rows: Int = preparedStatement.executeUpdate()
        if (rows > 0) {
            return "Atualizado com sucesso"
        }
        throw DatabaseException("Falha ao atualizar o telefone: $phone")
    }

    override fun deleteById(id: Int): Boolean {
        getById(id)
        try {
            val preparedStatement: PreparedStatement =
                connection.prepareStatement("delete from telefones where id = ?")

            preparedStatement.setInt(1, id)

            preparedStatement.executeUpdate()
            return true
        } catch (e: SQLIntegrityConstraintViolationException) {
            println("Falha ao excluir o telefne com id '$id'. Mensagem do erro: $e")
        }
        return false
    }

    override fun getByUserId(userId: Int): List<Phone> {
        val statement: PreparedStatement = connection.prepareStatement("select * from telefones where user_id = ?")
        statement.setInt(1, userId)
        val result = statement.executeQuery()
        val listToReturn = ArrayList<Phone>()

        while (result.next()) {
            val phone = Phone(
                result.getInt("id"),
                result.getString("ddd"),
                result.getString("numero"),
                userDaoImplementation.getById(result.getInt("user_id"))
            )
            listToReturn.add(phone)
        }
        return listToReturn
    }
}