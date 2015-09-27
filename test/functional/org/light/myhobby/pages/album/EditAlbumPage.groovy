package org.light.myhobby.pages.album

/**
 * @author Hanna_Aliakseichykava
 */
class EditAlbumPage extends BaseAlbumPage {
    
    static url = "album/edit"
    
    static at = { $('div#edit-album').displayed }
    
    static content = {
        nameField { $("input", name: "name") }
        descriptionField { $("textarea", name: "shortDescription") }
        updateButton { $("span#updateAlbum input") }
    }
}