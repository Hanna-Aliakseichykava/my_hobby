package org.light.myhobby

import grails.test.mixin.*
import org.junit.*
import org.springframework.mock.web.MockMultipartFile

/**
 * @author Hanna_Aliakseichykava
 */
@TestFor(ImageService)
class ImageServiceTests {

	private String FILE_NAME = "fileToSave.gif"
	private String FILE_TO_SAVE = "testFiles" + getSeparator() + FILE_NAME
	
	private String getSeparator() {
		return service.getSeparator()
	}
	
	private boolean isFileExists(String path) {
		return new File(path).exists()
	}

	private def getFileToSave() {
		final FileInputStream fis = new FileInputStream(FILE_TO_SAVE)
		return new MockMultipartFile("testFileName", FILE_NAME, "text/plain", fis)
	}
	
	void testCreateFolder() {
   
	   String baseDir = "target"
	   String dirName = "testGeneratedFolder" + new Date().getTime()
	   String fullPath = baseDir + getSeparator() + dirName
	   assert !isFileExists(fullPath)
	   
	   service.createFolder(dirName, baseDir)	   
	   assert isFileExists(fullPath)
    }
	
	void testSaveFile() {
   
	   String baseDir = "target"
	   String dirName = "testGeneratedFolder" + new Date().getTime()
	   String filePath = dirName + getSeparator() + "testFile${new Date().getTime()}.gif"
	   String fullPath = baseDir + getSeparator() + filePath
	   assert !isFileExists(fullPath)
	   
	   service.createFolder(dirName, baseDir)	  
	   service.saveFile(getFileToSave(), filePath, baseDir)
	   assert isFileExists(fullPath)
    }

    void testGetFileName() {
   	   assert service.getFileName(getFileToSave()) == FILE_NAME
    }
}
