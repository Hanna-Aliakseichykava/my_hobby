package org.light.myhobby

import grails.test.mixin.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
@TestFor(AlbumController)
@Mock([Album, User, Message])
class AlbumControllerTests extends MessageControllerTests {

	def imageService = new ImageService()

	//User
	String login = 'joe'
	String password = 'secret'
	String email = 'joe@mail.ru'

	def createdUser

	void setUp() {
		createdUser = new User(login: login, password: password, email: email)
		createdUser.save()
		assert createdUser.id != null
	}

	@Test
	void testCreateAction() {
		controller.create()
		assertTrue view == "/album/create"
	}

	@Test
	void testCreateAlbumSuccessful() {

		controller.imageService = imageService

		def albumName = "Album 1"
		def album = new Album(name: albumName, user: createdUser)

		assert album.validate()
		controller.createAlbum(album)
		assert view == "/album/show"
		assert flash.message != null
		assert flash.message.contains("created")

		assert Album.count() > 0
		assert createdUser.albums.size() > 0
		assert Album.findByName(albumName) != null
		assert imageService.isFileExists(imageService.getAlbumPath(album))
	}

	@Test
	void testCreateFailedNullUser() {

		def albumName = "Album 1"
		def album = new Album(name: albumName)

		assert !album.validate()
		assertEquals "nullable", album.errors["user"].code

		controller.createAlbum(album)
		assert view == "/album/create"
		assert model.ownerInstance != null
	}

	@Test
	void testCreateFailedShortName() {

		def albumName = "Al"
		def album = new Album(name: albumName, user: createdUser)

		assertFalse album.validate()
		assertEquals "size.toosmall", album.errors["name"].code

		controller.createAlbum(album)
		assertTrue view == "/album/create"
		assert model.ownerInstance != null
	}

	@Test
	void testEdit() {

		controller.edit()

		assert flash.message != null
		assert response.redirectedUrl == '/album/list'

		response.reset()

		def album = createAlbum()

		params.id = album.id

		controller.edit()

		assert view == "/album/edit"
		assert model.ownerInstance == album
	}

	@Test
	void testUpdateEmptyId() {

		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/album/list'
	}

	@Test
	void testUpdateDescription() {

		def newDescription = "New description"

		def album = createAlbum()

		params.id = album.id
		params["shortDescription"] = newDescription

		controller.update()

		assert view == "/album/show"
		assert flash.message != null
		assert flash.message.contains("updated")

		def editedAlbum = Album.get(album.id)
		assert editedAlbum.shortDescription == newDescription
	}

	@Test
	void testUpdateName() {

		def newName = "New name"

		def album = createAlbum()

		params.id = album.id
		params["name"] = newName

		controller.update()

		assert view == "/album/show"
		assert flash.message != null
		assert flash.message.contains("updated")

		def editedAlbum = Album.get(album.id)
		assert editedAlbum.name == newName
	}

	@Test
	void testDelete() {

		int initialCount = Album.count()

		controller.delete()
		assert response.redirectedUrl == '/album/list'
		assert flash.message != null
		assert flash.message == "default.not.found.message"

		response.reset()

		def album = createAlbum()

		assert Album.count() == initialCount + 1

		params.id = album.id
		controller.delete()

		assert Album.count() == initialCount
		assert Album.get(album.id) == null

		assert response.redirectedUrl == '/album/list'
		assert flash.message != null
		assert flash.message.contains("deleted")
	}

	@Test
	void testShow() {

		controller.show()

		assert flash.message != null
		assert response.redirectedUrl == '/album/list'

		response.reset()

		def album = createAlbum()

		params.id = album.id

		controller.show()

		assert view == '/album/show'
		assert flash.message == null
		assert model.ownerInstance == album
	}

	/*-------------------- Messages ------------------------*/    

	@Override
	protected def createOwnerInstance() {
		return createAlbum()
	}

	@Override
	protected def createOwnerWithMessages() {

		def album = createAlbum()
		def userFrom = createUser()

		for(int i = 0; i < 5; ++ i) {
			def mess = new Message(userFrom: userFrom, content: "Message " + (i + 1))
			album.addMessage(mess)
		}

		//album.save()
		return album
	}

	@Override
	protected def getOwnerInstance(Long id) {
		return Album.get(id)
	}

	@Override
	protected def getExpectedViewPrefix() {
		return "/album"
	}
}
