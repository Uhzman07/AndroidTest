package com.example.androidtest



import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest{

    // This is the @Test annotation
    @Test // Note that in a test case class, we are allowed to name our functions any how as long as we put it in a ``
    fun `empty username returns false` (){
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse() // This is to assume that the result is false then we get an error if we run because it does not match the required "true"


    }

    @Test // Note that in a test case class, we are allowed to name our functions any how as long as we put it in a ``
    fun `valid username amd correctly repeated password returns true` (){
        val result = RegistrationUtil.validateRegistrationInput(
            "Philip",
            "123",
            "123"
        )
        assertThat(result).isTrue()
        // If we wanted to assert a string instead
        //assertThat("hello").contains()
        //assertThat("hello").isEqualTo("hello")


    }

    @Test
    fun `username already exists returns false` (){
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
            "123",
            "123"
        )
        assertThat(result).isFalse() // This is to assume that the result is false then we get an error if we run because it does not match the required "true"


    }

    @Test
    fun `empty password returns false`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Usman",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `less than 2 digits password returns false`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Usman",
            "abcde4",
            "abcde4"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `incorrectly confirmed password returns false`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Usman",
            "abcde",
            "12345"
        )
        assertThat(result).isFalse()
    }


}