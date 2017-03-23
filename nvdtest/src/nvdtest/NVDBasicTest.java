package nvdtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class NVDBasicTest {

	  public static void main(String[] args) {

		  // Create a new instance of the html unit driver
	      WebDriver driver = new HtmlUnitDriver();

	      // And now use this to visit nvd.nist.gov
	      // driver.get("http://www.google.com");
	      driver.get("https://web.nvd.nist.gov/view/vuln/search");

	      // Find the text input element by its name
	      WebElement element = driver.findElement(By.name("pageForm"));

	      // Enter something to search for
	      element.sendKeys("Cheese!");

	      // Now submit the form. WebDriver will find the form for us from the element
	      element.submit();

	      // Check the title of the page
	      System.out.println("Page title is: " + driver.getTitle());

	      driver.quit();
	  }
}
