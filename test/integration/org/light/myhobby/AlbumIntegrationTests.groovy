package org.light.myhobby

import static org.junit.Assert.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
class AlbumIntegrationTests {
    
    //User
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
    
    User saveAlbums() {

        def user = new User(login: login, password: password, email: email)
        user.save()

        def album1 = new Album(name: "Album 1", shortDescription: "Short description to Album 1") 
        user.addToAlbums(album1) // save Album with help of User

        def album2 = new Album(name: "Album 2", shortDescription: "Short description to Album 2") 
        user.addToAlbums(album2) 

        def album3 = new Album(name: "Album 3", shortDescription: "Short description to Album 3") 
        user.addToAlbums(album3)
		
		user.save()

        return user;       
    }
    
    
    @Test
    void testSaveAlbums() {

        def user = saveAlbums()

        assertEquals 3, User.get(user.id).albums.size()
    }
    
    @Test
    void testAccessingAlbums() {

        def user = saveAlbums()

		def albums = User.get(user.id).albums
        def albumNames = albums.collect {it.name}

        assertEquals(['Album 1', 'Album 2', 'Album 3'], albumNames.sort())
		
		Album firstAlbum = albums.getAt(0)
		assert firstAlbum.id != null
		assert firstAlbum.dateCreated != null
		assert firstAlbum.lastUpdated != null
    }
    
}
