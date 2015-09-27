package org.light.myhobby.pages.user

import org.light.myhobby.pages.HomePage

/**
 * @author Hanna_Aliakseichykava
 */
class UserListPage extends HomePage {
    
    static url = "user/list"
    
    static at = { title == "User List" }
    
    static content = {        
        newUserButton { $("a", href: contains("/user/register")) }
        userLink { userLogin -> $("a", href: contains('/user/show/'), text: contains(userLogin)) }		
    }
}