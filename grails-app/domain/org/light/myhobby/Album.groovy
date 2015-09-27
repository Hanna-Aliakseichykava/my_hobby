package org.light.myhobby

/**
 * @author Hanna_Aliakseichykava
 */
class Album extends MessagesOwner {

	static belongsTo = [user: User]

	static hasMany = [pictures: Picture, comments: Message]

	static mappedBy = [comments: 'commentAlbum']

	String name
	String shortDescription
	Date dateCreated
	Date lastUpdated

	static constraints = {
		name(size: 3.. 20, blank: false)
		shortDescription(nullable: true, maxSize: 1000)
		dateCreated()
		lastUpdated()
	}

	String getInnerName() {
		"album-${id}"
	}

	String toString() {
		return name
	}

	static mapping = { sort dateCreated: "desc" }

	//-----------------------------
	@Override
	public def getMessagesForOwner() {
		return comments
	}

	@Override
	public void addToOwner(Message postedMessage) {
		postedMessage.commentAlbum = this
		addToComments(postedMessage)
	}

}
