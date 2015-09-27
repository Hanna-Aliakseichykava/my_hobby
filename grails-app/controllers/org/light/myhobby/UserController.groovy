package org.light.myhobby

/**
 * @author Hanna_Aliakseichykava
 */
class UserController extends MessageController {

	static scaffold = User

	def imageService

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[userInstanceList: User.list(params), userInstanceTotal: User.count()]
	}

	def search = {
		render(view: "search")
	}

	def show(Long id) {

		def instance = User.get(id)
		if (!instance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
			return
		}

		flash.message = null
		render(view: "show", model: [ownerInstance: instance])
	}

	def displayWall(Long id) {
		flash.message = null
		wall(id)
	}

	def results = {
		def users = User.findAllByLoginLike("%${params.login}%")
		return [users: users, term: params.login]
	}

	def register = {
		render(view: "register")
	}

	def edit (Long id) {
		def foundUser = User.get(id)

		if (!foundUser) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
			return
		}

		def props = [login:foundUser.login, email:foundUser.email,
			fullName:foundUser.profile?.fullName, biography:foundUser.profile?.biography,
			timezone:foundUser.profile?.timezone, country:foundUser.profile?.country]
		def urc = new UserRegistrationCommand(props)
		render(view: "edit", model: [ownerInstance: foundUser, user: urc])
	}


	def registerUser = { UserRegistrationCommand urc ->

		if(urc.hasErrors()) {
			render(view: "register", model: [user: urc])
			return
		}

		def user = new User(urc.properties)
		user.profile = new Profile(urc.properties)

		saveUserInfo(user, urc, "register", "Welcome aboard")
	}

	def updateUser = { Long id, Long version, UserRegistrationCommand urc ->

		def user = User.get(id)

		if (!user) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
			return
		}

		if(urc.hasErrors()) {
			render(view: "edit", model: [ownerInstance: user, user: urc])
			return
		}

		user.properties = urc.properties
		if(user.profile != null) {
			user.profile.properties = urc.properties
		} else {
			user.profile = new Profile(urc.properties)
		}

		saveUserInfo(user, urc, "edit", "Profile is updated")
	}

	def cancelRegister = {  redirect(uri: '/') }

	//-----------------------------------
	def saveUserInfo(User user, UserRegistrationCommand urc, String unsuccessView, String messagePrefix) {

		//log.info("Save user info [$urc]")
		flash.message = null

		def photo = request.getFile('photo')
		def background = request.getFile('background')

		if (imageService?.isFileToLarge(photo, imageService?.MAX_PHOTO_SIZE)) {
			render(view: unsuccessView, model: [user: urc, ownerInstance: user, photoFailure: true])
			return
		}

		if (imageService?.isFileToLarge(background, imageService?.MAX_BACKGROUND_SIZE)) {
			render(view: unsuccessView, model: [user: urc, ownerInstance: user, backgroundFailure: true])
			return
		}

		if(user.save()) {
			if(photo != null) imageService.savePhoto(photo, params.login)
			if(background != null) imageService.saveBackground(background, params.login)

			flash.message = messagePrefix + ", ${urc.login}"
			render(view: "show", model: [ownerInstance: user])
		}
		else{
			render(view: unsuccessView, model: [user: urc, ownerInstance: user])
		}
	}

	//-----------------------------------
	@Override
	protected def getOwnerInstance(Long id) {

		def instance = User.get(id)
		if (!instance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(action: "list")
			return null
		}
		return instance
	}

	@Override
	protected def getWallView() {
		return "wall"
	}

}


class UserRegistrationCommand {

	String login
	String password
	String passwordRepeat
	String email

	String fullName
	String biography
	String timezone
	String country

	static constraints = {
		login(size: 3.. 20, blank: false)
		password(size: 6.. 20, blank: false)
		passwordRepeat(nullable: false, validator: {passwd2, urc -> return passwd2 == urc.password})
		email(blank: false, email: true)

		fullName(nullable: true)
		biography(nullable: true, maxSize: 1000)
		country(nullable: true)
		timezone(nullable: true)
	}

}
