package org.light.myhobby.specs.message

import org.light.myhobby.*;
import org.light.myhobby.specs.BaseSpec
import org.light.myhobby.modules.*

/**
 * @author Hanna_Aliakseichykava
 */
abstract class MessageSpec extends BaseSpec {

	def cleanup() {
		logout()
	}

	def "Show messages" () {

		given: "owner with messages"
		def page = getMessagesPage()
		def messagesCount = 3
		def owner = createOwnerWithMessages(messagesCount)

		when:
		to page, owner.id

		then:
		at page

		and:
		wallMessagesModule.messagesCount == messagesCount
	}

	def "Add message - User is not logged in" () {

		given: "owner"
		def page = getMessagesPage()
		def owner = createOwnerInstance()
		def messageText = "test text"

		when:
		to page, owner.id

		then:
		at page

		and: "Owner has no messages"
		wallMessagesModule.messagesCount == 0

		when: "User input message"
		wallMessagesModule.saveMessage(messageText)

		then:
		at page

		and: "Message is not saved"
		wallMessagesModule.messagesCount == 0

		and: "Error is displayed"
		message.getErrorMessage() == "Property [userFrom] of class [Message] cannot be null"
	}

	def "Add message - User is logged in but text is empty" () {

		given: "owner"
		def page = getMessagesPage()
		def owner = createOwnerInstance()

		when: "user is logged in"
		login()

		and:
		to page, owner.id

		then:
		at page

		and: "Owner has no messages"
		wallMessagesModule.messagesCount == 0

		when: "User input message"
		wallMessagesModule.saveMessage("")

		then:
		at page

		and: "Message is not saved"
		wallMessagesModule.messagesCount == 0

		and: "Error is displayed"
		message.getErrorMessage() == "Property [Content] of class [Message] cannot be null"
	}

	def "Add message - success" () {

		given: "owner"
		def page = getMessagesPage()
		def owner = createOwnerInstance()
		def messageText = "test text"

		when: "user is logged in"
		login()

		and:
		to page, owner.id

		then:
		at page

		and: "Owner has no messages"
		wallMessagesModule.messagesCount == 0

		when: "User input message"
		wallMessagesModule.saveMessage(messageText)

		then:
		at page

		and: "Message is saved"
		wallMessagesModule.messagesCount == 1

		and: "New message contains appropriate info"
		WallMessageItem message = wallMessagesModule.messageItems.getAt(0)
		message.messageText == messageText
		message.author == userLogin
	}

	def "Edit message" () {

		given: "owner"
		def page = getMessagesPage()
		def owner = createOwnerInstance()
		def messageText = "test text"
		def newMessageText = "new test text"
		
		when: "user is logged in"
		login()

		and:
		to page, owner.id

		then:
		at page
		
		and: "Owner has no messages"
		wallMessagesModule.messagesCount == 0

		when: "User input message"
		wallMessagesModule.saveMessage(messageText)

		then:
		at page

		and: "Message is saved"
		wallMessagesModule.messagesCount == 1

		and:
		WallMessageItem message = wallMessagesModule.messageItems.getAt(0)
		message.messageText == messageText

		when:
		message.editLink.click()

		then:
		at page

		and:
		wallMessagesModule.contentInput.value() == messageText

		when:
		wallMessagesModule.saveMessage(newMessageText)

		then:
		at page

		and:
		wallMessagesModule.messagesCount == 1

		and:
		def updatedMessage = wallMessagesModule.messageItems.getAt(0)
		updatedMessage.messageText == newMessageText
	}

	def "Delete message" () {

		given: "owner"
		def page = getMessagesPage()
		def owner = createOwnerInstance()
		def messageText = "test text"

		when: "user is logged in"
		login()

		and:
		to page, owner.id

		then:
		at page

		and: "Owner has no messages"
		wallMessagesModule.messagesCount == 0

		when: "User input message"
		wallMessagesModule.saveMessage(messageText)

		then:
		at page

		and: "Message is saved"
		wallMessagesModule.messagesCount == 1

		when:
		WallMessageItem savedMessage = wallMessagesModule.messageItems.getAt(0)
		savedMessage.deleteLink.click()

		then:
		at page

		and:
		wallMessagesModule.messagesCount == 0
	}

	//-----------------------------------
	protected abstract def getMessagesPage();

	protected abstract def createOwnerInstance();

	protected abstract def createOwnerWithMessages(def messagesCount);

	//-----------------------------------
	protected User createUser() {

		def user = new User(
		login: 'user' + User.count(),
		password: 'password' + User.count(),
		email: 'mail' + User.count() + '@mail.ru')

		user.save()
		assert user.id !=  null
		return user
	}

	protected Album createAlbum() {

		def userFrom = createUser();
		def album = new Album(name: "Album for test", shortDescription: "Short description to Album", user: userFrom)

		album.save()
		assert album.id != null
		return album
	}

	protected Picture createPicture() {

		def album = createAlbum()

		def picture = new Picture(originalName: "Picture 1")
		album.addToPictures(picture)
		picture.save()
		
		return picture
	}
}