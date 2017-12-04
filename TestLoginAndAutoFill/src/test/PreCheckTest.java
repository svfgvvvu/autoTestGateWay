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
		driver.findElement(By.linkText("��������")).click();
		// ϵͳ����ģ��
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("ϵͳ����")).click();
		// H.248
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		checkBoxByAlert("����Э��","protocol_slt","H.248");
		// ����ģ��
		// T.30
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("����")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		checkBoxByAlert("����Э��","fax_type","T.30");
		// T.30ȫ��
		checkBoxByAlert("����ģʽ","fax_proto_mode","T.30ȫ��");
		// H.248ģ��
		//
		driver.switchTo().parentFrame();
		driver.switchTo().frame("left_bar");
		driver.findElement(By.linkText("H.248")).click();
		driver.switchTo().parentFrame();
		driver.switchTo().frame("Frame_Content");
		driver.findElement(By.linkText("�߼�����")).click();
		checkBoxByAlert("�������ѡ","codectype","G.711 PCMA");
		checkRadio("��������","ecan_enable");
		checkRadio("��������","vad_disable");
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
	//�����б���
	private boolean checkBoxByAlert(String name, String id, String expect) {
		/*
		 * @author wangzhuang
		 * checkbox id
		 * expect 
		 */
		System.out.println(name + "�Ѽ��");
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
	//��ѡ��ť���
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
	//ֵ���
	private boolean checkValue(String name, String id, String value) {
		System.out.println(name + "�Ѽ��");
		try {
			assertEquals(value, driver.findElement(By.id(id)).getText());
			return true;
		}catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}
	}
	
}
