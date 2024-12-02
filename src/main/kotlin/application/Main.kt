package application

import db.Database
import exception.EntityNotFoundException
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

    do {
        ConsoleUtils.printGeneralOptions()

        print("Resposta: ")
        var answer = scanner.nextLine()

        println()
        try {
            connection.autoCommit = false
            when (answer) {
                "1" -> {
                    print("**** Cadastro de Usuário ****\n")
                    print("\nInforme o nome: ")
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

                    connection.commit()
                }

                "2" -> {
                    print("Informe o id do usuário: ")
                    val id = scanner.nextInt()
                    scanner.nextLine()

                    print("\n**** Listagem de Usuário ****\n")
                    val user: User = userDao.getById(id)
                    println("\n$user")
                }

                "3" -> {
                    val result: List<User> = userDao.getAll()
                    println("**** Listagem de Todos os Usuários ****\n")
                    for ((index, value) in result.withIndex()) {
                        println("$value")
                        if (index != result.size - 1) {
                            println()
                        }
                    }
                }

                "4" -> {
                    print("Informe o id do usuário: ")
                    val id = scanner.nextInt()
                    scanner.nextLine()

                    println()

                    if (userDao.deleteById(id)) {
                        println("Registro excluído com sucesso!")
                    }
                }

                "5" -> {
                    print("Informe o id do usuário: ")
                    val id = scanner.nextInt()
                    scanner.nextLine()
                    val userInDb = userDao.getById(id)
                    val auxUser = User(userInDb.getId(), userInDb.getNome(), userInDb.getEmail())
                    do {
                        ConsoleUtils.printEditOptions(id)
                        print("Resposta: ")
                        val auxAnswer: String = scanner.nextLine()

                        when (auxAnswer) {
                            "1" -> {
                                print("Informe o novo nome: ")
                                val auxName = scanner.nextLine()
                                auxUser.setNome(auxName)
                            }

                            "2" -> {
                                print("Informe o novo email: ")
                                val auxEmail = scanner.nextLine()
                                auxUser.setEmail(auxEmail)
                            }

                            "3" -> {
                                print("\nInforme o id do telefone: ")
                                val phoneId = scanner.nextInt()
                                scanner.nextLine()
                                val phoneInDb = phoneDao.getById(phoneId)
                                val auxPhone = Phone(phoneInDb.getDdd(), phoneInDb.getNumber(), phoneInDb.getUser())
                                val phonesInDb: List<Phone> = phoneDao.getByUserId(id)

                                if (!phonesInDb.contains(phoneInDb)) {
                                    throw EntityNotFoundException("O telefone com id '$phoneId' não pertence ao usuário: ${userInDb.getNome()}")
                                }

                                do {
                                    ConsoleUtils.printEditPhoneOptions(phoneId)
                                    print("Resposta: ")
                                    val phoneAnswer = scanner.nextLine()
                                    println()

                                    when (phoneAnswer) {
                                        "1" -> {
                                            print("Informe o novo DDD: ")
                                            val auxDDD = scanner.nextLine()
                                            auxPhone.setDdd(auxDDD)
                                        }

                                        "2" -> {
                                            print("Informe o novo número: ")
                                            val auxNumber = scanner.nextLine()
                                            auxPhone.setNumber(auxNumber)
                                        }

                                        "3" -> {
                                            if (auxPhone.getDdd() == phoneInDb.getDdd() && auxPhone.getNumber() == phoneInDb.getNumber()) {
                                                print("Nenhuma alteração identificada! O registro permanece igual\n")
                                            } else {
                                                phoneDao.updatePhone(phoneId, auxPhone)
                                                connection.commit()
                                                print("Alterado com sucesso!\n")
                                            }
                                        }

                                        "0" -> {
                                            println("Saindo da seção 'Alteração do Telefone'")
                                        }

                                        else -> {
                                            print("\nOperação '$phoneAnswer' é inválida. Tente novamente!")
                                        }
                                    }
                                } while (phoneAnswer != "0")
                            }

                            "4" -> {
                                if (auxUser.getNome() == userInDb.getNome() && auxUser.getEmail() == userInDb.getEmail()) {
                                    print("\nNenhuma alteração identificada! O registro permanece igual\n")
                                } else {
                                    userDao.update(id, auxUser)
                                    connection.commit()
                                    print("\nAlterado com sucesso!\n")
                                }
                            }

                            "0" -> {
                                print("\nSaindo da seção 'Alteração do Cadastro'")
                            }

                            else -> {
                                print("\nOperação '$auxAnswer' é inválida. Tente novamente!")
                            }
                        }
                    } while (auxAnswer != "0")

                }

                "6" -> {

                    print("Informe o id do usuário: ")
                    val id = scanner.nextInt()
                    scanner.nextLine()
                    val userInDb = userDao.getById(id)

                    print("\n**** Cadastro de Telefone ****\n")
                    print("\nInforme o DDD do telefone: ")
                    val ddd = scanner.nextLine()
                    print("Informe o número do telefone: ")
                    val numero = scanner.nextLine()

                    val phone = Phone(ddd, numero, userInDb)
                    phoneDao.insert(phone)
                    println("\nTelefone\n$phone")
                }

                "7" -> {
                    print("Informe o id do telefone: ")
                    val phoneId = scanner.nextInt()
                    scanner.nextLine()

                    if (phoneDao.deleteById(phoneId)) {
                        println("Registro excluído com sucesso!")
                    }
                }
                "0" -> {
                    println("Saindo do programa...")
                }
                else -> {
                    println("Operação $answer é inválida. Tente novamente!")
                }
            }
        } catch (e: Exception) {
            connection.rollback()
            println("\nUm erro ocorreu durante a operação: ${e.message}")
        } finally {
            connection.autoCommit = true
        }
    } while (answer != "0")
    Database.closeConnection(connection)
}