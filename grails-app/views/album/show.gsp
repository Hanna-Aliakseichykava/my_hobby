
<%@ page import="org.light.myhobby.Album" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'album.label', default: 'Album')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-album" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-album" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list album">

				<g:if test="${ownerInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="album.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label" id = "name"><g:fieldValue bean="${ownerInstance}" field="name"/></span>
					
				</li>
				</g:if>

				<g:if test="${ownerInstance?.shortDescription}">
				<li class="fieldcontain">
					<span id="shortDescription-label" class="property-label"><g:message code="album.shortDescription.label" default="Short Description" /></span>
					
						<span class="property-value" aria-labelledby="shortDescription-label" id = "description"><g:fieldValue bean="${ownerInstance}" field="shortDescription"/></span>
					
				</li>
				</g:if>

				<g:if test="${ownerInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="album.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${ownerInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>

				<g:if test="${ownerInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="album.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${ownerInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>

				<g:if test="${ownerInstance?.pictures}">
				<li class="fieldcontain" id="pictures">
					<span id="pictures-label" class="property-label"><g:message code="album.pictures.label" default="Pictures" /></span>
					
					<g:each in="${ownerInstance.pictures}" var="p">
						<span class="property-value" aria-labelledby="pictures-label"><g:link controller="picture" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
					</g:each>
				</li>
				</g:if>

				<g:if test="${ownerInstance?.user}">
				<li class="fieldcontain">
					<span id="user-label" class="property-label"><g:message code="album.user.label" default="User" /></span>
					
						<span class="property-value" aria-labelledby="user-label" id="userLink"><g:link controller="user" action="show" id="${ownerInstance?.user?.id}">${ownerInstance?.user?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

			</ol>

			<span id="addPicture">
				 <g:if test="${ownerInstance?.id}">
					<g:link controller="picture" action="create" params="['album.id': ownerInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'picture.label', default: 'Picture')])}</g:link>					
				 </g:if>
			</span>
	
			<g:render template="/common/editDeleteBlock"/>

			<g:render template="/message/form" />
			<g:render template="/message/list" />

		</div>
	</body>
</html>
