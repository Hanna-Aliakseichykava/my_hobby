package org.light.myhobby

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(LoginController)
@Mock([User])
class LoginControllerTests {

    private static def userService = new UserService()
    
    def userLogin = 'user1'
    def userPass = 'password1'

    void setUp() {
        /*defineBeans {
            userService(UserService)
        }*/
        controller.userService = userService
    }
    //////////////////

    void testEnterNonExistanceUser() {
       
        mockCommandObject(UserLoginCommand)
        def testCmd = new UserLoginCommand(enteredLogin: userLogin, enteredPassword: userPass)
        
        controller.enter(testCmd)
        assert flash.isLoginOrPasswordIncorrect
        assert response.redirectedUrl == "/"
        assert session.user == null
    }
    
    void testEnterValidUser() {
       
        mockCommandObject(UserLoginCommand)
        def testCmd = new UserLoginCommand(enteredLogin: userLogin, enteredPassword: userPass)        
        
        def user = new User(login: userLogin, password: userPass, email: 'mail1@mail.ru')      
        user.save()
        controller.enter(testCmd)
        
        assert flash.isLoginOrPasswordIncorrect == false
        assert response.redirectedUrl == "/user/show/${user.id}"        
        assert session.user != null
        assert session.user.login == userLogin        
    }
    
    void testExit() {
        def user = new User(login: userLogin, password: userPass, email: 'mail1@mail.ru')      
        user.save()
        session.user = user
        assert session.user != null        
        controller.exit()        
        assert session.user == null        
    }
}
