<g:form>
	<fieldset class="buttons">
		<g:hiddenField name="id" value="${ownerInstance?.id}" />
		<span id="editInstance"><g:link class="edit" action="edit" id="${ownerInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link></span>
		<span id="deleteInstance"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
	</fieldset>
</g:form>