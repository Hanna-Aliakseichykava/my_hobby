package org.light.myhobby

abstract class MessageController {

	def wall(Long id) {
		displayWall(id, null)
	}

	def editMessage(Long messageId, Long ownerId) {

		assert ownerId
		flash.message = null

		def messageInstance = Message.get(messageId)

		if (!messageInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), messageId])
			displayWall(ownerId, null)
		} else {
			displayWall(ownerId, messageInstance)
		}
	}

	def saveMessage = { Long ownerId, Message messageInstance ->

		def feedbackMessageInstance = createOrUpdateMessage(ownerId, messageInstance)
		displayWall(ownerId, feedbackMessageInstance)
	}

	def deleteMessage(Long messageId, Long ownerId) {

		assert ownerId
		def messageInstance = Message.get(messageId)

		if (!messageInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), messageId])
			return displayWall(ownerId, null)
		}

		try {
			def ownerInstance = getOwnerInstance(ownerId)
			ownerInstance.deleteMessage(messageInstance)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'message.label', default: 'Message'), messageId])
			return displayWall(ownerId, null)
		}
		catch (Exception e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'message.label', default: 'Message'), messageId])
			displayWall(ownerId, null)
		}
	}


	//------------------------------------------------------------------
	private Message createOrUpdateMessage (Long ownerId, Message postedMessage) {

		if(!postedMessage) {
			return null
		}

		flash.message = null

		populateCreatedUser(postedMessage)
		postedMessage.validate()

		if(postedMessage.hasErrors()) {
			return postedMessage

		} else if(postedMessage.id) {
			updateMessage(ownerId, postedMessage)

		} else {
			createMessage(ownerId, postedMessage)
		}

		return null
	}

	private void createMessage(Long ownerId, def postedMessage) {

		def ownerInstance = getOwnerInstance(ownerId)
		ownerInstance.addMessage(postedMessage)
	}

	private void updateMessage(def ownerId, def postedMessage) {

		def message = Message.get(postedMessage.id)

		if (!message) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), id])
			return
		}

		def ownerInstance = getOwnerInstance(ownerId)
		ownerInstance.updateMessage(postedMessage)
	}

	private def displayWall (Long ownerId, def messageInstance) {

		if(messageInstance == null) {
			messageInstance = createEmptyMessage()
		}

		def allMessagesCount
		def messages

		def ownerInstance = getOwnerInstance(ownerId)

		if(ownerInstance) {
			allMessagesCount = ownerInstance.getMessages()?.size() ?: 0
			messages = Message.getCommentsForOwner(ownerInstance, params)
		}

		render(view: getWallView(), model: [
			ownerInstance: ownerInstance,
			messageInstanceList: messages,
			messageInstanceTotal: allMessagesCount,
			messageInstance: messageInstance
		])
	}

	private createEmptyMessage() {
		return new Message(userFrom: session.user)
	}

	private populateCreatedUser(Message mes) {
		if(mes.userFrom == null) {
			mes.userFrom = session.user
		}
	}

	//------------------------
	protected abstract def getOwnerInstance(Long id);

	protected def getWallView() {
		return "show"
	}

}
