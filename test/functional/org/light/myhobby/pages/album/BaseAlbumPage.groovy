package org.light.myhobby.pages.album

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class BaseAlbumPage extends HomePage {
    
    static content = {
        deleteButton { $("span#deleteInstance input") }
        addPictureLink { $("span#addPicture a") }
		
		firstPictureLinkText { $("#pictures a").text() }

		deleteButton { $('span#deleteInstance input') }
    }
    
    public void delete() {
        assert withConfirm (true) {
            deleteButton.click()
        }
    }
}