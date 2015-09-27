package org.light.myhobby.pages.picture

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class EditPicturePage extends HomePage {
    
    static url = "picture/edit"
    
    static at = { title == "Edit Picture" }
    
    static content = {

		descriptionField { $("textarea", name: 'shortDescription') }
        albumList { $('select#album') }

        updateButton { $('span#updatePicture input') }
        deletButton { $('span#deletePicture input') }
    }
}