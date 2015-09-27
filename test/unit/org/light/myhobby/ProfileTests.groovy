package org.light.myhobby

import grails.test.mixin.*
import org.junit.*

/**
 * @author Hanna_Aliakseichykava
 */
@TestFor(Profile)
class ProfileTests {

    void testConstraints() { 
        
        def testProfile = new Profile()
        assertTrue testProfile.validate()  
    }
}
