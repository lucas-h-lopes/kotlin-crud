package model

import exception.InvalidUserException
import util.ValidationUtils

class User(
    private var id: Int,
    private var nome: String,
    private var email: String

) {
    init {
        setNome(nome)
        setEmail(email)
    }

    constructor(nome: String, email: String) : this(0, nome, email)
    constructor() : this (0, "", "")

    fun getId(): Int {
        return this.id
    }

    fun setId(id: Int) {
        this.id = id;
    }

    fun getNome(): String {
        return this.nome
    }

    fun getEmail(): String {
        return this.email;
    }

    fun setNome(nome: String) {
        if (nome.isBlank() || nome.length < 5 || nome.length > 100) {
            throw InvalidUserException("O nome deve possuir 5 a 100 caracteres")
        }
        this.nome = nome;
    }

    override fun toString(): String {
        return "id: $id\nname: $nome\nemail: $email"
    }

    fun setEmail(email: String) {
        if (email.isBlank() || email.length < 10 || email.length > 200) {
            throw InvalidUserException("O email deve possuir 10 a 200 caracteres")
        }

        if (!ValidationUtils.isEmailValid(email)) {
            throw InvalidUserException("O email deve possuir um email válido, p.ex.: exemplo.usuario@dominio.com")
        }
            this.email = email
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + nome.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (nome != other.nome) return false
        if (email != other.email) return false

        return true
    }


}
