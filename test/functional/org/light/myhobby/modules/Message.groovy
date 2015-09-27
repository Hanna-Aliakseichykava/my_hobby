package org.light.myhobby.modules

import geb.Module

/**
 * @author Hanna_Aliakseichykava
 */
class Message extends Module {
    
    static content = {
        message { $("div.message", role: "status") }
		error { $(".errors") }
    }
    
    public boolean infoDisplayed() {
        return message.displayed
    }
    
    public String getInfoMessage() {
        return message.text()
    }
	
	public boolean errorDisplayed() {
		return error.displayed
	}
	
	public String getErrorMessage() {
		return error.text()
	}
}