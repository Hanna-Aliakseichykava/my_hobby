package org.light.myhobby

class UserService {

    def findUser(login, password) {
       return User.findByLoginAndPassword(login, password)        
    }
}
