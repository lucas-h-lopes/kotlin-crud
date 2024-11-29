package util

abstract class ConsoleUtils {
    companion object{
        fun printOptions() {
            println("\n**** Escolha uma operação ****\n")
            println("1 - Inserir usuário")
            println("2 - Buscar um usuário")
            println("3 - Buscar todos os usuários")
            println("4 - Excluir um usuário")
            println("5 - Alterar dados de um usuário")
        }
    }
}