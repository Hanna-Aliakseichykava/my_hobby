package org.light.myhobby

import grails.test.mixin.*
import org.junit.*
import grails.test.GrailsUnitTestCase

/**
 * @author Hanna_Aliakseichykava
 */
@TestFor(Album)
class AlbumTests {

	private static final int MIN_NAME_LENGTH = 3
	private static final int MAX_NAME_LENGTH = 20

	private static final int MAX_DESCR_LENGTH = 1000

	@Test
    void testConstraintsNull() {      
       
        def testAlbum = new Album()

        assertFalse testAlbum.validate() 
        assertTrue testAlbum.hasErrors()

        assertEquals "nullable", testAlbum.errors["name"].code
        assertEquals "nullable", testAlbum.errors["user"].code
    }

    @Test
    void testConstraintsNameSize() {      
        
        def user = new User(login: 'login1', password: 'password1', email: 'email1@mail.ru')
        def testAlbum = new Album(user: user, name: "")

        assertFalse testAlbum.validate()
        assertTrue testAlbum.hasErrors()
        assertEquals "nullable", testAlbum.errors["name"].code

  		testAlbum = new Album(user: user, name: "a".multiply(MIN_NAME_LENGTH - 1))

        assertFalse testAlbum.validate()
        assertTrue testAlbum.hasErrors()
        assertEquals "size.toosmall", testAlbum.errors["name"].code

        testAlbum = new Album(user: user, name: "a".multiply(MAX_NAME_LENGTH + 1))

        assertFalse testAlbum.validate()
        assertTrue testAlbum.hasErrors()
        assertEquals "size.toobig", testAlbum.errors["name"].code

      	testAlbum = new Album(user: user, name: "Album1")

        assertTrue testAlbum.validate()
        assertFalse testAlbum.hasErrors()
    }

    @Test
    void testConstraintsDescriptionSize() {      
        
        def user = new User(login: 'login1', password: 'password1', email: 'email1@mail.ru')
        def testAlbum = new Album(user: user, name: "Album1", shortDescription: "a".multiply(MAX_DESCR_LENGTH + 1))

        assertFalse testAlbum.validate()
        assertTrue testAlbum.hasErrors()
        assertEquals "maxSize.exceeded", testAlbum.errors["shortDescription"].code

        testAlbum = new Album(user: user, name: "Album1", shortDescription: "a".multiply(MAX_DESCR_LENGTH))

        assertTrue testAlbum.validate()
        assertFalse testAlbum.hasErrors()
    }
}
