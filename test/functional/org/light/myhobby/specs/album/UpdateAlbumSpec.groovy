package org.light.myhobby.specs.album

import org.light.myhobby.Album
import org.light.myhobby.specs.BaseSpec
import org.light.myhobby.pages.HomePage
import org.light.myhobby.pages.user.ShowUserPage
import org.light.myhobby.pages.album.ShowAlbumPage
import org.light.myhobby.pages.album.AlbumListPage
import org.light.myhobby.pages.album.EditAlbumPage

/**
 * @author Hanna_Aliakseichykava
 */
class UpdateAlbumSpec extends BaseSpec {
	  
    def setup() {
        login()
    }
	
    def "Update album" () {
        given: "Created album"
        Album album = new Album(name: "AutotestAlbum", shortDescription: "Short description to Album", user: createdUser).save()
        
        and: "New album data"
        String newName = "NewAlbumName"
        String newDescription = "New Album Description"
        
        when: 
        to AlbumListPage
        
        and:
        albumLink(album.name).click()
        
        then:
        at ShowAlbumPage
        
        when:
        editButton.click()
        
        then:
        at EditAlbumPage
        
        when: "Name and description are changed"
        nameField = newName
        descriptionField = newDescription
        
        and:
        updateButton.click()
        
        then:
        at ShowAlbumPage
        
        and:
        nameLabel == newName
        descriptionLabel == newDescription
    }
}