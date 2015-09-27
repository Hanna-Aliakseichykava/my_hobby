package org.light.myhobby.modules

import geb.Module

/**
 * @author Hanna_Aliakseichykava
 */
class LoginModule extends Module {
    
    static content = {
        loginField { $("input#enteredLogin") }	
        passwordField { $("input#enteredPassword") }	
        enterButton { $("input#enterSystem") }
        loginLabel { $("span#loginLabel").text() }
		
		exitLink { $("a", name: 'exitLink') }
    }
    
    public void login(String login, String pass) {
        loginField.value(login)
        passwordField.value(pass)
        enterButton.click()
    }
	
	public void logout() {
		exitLink.click()
	}

}