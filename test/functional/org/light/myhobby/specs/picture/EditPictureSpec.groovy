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
class EditPictureSpec extends BaseSpec {
	  
    def setup() {
        login() 
    }
	
    @Unroll
    def "Edit picture" () {
        
        given: "Created album"
        Album album1 = new Album(name: "AutotestAlbum", shortDescription: "Short description to Album", user: createdUser).save()
        Album album2 = new Album(name: "AutotestAlbum2", shortDescription: "Short description to Album 2", user: createdUser).save()
        
        when:
        to ShowAlbumPage, album1.id
        
        then:
        at ShowAlbumPage

        and:
        addPictureLink.click()

        then:
        at CreatePicturePage
        
        when:
        selectFile(filePath)

        and:
        descriptionField = description

        and:
        createButton.click()

        then:
        at ShowPicturePage

        and:
        descriptionLabel == description
        originalNameLabel == fileName
        albumLink.text() == album1.toString()

        when:
        editButton.click()

        then:
        at EditPicturePage

        when:
        descriptionField = newDescription
        albumList = album2.toString()

        and:
        updateButton.click()

        then:
        at ShowPicturePage

        and:
        descriptionLabel == newDescription
        originalNameLabel == fileName
        albumLink.text() == album2.toString()

        where:
        fileName          | description                | newDescription
        "fileToSave.gif"  | 'test picture description' | "new description"

        filePath = "testFiles" + imageService.getSeparator() + fileName
    }
}