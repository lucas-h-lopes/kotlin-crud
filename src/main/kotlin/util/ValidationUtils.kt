package util

abstract class ValidationUtils {

    companion object{
        private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        private val phoneRegex = Regex("^[0-9]{9}$")
        fun isEmailValid(email : String): Boolean{
            return emailRegex.matches(email)
        }

        fun isPhoneValid(number : String) : Boolean{
            return phoneRegex.matches(number)
        }
    }
}