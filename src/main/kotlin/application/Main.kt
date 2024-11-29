package application

import db.Database
import model.User
import model.dao.DaoFactory
import model.dao.UserDao
import util.ConsoleUtils

fun main() {
    val scanner: java.util.Scanner = java.util.Scanner(System.`in`)
    val userDao: UserDao = DaoFactory.createUserDao(Database.getConnection())

    ConsoleUtils.printOptions()

    print("\nResposta: ")
    var answer = scanner.nextLine()

    println()
    when (answer) {
        "1" -> {
            print("Informe o nome do usuário: ")
            val nome = scanner.nextLine()
            print("Informe o email do usuário: ")
            val email = scanner.nextLine()

            var user : User =userDao.insert(User(nome, email))
            println(user)
        }
    }

}