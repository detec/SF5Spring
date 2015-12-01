package org.openbox.sf5.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginIT extends AbstractSeleniumTest {

	private String appLocation;

	public String getAppLocation() {
		return appLocation;
	}

	public void setAppLocation(String appLocation) {
		this.appLocation = appLocation;
	}

	// @Before

	public void doLogin() {
		driver.get(appLocation);

		// find field username
		WebElement inputField = driver.findElement(By.xpath("//*[@id='username']"));
		assertThat(inputField.isSelected());
		inputField.clear();
		inputField.sendKeys("admin");

		// find field password
		WebElement passwordField = driver.findElement(By.xpath("//*[@id='password']"));
		assertThat(passwordField.isSelected());
		passwordField.clear();
		passwordField.sendKeys("1");

		WebElement loginButton = driver.findElement(By.xpath("//*[@name='submit']"));
		assertThat(loginButton.isSelected());
		loginButton.click();

		// waiting while it processes input
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		WebElement uploadForm = driver.findElement(By.xpath("//*[@id='SettingsTable']"));
		assertThat(uploadForm.isSelected());

	}

}
