grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolver = "maven"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "info" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    def spockGrails = '0.7-groovy-2.0'
    def spockVersion = '0.7'
    def gebVersion = '0.9.0-RC-1'
    def seleniumVersion = '2.35.0'
        
    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.20'
        
        test "org.spockframework:spock-grails-support:$spockGrails"
            
        test "org.gebish:geb-spock:$gebVersion"

        test("org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion")
	    test("org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion")
        test("org.seleniumhq.selenium:selenium-support:$seleniumVersion")
    }
    

    plugins {
        // plugins for the build system only
        build ':tomcat:7.0.52.1'
        // plugins for the compile step
        compile ':scaffolding:2.1.0'
        compile ':cache:1.1.3'
        compile ':asset-pipeline:1.8.3'

        // plugins needed at runtime but not for compilation
        runtime ':hibernate4:4.3.5.2' // or ':hibernate:3.6.10.14'
        //runtime ':hibernate4:4.3.4'
        runtime ':database-migration:1.4.0'
        runtime ':jquery:1.11.0.2'
        
        test ":code-coverage:1.2.5" 
        test ":geb:$gebVersion"
    }
}
