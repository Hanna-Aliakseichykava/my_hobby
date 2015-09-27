package org.light.myhobby.pages.picture

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class ShowPicturePage extends HomePage {
    
    static url = "picture/show"
    
    static at = { title == "Show Picture" }
    
    static content = {

		descriptionLabel { $("span", 'aria-labelledby': 'shortDescription-label').text() }
        originalNameLabel { $("span", 'aria-labelledby': 'originalName-label').text() }
        albumLink { $('a', href: contains('album/show')) }

        editButton { $('span#editInstance a') }
        deleteButton { $('span#deleteInstance input') }
    }

    public void delete() {
        assert withConfirm (true) {
            deleteButton.click()
        }
    }
}