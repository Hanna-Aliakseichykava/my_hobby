
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="default.user.wall" default="User Wall" /></title>
</head>

<body>

	<div class="nav" role="navigation">
		<ul>
			<li id="showUserInfo">
				<g:link action="show"
					id="${ownerInstance?.id}">
					<g:message code="default.show.user.label" default="User Info" />
				</g:link>
			</li>
		</ul>
	</div>

	<div id="show-user-wall" class="content scaffold-show" role="main">

		<h1>
			${ownerInstance.login}
		</h1>

		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>

		<g:render template="/message/form-list" />

	</div>

</body>
</html>
