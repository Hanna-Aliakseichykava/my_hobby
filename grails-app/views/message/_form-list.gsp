<div id="list-message" class="">

	<h1><g:message code="message.list.label" default="Messages List" /></h1>

	<div class="message-list-wrapper">
		<div class="messages-list">

			<div id="messageArea">
				<g:form action="saveMessage" class="new-comment-block">
					<g:hiddenField name="ownerId" value="${ownerInstance?.id}" />
					<g:hiddenField name="id" value="${messageInstance?.id}" />
				
					<g:hasErrors>
						<div class="errors">
							<g:renderErrors bean="${messageInstance}" as="list" />
						</div>
					</g:hasErrors>
			
					<div>
						<span class="fieldcontain ${hasErrors(bean: messageInstance, field: 'content', 'error')} ">
							<g:textArea name="content" cols="" rows="" maxlength="1000" value="${messageInstance?.content}"/>
						</span>
					
						<span id="addMessage" class="comment-link"> 
							<g:submitButton name="saveMessage" class="add" value="${message(code: 'default.button.comment.add', default: 'Add Comment')}"/>
						</span>
					</div>
				</g:form>
			</div>

			<g:each in="${messageInstanceList}" status="i" var="messageInstance">
				<div class="message-instance">
					<div class="content-column column">
						<div class="">
							<span class="author-block">${fieldValue(bean: messageInstance, field: "userFrom")}</span>
							<span class="content-block">${fieldValue(bean: messageInstance, field: "content")}</span>
						</div>
						<div class="dates-block">
							<g:formatDate date="${messageInstance.dateCreated}" />
							<g:formatDate date="${messageInstance.lastUpdated}" />
						</div>
					</div>
					<div class="links-block column">
						<g:link class="edit link" action="editMessage" params="${[messageId: messageInstance.id, ownerId: ownerInstance.id]}" title="${message(code: 'default.button.edit.label', default: 'Edit')}"></g:link><g:link class="delete link" action="deleteMessage" params="${[messageId: messageInstance.id, ownerId: ownerInstance.id]}" title="${message(code: 'default.button.delete.label', default: 'Delete')}"></g:link>
					</div>
				</div>
			</g:each>
		</div>
	</div>

</div>

<div id="list-message-pagination" class="content scaffold-list" role="main">
	<div class="pagination">
		<g:paginate action="wall" params="${[id: ownerInstance.id]}" total="${messageInstanceTotal}" />
	</div>
</div>