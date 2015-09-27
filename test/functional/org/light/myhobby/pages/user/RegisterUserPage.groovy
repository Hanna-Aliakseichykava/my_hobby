package org.light.myhobby.pages.user

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class RegisterUserPage extends HomePage{
    
    static url = "user/register"
    
    static at = { $("div#create-user") }
    
    static content = {
        loginField{ $("input#login") }
		passwordField{ $("input#password") }
		passwordRepeatField{ $("input#passwordRepeat") }
		emailField{ $("input#email") }
		registerButton{ $("input#registerUser") }		
    }
}