import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver

baseUrl = "http://localhost:8080/myhobby/"

reportsDir = "target/geb-reports"

driver = { 
    def chromeDriver = new File('test/drivers/chrome_win32_2.9/chromedriver.exe')
    System.setProperty('webdriver.chrome.driver', chromeDriver.absolutePath)
	
	def locale = "en-us"
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--lang=" + locale);
	
    new ChromeDriver(options)
    
    //System.setProperty("webdriver.firefox.bin", "d:/prg/browser-ff-12.0/FirefoxPortable.exe")
    //new FirefoxDriver()
}

waiting {
    timeout = 20
    retryInterval = 0.5
	
    presets {
        slow {
            timeout = 30
            retryInterval = 1
        }
        quick {
            timeout = 1
        }
    }
}