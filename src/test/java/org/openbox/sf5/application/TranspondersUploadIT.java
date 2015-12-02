package org.openbox.sf5.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TranspondersUploadIT extends AbstractSeleniumTest {

	private String appLocation;

	public void doUpload(String absolutePath) {
		// get to upload page
		driver.get(appLocation + "upload");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// check that we got
		WebElement UploadForm = driver.findElement(By.id("UploadForm"));
		assertThat(UploadForm.isDisplayed());

		// find field username
		WebElement inputField = driver.findElement(By.xpath("//*[@id='UploadField']"));
		assertThat(inputField.isSelected());

		inputField.sendKeys(absolutePath);

		// upload file to server
		WebElement browseButton = driver.findElement(By.id("uploadButton"));
		assertThat(browseButton.isSelected());
		browseButton.click();

		// waiting while it processes input
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// check that we got
		WebElement transponderListForm = driver.findElement(By.id("transponderListForm"));
		assertThat(transponderListForm.isDisplayed());

	}

	public String getAppLocation() {
		return appLocation;
	}

	public void setAppLocation(String appLocation) {
		this.appLocation = appLocation;
	}

}
