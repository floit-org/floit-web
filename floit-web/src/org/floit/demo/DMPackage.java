package org.floit.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DMPackage {
	static WebDriver driver = null;
	static final int TIMEOUT = 10;
	static final int PAGE_TIMEOUT = 30;	
	static final int UNINSTALL_TIMEOUT = 180;
	static final int INSTALL_TIMEOUT = 180;
	static final String SUMMARY_PANEL = "Dealmaker Opportunity Summary";
	private static final String PRIME_FIELD = "PRIME Action";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// dmpackage [action [install,uninstall]] [org] [password] -i -u
		String action = args[0];
		String org = args[1];
		String password = args[2];

		if (!"install".equals(action) && !"uninstall".equals(action)) {
			System.out.println("Fail to run, invalid action provided. Valid values are install or uninstall");
			System.exit(1);
		}
		if (!(org != null && org.length() > 0) && !(password != null && password.length() > 0)) {
			System.out.println("Fail to run, valid org and password must be supplied");
			System.exit(1);
		} 		

		try {

			DMPackage test = new DMPackage();
			driver = new FirefoxDriver(test.setFirefoxProfile("TAS Test"));
			// driver.manage().window().maximize();

			if ("uninstall".equals(action)) {
				System.out.println("Run package uninstall in org: " + org);
				test.runLogin(org, password);
				test.runSetup();
				test.runRemoveSummaryPanel();
				test.runRemovePrimeField();
				test.runUninstallPackage("Dealmaker");

			} else if ("install".equals(action)) {
				String installURL = args[3];
				String installPassword = args[4];

				System.out.println("Run package install in org: " + org);
				test.runLogin(org, password);
				test.runSetup();
				test.runInstallPackage(installURL, installPassword);
			}

			driver.quit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (driver != null)
				driver.quit();
		}
	}

	/**
	 * 
	 * @param pack
	 * @param pw
	 */
	public void runInstallPackage(String pack, String pw) {
		
		// Load the package URL
		driver.get(pack);
		
		System.out.println("Installing package - " + pack);
		
		// Wait for package installation details page to load
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Package Installation Details']")));
		
		WebElement password = driver.findElement(By.id("InstallPackagePage:InstallPackageForm:PasswordPageBlock:PasswordRequiredBlockSection:PasswordRequiredBlockSectionItem1:PasswordText"));
		password.sendKeys(pw);
		
		WebElement submit = driver.findElement(By.id("InstallPackagePage:InstallPackageForm:PasswordPageBlock:PasswordRequiredBlockSection:PasswordRequiredBlockSectionItem1:SubmitBtn"));
		submit.click();
		
		// Wait for package installation details page to load
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Package Installation Details']")));
		
		if(!isElementPresent(By.id("InstallPackagePage:InstallPackageForm:InstallBtn"))) {
			return;
		}
		
		WebElement proceed = driver.findElement(By.id("InstallPackagePage:InstallPackageForm:InstallBtn"));
		proceed.click();
		
		// Wait for page to load
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Dealmaker')]")));
		
		WebElement next = driver.findElement(By.name("goNext")); 
		next.click();
		
		// Wait for page to load - Step 2. Choose security level
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Step 2. Choose security level')]")));		
		
		WebElement check = driver.findElement(By.xpath("//table/tbody/tr[3]/td/input[@type='radio']"));
		check.click();
		
		// Wait for div to display
		(new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.id("securitySettings")));
		
		// Set the permissions for Normal users
		//Select selectReadOnly = new Select(driver.findElement(By.xpath("//table/tbody/tr[th/text()='Normal Read Only']/td/select")));
		//selectReadOnly.selectByVisibleText("Dealmaker Read Only");
		
		Select selectUser= new Select(driver.findElement(By.xpath("//table/tbody/tr[th/text()='Normal User']/td/select")));
		selectUser.selectByVisibleText("Dealmaker User");		

		next = driver.findElement(By.name("goNext")); 
		next.click();
		
		// Wait for page to load - Step 3. Install Package
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Step 3. Install Package')]")));
		
		WebElement installButton = driver.findElement(By.xpath("//input[@type='submit' and @title='Install']"));
		installButton.click();
		
		// Wait for page to load - Step 3. Install Package
		(new WebDriverWait(driver, INSTALL_TIMEOUT)).until(ExpectedConditions.titleContains("Processing "));
		
	}

	/**
	 * 
	 * @param profileName
	 * @return
	 */
	public FirefoxProfile setFirefoxProfile(String profileName) {
		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile profile = allProfiles.getProfile(profileName);
		return profile;
	}

	/**
	 * 
	 */
	public void runLogin(String username, String password) throws Exception {

		driver.get("https://login.salesforce.com");

		WebElement login = driver.findElement(By.name("username"));
		WebElement pw = driver.findElement(By.name("pw"));

		login.sendKeys(username);
		pw.sendKeys(password);

		login.submit();

		// Wait for home page
		(new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.titleContains("salesforce.com "));
		(new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.id("userNav")));

		System.out.println("Login successfull");
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void runSetup() throws Exception {
		WebElement userNav = driver.findElement(By.id("userNav"));
		userNav.click();

		(new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Setup")));
		WebElement setup = driver.findElement(By.linkText("Setup"));
		setup.click();

		// Wait for page to load
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(By.linkText("System Overview")));
	}

	/**
	 * 
	 * @param packageName
	 * @throws Exception
	 */
	public void runUninstallPackage(String packageName) throws Exception {

		WebElement ip = driver.findElement(By.linkText("Installed Packages"));
		ip.click();

		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(By.xpath("//h1[text()='Installed Packages']")).isDisplayed();
			}
		});
		
		// Check if the package is installed
		if(!isElementPresent(By.xpath("//a[contains(@title, 'Dealmaker') and text()='Uninstall']"))) {
			System.out.println("Looks like Dealmaker package is not installed, skip uninstall step");
			return;
		}
		
		// Click uninstall link
		WebElement uninstall = driver.findElement(By.xpath("//a[contains(@title, 'Dealmaker') and text()='Uninstall']"));
		uninstall.click();
		
		// Wait for uninstall package page to load
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Uninstalling a Package']")));
		
		WebElement checkbox = driver.findElement(By.id("p5"));
		checkbox.click();
		
		WebElement save = driver.findElement(By.name("save"));
		save.click();
		
		// TODO: Verify uninstall if needs be
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void runRemoveSummaryPanel() throws Exception {
		// Assume your on the setup page
		WebElement cust = driver.findElement(By.linkText("Customize"));
		cust.click();

		// Wait for page to load
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(By.linkText("Opportunities")).isDisplayed();
			}
		});

		// 
		WebElement opp = driver.findElement(By.id("Opportunity_font"));
		opp.click();

		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(By.linkText("Page Layouts")).isDisplayed();
			}
		});

		WebElement page = driver.findElement(By.linkText("Page Layouts"));
		page.click();

		// Wait for page to load
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(By.xpath("//h1[text()='Opportunity Page Layout']")).isDisplayed();
			}
		});

		// Click edit link
		WebElement edit = driver.findElement(By.linkText("Edit"));
		edit.click();

		// Give the page time to load
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//button[text()='Opportunity Layout']")));
		
		
		// If fully load check if the Dealmaker Summary panel is on the page
		if (!isElementPresent(By.xpath("//div[2]/div/div/span[text()='" + SUMMARY_PANEL + "']"))) {
			System.out.println("Looks like Summary panel is not present, skip remove step");
			return;
		}
		
		Actions action = new Actions(driver);
		WebElement panel = driver.findElement(By.xpath("//div[div/span[text()='Dealmaker']]//table/tbody/tr"));
		action.moveToElement(panel);
		action.perform();

		WebElement widget = driver.findElement(By
				.xpath("//div[div/span[text()='Dealmaker']]//table/tbody/tr/td[1]/*/*/div[@class='widget']"));

		// Enable widget using Javascript
		String displayScript = "document.getElementById('" + widget.getAttribute("id") + "').style.display = 'block'";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(displayScript, widget);

		WebElement remove = driver.findElement(By
				.xpath("//div[div/span[text()='Dealmaker']]//table/tbody/tr/td[1]//div[@class='remove']"));
		//System.out.println("remove ID " + remove.getAttribute("id"));
		remove.click();


		System.out.println("Summary should be removed");


		WebElement save = driver.findElement(By.id("saveBtn"));
		save.click();

		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(By.xpath("//h1[text()='Opportunity Page Layout']")).isDisplayed();
			}
		});
	}
	
	public void runRemovePrimeField() {
		
		// Assume your on the setup page
		WebElement cust = driver.findElement(By.linkText("Customize"));
		cust.click();
		// Wait for menu to expand
		(new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Activities")));
		
		WebElement activitiesLink = driver.findElement(By.id("Activity_font"));
		activitiesLink.click();	
		
		// Wait for menu to expand
		(new WebDriverWait(driver, TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Task Page Layouts")));
		
		WebElement pageLayouts = driver.findElement(By.linkText("Task Page Layouts"));
		pageLayouts.click();	
		
		// Wait for page to load
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Task Page Layout']")));
		
		// Click edit link
		WebElement edit = driver.findElement(By.linkText("Edit"));
		edit.click();		
		
		// Give the page time to load
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//button[text()='Task Layout']")));

		// If fully load check if the Dealmaker Summary panel is on the page
		if (!isElementPresent(By.xpath("//td[div/div/div[@class='itemLabel']/span[text()='" + PRIME_FIELD + "']]"))) {
			System.out.println("Looks like the PRIME field is not present, skip remove step");
			return;
		}
		
		
		Actions action = new Actions(driver);
		WebElement field = driver.findElement(By.xpath("//td[div/div/div[@class='itemLabel']/span[text()='" + PRIME_FIELD + "']]"));
		action.moveToElement(field);
		action.perform();

		WebElement widget = driver.findElement(By
				.xpath("//td/div/div[div/span[text()='PRIME Action']]/div[@class='widget']"));

		// Enable widget using Javascript
		String displayScript = "document.getElementById('" + widget.getAttribute("id") + "').style.display = 'block'";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(displayScript, widget);

		WebElement remove = driver.findElement(By
				.xpath("//td/div/div[div/span[text()='PRIME Action']]/div[@class='widget']/div[@class='remove']"));
		//System.out.println("remove ID " + remove.getAttribute("id"));
		remove.click();

		System.out.println("PRIME should be removed");

		WebElement save = driver.findElement(By.id("saveBtn"));
		save.click();		
		
		// Wait for page to load
		(new WebDriverWait(driver, PAGE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Task Page Layout']")));
	}
	
	/**
	 * 
	 * @param locator
	 * @return
	 */
	private boolean isElementPresent(By locator) {

		boolean present = false;
		try {
			driver.findElement(locator);
			present = true;
		} catch (NoSuchElementException e) {
			System.out.println("Element [" + locator.toString() + "] is not present");
		}
		return present;
	}

}
