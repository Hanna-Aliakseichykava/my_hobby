import grails.util.GrailsUtil
import org.light.myhobby.User
import org.light.myhobby.Album
import org.light.myhobby.Message

class BootStrap {

    def init = { servletContext ->
		switch(GrailsUtil.environment) {

			case "development":
				
                User user1 = new User(login : "first", password : "111111", email : "first@mail.ru").save()
				User user2 = new User(login : "second", password : "222222", email : "second@mail.ru").save()
                             
                new Album(name: "Album first", shortDescription: "Short description to Album", user: user1).save()
                new Album(name: "Album second", shortDescription: "Short description to Album", user: user2).save()
			
				for(int i = 0; i < 15; ++ i) {
					def mess = new Message(userFrom: user1, content: "Message " + (i + 1))
					user2.addToWallMessages(mess) 
				}
				
			break
			
			case "production":
			break
		}
    }
    def destroy = {
    }
}
