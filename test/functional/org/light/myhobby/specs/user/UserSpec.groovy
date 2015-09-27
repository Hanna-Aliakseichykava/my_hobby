package org.light.myhobby.specs.user

import org.light.myhobby.specs.BaseSpec
import org.light.myhobby.pages.HomePage
import org.light.myhobby.pages.user.EditUserPage
import org.light.myhobby.pages.user.ShowUserPage
import org.light.myhobby.pages.user.RegisterUserPage
import org.light.myhobby.pages.user.UserListPage

/**
 * @author Hanna_Aliakseichykava
 */
class UserSpec extends BaseSpec {

	def "Register user"() {

		given:
		def login = getNewLogin()

		when:
		to UserListPage

		then:
		at UserListPage

		when:
		newUserButton.click()

		then:
		at RegisterUserPage

		when: "New user form is filled"
		loginField.value(login)
		passwordField.value(password)
		passwordRepeatField.value(password)
		emailField.value(email)

		and: "Register button is clicked"
		registerButton.click()

		then:
		at ShowUserPage

		and: "Message is appeared"
		infoMessage == 'Welcome aboard, ' + login
	}

	def "open User Info"() {

		when:
		to UserListPage

		then:
		at UserListPage

		when:
		userLink(userLogin).click()

		then:
		at ShowUserPage
	}

	def "Log in as user" () {

		when:
		to HomePage

		then:
		at HomePage

		when: "Fill login and password"
		loginModule.loginField.value(userLogin)
		loginModule.passwordField.value(password)

		and:
		loginModule.enterButton.click()

		then:
		at ShowUserPage

		and:
		loginModule.loginLabel.contains(userLogin)
	}

	def "Update User's photo" () {

		given: "File"
		def fileName = "photoToSave.jpg"
		def filePath = "testFiles" + imageService.getSeparator() + fileName

		when:
		to HomePage

		and: "Login as User"
		loginModule.login(userLogin, password)

		then:
		at ShowUserPage

		when: "Edit User button is clicked"
		editUserButton.click()

		then:
		at EditUserPage

		//when: "Login is filled"
		//loginField.value(userLogin)

		when: "Photo is uploaded"
		selectFile(filePath)

		and: "Password is filled twice"
		passwordField.value(password)
		passwordRepeatField.value(password)

		and: "Update button is clicked"
		updateButton.click()

		then:
		at ShowUserPage

		and: "Message is appeared"
		infoMessage == 'Profile is updated, ' + userLogin
	}

	def "Update User's Login" () {

		given: "New login"
		def newLogin = "new-user-login"

		when:
		to HomePage

		and: "Login as User"
		loginModule.login(userLogin, password)

		then:
		at ShowUserPage

		when: "Edit User button is clicked"
		editUserButton.click()

		then:
		at EditUserPage

		when: "Login is changed"
		loginField.value(newLogin)

		and: "Password is filled twice"
		passwordField.value(password)
		passwordRepeatField.value(password)

		and: "Update button is clicked"
		updateButton.click()

		then:
		at ShowUserPage

		and: "Login is changed"
		loginLabel == newLogin

		and: "Message is appeared"
		infoMessage == 'Profile is updated, ' + newLogin
	}
}