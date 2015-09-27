<html>
  <head>
    <title><g:message code="default.search.user.title"/></title>
    <meta name="layout" content="main">
  </head>
  <body>

  <formset>
    <legend><g:message code="default.search.user.legend"/></legend>

    <g:form action="results">
      <label for="login"><g:message code="default.search.user.login"/></label>
      <g:textField name="login" />
      <g:submitButton name="search" value="${message(code: 'default.button.search.label')}" />
    </g:form>     
  </formset>  

</body>
</html>