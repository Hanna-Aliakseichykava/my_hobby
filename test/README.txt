1) Run unit tests:

grails test-app -unit
grails test-app -unit <TestsName>
grails test-app <TestsName>

2) Integration tests:

grails test-app -integration
grails test-app Foo
grails test-app Foo Bar

3) Functional tests

grails test-app functional *Spec
grails test-app functional: -inline
grails test-app functional <SpecName>

4) Cobertura
grails test-app -coverage

5) Run app:
grails run-app
http://localhost:8080/myhobby

http://localhost:8080/myhobby/user/show/1
