package org.light.myhobby

import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
abstract class MessageControllerTests {

	private static final int MAX_CONTENT_LENGTH = 2000

	@Test
	void testWall() {

		controller.wall()

		assert flash.message != null
		assert response.redirectedUrl == getExpectedViewPrefix() + '/list'

		response.reset()

		def owner = createOwnerWithMessages()
		def sessionUser = createUser()

		session.user = sessionUser
		controller.wall(owner.id)

		assert view == getExpectedView()

		assert model['ownerInstance']?.id == owner.id
		assert model['messageInstanceTotal'] > 0
		assert model['messageInstanceTotal'] == owner.getMessages().size()
		assert model['messageInstanceList'] != null
		assert model['messageInstanceList'].size() == owner.getMessages().size()

		assert model['messageInstance'] != null
		assert model['messageInstance']?.userFrom?.login == sessionUser.login
		assert model['messageInstance']?.content == null
	}

	@Test
	void testWallEmptyMessages() {
		def owner = createOwnerInstance()

		controller.wall(owner.id)

		assert controller.flash.message == null

		assert view == getExpectedView()

		assert model['ownerInstance']?.id == owner.id
		assert model['messageInstanceTotal'] == 0
		assert model['messageInstanceList'] != null
		assert model['messageInstanceList'] == []

		assert model['messageInstance'] != null
	}

	@Test
	void testCreateWallMessageNullable() {

		controller.saveMessage()

		assert flash.message != null
		assert response.redirectedUrl == getExpectedViewPrefix() + '/list'

		response.reset()

		def owner = createOwnerWithMessages()

		def userFrom = createUser()
		session.user = userFrom
		controller.saveMessage(owner.id, null)

		assert view == getExpectedView()

		assert model['ownerInstance'] != null
		assert model['ownerInstance'] == owner
		assert model['ownerInstance']?.id == owner.id

		assert model['messageInstance'] != null
		assert model['messageInstance'].userFrom.login == userFrom.login
		assert model['messageInstance'].hasErrors() == false
	}

	@Test
	void testCreateWallMessageContentError() {
		def owner = createOwnerWithMessages()

		def message = new Message(userFrom: null, content: "")

		controller.saveMessage(owner.id, message)

		assert view == getExpectedView()

		assert model['ownerInstance'] != null
		assert model['ownerInstance'] == owner
		assert model['ownerInstance']?.id == owner.id

		assert model['messageInstance'] != null
		assert model['messageInstance'].hasErrors()
		assert model['messageInstance'].errors["userFrom"].code == "nullable"
		assert model['messageInstance'].errors["content"].code == "nullable"

		response.reset()

		message = new Message(userFrom: null, content: "a".multiply(MAX_CONTENT_LENGTH + 1))

		controller.saveMessage(owner.id, message)

		assert view == getExpectedView()

		assert model['ownerInstance'] != null
		assert model['ownerInstance'] == owner
		assert model['ownerInstance']?.id == owner.id

		assert model['messageInstance'] != null
		assert model['messageInstance'].hasErrors()
		assert model['messageInstance'].errors["userFrom"].code == "nullable"
		assert model['messageInstance'].errors["content"].code == "maxSize.exceeded"
	}

	@Test
	void testCreateWallMessage() {

		def owner = createOwnerInstance()

		String messageContent = "a".multiply(10)
		def message = new Message(userFrom: null, content: messageContent)

		controller.saveMessage(owner.id, message)
		assert model['messageInstance'].errors != null

		assert model['messageInstance'] != null
		assert model['messageInstance'].hasErrors()
		assert model['messageInstance'].errors["userFrom"].code == "nullable"
		assert model['messageInstance'].errors["content"] == null


		def userFrom = createUser()
		message = new Message(userFrom: userFrom, content: messageContent)
		controller.saveMessage(owner.id, message)

		def foundOwner = getOwnerInstance(owner.id)

		assert foundOwner != null
		def foundMessages = foundOwner.getMessages()
		assert foundMessages.size() == 1
		assert foundMessages.collect { it.content } == [messageContent]
		assert foundMessages.collect { it.userFrom } == [userFrom]

		assert view == getExpectedView()

		assert model['ownerInstance'] != null
		assert model['ownerInstance'] == owner

		assert model['messageInstance'] != null
		assert !model['messageInstance'].hasErrors()
		assert model['messageInstanceTotal'] == 1
		assert model['messageInstanceList'].size() == 1
		
		Message firstMessage = foundMessages.getAt(0)
		assert firstMessage.id != null
		assert firstMessage.content == messageContent
		assert firstMessage.userFrom.login == userFrom.login
		assert firstMessage.dateCreated != null
		assert firstMessage.lastUpdated != null
	}

	@Test
	void testEditMessageAction() {

		def owner = createOwnerInstance()
		def userFrom = createUser()

		String messageContent = "a".multiply(10)
		def message = new Message(userFrom: userFrom, content: messageContent)
		controller.saveMessage(owner.id, message)
		assert message.id != null

		def foundOwner = getOwnerInstance(owner.id)
		assert foundOwner != null
		def foundMessages = foundOwner.getMessages()
		assert foundMessages.size() == 1

		controller.editMessage(message.id, owner.id)

		assert view == getExpectedView()

		assert model['ownerInstance'] != null
		assert model['ownerInstance'] == owner

		assert model['messageInstance'] != null
		assert !model['messageInstance'].hasErrors()
		assert model['messageInstance'].id != null
		assert model['messageInstance'].id == message.id

		assert model['messageInstanceTotal'] == 1
		assert model['messageInstanceList'].size() == 1
	}

	@Test
	void testUpdateMessage() {

		def owner = createOwnerInstance()
		def userFrom = createUser()

		String messageContent = "a".multiply(10)
		def message = new Message(userFrom: userFrom, content: messageContent)
		controller.saveMessage(owner.id, message)
		assert message.id != null

		def foundOwner = getOwnerInstance(owner.id)
		assert foundOwner != null
		def foundMessages = foundOwner.getMessagesForOwner()
		assert foundMessages.size() == 1

		def newMessageContent = "b".multiply(10)
		message.content = newMessageContent
		controller.saveMessage(owner.id, message)

		foundOwner = getOwnerInstance(owner.id)
		assert foundOwner != null
		foundMessages = foundOwner.getMessagesForOwner()
		assert foundMessages.size() == 1
		assert foundMessages.collect { it.content } == [newMessageContent]
		assert foundMessages.collect { it.userFrom } == [userFrom]

		assert view == getExpectedView()

		assert model['ownerInstance'] != null
		assert model['ownerInstance'] == owner

		assert model['messageInstance'] != null
		assert !model['messageInstance'].hasErrors()
		assert model['messageInstance'].id == null

		assert model['messageInstanceTotal'] == 1
		assert model['messageInstanceList'].size() == 1
	}

	@Test
	void testDeleteMessage() {

		def owner = createOwnerInstance()
		def userFrom = createUser()

		String messageContent = "a".multiply(10)
		def message = new Message(userFrom: userFrom, content: messageContent)
		controller.saveMessage(owner.id, message)
		assert message.id != null

		def foundOwner = getOwnerInstance(owner.id)
		assert foundOwner != null
		def foundMessages = foundOwner.getMessagesForOwner()
		assert foundMessages.size() == 1

		controller.deleteMessage(message.id, owner.id)

		foundOwner = getOwnerInstance(owner.id)
		assert foundOwner != null
		foundMessages = foundOwner.getMessagesForOwner()
		assert foundMessages.size() == 0

		assert view == getExpectedView()

		assert model['ownerInstance'] != null
		assert model['ownerInstance'] == owner

		assert model['messageInstance'] != null
		assert !model['messageInstance'].hasErrors()
		assert model['messageInstance'].id == null

		assert model['messageInstanceTotal'] == 0
		assert model['messageInstanceList'].size() == 0
	}

	//------------------------
	User createUser() {

		def user = new User(
				login: 'user' + User.count(),
				password: 'password' + User.count(),
				email: 'mail' + User.count() + '@mail.ru')

		user.save()
		assert user.id !=  null
		return user
	}

	Album createAlbum() {

		def userFrom = createUser();
		def album = new Album(name: "Album for test", shortDescription: "Short description to Album", user: userFrom)

		album.save()
		assert album.id != null
		return album
	}

	//------------------------
	protected abstract def createOwnerInstance();

	protected abstract def createOwnerWithMessages();

	protected abstract def getOwnerInstance(Long id);

	protected abstract def getExpectedViewPrefix();

	protected def getExpectedView() {
		return getExpectedViewPrefix() + "/show"
	}
}