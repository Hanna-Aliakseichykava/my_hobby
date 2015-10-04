package org.light.myhobby.pages.album

/**
 * @author Hanna_Aliakseichykava
 */
class ShowAlbumPage extends BaseAlbumPage {
    
    static url = "album/show"
    
    static at = { $('div#show-album').displayed }
    
    static content = {
        nameLabel { $("span#name").text() }
        descriptionLabel { $("span#description").text() }
        
        userLink { $("span#userLink a") }
		
		editButton { $('span#editInstance a') }
    }
}