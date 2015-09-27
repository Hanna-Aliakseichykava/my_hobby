package org.light.myhobby



import grails.test.mixin.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
@TestFor(UserService)
@Mock(User)
class UserServiceTests {

    void testFindUser() {
       def userLogin = 'user1'
       def userPass = 'password1'
       
       assertTrue service.findUser(userLogin, userPass) == null
              
       def user = new User(login: userLogin, password: userPass, email: 'mail1@mail.ru')      
       user.save()
       
       assertTrue service.findUser(userLogin, userPass) != null
       assertTrue service.findUser(userLogin, userPass).login == userLogin
    }
}
