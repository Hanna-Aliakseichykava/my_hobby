package org.light.myhobby.pages.picture

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class PictureListPage extends HomePage {
    
    static url = "picture/list"
    
    static at = { $('div#list-picture').displayed }
    
    static content = {        
	}
}