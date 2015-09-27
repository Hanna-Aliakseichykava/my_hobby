
<g:hasErrors>
  <div class="errors">
    <g:renderErrors bean="${user}" as="list" />
  </div>
</g:hasErrors>

<dt><g:message code="default.registration.label.login"/></dt>
<dd><g:textField name="login" value="${user?.login}"/><span class="required-indicator">*</span></dd>

<dt><g:message code="default.registration.label.password"/></dt>
<dd><g:passwordField name="password" value="${user?.password}"/><span class="required-indicator">*</span></dd>

<dt><g:message code="default.registration.label.repeat.password"/></dt>
<dd><g:passwordField name="passwordRepeat" value="${user?.passwordRepeat}"/><span class="required-indicator">*</span></dd>

<dt><g:message code="default.registration.label.email"/></dt>
<dd><g:textField name="email" value="${user?.email}"/><span class="required-indicator">*</span></dd>

<dt><g:message code="default.registration.label.full.name"/></dt>
<dd><g:textField name="fullName" value="${user?.fullName}"/></dd>

<dt><g:message code="default.registration.label.biography"/></dt>
<dd><g:textArea name="biography" value="${user?.biography}"/></dd>

<dt><g:message code="default.registration.label.country"/></dt>
<dd><g:countrySelect name="country" noSelection="['':'Choose your country...']"/></dd>

<dt><g:message code="default.registration.label.time.zone"/></dt>
<dd><g:timeZoneSelect name="timezone"/></dd>

<dt><g:message code="default.registration.label.photo"/></dt>          
<dd>           
  <input type="file" name="photo"/>  
  <g:if test="${photoFailure}">
    <span class="errors"><g:message code="default.registration.failure.photo"/></span>
  </g:if>
</dd>

<dt><g:message code="default.registration.label.background"/></dt>          
<dd>
  <input type="file" name="background"/>
<g:if test="${backgroundFailure}">
  <span class="errors"><g:message code="default.registration.failure.background"/></span>
</g:if>
</dd>			
