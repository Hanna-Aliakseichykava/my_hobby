
<%@ page import="org.light.myhobby.Album" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'album.label', default: 'Album')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-album" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create">Te-e-e-e-emp</g:link></li>
			</ul>
		</div>
		<div id="list-album" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'album.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="shortDescription" title="${message(code: 'album.shortDescription.label', default: 'Short Description')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'album.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'album.lastUpdated.label', default: 'Last Updated')}" />
					
						<th><g:message code="album.user.label" default="User" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${albumInstanceList}" status="i" var="albumInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${albumInstance.id}">${fieldValue(bean: albumInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: albumInstance, field: "shortDescription")}</td>
					
						<td><g:formatDate date="${albumInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${albumInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: albumInstance, field: "user")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${albumInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
