package org.light.myhobby

abstract class MessagesOwner {

	public abstract def getMessagesForOwner()

	public abstract void addToOwner(Message postedMessage)

	public def getMessages() {
		return getMessagesForOwner()
	}

	public void addMessage(Message postedMessage) {
		addToOwner(postedMessage)
		this.save(flush: true)
	}

	public void updateMessage(Message postedMessage) {
		def messages = getMessages()
		def message = messages.find { it.id == postedMessage.id }

		message.content = postedMessage.content
		this.save(flush: true)
	}

	public void deleteMessage(Message postedMessage) {
		def messages = getMessages()
		messages.removeAll { it.id == postedMessage.id }

		this.save(flush: true)
	}
}