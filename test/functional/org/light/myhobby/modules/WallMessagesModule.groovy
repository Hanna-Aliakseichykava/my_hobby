package org.light.myhobby.modules

import geb.Module

/**
 * @author Hanna_Aliakseichykava
 */
class WallMessagesModule extends Module {

	static content = {
		container { $("#list-message") }

		contentInput { container.find("textarea", name: "content") }
		saveMessageButton { container.find("input", name: "saveMessage") }


		messagesList { container.find(".messages-list") }

		messageItems(required: false) {
			messagesList.find(".message-instance").collect { item->
				module(WallMessageItem, item)
			}
		}

		messagesCount { messagesList.find(".message-instance").size() }
	}

	public saveMessage(def messageText) {
		contentInput.value(messageText)
		saveMessageButton.click()
	}
}