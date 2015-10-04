package org.light.myhobby.specs.message

import org.light.myhobby.pages.user.*
import org.light.myhobby.*;

/**
 * @author Hanna_Aliakseichykava
 */
class UserWallMessageSpec extends MessageSpec {

	@Override
	protected def getMessagesPage() {
		return UserWallPage
	}

	@Override
	protected def createOwnerInstance() {
		return createUser()
	}

	@Override
	protected def createOwnerWithMessages(def messagesCount) {
		def userTo = createUser()
		def userFrom = createUser()

		for(int i = 0; i < 5; ++ i) {
			def mess = new Message(userFrom: userFrom, content: "Message " + (i + 1))
			userTo.addMessage(mess)
		}

		return userTo
	}
}