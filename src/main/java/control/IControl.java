package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public interface IControl {
	void click() throws Exception;

	void enterText(String text) throws Exception;

	void enterText(Keys text) throws Exception;
	
	void uploadFile(String text) throws Exception;

	String getText() throws Exception;
	
	String getValue() throws Exception;

	void assertText(String text) throws Exception;

	boolean isVisible() throws Exception;

	void assertVisible() throws Exception;

	boolean isEnabled() throws Exception;

	void assertEnabled() throws Exception;

	void scrollTo() throws Exception;

	void selectDropDownByValue(String value) throws Exception;

	void waitUntilVisible() throws Exception;

	void waitUntilClickable() throws Exception;

	IControl getControl(String name) throws Exception;

	List<IControl> getControls(String name) throws Exception;
	
	void hover() throws Exception;

	void clear() throws Exception;

	ArrayList<String> column_headers() throws Exception;

	void verifyValues(HashMap<String, String> unique_column_name_and_values,
			HashMap<String, String> expected_column_name_and_values) throws Exception;	
	
	boolean isMandatoryField() throws Exception;
	
	void enterDate(String string) throws Exception;

	void enterValue(String text) throws Exception;

	String getErrorMessage() throws Exception;

	boolean isSelected() throws Exception;
	
	boolean isReadOnly() throws Exception;

	boolean isDropdown() throws Exception;

	void clearfield() throws Exception;

	boolean isElementPresent() throws Exception;

	void clear_via_javascript() throws Exception;

	WebElement label() throws Exception;

	WebElement getTooltip() throws Exception;

	void checkboxCheck() throws Exception;

	void checkboxUnCheck() throws Exception;

	List<WebElement> rows() throws Exception;
	
	void  scrollDownAndUp() throws Exception;
	
    void  pageUpAndDown() throws Exception;

    WebElement thisControlElement() throws Exception;   

}
