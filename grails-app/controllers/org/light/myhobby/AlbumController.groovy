package org.light.myhobby

import org.springframework.dao.DataIntegrityViolationException

class AlbumController extends MessageController {

	static scaffold = Album

	def imageService

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[albumInstanceList: Album.list(params), albumInstanceTotal: Album.count()]
	}

	def show(Long id) {
		flash.message = null
		wall(id)
	}

	def create = {
		// go to create.gsp
		render(view: "create")
	}

	def createAlbum = { Album album ->

		if(album.hasErrors()) {
			render(view: "create", model: [ownerInstance: album])
		} else if(album.save()){
			flash.message = message(code: 'default.created.message', args: [message(code: 'album.id', default: 'Album'), album.id])
			imageService.createFolder(imageService.getAlbumPath(album))
			wall(album.id)
		} else {
			render(view: "create", model: [ownerInstance: album])
		}
	}

	def edit(Long id) {
		def instance = Album.get(id)
		if (!instance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'album.label', default: 'Album'), id])
			redirect(action: "list")
			return
		}

		render(view: "edit", model: [ownerInstance: instance])
	}

	def update (Long id) {
		def instance = Album.get(id)
		if (!instance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'album.label', default: 'Album'), id])
			redirect(action: "list")
			return
		}

		instance.properties = params

		if (!instance.save(flush: true)) {
			render(view: "edit", model: [ownerInstance: instance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'album.label', default: 'Album'), instance.id])
		wall(id)
	}

	def delete(Long id) {
		def instance = Album.get(id)
		if (!instance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'album.label', default: 'Album'), id])
			redirect(action: "list")
			return
		}

		try {
			instance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'album.label', default: 'Album'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'album.label', default: 'Album'), id])
			wall(id)
		}
	}

	//-----------------------------------
	@Override
	protected def getOwnerInstance(Long id) {

		def instance = Album.get(id)
		if (!instance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'album.label', default: 'Album'), id])
			redirect(action: "list")
			return null
		} else {
			return instance
		}
	}

}

