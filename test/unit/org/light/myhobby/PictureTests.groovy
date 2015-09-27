package org.light.myhobby

import grails.test.mixin.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
@TestFor(Picture)
class PictureTests {

    private static final int MAX_DESCRIPTION_LENGTH = 1000

    @Test
    void testConstraintsNull() {      
       
        def testPicture = new Picture()
        assertFalse testPicture.validate()
        assertTrue testPicture.hasErrors()       
        assertEquals "nullable", testPicture.errors["album"].code
     }

     @Test
     void testConstraintsToBigDescription() {      
              
        def user = new User(login: 'login1', password: 'password1', email: 'email1@mail.ru')
        def album = new Album(user: user, name: "Album1") 

        def testPicture = new Picture(album: album, shortDescription: "a".multiply(MAX_DESCRIPTION_LENGTH + 1))
        assertFalse testPicture.validate()
        assertTrue testPicture.hasErrors()
        assertEquals "maxSize.exceeded", testPicture.errors["shortDescription"].code

        testPicture = new Picture(album: album, shortDescription: "a".multiply(MAX_DESCRIPTION_LENGTH))
        assertTrue testPicture.validate()
        assertFalse testPicture.hasErrors() 
    }

    @Test
    void testConstraintsPass() {      
              
        def user = new User(login: 'login1', password: 'password1', email: 'email1@mail.ru')
        def album = new Album(user: user, name: "Album2")        
        def testPicture = new Picture(album: album)
        assertTrue testPicture.validate()
        assertFalse testPicture.hasErrors()             
    }

}
