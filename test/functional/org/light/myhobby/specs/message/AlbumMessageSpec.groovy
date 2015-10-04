package org.light.myhobby.specs.message

import org.light.myhobby.pages.album.*
import org.light.myhobby.*;

/**
 * @author Hanna_Aliakseichykava
 */
class AlbumMessageSpec extends MessageSpec {

	@Override
	protected def getMessagesPage() {
		return ShowAlbumPage
	}

	@Override
	protected def createOwnerInstance() {
		return createAlbum()
	}

	@Override
	protected def createOwnerWithMessages(def messagesCount) {
		def album = createAlbum()
		def userFrom = createUser()

		for(int i = 0; i < messagesCount; ++ i) {
			def mess = new Message(userFrom: userFrom, content: "Message " + (i + 1))
			album.addMessage(mess)
		}

		return album
	}
}