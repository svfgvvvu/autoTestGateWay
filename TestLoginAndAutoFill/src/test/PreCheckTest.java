package test;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PreCheckTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.1.1/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testUntitledTestCase() throws Exception {
		driver.get("http://192.168.1.1/");
		driver.findElement(By.name("user_name")).click();
		driver.findElement(By.name("user_name")).clear();
		driver.findElement(By.name("user_name")).sendKeys("telecomadmin");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("nE7jA%5m");
		driver.findElement(By.name("login")).click();
		driver.findElement(By.linkText("语音配置")).click();
		// 系统设置模块
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("系统配置")).click();
		// H.248
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		checkBoxByAlert("语音协议","protocol_slt","H.248");
		// 传真模块
		// T.30
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("传真")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		checkBoxByAlert("传真协议","fax_type","T.30");
		// T.30全控
		checkBoxByAlert("传真模式","fax_proto_mode","T.30全控");
		// H.248模块
		//
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("H.248")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		driver.findElement(By.linkText("高级设置")).click();
		checkBoxByAlert("编解码优选","codectype","G.711 PCMA");
		checkRadio("回声消除","ecan_enable");
		checkRadio("静音抑制","vad_disable");
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
	//下拉列表检查
	private boolean checkBoxByAlert(String name, String id, String expect) {
		/*
		 * @author wangzhuang
		 * checkbox id
		 * expect 
		 */
		System.out.println(name + "已检查");
		try {
			String js = "var " + id +" = document.getElementById(\"" + id + "\");"
					+ "alert(" + id + ".options[" + id + ".selectedIndex].innerText);";
			((JavascriptExecutor) driver).executeScript(js);
			if (isAlertPresent()) {
				assertEquals(expect, closeAlertAndGetItsText());
			}
			return true;
		} catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}
	}
	//单选按钮检查
	private boolean checkRadio(String name, String id) {
		System.out.println(name + "已检查");
		try {
			assertEquals(true, driver.findElement(By.id(id)).isSelected());
			return true;
		} catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}
	}
	//值检查
	private boolean checkValue(String name, String id, String value) {
		System.out.println(name + "已检查");
		try {
			assertEquals(value, driver.findElement(By.id(id)).getText());
			return true;
		}catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}
	}
	
}
