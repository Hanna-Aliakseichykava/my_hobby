package org.light.myhobby

import static org.junit.Assert.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
class PictureIntegrationTests {

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
    
    User savePictures() {

        def user = new User(login: login, password: password, email: email)
        user.save()

        def album = new Album(name: "Album 1", shortDescription: "Short description to Album 1") 
        user.addToAlbums(album)
		user.save()

        def picture1 = new Picture(originalName: "Picture 1") 
        album.addToPictures(picture1) 
        
        def picture2 = new Picture(originalName: "Picture 2") 
        album.addToPictures(picture2) 
        
        def picture3 = new Picture(originalName: "Picture 3") 
        album.addToPictures(picture3) 
		
		album.save()
        
        return user;       
    }
    
    
    @Test
    void testSavePictures() {

        def user = savePictures()

        assertEquals 3, User.get(user.id).albums.toList()[0].pictures.size()       
    }
    
    @Test
    void testAccessingPictures() {

        def user = savePictures()

        def foundUser = User.get(user.id)
        def album = foundUser.albums.toList()[0]
		def pictures = album.pictures
        def pictureNames = pictures.collect {it.originalName}

        assertEquals(['Picture 1', 'Picture 2', 'Picture 3'], pictureNames.sort())
		
		Picture firstPicture = pictures.getAt(0)
		assert firstPicture.id != null
		assert firstPicture.dateCreated != null
		assert firstPicture.lastUpdated != null
    }
    
}
