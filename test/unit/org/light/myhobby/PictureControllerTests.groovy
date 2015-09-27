package org.light.myhobby

import org.junit.*
import grails.test.mixin.*
import org.springframework.mock.web.MockMultipartFile

@TestFor(PictureController)
@Mock([Picture, User, Album, Message])
class PictureControllerTests extends MessageControllerTests {

    private static def imageService = new ImageService()

    private static final String PICT_DESCR = "PictDesrc"

    private static final String FILE_NAME = "fileToSave.gif"
    private static final String FILE_TO_SAVE = "testFiles" + imageService.getSeparator() + FILE_NAME

    private static User user
    
    private static Album album

    @Before
    void setUpTest() {

        controller.imageService = imageService

        if(user == null) {
            user = new User(login: "userLogin", password: "password", email: "test@mail.ru").save()
        }
        
        if(album == null) {
            album = new Album(
            name: "AutotestAlbum", 
            shortDescription: "Short description to Album", 
            user: user).save()
        }

    }

    def populateValidParams(params) {
        
        assert params != null
        
        params["album"] = album
        params["shortDescription"] = PICT_DESCR
    }

	@Test
    void testIndex() {
        controller.index()
        assert response.redirectedUrl == "/picture/list"
    }

	@Test
    void testList() {

        def model = controller.list()

        assert model.pictureInstanceList.size() == 0
        assert model.pictureInstanceTotal == 0
    }

	@Test
    void testCreate() {

        def model = controller.create()

        assert model.ownerInstance != null
    }

	@Test
    void testSaveEmptyPictureInstance() {

        int count = Picture.count()
        controller.save(null)
        
        assert view == '/picture/create'
        assert Picture.count() == count
        assert model.ownerInstance == null
    }

	@Test
    void testSaveEmptyImage() {

        int count = Picture.count()
        controller.save(new Picture())
        
        assert view == '/picture/create'
        assert Picture.count() == count
        assert model.ownerInstance != null
		assert model.imageEmptyFailure
    }

	@Test
    void testSaveEmptyAlbum() {

        int count = Picture.count()

        request.addFile(new MockMultipartFile("image", FILE_NAME, "text/plain", new FileInputStream(FILE_TO_SAVE)))

        controller.save(new Picture())

        assert view == '/picture/create'
        assert model.ownerInstance != null

        assert Picture.count() == count
    }

	@Test
    void testSaveNullFile() {

        int count = Picture.count()
        request.files = null

        populateValidParams(params)

        controller.save(new Picture(params))

        assert view == '/picture/create'
        assert model.imageEmptyFailure == true
        assert model.ownerInstance != null

        assert Picture.count() == count
    }  
	
	@Test
	void testSaveEmptyFile() {

		int count = Picture.count()
		request.files = Arrays.asList(new File(""))

		populateValidParams(params)

		controller.save(new Picture(params))

		assert view == '/picture/create'
		assert model.imageEmptyFailure == true
		assert model.ownerInstance != null

		assert Picture.count() == count
	}

	@Test
    void testSave() {

        int count = Picture.count()
        controller.save(new Picture())
        
        assert view == '/picture/create'
        assert Picture.count() == count
        assert model.ownerInstance != null

        response.reset()

        request.addFile(new MockMultipartFile("image", FILE_NAME, "text/plain", new FileInputStream(FILE_TO_SAVE)))
        populateValidParams(params)

        controller.save(new Picture(params))

        assertTrue view == "/picture/show"
        assertTrue flash.message.contains("created") 
        assert Picture.count() == count + 1
    }

	@Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/picture/list'

        response.reset()

        int count = Picture.count()
        def picture = new Picture(["album": album])

        assert picture.save() != null
        assert Picture.count() == count + 1

        params.id = picture.id

        controller.show()

		assert view == '/picture/show'
        assert controller.flash.message == null
        assert model.ownerInstance == picture
    }

	@Test
    void testEdit() {

        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/picture/list'

        response.reset()

        def picture = new Picture(["album": album])
        assert picture.save() != null

        params.id = picture.id

        controller.edit()
		assertTrue view == "/picture/edit"
        assert model.ownerInstance == picture
    }

	@Test
    void testUpdateEmptyId() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/picture/list' 
    }

	@Test
    void testUpdateDescription() {

        def newDescription = "New description"

        def picture = new Picture(["album": album])
        assert picture.save() != null

        params.id = picture.id
        params["shortDescription"] = newDescription

        controller.update()

        assertTrue view == "/picture/show"
        assertTrue flash.message.contains("updated") 

        def editedPicture = Picture.get(picture.id)
        assert editedPicture.shortDescription == newDescription
    }

	@Test
    void testUpdateEmptyAlbum() {

        def picture = new Picture(["album": album])
        assert picture.save() != null

        params.id = picture.id
        params["album"] = null

        controller.update()

        assert view == "/picture/edit"
        assert model.ownerInstance != null
    }

	@Test
    void testUpdateAlbum() {

        def picture = new Picture(["album": album])
        assert picture.save() != null

        def newAlbum = new Album(
            name: "AutotestAlbum2", 
            shortDescription: "Short description to Album 2", 
            user: user).save()

        params.id = picture.id
        params["album"] = newAlbum

        controller.update()

        assertTrue view == "/picture/show"
        assertTrue flash.message.contains("updated") 

        def editedPicture = Picture.get(picture.id)
        assert editedPicture.album == newAlbum
    }

	@Test
    void testDelete() {

        int initialCount = Picture.count()

        controller.delete()
        assert response.redirectedUrl == '/picture/list'
        assert flash.message != null
        assert flash.message == "default.not.found.message"

        response.reset()

        def picture = new Picture(["album": album])

        assert picture.save() != null

        assert Picture.count() == initialCount + 1

        params.id = picture.id
        controller.delete()

        assert Picture.count() == initialCount
        assert Picture.get(picture.id) == null
        assert response.redirectedUrl == '/picture/list'
        assert flash.message != null
        assert flash.message.contains("deleted")

    }
    
     /*-------------------- Messages ------------------------*/    
     
     Picture createPicture() {

        def album = createAlbum() 

        def picture = new Picture(originalName: "Picture 1") 
        album.addToPictures(picture)
        //album.save()
        picture.save()

        return picture
    }

	@Override
    protected def createOwnerInstance() {
    	return createPicture()
    }

	@Override
    protected def createOwnerWithMessages() {

        def picture = createPicture()
        def userFrom = createUser()

        for(int i = 0; i < 5; ++ i) {
            def mess = new Message(userFrom: userFrom, content: "Message " + (i + 1))
            picture.addMessage(mess) 
        }

		//picture.save()
        return picture
    }

	@Override
    protected def getOwnerInstance(Long id) {
    	return Picture.get(id)
    }

	@Override
    protected def getExpectedViewPrefix() {
    	return "/picture"
    }
}
