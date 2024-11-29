package application

import db.Database
import model.Phone
import model.User
import model.dao.DaoFactory
import model.dao.PhoneDao
import model.dao.UserDao
import util.ConsoleUtils
import java.sql.Connection

fun main() {
    val connection: Connection = Database.getConnection()
    val scanner: java.util.Scanner = java.util.Scanner(System.`in`)

    val daoFactory = DaoFactory(connection)
    val userDao: UserDao = daoFactory.createUserDao()
    val phoneDao: PhoneDao = daoFactory.createPhoneDao()

    ConsoleUtils.printOptions()

    print("\nResposta: ")
    var answer = scanner.nextLine()

    println()
    try {
        connection.autoCommit = false
        when (answer) {
            "1" -> {
                print("Informe o nome: ")
                val nome = scanner.nextLine()
                print("Informe o email: ")
                val email = scanner.nextLine()
                print("Informe o DDD do telefone: ")
                val ddd = scanner.nextLine()
                print("Informe o número do telefone: ")
                val numero = scanner.nextLine()

                val user: User = userDao.insert(User(nome, email))
                val phone: Phone = phoneDao.insert(Phone(ddd, numero, user))

                println("\nUsuário\n$user\n")
                println("Telefone\n$phone")
            }

            "2" -> {
                print("Informe o id do usuário: ")
                val id = scanner.nextInt()
                scanner.nextLine()

                val user: User = userDao.getById(id)
                println(user)
            }
        }
    } catch (e: Exception) {
        connection.rollback()
        println("\nUm erro ocorreu durante a operação: ${e.message}")
    } finally {
        connection.autoCommit = true
    }

}