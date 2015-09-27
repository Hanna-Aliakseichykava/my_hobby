package org.light.myhobby

import grails.test.mixin.*

import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
@TestFor(UserController)
@Mock([User, Message])
class UserControllerTests extends MessageControllerTests {

	void setUp() {
		defineBeans { 
			/*someService(SomeService) { bean ->
				bean.autowire = true
			}*/
			imageService(ImageService)
		}
	}

	/*-------------------- Search ------------------------*/

	@Test
	void testSearchAction() {
		controller.search()
		assertTrue view == "/user/search"
	}

	@Test
	void testResults() {

		def user1 = createUser()
		def user2 = createUser()

		controller.params.login = 'user'
		def model = controller.results()

		assertEquals 'user', model['term']
		assertEquals 2, model['users']?.size()
	}

	/*-------------------- User ------------------------*/

	@Test
	void testRegisterAction() {
		controller.register()
		assertTrue view == "/user/register"
	}

	@Test
	void testRegisterFailedWrongRepetedPassword() {
		mockCommandObject(UserRegistrationCommand)

		def testCmd = new UserRegistrationCommand(login: "user1", password: "password1", passwordRepeat: "password2", email: "my@mail.ru")
		assertFalse testCmd.validate()
		assertEquals "validator.invalid", testCmd.errors["passwordRepeat"].code

		controller.registerUser(testCmd)
		assertTrue flash.message == null
		assertTrue view == "/user/register"
	}

	@Test
	void testRegisterFailedNullEmail() {
		mockCommandObject(UserRegistrationCommand)

		def testCmd = new UserRegistrationCommand(login: "user1", password: "password1", passwordRepeat: "password1")

		assertFalse testCmd.validate()
		assertEquals "nullable", testCmd.errors["email"].code

		controller.registerUser(testCmd)
		assertTrue flash.message == null
		assertTrue view == "/user/register"
	}

	@Test
	void testRegisterFailedWrongEmail() {
		mockCommandObject(UserRegistrationCommand)

		def testCmd = new UserRegistrationCommand(login: "user1", password: "password1", passwordRepeat: "password1", email: "wrongemail")

		assertFalse testCmd.validate()
		assertEquals "email.invalid", testCmd.errors["email"].code

		controller.registerUser(testCmd)
		assertTrue flash.message == null
		assertTrue view == "/user/register"
	}

	@Test
	void testRegisterSuccessful() {

		mockCommandObject(UserRegistrationCommand)

		String login = "user1";
		String password = "password1";
		def testCmd = new UserRegistrationCommand(login: login, password: password, passwordRepeat: password, email: "my@mail.ru")

		assertTrue testCmd.validate()
		controller.registerUser(testCmd)
		assertTrue view == "/user/show"
		assertTrue flash.message == "Welcome aboard, ${login}"

		assertTrue User.count() > 0
		assertNotNull User.findByLoginAndPassword(login, password)
	}

	@Test
	void testCancelRegisterAction() {
		controller.cancelRegister()
		assertTrue view != "/user/register"
		assert !response.redirectedUrl.contains("user")
	}

	@Test
	void testEditActionFailed() {
		controller.edit(null)
		assert response.redirectedUrl.contains("/user/list")
	}

	@Test
	void testEditAction() {

		mockCommandObject(UserRegistrationCommand)

		def userLogin = 'user1'
		def userPass = 'password1'
		def user = new User(login: userLogin, password: userPass, email: 'mail1@mail.ru')
		user.save()

		controller.edit(user.id)

		assertEquals "/user/edit", view
		assertEquals userLogin, model['user']?.login
		assertEquals user.id, model['ownerInstance']?.id
	}

	def OLD_EMAIL = "old-my@mail.ru"
	def NEW_EMAIL = "new-my@mail.ru"
	def OLD_LOGIN = "old-user1"
	def NEW_LOGIN = "new-user1"

	private int populateValidParamsAndCreateUser(params) {
		assert params != null
		params["login"] = OLD_LOGIN
		params["password"] = "password1"
		params["passwordRepeat"] = "password1"
		params["email"] = OLD_EMAIL

		def user = new User(params)

		assert user.save() != null
		return user.id
	}

	@Test
	void testUpdateUserValid() {

		def userId = populateValidParamsAndCreateUser(params)
		params.id = userId

		controller.updateUser()

		assertTrue view == "/user/show"
		assertTrue flash.message.contains("Profile is updated")
	}

	@Test
	void testUpdateUserValidChangeEmail() {

		def userId = populateValidParamsAndCreateUser(params)
		assert User.get(userId).email == OLD_EMAIL

		params.id = userId
		params.email = NEW_EMAIL

		controller.updateUser()

		assertTrue view == "/user/show"
		assertTrue flash.message.contains("Profile is updated")
		assert User.get(userId).email == NEW_EMAIL
	}

	@Test
	void testUpdateUserValidChangeLogin() {

		def userId = populateValidParamsAndCreateUser(params)
		assert User.get(userId).login == OLD_LOGIN

		params.id = userId
		params.login = NEW_LOGIN

		controller.updateUser()

		assertTrue view == "/user/show"
		assertTrue flash.message.contains("Profile is updated")
		assert User.get(userId).login == NEW_LOGIN
	}

	@Test
	void testUpdateUserEmptyCommand() {
		controller.updateUser()

		assert flash.message != null
		assert response.redirectedUrl == '/user/list'
	}

	@Test
	void testUpdateUserInvalidParameters() {

		params.id = populateValidParamsAndCreateUser(params)

		params.email = "BadEmail"

		controller.updateUser()

		assert view == "/user/edit"
		assert model.user != null
		assert model.user.errors.getFieldError('email')
	}

	@Test
	void testShow() {

		controller.show()

		assert flash.message != null
		assert response.redirectedUrl == '/user/list'

		response.reset()

		def userId = populateValidParamsAndCreateUser(params)

		params.id = userId

		controller.show()

		assert view == '/user/show'
		assert model.ownerInstance != null
		assert model.ownerInstance.id == userId
		assert controller.flash.message == null
	}

	@Test
	void testDisplayWall() {

		controller.displayWall()

		assert flash.message != null
		assert response.redirectedUrl == '/user/list'

		response.reset()

		def userId = populateValidParamsAndCreateUser(params)

		controller.displayWall(userId)

		assert view == '/user/wall'
		assert model.ownerInstance != null
		assert model.ownerInstance.id == userId
		assert controller.flash.message == null
	}

	/*-------------------- Messages ------------------------*/    

	@Override
	protected def createOwnerInstance() {
		return createUser()
	}

	@Override
	protected def createOwnerWithMessages() {

		def userTo = createUser()
		def userFrom = createUser()

		for(int i = 0; i < 5; ++ i) {
			def mess = new Message(userFrom: userFrom, content: "Message " + (i + 1))
			userTo.addMessage(mess)
		}

		return userTo
	}

	@Override
	protected def getOwnerInstance(Long id) {
		return User.get(id)
	}

	@Override
	protected def getExpectedViewPrefix() {
		return "/user"
	}

	protected def getExpectedView() {
		return getExpectedViewPrefix() + "/wall"
	}
}