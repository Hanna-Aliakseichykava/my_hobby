package org.light.myhobby

/**
 * @author Hanna_Aliakseichykava
 */
class User extends MessagesOwner {

	String login
	String password
	String email
	Date dateCreated
	Date lastUpdated

	Profile profile

	static hasMany = [albums: Album, postedMessages: Message, wallMessages: Message]

	static mappedBy = [postedMessages: 'userFrom', wallMessages: 'wallUser']

	static constraints = {
		login(size: 3.. 20, blank: false, unique: true)
		password(size: 6.. 20, blank: false)
		email(blank: false, email: true)
		dateCreated()
		lastUpdated()
		profile(nullable: true)
	}

	static mapping = {
		sort "login": "asc"
		profile lazy: false
	}

	String toString() {
		return login
	}

	//------------------------------
	@Override
	public def getMessagesForOwner() {
		return wallMessages
	}

	@Override
	public void addToOwner(Message postedMessage) {
		postedMessage.wallUser = this
		addToWallMessages(postedMessage)
	}
}
