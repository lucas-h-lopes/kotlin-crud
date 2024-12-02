package util

abstract class ConsoleUtils {
    companion object{
        fun printGeneralOptions() {
            println("\n**** Escolha uma operação ****\n")
            println("1 - Inserir usuário")
            println("2 - Buscar um usuário")
            println("3 - Buscar todos os usuários")
            println("4 - Excluir um usuário")
            println("5 - Alterar dados de um usuário")
            println("6 - Cadastrar telefone a um usuário")
            println("7 - Excluir telefone de um usuário")
            println("0 - Sair do programa")
        }

        fun printEditOptions(userId : Int){
            println("\n**** Alteração do Cadastro - Usuário $userId ****\n")
            println("1 - Nome")
            println("2 - Email")
            println("3 - Telefone")
            println("4 - Confirmar alterações")
            println("0 - Sair da seção 'Alteração do Cadastro'")
        }
        fun printEditPhoneOptions(telId : Int){
            println("\n**** Alteração do Telefone - Telefone $telId ****\n")
            println("1 - DDD")
            println("2 - Número")
            println("3 - Confirmar alterações")
            println("0 - Sair da seção 'Alteração do Telefone'")
        }
    }
}