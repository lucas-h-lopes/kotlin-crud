package model

import exception.InvalidPhoneException
import util.ValidationUtils

data class Phone(private val id: Int, private var ddd: String, private var number: String, private var user : User) {

    fun getId() : Int{
        return this.id
    }

    fun getDdd() : String{
        return this.ddd;
    }

    fun getUser() : User{
        return this.user
    }

    fun setUser(user : User){
        this.user = user
    }

    fun setDdd(ddd : String){
        if(ddd.isBlank() || ddd.length != 2){
            throw InvalidPhoneException("O DDD deve possuir exatamente 2 dígitos")
        }
        this.ddd = ddd;
    }

    fun getNumber() : String{
        return this.number;
    }

    fun setNumber(number : String){
        if(number.length != 9){
            throw InvalidPhoneException("O número deve possuir exatamente 9 caracteres")
        }
        if(!ValidationUtils.isPhoneValid(number)){
            throw InvalidPhoneException("Formato do telefone inválido.")
        }
        this.number = number
    }
}