package org.light.myhobby.pages.picture

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class CreatePicturePage extends HomePage {
    
    static url = "picture/create"
    
    static at = { title == "Create Picture" }
    
    static content = {
		fileInputField { $("input", type: 'file', name: 'image') }
		descriptionField { $("textarea", name: 'shortDescription') }
		createButton { $("input", name: 'create') }
    }

    public void selectFile(def filePath) {
    	String fullPath = new File(filePath).getCanonicalPath()
    	fileInputField = fullPath
    }
}