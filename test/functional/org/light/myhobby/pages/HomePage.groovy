package org.light.myhobby.pages

import geb.Page
import org.light.myhobby.modules.LoginModule
import org.light.myhobby.modules.Message

/**
 * @author Hanna_Aliakseichykava
 */
class HomePage extends Page {
    
    static url = ""
    
    static at = { title == "Welcome to Grails" }
    
    static content = {
        loginModule { module LoginModule }
        message { module Message }
    }
}