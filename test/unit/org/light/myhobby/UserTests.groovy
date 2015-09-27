package org.light.myhobby

import grails.test.mixin.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
@TestFor(User)
class UserTests {

    private static final int MAX_LOGIN_LENGTH = 20
    private static final int MAX_PASSWORD_LENGTH = 20

    @Test
    void testConstraintsSize() { 
       
        def testUser = new User()
        assertFalse testUser.validate()        
        assertEquals "nullable", testUser.errors["login"].code
        assertEquals "nullable", testUser.errors["password"].code
        assertEquals "nullable", testUser.errors["email"].code
                
        testUser = new User(login: 'wi', password: 'will', email: 'will@mail.ru')
        assertFalse testUser.validate()        
        assertEquals "size.toosmall", testUser.errors["login"].code
        assertEquals "size.toosmall", testUser.errors["password"].code

        testUser = new User(login: "a".multiply(MAX_LOGIN_LENGTH + 1), password: "a".multiply(MAX_PASSWORD_LENGTH + 1), email: 'will@mail.ru')
        assertFalse testUser.validate()        
        assertEquals "size.toobig", testUser.errors["login"].code
        assertEquals "size.toobig", testUser.errors["password"].code        
        
    }

    @Test
    void testConstraintsUnique() { 
       
        def user1 = new User(login: 'user1', password: 'password1', email: 'mail1@mail.ru')
        assertTrue user1.validate() 
        def savedUser1 = user1.save(flush:true, failOnError:true)
        assertNotNull savedUser1
        mockForConstraintsTests(User, [savedUser1])

        
        def testUser = new User(login: 'user1', password: 'password1', email: 'mail1@mail.ru')
        assertFalse testUser.validate()   
        assertEquals "unique", testUser.errors["login"]
               
        testUser = new User(login: 'user2', password: 'password2', email: 'mail2@mail.ru')
        assertTrue testUser.validate()        
    }
    
}
