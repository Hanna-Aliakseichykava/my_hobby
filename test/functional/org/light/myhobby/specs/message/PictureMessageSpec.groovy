package org.light.myhobby.specs.message

import org.light.myhobby.pages.picture.*
import org.light.myhobby.*;

/**
 * @author Hanna_Aliakseichykava
 */
class PictureMessageSpec extends MessageSpec {

	@Override
	protected def getMessagesPage() {
		return ShowPicturePage
	}

	@Override
	protected def createOwnerInstance() {
		return createPicture()
	}

	@Override
	protected def createOwnerWithMessages(def messagesCount) {
		def picture = createPicture()
		def userFrom = createUser()

		for(int i = 0; i < messagesCount; ++ i) {
			def mess = new Message(userFrom: userFrom, content: "Message " + (i + 1))
			picture.addMessage(mess)
		}

		return picture
	}
}