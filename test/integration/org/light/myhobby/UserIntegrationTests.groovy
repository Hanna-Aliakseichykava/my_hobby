package org.light.myhobby

import static org.junit.Assert.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
class UserIntegrationTests {
    
    String login = 'joe'
    String password = 'secret'
    String email = 'joe@mail.ru'

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }
   
    User saveUser() {
       
        def user = new User(login: login, password: password, email: email)
        assertNotNull user.save()
        assertNotNull user.id

        return user
    }
    
    @Test
    void testSaveUser() { 
        
        def user = saveUser() 
        
        def foundUser = User.get(user.id)
        assertEquals login, foundUser.login
    }
    
    @Test
    void testSaveAndUpdateUser() {  
        
        def user = saveUser() 
        
        def foundUser = User.get(user.id)
        String newPassword = 'newPassword'
        foundUser.password = newPassword
        foundUser.save()

        def editedUser = User.get(user.id)
        assertEquals newPassword, editedUser.password        
    }
    
    @Test
    void testSaveAndDeleteUser() {  
        
        def user = saveUser() 
        
        def foundUser = User.get(user.id)
        foundUser.delete()
        
        assertFalse User.exists(foundUser.id)
        
    }
    
    @Test
    void testWrongUser() {  
        
        def user = new User(login: login, password: 'tiny', email: email)

        assertFalse user.validate()
        assertTrue user.hasErrors()

        def errors = user.errors

        assertEquals "size.toosmall", errors.getFieldError("password").code
        assertEquals "tiny", errors.getFieldError("password").rejectedValue

        assertNull errors.getFieldError("login")        
    }
    
    @Test
    void testWrongUserSaveCorrected() {  
        
       def user = new User(login: login, password: 'tiny', email: email)

        assertFalse user.validate()
        assertTrue user.hasErrors()
        assertNull user.save()

        user.password = password
        assertTrue user.validate()
        assertFalse user.hasErrors()
        assertNotNull user.save()
    }
    
}
