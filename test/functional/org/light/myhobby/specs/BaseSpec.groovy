package org.light.myhobby.specs

import geb.spock.GebSpec
import geb.spock.GebReportingSpec
import spock.lang.Shared
import org.light.myhobby.User
import org.light.myhobby.pages.*
import org.light.myhobby.pages.user.*
import org.light.myhobby.ImageService

/**
 * @author Hanna_Aliakseichykava
 */
abstract class BaseSpec extends GebReportingSpec {

	@Shared protected def userService
	@Shared protected def imageService = new ImageService()

	@Shared protected String userLogin
	@Shared protected String password = "password"
	@Shared protected String email = "test@mail.ru"
	@Shared protected User createdUser

	private static final def STATIC_USER_LOGIN = "login-"

	protected static getNewLogin() {
		return STATIC_USER_LOGIN + User.count()
	}

	//Create User
	def setupSpec() {
		userLogin = getNewLogin()
		createdUser = new User(login: userLogin, password: password, email: email)
		createdUser.save()
	}

	def login() {

		to HomePage
		assert at(HomePage)

		loginModule.login(userLogin, password)
		waitFor('slow') {
			at(ShowUserPage)
		}
		assert loginModule.loginLabel.contains(userLogin)
	}

	def cleanupSpec() {
		to HomePage
		assert at(HomePage)

		loginModule.logout()

		assert at(HomePage)
		assert loginModule.loginLabel.contains("Guest")
	}
}