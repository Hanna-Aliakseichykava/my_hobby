package org.light.myhobby

//import org.codehaus.groovy.grails.commons.*
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication

class ImageService {
   
    //def config = ConfigurationHolder.config
    def grailsApplication = new DefaultGrailsApplication()
    def config = grailsApplication.config
    
    static final String DEFAULT_PHOTO = "default_photo.gif"
    static final String DEFAULT_BACKGROUND = "default_background.gif"
    static final String DEFAULT_PICTURE = "default_picture.gif"
    
    String BASE_DIR = config.baseFilesDir
    int MAX_PHOTO_SIZE = config.maxPhotoSize * 1024 * 1024
    int MAX_BACKGROUND_SIZE = config.maxBackgroundSize * 1024 * 1024
    int MAX_PICTURE_SIZE = config.maxPictureSize * 1024 * 1024

    public static String getSeparator() {
	return System.getProperty("file.separator")
    }
       
    def String getFullPath(String relativePath, String baseDir = BASE_DIR) {        
        return "${baseDir}${getSeparator()}${relativePath}"
    }
    
    def createFolder(relativePath, String baseDir = BASE_DIR) {
        try{
            String path = getFullPath(relativePath, baseDir)
            File folder = new File(path)        
            folder.mkdirs()
            log.info("Creating folder [$path] is successful")  
        } catch(Exception e) {
            log.info("Create folder [$path]: " + e)
        }
    }
    
    def saveFile(def file, def relativePath, String baseDir = BASE_DIR) {
        if(file != null && !file.isEmpty()) {
            String path = getFullPath(relativePath, baseDir)
            try{
                file.transferTo(new File(path))
                log.info("Saving file [$path] is successful")				
            } catch(Exception e) {
                log.info("Save file [$path]: " + e)
            }   
        }        
    }	
	
    def getFileName(def file) {
        return file.getOriginalFilename()
    }

    def boolean isFileToLarge (def file, def maxSize) {        
       return file?.size > maxSize       
    }   
    
    def boolean isFileExists(String relativePath, String baseDir = BASE_DIR) {        
        String path = getFullPath(relativePath, baseDir)
        def file = new File(path)
        return file.exists()
    }

    //-------------------------------------------    
    public static String getPhotoPath(String login) {        
        return "${login}${getSeparator()}photo.gif"
    }
    
    public static String getBackgroundPath(String login) {        
        return "${login}${getSeparator()}background.gif"
    }

    public static String getAlbumPath(Album album) {        
        return "${album.user.login}${getSeparator()}${album.getInnerName()}"
    }

    public static String getPicturePath(Picture picture) {        
        return "${getAlbumPath(picture.album)}${getSeparator()}${picture.systemName}"
    }


    
    def savePhoto(file, login) {  
        createFolder(login)
        saveFile(file, getPhotoPath(login))
    }
    
    def saveBackground(file, login) { 
        createFolder(login)
        saveFile(file, getBackgroundPath(login))
    }

    def savePicture(file, picture) { 
        createFolder(picture.album.user.login)
        createFolder(getAlbumPath(picture.album))
        saveFile(file, getPicturePath(picture))
    }  
    
}
