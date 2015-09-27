package org.light.myhobby.pages.album

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class CreateAlbumPage extends HomePage {
    
    static url = "album/create"
    
    static at = { title == "Create Album" }
    
    static content = {
		nameField { $("input", name: 'name') }
		descriptionField { $("textarea", name: 'shortDescription') }
		createButton { $("input", name: 'create') }
    }
}