<%@ page import="org.light.myhobby.Picture" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'picture.label', default: 'Picture')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-picture" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-picture" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${ownerInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${ownerInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>

			<div >
		      <center><img height="60%" src = "<g:createLink controller = 'image' action = 'renderPicture' id = '${ownerInstance?.id}'/>"/></center>
		    <div/>
		    
			<g:form method="post" enctype="multipart/form-data">

				<g:hiddenField name="id" value="${ownerInstance?.id}" />
				<g:hiddenField name="version" value="${ownerInstance?.version}" />
				
				<fieldset class="form">
					
					<div class="fieldcontain ${hasErrors(bean: ownerInstance, field: 'shortDescription', 'error')} ">
					<label for="shortDescription">
						<g:message code="picture.shortDescription.label" default="Short Description" />
						
					</label>
					<g:textArea name="shortDescription" cols="40" rows="5" maxlength="1000" value="${ownerInstance?.shortDescription}"/>
				</div>

				<div class="fieldcontain ${hasErrors(bean: ownerInstance, field: 'album', 'error')} required">
					<label for="album">
						<g:message code="picture.album.label" default="Album" />
						<span class="required-indicator">*</span>
					</label>
					<g:select id="album" name="album.id" from="${org.light.myhobby.Album.list()}" optionKey="id" required="" value="${owner?.album?.id}" class="many-to-one"/>
				</div>

				</fieldset>

				<fieldset class="buttons">
					
					<span id="updatePicture"> 
						<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					</span>

					<span id="deletePicture">
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</span>
					
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
