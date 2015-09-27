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
class DeletePictureSpec extends BaseSpec {

	def setup() {
		login()
	}

	@Unroll
	def "Delete picture" () {

		given: "Created album"
		Album album = new Album(name: "AutotestAlbum", shortDescription: "Short description to Album", user: createdUser).save()

		when:
		to ShowAlbumPage, album.id

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

		when:
		delete()

		then:
		at PictureListPage
		message.infoDisplayed()
		message.getInfoMessage().contains("Picture")
		message.getInfoMessage().contains("deleted")

		where:
		fileName          | description
		"fileToSave.gif"  | 'test picture description'

		filePath = "testFiles" + imageService.getSeparator() + fileName
	}
}