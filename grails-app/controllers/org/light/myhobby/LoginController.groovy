package org.light.myhobby

class LoginController {

    def userService

    def enter = { UserLoginCommand ulc ->        
        
        def user = userService.findUser(ulc.enteredLogin, ulc.enteredPassword)
        if(user) {
            session.user = user
            flash.isLoginOrPasswordIncorrect = false              
            redirect(controller: "user", action: "show", id: user.id)
            return
        } 
        else {
            session.user = null
            flash.isLoginOrPasswordIncorrect = true            
            redirect(uri: '/')            
        }        
    }
    
    def exit = { 
        session.user = null
        redirect(uri: '/')
    }
}


class UserLoginCommand {
    
    String enteredLogin
    String enteredPassword
    
}