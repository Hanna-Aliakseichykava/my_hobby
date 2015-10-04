package org.light.myhobby

/**
 * @author Hanna_Aliakseichykava
 */
class Message {

	static belongsTo = [
		userFrom: User,

		wallUser: User,
		commentAlbum: Album,
		commentPicture: Picture
	]

	String content
	Date dateCreated
	Date lastUpdated

	static constraints = {

		content(blank: false, maxSize: 2000)
		userFrom(nullable: false)
		dateCreated()
		lastUpdated()

		wallUser(nullable: true)
		commentAlbum(nullable: true)
		commentPicture(nullable: true)
	}

	String toString() {
		return userFrom.login + " - " + content
	}

	static mapping = { sort dateCreated: "asc" }

	/*public static List<Message> getCommentsForOwner(User user, def params = [:]) {

		def messages = Message.createCriteria().list(params) { eq('wallUser', user) }
		return messages
	}

	public static List<Message> getCommentsForOwner(Album album, def params = [:]) {

		def messages = Message.createCriteria().list(params) { eq('commentAlbum', album) }
		return messages
	}

	public static List<Message> getCommentsForOwner(Picture picture, def params = [:]) {

		def messages = Message.createCriteria().list(params) { eq('commentPicture', picture) }
		return messages
	}*/

	public static def getCommentsForOwner(MessagesOwner owner, def params = [:]) {
		def messages = (owner.getMessages() ?: []).toList()
		messages.sort{ it.dateCreated }
		Integer offset = params.offset ?: 0
		Integer max = Math.min((params.max ?: 10).toInteger(), 100)
		Integer maxIndex = Math.min(offset + max, messages.size())
		return messages.subList(offset, maxIndex)
	}
}
