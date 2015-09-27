<html>
  <head>
    <title><g:message code="default.search.results.title"/></title>
    <meta name="layout" content="main">
  </head>
  <body>

    <h1><g:message code="default.search.results.header"/></h1>

    <div class="contentWithMargins">
      <p>
        Searched ${org.light.myhobby.User.count()} records
        for items matching <em>${term}</em>.
        Found <strong>${users.size()}</strong> hits.
      </p>


      <ul>
        <g:each var="user" in="${users}">
          <li>${user.login}</li>
        </g:each>
      </ul>

      <g:link action='search'><g:message code="default.search.again"/></g:link>   
    </div>


  </body>
</html>