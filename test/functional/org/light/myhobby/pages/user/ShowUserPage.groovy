package org.light.myhobby.pages.user

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class ShowUserPage extends HomePage {
    
    static url = "user/show"
    
    static at = { title == "Show User" }
    
    static content = {
    	infoMessage { $("div[role='status']").text() }
    
        loginLabel { $("span[name='login-label']").text() }
        //emailLabel { $("span[name='email-label']").text() }
        
        createAlbumButton { $("li#createAlbum a") }
        albumLink (required: false) { name -> $("span#albums-list span a", text: contains(name)) }
        
        editUserButton { $("span[name='editInstance'] a") }
        //deleteUserButton { $("span[name='deleteUser'] input") }
    }
}