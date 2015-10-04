package org.light.myhobby.pages

import geb.Page
import org.light.myhobby.modules.*

/**
 * @author Hanna_Aliakseichykava
 */
class HomePage extends Page {

	static url = ""

	static at = { title == "Welcome to Grails" }

	static content = {
		loginModule { module LoginModule }
		message { module FlashMessage }
		wallMessagesModule { module WallMessagesModule }
	}
}