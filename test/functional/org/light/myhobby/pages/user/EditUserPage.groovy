package org.light.myhobby.pages.user

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class EditUserPage extends HomePage {
    
    static url = "user/edit"
    
    static at = { title == "Edit User" }
    
    static content = {
        form { $("form[name='editUserForm']") }
        loginField { form.login() }
        passwordField { form.password() }
        passwordRepeatField { form.passwordRepeat() }
        fileInputField { $("input", type: 'file', name: 'photo') }
        
        updateButton { $("span[name='updateUser'] input") }
        //deleteButton { $("span[name='deleteUser'] input") }
    }

    public void selectFile(def filePath) {
    	String fullPath = new File(filePath).getCanonicalPath()
    	fileInputField = fullPath
    }
}