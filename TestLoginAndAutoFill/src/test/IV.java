package test;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IV {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String[][] account = {
			{"6001", "123456"},{"6002", "123456"},{"6003", "123456"},{"6004", "123456"},
			{"6005", "123456"},{"6006", "123456"},{"6007", "123456"},{"6008", "123456"}
	};
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "https://192.168.1.1/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void test() throws Exception {
		driver.get("http://192.168.1.1/");
		driver.findElement(By.name("user_name")).clear();
		driver.findElement(By.name("user_name")).sendKeys("telecomadmin");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("nE7jA%5m");
		driver.findElement(By.name("login")).click();
		driver.findElement(By.linkText("基础配置")).click();
		driver.switchTo().frame("Frame_Content");
		driver.findElement(By.linkText("WAN子接口")).click();
		driver.findElement(By.linkText("添加")).click();
		driver.findElement(By.id("Nwansub_id")).click();
		driver.findElement(By.id("Nwansub_id")).click();
		driver.findElement(By.id("Nwansub_id")).clear();
		driver.findElement(By.id("Nwansub_id")).sendKeys("41");
		driver.findElement(By.id("service_type")).click();
		new Select(driver.findElement(By.id("service_type"))).selectByVisibleText("Internet");
		driver.findElement(By.xpath("//option[@value='1']")).click();
		driver.findElement(By.id("link_mode")).click();
		new Select(driver.findElement(By.id("link_mode"))).selectByVisibleText("DHCP获取IP地址");
		driver.findElement(By.xpath("(//option[@value='2'])[3]")).click();
		driver.findElement(By.id("Dmode_dns")).click();
		driver.findElement(By.id("submit_Nuser")).click();
		driver.findElement(By.linkText("添加")).click();
		driver.findElement(By.id("Nwansub_id")).click();
		driver.findElement(By.id("Nwansub_id")).clear();
		driver.findElement(By.id("Nwansub_id")).sendKeys("42");
		driver.findElement(By.id("service_type")).click();
		new Select(driver.findElement(By.id("service_type"))).selectByVisibleText("Voice");
		driver.findElement(By.xpath("//option[@value='3']")).click();
		driver.findElement(By.id("link_mode")).click();
		new Select(driver.findElement(By.id("link_mode"))).selectByVisibleText("DHCP获取IP地址");
		driver.findElement(By.xpath("(//option[@value='2'])[3]")).click();
		driver.findElement(By.id("Dmode_dns")).click();
		driver.findElement(By.id("submit_Nuser")).click();
		driver.switchTo().parentFrame();
		driver.findElement(By.linkText("语音配置")).click();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("SIP")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		driver.findElement(By.id("check_port")).click();
		new Select(driver.findElement(By.id("check_port"))).selectByVisibleText("ALL");
		driver.findElement(By.xpath("//option[@value='0']")).click();
		driver.findElement(By.id("pri_register_serv_ip")).click();
		driver.findElement(By.id("pri_register_serv_ip")).clear();
		driver.findElement(By.id("pri_register_serv_ip")).sendKeys("10.0.1.3");
		driver.findElement(By.id("out_pri_register_serv_ip")).click();
		driver.findElement(By.id("out_pri_register_serv_ip")).clear();
		driver.findElement(By.id("out_pri_register_serv_ip")).sendKeys("10.0.1.3");
		driver.findElement(By.id("pri_proxy_serv_ip")).click();
		driver.findElement(By.id("pri_proxy_serv_ip")).clear();
		driver.findElement(By.id("pri_proxy_serv_ip")).sendKeys("10.0.1.3");
		driver.findElement(By.name("submit_server")).click();
		driver.switchTo().alert();
		assertEquals("修改成功！请保存配置！", closeAlertAndGetItsText());
		driver.findElement(By.linkText("用户认证")).click();
		for(int i = 0; i < 8; i++) {
		fillingVoiceMenu(i+1,account[i][0],account[i][1]);
		}
		driver.switchTo().parentFrame();
		driver.findElement(By.linkText("保存配置")).click();
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions
                    .alertIsPresent());
           driver.switchTo().alert();
           
        } catch (NoAlertPresentException NofindAlert) {

            NofindAlert.printStackTrace();
            // throw NofindAlert;
        }
		assertEquals("配置保存成功!", closeAlertAndGetItsText());
		driver.switchTo().parentFrame();
		driver.findElement(By.linkText("系统管理")).click();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.id("TabContent7")).click();
		driver.findElement(By.linkText("配置维护")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		driver.findElement(By.name("button")).click();
		driver.switchTo().alert();
		assertEquals("配置保存成功!", closeAlertAndGetItsText());
		driver.switchTo().parentFrame();
		driver.findElement(By.linkText("退出")).click();
	}

	public boolean isContentAppeared(WebDriver driver, String content) {
		boolean status = false;
		try {
			driver.findElement(By.xpath(content));
			System.out.println(content + " is appeard!");
			status = true;
		} catch (NoSuchElementException e) {
			status = false;
			System.out.println("'" + content + "' doesn't exist!");
		}
		return status;
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
	private boolean fillingVoiceMenu(int index, String account, String password) {
		/*
		 * @author by wangzhaung
		 */
		if (isContentAppeared(driver, "//tr["+ index +"]/td[18]/div/span/a/img")) {
			driver.findElement(By.xpath("//tr["+ index +"]/td[18]/div/span/a/img")).click();
		} else {
			driver.findElement(By.xpath("//a[contains(@href, \"javascript:index_mod('user_auth','"+ index +"')\")]")).click();
		}
		driver.findElement(By.id("sip_user_name")).click();
		driver.findElement(By.id("sip_user_name")).clear();
		driver.findElement(By.id("sip_user_name")).sendKeys(account);
		driver.findElement(By.id("sip_account")).clear();
		driver.findElement(By.id("sip_account")).sendKeys(account);
		driver.findElement(By.id("sip_disname")).clear();
		driver.findElement(By.id("sip_disname")).sendKeys(account);
		driver.findElement(By.id("sip_user_password")).clear();
		driver.findElement(By.id("sip_user_password")).sendKeys(password);
		driver.findElement(By.id("alias_name")).clear();
		driver.findElement(By.id("alias_name")).sendKeys("");
		driver.findElement(By.id("sip_activation_open")).click();
		driver.findElement(By.name("submit_server")).click();
		driver.switchTo().alert();
		assertEquals("修改成功！请保存配置！", closeAlertAndGetItsText());
		return true;
	}
}
