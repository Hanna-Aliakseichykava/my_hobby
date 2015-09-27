
<%@ page import="org.light.myhobby.User" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
  
<body>

 <div class="nav" role="navigation">
    <ul>	  
      <li id="createAlbum"><g:link class="create" controller ="album" action="create"><g:message code="default.new.album.label" args="['Album']" /></g:link></li>
      <li id="showWall"><g:link controller ="user" action="wall" id="${ownerInstance?.id}"><g:message code="default.user.wall" default="User Wall"/></g:link></li>
    </ul>
  </div>

  <a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  
  <div id="show-user" class="content scaffold-show" role="main">
  
    <h1><g:message code="default.show.user.label" /></h1>
	
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>

    <div>
      <img height="30%" style="padding-left:30px;" src = "<g:createLink controller = 'image' action = 'renderPhoto' login = '${userInstance?.login}' />"/>
    <div/>

      <ol class="property-list user">     

        <g:if test="${ownerInstance?.login}">
          <li class="fieldcontain">
            <span id="login-label" class="property-label"><g:message code="default.user.login.label" default="Login" /></span>

            <span class="property-value" aria-labelledby="login-label" name="login-label"><g:fieldValue bean="${ownerInstance}" field="login"/></span>

          </li>
        </g:if>						

        <g:if test="${ownerInstance?.email}">
          <li class="fieldcontain">
            <span id="email-label" class="property-label"><g:message code="default.user.email.label" default="Email" /></span>

            <span class="property-value" aria-labelledby="email-label" name="email-label"><g:fieldValue bean="${ownerInstance}" field="email"/></span>

          </li>
        </g:if>

        <g:if test="${ownerInstance?.dateCreated}">
          <li class="fieldcontain">
            <span id="dateCreated-label" class="property-label"><g:message code="default.user.dateCreated.label" default="Date Created" /></span>

            <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${ownerInstance?.dateCreated}" /></span>

          </li>
        </g:if>

        <g:if test="${ownerInstance?.lastUpdated}">
          <li class="fieldcontain">
            <span id="lastUpdated-label" class="property-label"><g:message code="default.user.lastUpdated.label" default="Last Updated" /></span>

            <span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${ownerInstance?.lastUpdated}" /></span>

          </li>
        </g:if>

        <g:if test="${ownerInstance?.profile}">
          <li class="fieldcontain">
            <span id="profile-label" class="property-label"><g:message code="user.profile.label" default="Profile" /></span>

            <span class="property-value" aria-labelledby="profile-label"><g:link controller="profile" action="show" id="${ownerInstance?.profile?.id}">${userInstance?.profile?.encodeAsHTML()}</g:link></span>

          </li>
        </g:if>

        <g:if test="${ownerInstance?.albums}">
          <li class="fieldcontain">
            <span id="albums-label" class="property-label"><g:message code="default.user.albums.label" default="Albums" /></span>

			<span id="albums-list">
				<g:each in="${ownerInstance.albums}" var="a">
					<span class="property-value" aria-labelledby="albums-label"><g:link controller="album" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
				</g:each>
			</span>
          

          </li>
        </g:if>

		</ol>

		<g:render template="/common/editDeleteBlock"/>

	</div>
</body>
</html>
