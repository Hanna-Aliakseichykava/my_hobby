package org.light.myhobby

/**
 * @author Hanna_Aliakseichykava
 */
class Profile {   

    static belongsTo = User
    
    String fullName
    String biography 
    String timezone
    String country
    
    static constraints = {
        fullName(nullable: true)          
        biography(nullable: true, maxSize: 1000)
        country(nullable: true)
        timezone(nullable: true)        
    }
    
    String toString() {
        "Profile for ${fullName} (${id})"
    }
    
}

