package org.light.myhobby.modules

import geb.Module

/**
 * @author Hanna_Aliakseichykava
 */

class WallMessageItem extends Module {
	static content = {
		author { $(".author-block").text() }
		messageText { $(".content-block").text() }
		editLink { $("a.edit") }
		deleteLink { $("a.delete") }
	}

}