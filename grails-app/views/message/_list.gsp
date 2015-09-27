<div id="list-message" class="content scaffold-list" role="main">
  
  <h1><g:message code="message.list.label" default="Messages List" /></h1>

  <table>
    
    <thead>
      <tr>

        <g:sortableColumn property="content" title="${message(code: 'message.content.label', default: 'Content')}" />

        <g:sortableColumn property="dateCreated" title="${message(code: 'message.dateCreated.label', default: 'Date Created')}" />

        <g:sortableColumn property="lastUpdated" title="${message(code: 'message.lastUpdated.label', default: 'Last Updated')}" />

        <th><g:message code="message.user.label" default="User" /></th>

      </tr>
    </thead>
    
    <tbody>
    <g:each in="${messageInstanceList}" status="i" var="messageInstance">
      <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

        <td>${fieldValue(bean: messageInstance, field: "content")}</td>

        <td><g:formatDate date="${messageInstance.dateCreated}" /></td>

        <td><g:formatDate date="${messageInstance.lastUpdated}" /></td>

        <td>${fieldValue(bean: messageInstance, field: "userFrom")}</td>

        <td><g:link action="editMessage" params="${[messageId: messageInstance.id, ownerId: ownerInstance.id]}">${message(code: 'default.button.edit.label', default: 'Edit')}</g:link></td>

        <td><g:link action="deleteMessage" params="${[messageId: messageInstance.id, ownerId: ownerInstance.id]}">${message(code: 'default.button.delete.label', default: 'Delete')}</g:link></td> 

      </tr>
    </g:each>
    </tbody>
    
  </table>  
  
</div>

<div class="pagination">
    <g:paginate action="wall" params="${[id: ownerInstance.id]}" total="${messageInstanceTotal}" />
</div>