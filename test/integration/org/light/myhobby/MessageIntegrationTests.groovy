package org.light.myhobby

import static org.junit.Assert.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
class MessageIntegrationTests {

	//User
	String login = 'joe'
	String password = 'secret'
	String email = 'joe@mail.ru'

	@Test
	void testSaveMessage() {

		def content = "Content " + Message.count()
		def message = saveMessage(content)

		def foundMessage = Message.get(message.id)
		assertTrue foundMessage.content == content
		assert foundMessage.id != null
		assert foundMessage.dateCreated != null
		assert foundMessage.lastUpdated != null
	}

	@Test
	void testUpdateMessage() {

		def content = "Content " + Message.count()
		def message = saveMessage(content)

		def foundMessage = Message.get(message.id)

		String newContent = 'newContent'

		assertTrue foundMessage.content == content

		foundMessage.content = newContent
		foundMessage.save()

		def editedMessage = Message.get(message.id)
		assertTrue editedMessage.content == newContent
	}

	@Test
	void testDeleteMessage() {

		def content = "Content " + Message.count()
		def message = saveMessage(content)
		assertTrue Message.exists(message.id)

		def foundMessage = Message.get(message.id)
		foundMessage.delete()

		assertFalse Message.exists(message.id)

	}

	@Test
	void testWrongMessage() {

		def message = new Message()

		assertFalse message.validate()
		assertTrue message.hasErrors()

		def errors = message.errors

		assertEquals "nullable", errors.getFieldError("userFrom").code
		assertEquals "nullable", errors.getFieldError("content").code
	}

	@Test
	void testAddMessageToUser() {

		def user = createUser()
		def content = "Content " + Message.count()

		def message = new Message(userFrom: user, content: content)
		user.addMessage(message)

		def foundUser = User.get(user.id)

		//assert foundUser.wallMessages.size() == 1

		//def messagesContent = foundUser.wallMessages.collect { it.content }
		//assert messagesContent == [content]

		def foundMessages = foundUser.getMessages()
		assert foundMessages.size() == 1
		def messagesContent = foundMessages.collect { it.content }
		assert messagesContent == [content]

		def foundMessage = foundMessages.getAt(0)
		assert foundMessage.id != null
		assert foundMessage.dateCreated != null
		assert foundMessage.lastUpdated != null
	}

	@Test
	void testAddMessageToAlbum() {

		def album = createAlbum()
		def user = album.user
		def content = "Content " + Message.count()

		def message = new Message(userFrom: user, content: content)
		album.addMessage(message)

		assert album.id != null
		def foundAlbum = Album.get(album.id)

		//assert foundAlbum.comments.size() == 1

		//def messagesContent = foundAlbum.comments.collect { it.content }

		//assert messagesContent == [content]

		def foundMessages = foundAlbum.getMessages()
		assert foundMessages.size() == 1
		def messagesContent = foundMessages.collect { it.content }
		assert messagesContent == [content]

		def foundMessage = foundMessages.getAt(0)
		assert foundMessage.id != null
		assert foundMessage.dateCreated != null
		assert foundMessage.lastUpdated != null
	}

	@Test
	void testAddMessageToPicture() {

		def picture = createPicture()
		def user = picture.album.user
		def content = "Content " + Message.count()

		def message = new Message(userFrom: user, content: content)
		picture.addMessage(message)

		assert picture.id != null
		def foundPicture = Picture.get(picture.id)

		//assert foundPicture.comments.size() == 1

		//def messagesContent = foundPicture.comments.collect { it.content }

		//assert messagesContent == [content]

		def foundMessages = foundPicture.getMessages()
		assert foundMessages.size() == 1
		def messagesContent = foundMessages.collect { it.content }
		assert messagesContent == [content]

		def foundMessage = foundMessages.getAt(0)
		assert foundMessage.id != null
		assert foundMessage.dateCreated != null
		assert foundMessage.lastUpdated != null
	}


	@Test
	void testGetWallCommentsForUser() {
		User user = createUserWithWallMessages();

		//assert user.wallMessages.size() == MESSAGES_COUNT
		assert user.getMessages().size() == MESSAGES_COUNT

		def foundMessages = Message.getCommentsForOwner(user)
		assert foundMessages.size() == MESSAGES_COUNT
		def contents = foundMessages.collect { it.content }
		assertEquals(EXPECTED_MESSAGES, contents.toArray().sort())
	}

	@Test
	void testGetCommentsForAlbum() {
		Album album = createAlbumWithMessages();

		//assert album.comments.size() == MESSAGES_COUNT
		assert album.getMessages().size() == MESSAGES_COUNT

		def foundMessages = Message.getCommentsForOwner(album)
		assert foundMessages.size() == MESSAGES_COUNT
		def contents = foundMessages.collect { it.content }
		assertEquals(EXPECTED_MESSAGES, contents.toArray().sort())
	}

	@Test
	void testGetCommentsForPicture() {
		Picture picture = createPictureWithMessages();

		//assert picture.comments.size() == MESSAGES_COUNT
		assert picture.getMessages().size() == MESSAGES_COUNT

		def foundMessages = Message.getCommentsForOwner(picture)
		assert foundMessages.size() == MESSAGES_COUNT
		def contents = foundMessages.collect { it.content }
		assertEquals(EXPECTED_MESSAGES, contents.toArray().sort())
	}

	@Test
	void testGetWallCommentsDefaultPagination() {
		User user = createUserWithWallMessages();

		//assert user.wallMessages.size() == MESSAGES_COUNT
		assert user.getMessages().size() == MESSAGES_COUNT

		def foundMessages = Message.getCommentsForOwner(user)
		assert foundMessages.size() == MESSAGES_COUNT
		def contents = foundMessages.collect { it.content }
		assertEquals(EXPECTED_MESSAGES, contents.toArray().sort())

		def params = [:]
		foundMessages = Message.getCommentsForOwner(user, params)
		assert foundMessages.size() == MESSAGES_COUNT

		params = [:]
		params.max = 10;
		foundMessages = Message.getCommentsForOwner(user, params)
		assert foundMessages.size() == MESSAGES_COUNT
	}

	@Test
	void testGetWallCommentsPagination() {
		def messagesCount = 15
		User user = createUserWithWallMessages(messagesCount);

		//assert user.wallMessages.size() == messagesCount
		assert user.getMessages().size() == messagesCount

		def max = 10;
		def offset = 10;
		def params = [:]
		params.max = max;
		params.offset = offset;
		def foundMessages = Message.getCommentsForOwner(user, params)
		assert foundMessages.size() == messagesCount - offset
	}

	@Test
	void testGetWallCommentsMessageFields() {
		User user = createUserWithWallMessages();

		//assert user.wallMessages.size() == MESSAGES_COUNT
		assert user.getMessages().size() == MESSAGES_COUNT

		def foundMessages = Message.getCommentsForOwner(user)
		assert foundMessages.size() == MESSAGES_COUNT
		def contents = foundMessages.collect { it.content }
		assertEquals(EXPECTED_MESSAGES, contents.toArray().sort())

		Message firstMessage = foundMessages.get(0)
		assert firstMessage.id != null
		assert firstMessage.dateCreated != null
		assert firstMessage.lastUpdated != null
	}

	@Test
	void testUpdateOwnerMessage() {
		def newContent = "new content"
		User user = createUserWithWallMessages(1);

		assert user.getMessages().size() == 1

		def foundMessages = user.getMessages().toList()
		Message firstMessage = foundMessages.get(0)
		assert firstMessage.id != null
		assert firstMessage.content != newContent

		firstMessage.content = newContent
		user.updateMessage(firstMessage)
		assert user.getMessages().size() == 1

		def editedMessage = Message.get(firstMessage.id)
		assert editedMessage.id != null
		assertTrue editedMessage.content == newContent

		foundMessages = user.getMessages().toList()
		firstMessage = foundMessages.get(0)
		assert firstMessage.id != null
		assert firstMessage.content == newContent
	}

	@Test
	void testDeleteOwnerMessage() {

		User user = createUserWithWallMessages(2);

		assert user.getMessages().size() == 2

		def foundMessages = user.getMessages().toList()
		Message firstMessage = foundMessages.get(0)
		assert firstMessage.id != null
		def firstId = firstMessage.id

		user.deleteMessage(firstMessage)
		assert user.getMessages().size() == 1

		foundMessages = user.getMessages().toList()
		def foundMessage = foundMessages.get(0)
		assert foundMessage.id != null
		assert foundMessage.id != firstId

	}
	/*---------------------------------------------*/

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

		def user = createUser()

		def album = new Album(name: "Album 1", shortDescription: "Short description to Album 1")
		user.addToAlbums(album)
		user.save()
		assert album.id !=  null

		return album
	}

	Picture createPicture() {

		def album = createAlbum()

		def picture = new Picture(originalName: "Picture 1")
		album.addToPictures(picture)
		album.save()
		assert picture.id !=  null

		return picture
	}

	Message saveMessage(String content) {

		def user = createUser()

		def message = new Message(userFrom: user, content: content)

		assertNotNull message.save()
		assertNotNull message.id

		return message
	}

	//------------------------------------

	private static final int MESSAGES_COUNT = 5;
	private static final String[] EXPECTED_MESSAGES =
	["Message 1", "Message 2", "Message 3", "Message 4", "Message 5"];

	private User createUserWithWallMessages() {
		return createUserWithWallMessages(MESSAGES_COUNT)
	}
	private User createUserWithWallMessages(def messagesCount) {

		def userWithWall = createUser()
		def userFrom = createUser()

		for(int i = 0; i < messagesCount; ++ i) {
			def mess = new Message(userFrom: userFrom, content: "Message " + (i + 1))
			userWithWall.addMessage(mess)
		}

		//userWithWall.save()
		return userWithWall
	}

	private Album createAlbumWithMessages() {

		def album = createAlbum()
		def user = createUser()

		for(int i = 0; i < MESSAGES_COUNT; ++ i) {
			def mess = new Message(userFrom: user, content: "Message " + (i + 1))
			album.addMessage(mess)
		}

		//album.save()
		return album
	}

	private Picture createPictureWithMessages() {

		def picture = createPicture()
		def user = createUser()

		for(int i = 0; i < MESSAGES_COUNT; ++ i) {
			def mess = new Message(userFrom: user, content: "Message " + (i + 1))
			picture.addMessage(mess)
		}

		//picture.save()
		return picture
	}
}
