<div id="messageArea">
	<g:form action="saveMessage" class="new-comment-block">
		<g:hiddenField name="ownerId" value="${ownerInstance?.id}" />
		<g:hiddenField name="id" value="${messageInstance?.id}" />
	
		<g:hasErrors>
			<div class="errors">
				<g:renderErrors bean="${messageInstance}" as="list" />
			</div>
		</g:hasErrors>

		<div class="fieldcontain ${hasErrors(bean: messageInstance, field: 'content', 'error')} ">
			<label for="shortDescription">
				<g:message code="default.button.comment.add" default="Add Comment" />
			</label>
			<g:textArea name="content" cols="60" rows="4" maxlength="1000" value="${messageInstance?.content}"/>
		</div>
	
		<span id="addMessage" class="comment-link"> 
			<g:submitButton name="saveMessage" class="add" value="${message(code: 'default.button.comment.add', default: 'Add Comment')}"/>
		</span>
	</g:form>
</div>