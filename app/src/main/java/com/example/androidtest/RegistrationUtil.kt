package com.example.androidtest

object RegistrationUtil {

    private val existingUser = listOf("Peter", "Carl")

    /**
     * the input is not valid if ...
     * .. the username/password is empty
     * .. the username is already taken
     * .. the confirmed password is not the same as the real password
     * .. the password contains less than two digits
     * ..
     */

    fun validateRegistrationInput(
       username: String,
       password: String,
       confirmedPassword: String
    ) : Boolean{
        if(username.isEmpty() || password.isEmpty()){
            return false
        }
        if(username in existingUser){ // This is a way to check if the password that we had input is in the list
            return false
        }
        if(password != confirmedPassword){
            return false
        }
        if(password.count{it.isDigit()} < 2){ // This means we are counting the digits only in the password
            return false

        }
        return true
    }
}