<%@ page import="org.light.myhobby.Picture" %>


<div>
	<label for="image">
		<g:message code="picture.image.label" default="Image" />		
	</label>
          
	<input type="file" name="image"/>  
	<g:if test="${imageSizeFailure}">
		<span class="errors"><g:message code="default.picture.failure.image.size"/></span>
	</g:if>
	<g:if test="${imageEmptyFailure}">
		<span class="errors"><g:message code="default.picture.failure.image.empty"/></span>
	</g:if>

</div>

<div class="fieldcontain ${hasErrors(bean: ownerInstance, field: 'shortDescription', 'error')} ">
	<label for="shortDescription">
		<g:message code="picture.shortDescription.label" default="Short Description" />
		
	</label>
	<g:textArea name="shortDescription" cols="40" rows="5" maxlength="1000" value="${ownerInstance?.shortDescription}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ownerInstance, field: 'album', 'error')} required">
	<label for="album">
		<g:message code="picture.album.label" default="Album" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="album" name="album.id" from="${org.light.myhobby.Album.list()}" optionKey="id" required="" value="${ownerInstance?.album?.id}" class="many-to-one"/>
</div>

