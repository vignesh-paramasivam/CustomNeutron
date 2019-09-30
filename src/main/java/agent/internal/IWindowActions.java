package agent.internal;

import java.io.File;

import org.openqa.selenium.WebElement;

public interface IWindowActions {

	File takeSnapShot() throws Exception;

	void swipeUp() throws Exception;

	void swipeUp(int count) throws Exception;

	void swipeDown() throws Exception;

	void swipeDown(int count) throws Exception;

	void goTo(String url) throws Exception;

	void goBack() throws Exception;

	void switchToNativeView() throws Exception;

	void switchToWebView() throws Exception;

	void switchToNewWindow() throws Exception;

	void switchToMainWindow() throws Exception;

	void acceptAlert() throws Exception;

	void scrollUp() throws Exception;

	void scrollUp(int count) throws Exception;

	void scrollDown() throws Exception;

	void scrollDown(int count) throws Exception;
	
	void swipeDownTillElement(String elementName) throws Exception;

	void assertPageLoad() throws Exception;
	
	void scrollIntoView(WebElement element) throws Exception;

	File takeSnapShot(String testname) throws Exception;
}
