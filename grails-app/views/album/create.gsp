<%@ page import="org.light.myhobby.Album" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'album.label', default: 'Album')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-album" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>				
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-album" class="content scaffold-create" role="main">
		
			<h1><g:message code="default.new.album.label" /></h1>
			
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
			<g:form action="createAlbum" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
                                <div class="fieldcontain ${hasErrors(bean: ownerInstance, field: 'user', 'error')} required">
                                        <g:hiddenField name="user.id" maxlength="20" required="" value="${session.user?.id}"/>	
                                </div>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" id="createAlbum"/>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
