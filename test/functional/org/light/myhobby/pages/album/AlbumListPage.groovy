package org.light.myhobby.pages.album

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class AlbumListPage extends HomePage {
    
    static url = "album/list"
    
    static at = { $('div#list-album').displayed }
    
    static content = {        
	albumLink (requied: false) { name -> $("a", href: contains('/album/show/'), text: contains(name)) }		
    }
}