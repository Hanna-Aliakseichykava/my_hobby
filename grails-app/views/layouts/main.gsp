<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Welcome"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <asset:stylesheet href="main.css"/>
    <asset:stylesheet href="app.css"/>

    <asset:javascript src="application.js"/>
    <!-- asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/ -->
    
  <g:layoutHead/>
  <r:layoutResources />
</head>

<!--body style="background-image: ${resource(dir: 'images', file: 'bamboo.jpg')}"-->

<body>

  <div id="grailsLogo" role="banner">
    <b>&nbsp;&nbsp;LIGHT</b>
  </div>

  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      <li><g:link class="list" controller ="user" action="list"><g:message code="default.list.label" args="['User']" /></g:link></li>
      <li><g:link class="list" controller ="album" action="list"><g:message code="default.list.label" args="['Album']" /></g:link></li>      
    </ul>
  </div>

<g:layoutBody/> 

<div class="footer" role="contentinfo"></div>

<g:form controller ="login" action="enter">
  <fieldset class="buttons">	
    <g:message code="default.login.label"/>
    <g:textField name="enteredLogin" />
    <g:message code="default.password.label"/>
    <g:passwordField name="enteredPassword" />
    <g:submitButton name="enterSystem" value="${message(code: 'default.button.enter', default: 'Enter')}" />  

    <g:link name="exitLink" controller="login" action="exit"><g:message code="default.button.exit"/></g:link>

    <span id="loginLabel" style="float:right">        
      <g:if test="${session.user}">
        <g:message code="default.hello.label"/> 
        ${session.user.login}!
      </g:if>
      <g:elseif test="${flash.isLoginOrPasswordIncorrect}"> 
        <font color="#CC0000"><g:message code="default.incorrect.user"/></font>
      </g:elseif>
      <g:else>
        <g:message code="default.hello.label"/> 
        <g:message code="default.guest.label"/>!
      </g:else>        
    </span>

  </fieldset>
</g:form>
<br>


<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
<g:javascript library="application"/>
<r:layoutResources />
</body>
</html>