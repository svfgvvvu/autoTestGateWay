package test;

import java.util.concurrent.TimeUnit;

import org.apache.poi.util.SystemOutLogger;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PreCheckTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private int pass;
	private int sum;

	@Before
	public void setUp() throws Exception {
		pass = 0;
		sum = 1;
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
		pass += checkResult(checkBoxByAlert("语音协议", "protocol_slt", "H.248"));
		// 传真模块
		// T.30
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("传真")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		pass += checkResult(checkBoxByAlert("传真协议", "fax_type", "T.30"));
		// T.30全控
		pass += checkResult(checkBoxByAlert("传真模式", "fax_proto_mode", "T.30全控"));
		// H.248模块
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("H.248")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		driver.findElement(By.linkText("高级设置")).click();
		pass += checkResult(checkBoxByAlert("编解码优选", "codectype", "G.711 PCMA"));
		pass += checkResult(checkBoxByAlert("DTMF转移模式", "dtmfsetting", "InBand"));
		pass += checkResult(checkBoxByAlert("DTMF二次拨号", "secdialdtmf", "透传模式"));
		pass += checkResult(checkBoxByAlert("数图匹配模式", "dial_match_mode", "智能匹配"));
		pass += checkResult(checkBoxByAlert("来电显示模式", "phone_clid_type", "FSK"));
		pass += checkResult(checkBoxByAlert("心跳方式", "heart_mode", "被动心跳回应"));
		pass += checkResult(checkBoxByAlert("数图匹配模式", "dial_match_mode", "智能匹配"));
		pass += checkResult(checkValue("RTP打包周期", "PkTime", "20"));
		try {
			assertEquals("根注册", driver.findElement(By.xpath("//tr[8]/td[2]")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		pass += checkResult(checkValue("RTP打包周期", "PkTime", "20"));
		pass += checkResult(checkValue("TOS 默认值", "tos", "0"));
		pass += checkResult(checkValue("拍叉最小时间", "mintimer", "90"));
		pass += checkResult(checkValue("拍叉最大时间", "maxtimer", "500"));
		pass += checkResult(checkRadio("回声消除", "ecan_enable"));
		pass += checkResult(checkRadio("静音抑制", "vad_disable"));
		// SIP
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("SIP")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		driver.findElement(By.linkText("高级设置")).click();
		pass += checkResult(checkRadio("话机时间同步", "time_sync_enable"));
		pass += checkResult(checkValue("话机摘机不拨号时间", "StartDigitTimer", "20"));
		pass += checkResult(checkValue("位间短定时器时间", "InterDigitTimerShort", "5"));
		pass += checkResult(checkValue("位间长定时器时间", "InterDigitTimerLong", "10"));
		pass += checkResult(checkValue("久叫不应时间", "NoAnswerTimer", "60"));
		pass += checkResult(checkValue("催挂音时间", "HangingReminderToneTimer", "60"));
		pass += checkResult(checkValue("放忙音时间", "BusyToneTimer", "40"));
		driver.findElement(By.linkText("服务器配置")).click();
		pass += checkResult(checkValue("SIP注册周期", "sip_expires_num", "3600"));
		System.out.println("Sum:" + sum + "    Pass:" + pass);
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

	private int checkResult(boolean result) {
		if (result) {
			System.out.println("成功");
			return 1;
		} else {
			System.err.println("失败");
			return 0;
		}
	}

	// 下拉列表检查
	private boolean checkBoxByAlert(String name, String id, String expect) {
		/*
		 * @author wangzhuang checkbox id expect
		 */
		System.out.println(name + "已检查");
		try {
			String js = "var " + id + " = document.getElementById(\"" + id + "\");" + "alert(" + id + ".options[" + id
					+ ".selectedIndex].innerText);";
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

	// 单选按钮检查
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

	// 值检查
	private boolean checkValue(String name, String id, String expect) {
		System.out.println(name + "已检查");
		try {
			String js = "alert(document.getElementById(\"" + id + "\").value);";
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

}
