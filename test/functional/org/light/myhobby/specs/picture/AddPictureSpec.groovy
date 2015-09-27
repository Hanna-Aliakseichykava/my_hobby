package org.light.myhobby.specs.picture

import spock.lang.Unroll
import org.light.myhobby.Album
import org.light.myhobby.specs.BaseSpec
import org.light.myhobby.pages.*
import org.light.myhobby.pages.album.*
import org.light.myhobby.pages.picture.*

/**
 * @author Hanna_Aliakseichykava
 */
class AddPictureSpec extends BaseSpec {

	def setup() {
		login()
	}

	def "Add empty picture" () {

		given: "Created album"
		Album album = new Album(name: "AutotestAlbum", shortDescription: "Short description to Album", user: createdUser).save()

		when:
		to ShowAlbumPage, album.id

		then:
		at ShowAlbumPage

		and:
		addPictureLink.click()

		then:
		at CreatePicturePage

		when:
		createButton.click()

		then:
		at CreatePicturePage
		message.errorDisplayed()
		message.getErrorMessage().contains("Please, select image")
	}

	@Unroll
	def "Add picture" () {

		given: "Created album"
		Album album = new Album(name: "AutotestAlbum", shortDescription: "Short description to Album", user: createdUser).save()

		when:
		to page, album.id

		then:
		at page

		and:
		addPictureLink.click()

		then:
		waitFor('slow') {
			at CreatePicturePage
		}

		when:
		selectFile(filePath)

		and:
		descriptionField = description

		and:
		createButton.click()

		then:
		at ShowPicturePage
		message.infoDisplayed()
		message.getInfoMessage().contains("Picture")
		message.getInfoMessage().contains("created")

		and:
		descriptionLabel == description
		originalNameLabel == fileName
		albumLink.text() == album.toString()

		when:
		to page, album.id

		then:
		at page

		and:
		firstPictureLinkText == fileName

		where:
		page          | fileName         | description
		ShowAlbumPage | "fileToSave.gif" | 'test picture description'
		EditAlbumPage | "fileToSave.gif" | 'test picture description'

		filePath = "testFiles" + imageService.getSeparator() + fileName
	}
}