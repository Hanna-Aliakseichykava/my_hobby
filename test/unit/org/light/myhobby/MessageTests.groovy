package org.light.myhobby

import grails.test.mixin.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
@TestFor(Message)
class MessageTests {

	private static final int MAX_CONTENT_LENGTH = 2000

	@Test
	void testConstraintsNull() {

		def message = new Message()

		assertFalse message.validate()
		assertTrue message.hasErrors()

		assertEquals "nullable", message.errors["userFrom"].code
		assertEquals "nullable", message.errors["content"].code
	}

	@Test
	void testConstraintsNull2() {

		def message = new Message(userFrom: null, content: null)

		assertFalse message.validate()
		assertTrue message.hasErrors()

		assertEquals "nullable", message.errors["userFrom"].code
		assertEquals "nullable", message.errors["content"].code
	}

	@Test
	void testConstraintsContentSize() {

		def user = new User(login: 'login1', password: 'password1', email: 'email1@mail.ru')

		def message = new Message(userFrom: user, content: "")

		assertFalse message.validate()
		assertTrue message.hasErrors()

		assertEquals "nullable", message.errors["content"].code

		message = new Message(userFrom: user, content: "a".multiply(MAX_CONTENT_LENGTH + 1))

		assertFalse message.validate()
		assertTrue message.hasErrors()

		assertEquals "maxSize.exceeded", message.errors["content"].code
	}

	@Test
	void testConstraintsPass() {

		def user = new User(login: 'login1', password: 'password1', email: 'email1@mail.ru')
		def message = new Message(userFrom: user, content: "Content " + Message.count())

		assertTrue message.validate()
		assertFalse message.hasErrors()
	}
}
