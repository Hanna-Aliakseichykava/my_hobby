package org.light.myhobby

import java.util.Date;

/**
 * @author Hanna_Aliakseichykava
 */
class Picture extends MessagesOwner {

	static belongsTo = [album: Album]

	static hasMany = [comments: Message]

	static mappedBy = [comments: 'commentPicture']

	String shortDescription
	String originalName
	String systemName
	Date dateCreated
	Date lastUpdated

	static constraints = {
		shortDescription(nullable: true, maxSize: 1000)
		originalName(nullable: true)
		systemName(nullable: true)
		dateCreated()
		lastUpdated()
	}

	String toString() {
		return originalName
	}

	String setNames(String name) {
		originalName = name
		systemName = "${id}.${getExtension(name)}"
	}

	String getExtension(String name) {
		int index = name.lastIndexOf('.')
		return name.substring(index + 1)
	}

	//---------------------------------
	@Override
	public def getMessagesForOwner() {
		return comments
	}

	@Override
	public void addToOwner(Message postedMessage) {
		postedMessage.commentPicture = this
		addToComments(postedMessage)
	}
}
