package org.light.myhobby

import org.springframework.dao.DataIntegrityViolationException

class PictureController extends MessageController {

	//static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	//static scaffold = Picture

	def imageService

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[pictureInstanceList: Picture.list(params), pictureInstanceTotal: Picture.count()]
	}

	def create() {
		[ownerInstance: new Picture(params)]
	}

	def save = { Picture ownerInstance ->
		return savePictureInfo(ownerInstance, "create")
	}

	def show(Long id) {
		flash.message = null
		wall(id)
	}

	def edit(Long id) {
		def pictureInstance = Picture.get(id)
		if (!pictureInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'picture.label', default: 'Picture'),
				id
			])
			redirect(action: "list")
			return
		}

		render(view: "edit", model: [ownerInstance: pictureInstance])
	}

	def update (Long id) {
		def pictureInstance = Picture.get(id)
		if (!pictureInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'picture.label', default: 'Picture'),
				id
			])
			redirect(action: "list")
			return
		}

		pictureInstance.properties = params

		if (!pictureInstance.save(flush: true)) {
			render(view: "edit", model: [ownerInstance: pictureInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'picture.label', default: 'Picture'),
			pictureInstance.id
		])
		//render(view: "show", model: [ownerInstance: pictureInstance])
		wall(pictureInstance.id)
	}

	def delete(Long id) {
		def pictureInstance = Picture.get(id)
		if (!pictureInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'picture.label', default: 'Picture'),
				id
			])
			redirect(action: "list")
			return
		}

		try {
			pictureInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'picture.label', default: 'Picture'),
				id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'picture.label', default: 'Picture'),
				id
			])
			render(view: "show", model: [ownerInstance: pictureInstance])
		}
	}

	/*------------------------------------------------------*/
	def savePictureInfo(Picture picture, String unsuccessView) {

		flash.message = null

		if (picture == null) {
			render(view: unsuccessView, model: [ownerInstance: picture, imageEmptyFailure: true])
			return
		}

		def image = request.getFile('image')

		if (image == null || image.empty) {
			render(view: unsuccessView, model: [ownerInstance: picture, imageEmptyFailure: true])

		} else if (imageService?.isFileToLarge(image, imageService?.MAX_PICTURE_SIZE)) {

			render(view: unsuccessView, model: [ownerInstance: picture, imageSizeFailure: true])
			return

		} else if (picture.save(flush: true)) {
			picture.setNames(imageService.getFileName(image))
			imageService.savePicture(image, picture)

			flash.message = message(code: 'default.created.message', args: [
				message(code: 'picture.label', default: 'Picture'),
				picture.id
			])
			wall(picture.id)

		} else {
			flash.message = message(code: 'default.not.created.message', args: [
				message(code: 'picture.label', default: 'Picture')
			])
			render(view: unsuccessView, model: [ownerInstance: picture])
		}

	}

	//-----------------------------------
	@Override
	protected def getOwnerInstance(Long id) {

		def instance = Picture.get(id)
		if (!instance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'picture.label', default: 'Picture'),
				id
			])
			redirect(action: "list")
			return null
		} else {
			return instance
		}
	}
}
