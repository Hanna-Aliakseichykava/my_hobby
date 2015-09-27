<html>
  <head>
    <title><g:message code="default.registration.title"/></title>
    <meta name="layout" content ="main"/>
  </head>
  <body>    

    <div id="create-user" class="content scaffold-create" role="main">

      <h1><g:message code="default.registration.title"/></h1>

      

      <g:uploadForm>

        <fieldset class="form">
          <g:render template="form"/>
        </fieldset>

        <fieldset class="buttons">
          <g:actionSubmit class="save" action="registerUser" value="${message(code: 'default.registration.button.register', default: 'Register')}" id="registerUser"/>
          <g:actionSubmit class="delete" action="cancelRegister" value="${message(code: 'default.registration.button.cancel', default: 'Cancel')}" id="cancelRegisterUser"/>
        </fieldset>

      </g:uploadForm>      

    </div>

  </body>
</html>