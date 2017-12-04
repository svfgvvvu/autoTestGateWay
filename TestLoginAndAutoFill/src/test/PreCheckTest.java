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
		driver.findElement(By.linkText("��������")).click();
		// ϵͳ����ģ��
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("ϵͳ����")).click();
		// H.248
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		pass += checkResult(checkBoxByAlert("����Э��", "protocol_slt", "H.248"));
		// ����ģ��
		// T.30
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("����")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		pass += checkResult(checkBoxByAlert("����Э��", "fax_type", "T.30"));
		// T.30ȫ��
		pass += checkResult(checkBoxByAlert("����ģʽ", "fax_proto_mode", "T.30ȫ��"));
		// H.248ģ��
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("H.248")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		driver.findElement(By.linkText("�߼�����")).click();
		pass += checkResult(checkBoxByAlert("�������ѡ", "codectype", "G.711 PCMA"));
		pass += checkResult(checkBoxByAlert("DTMFת��ģʽ", "dtmfsetting", "InBand"));
		pass += checkResult(checkBoxByAlert("DTMF���β���", "secdialdtmf", "͸��ģʽ"));
		pass += checkResult(checkBoxByAlert("��ͼƥ��ģʽ", "dial_match_mode", "����ƥ��"));
		pass += checkResult(checkBoxByAlert("������ʾģʽ", "phone_clid_type", "FSK"));
		pass += checkResult(checkBoxByAlert("������ʽ", "heart_mode", "����������Ӧ"));
		pass += checkResult(checkBoxByAlert("��ͼƥ��ģʽ", "dial_match_mode", "����ƥ��"));
		pass += checkResult(checkValue("RTP�������", "PkTime", "20"));
		try {
			assertEquals("��ע��", driver.findElement(By.xpath("//tr[8]/td[2]")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		pass += checkResult(checkValue("RTP�������", "PkTime", "20"));
		pass += checkResult(checkValue("TOS Ĭ��ֵ", "tos", "0"));
		pass += checkResult(checkValue("�Ĳ���Сʱ��", "mintimer", "90"));
		pass += checkResult(checkValue("�Ĳ����ʱ��", "maxtimer", "500"));
		pass += checkResult(checkRadio("��������", "ecan_enable"));
		pass += checkResult(checkRadio("��������", "vad_disable"));
		// SIP
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("SIP")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		driver.findElement(By.linkText("�߼�����")).click();
		pass += checkResult(checkRadio("����ʱ��ͬ��", "time_sync_enable"));
		pass += checkResult(checkValue("����ժ��������ʱ��", "StartDigitTimer", "20"));
		pass += checkResult(checkValue("λ��̶�ʱ��ʱ��", "InterDigitTimerShort", "5"));
		pass += checkResult(checkValue("λ�䳤��ʱ��ʱ��", "InterDigitTimerLong", "10"));
		pass += checkResult(checkValue("�ýв�Ӧʱ��", "NoAnswerTimer", "60"));
		pass += checkResult(checkValue("�߹���ʱ��", "HangingReminderToneTimer", "60"));
		pass += checkResult(checkValue("��æ��ʱ��", "BusyToneTimer", "40"));
		driver.findElement(By.linkText("����������")).click();
		pass += checkResult(checkValue("SIPע������", "sip_expires_num", "3600"));
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
			System.out.println("�ɹ�");
			return 1;
		} else {
			System.err.println("ʧ��");
			return 0;
		}
	}

	// �����б���
	private boolean checkBoxByAlert(String name, String id, String expect) {
		/*
		 * @author wangzhuang checkbox id expect
		 */
		System.out.println(name + "�Ѽ��");
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

	// ��ѡ��ť���
	private boolean checkRadio(String name, String id) {
		System.out.println(name + "�Ѽ��");
		try {
			assertEquals(true, driver.findElement(By.id(id)).isSelected());
			return true;
		} catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}
	}

	// ֵ���
	private boolean checkValue(String name, String id, String expect) {
		System.out.println(name + "�Ѽ��");
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
