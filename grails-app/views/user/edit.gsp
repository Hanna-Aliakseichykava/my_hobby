<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="edit-user" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			                        
			<g:uploadForm name="editUserForm">	
                <g:hiddenField name="id" value="${ownerInstance?.id}" />
				<g:hiddenField name="version" value="${ownerInstance?.version}" />

				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<span name="updateUser"><g:actionSubmit class="save" action="updateUser" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
					<span name="deleteUser"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
				</fieldset>
			</g:uploadForm>
		</div>
	</body>
</html>
