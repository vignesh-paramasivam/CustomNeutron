package page;

import java.util.List;

import org.openqa.selenium.WebElement;

import agent.IAgent;
import agent.internal.IWindowActions;
import control.IControl;
import enums.Platform;
import pagedef.Identifier;

public interface IPage extends IWindowActions {
	IControl getControl(String name) throws Exception;

	List<IControl> getControls(String name) throws Exception;

	IAgent getAgent();

	Platform getPlatform();

	Identifier getIdentifier(String name);

	String pageName();

	IControl getTextboxControl(String string) throws Exception;

	IControl getButtonControl(String string) throws Exception;

	IControl getLinkControl(String string) throws Exception;

	IControl getCheckboxControl(String string) throws Exception;

	IControl getCalendarControl(String string) throws Exception;

	IControl getTextboxControl(String string, WebElement parentElement) throws Exception;

	IControl getButtonControl(String string, WebElement parentElement) throws Exception;

	IControl getLinkControl(String string, WebElement parentElement) throws Exception;
	
	IControl getTextareaControl(String string) throws Exception;

	IControl getTextareaControl(String string, WebElement dialog) throws Exception;

	IControl getCheckboxControl(String string, WebElement parentElement) throws Exception;

	IControl getCalendarControl(String string, WebElement parentElement) throws Exception;
	
	IControl getDropdownControl(String string) throws Exception;

	IControl getDropdownControl(String string, WebElement parentElement) throws Exception;

	IControl getCascadingDropdownControl(String string, WebElement parentElement) throws Exception;

	IControl getMenuControl(String string) throws Exception;
	
	IControl getSectionControl(String string) throws Exception;
	
	IControl getSectionControl(String string, WebElement parentElement) throws Exception;
	
    IControl getDualCalendarControl(String string) throws Exception;
	
	IControl getDualCalendarControl(String string, WebElement parentElement) throws Exception;

	IControl getRadioControl(String string) throws Exception;

	IControl getRadioControl(String string, WebElement parentElement) throws Exception;

	IControl getAttachmentControl(String string) throws Exception;

	IControl getAttachmentControl(String string, WebElement parentElement) throws Exception;


}
