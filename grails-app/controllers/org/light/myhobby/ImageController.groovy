package org.light.myhobby

class ImageController {

    def imageService  

    def selectAndRenderImage (imagePath, defaultImagePath) {        
        if(imageService.isFileExists(imagePath)) {
            renderImage(imagePath)
        }
        else if(imageService.isFileExists(defaultImagePath)){
            renderImage(defaultImagePath)
        }
    }
    
    def renderImage (imagePath) {        
        String path = imageService.getFullPath(imagePath)        
        def image = new File(path).getBytes()
        response.setContentLength(image.length)
        response.outputStream.write(image)        
    }
    
    //-------------------------------------------

    def renderPhoto = {        
        selectAndRenderImage(imageService.getPhotoPath(params.login), imageService.DEFAULT_PHOTO)
    }
    
    def renderPicture = {     
        Picture picture = Picture.findById(params.id) 
        selectAndRenderImage(imageService.getPicturePath(picture), imageService.DEFAULT_PICTURE)
    }
}
