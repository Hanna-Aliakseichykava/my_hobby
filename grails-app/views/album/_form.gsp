


<div class="fieldcontain ${hasErrors(bean: ownerInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="default.album.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="20" required="" value="${ownerInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ownerInstance, field: 'shortDescription', 'error')} ">
	<label for="shortDescription">
		<g:message code="default.album.shortDescription.label" default="Short Description" />		
	</label>
	<g:textArea name="shortDescription" cols="40" rows="5" maxlength="1000" value="${ownerInstance?.shortDescription}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ownerInstance, field: 'pictures', 'error')} ">
	<label for="pictures">
		<g:message code="album.pictures.label" default="Pictures" />
		
	</label>
	
<ul class="one-to-many" id="pictures">
	<g:each in="${ownerInstance?.pictures?}" var="p">
		<li><g:link controller="picture" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
	</g:each>

	<span id="addPicture">        
      <g:if test="${ownerInstance?.id}">
        <li class="add">
		<g:link controller="picture" action="create" params="['album.id': ownerInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'picture.label', default: 'Picture')])}</g:link>
		</li>
      </g:if>     
    </span>
	
</ul>

</div>