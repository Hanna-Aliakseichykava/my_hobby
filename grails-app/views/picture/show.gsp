
<%@ page import="org.light.myhobby.Picture" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'picture.label', default: 'Picture')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-picture" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-picture" class="content scaffold-show" role="main">
			
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>

			<div >
		      <center><img height="60%" src = "<g:createLink controller = 'image' action = 'renderPicture' id = '${ownerInstance?.id}'/>"/></center>
		    <div/>

			<ol class="property-list picture">
			
				<g:if test="${ownerInstance?.shortDescription}">
				<li class="fieldcontain">
					<span id="shortDescription-label" class="property-label"><g:message code="picture.shortDescription.label" default="Short Description" /></span>
					
						<span class="property-value" aria-labelledby="shortDescription-label"><g:fieldValue bean="${ownerInstance}" field="shortDescription"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ownerInstance?.originalName}">
				<li class="fieldcontain">
					<span id="originalName-label" class="property-label"><g:message code="picture.originalName.label" default="Original Name" /></span>
					
						<span class="property-value" aria-labelledby="originalName-label"><g:fieldValue bean="${ownerInstance}" field="originalName"/></span>
					
				</li>
				</g:if>
					
				<g:if test="${ownerInstance?.album}">
				<li class="fieldcontain">
					<span id="album-label" class="property-label"><g:message code="picture.album.label" default="Album" /></span>
					
						<span class="property-value" aria-labelledby="album-label"><g:link controller="album" action="show" id="${ownerInstance?.album?.id}">${ownerInstance?.album?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>

			<g:render template="/common/editDeleteBlock"/>

			<g:render template="/message/form-list" />

		</div>
	</body>
</html>
