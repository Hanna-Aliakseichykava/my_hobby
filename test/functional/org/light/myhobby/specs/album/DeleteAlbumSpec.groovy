package org.light.myhobby.specs.album

import spock.lang.Unroll
import org.light.myhobby.Album
import org.light.myhobby.specs.BaseSpec
import org.light.myhobby.pages.HomePage
import org.light.myhobby.pages.user.ShowUserPage
import org.light.myhobby.pages.album.*
import org.light.myhobby.pages.album.AlbumListPage

/**
 * @author Hanna_Aliakseichykava
 */
class DeleteAlbumSpec extends BaseSpec {
	  
    def setup() {
        login()
    }
	
    @Unroll
    def "Delete album" () {
        
        given: "Created album"
        Album album = new Album(
            name: "AutotestAlbum2", 
            shortDescription: "Short description to Album 2", 
            user: createdUser).save()
        
        when: 
        to page, album.id
        
        then:
        at page
        
        when: "User delete Album"
        delete()
        
        then: "Album is deleted"
        at AlbumListPage
        message.infoDisplayed()
        message.getInfoMessage() == "Album ${album.id} deleted"

        where:
        page << [
            ShowAlbumPage,
            EditAlbumPage
        ]
    }
}