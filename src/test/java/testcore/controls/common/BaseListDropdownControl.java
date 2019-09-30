package testcore.controls.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import page.IPage;

public class BaseListDropdownControl extends DropdownControl {

	String GROUPDROPDOWN_IDENTIFIER = "//button[@id='asset-groups-toggle']";
	String group_list_identifier    = "//span[@id='edit']/preceding-sibling::span[1]";
	String edit_icon_identifier       = "following-sibling::span[@id='edit']";
	String delete_icon_identifier   = "following-sibling::span[@id='delete']";

	public BaseListDropdownControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}


	/**Edit the specified group in BaseList Page
	 * @throws Exception **/
	public void openEditGroupDialog(String group) throws Exception{
		this.getAgent().getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='asset-groups-toggle']")));  
		this.getAgent().getWebDriver().findElement(By.xpath(GROUPDROPDOWN_IDENTIFIER)).click();
		List<WebElement> groupList = this.getAgent().getWebDriver().findElements(By.xpath(group_list_identifier));
		for(WebElement groupName : groupList){
			if(group.equals(groupName.getText())){
				groupName.findElement(By.xpath(edit_icon_identifier)).click();
				break;
			}
		}
	}

	/**Delete the specified group in BaseList Page
	 * @throws Exception **/
	public void deleteGroup(String group) throws Exception{
		if(this.getAgent().getWebDriver().findElement(By.cssSelector("div.dropdown-menu[role='menu']")).getAttribute("aria-hidden").equals("true")){
			this.getAgent().getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='asset-groups-toggle']")));   
			this.getAgent().getWebDriver().findElement(By.xpath(GROUPDROPDOWN_IDENTIFIER)).click();
		};
   		List<WebElement> groupList = this.getAgent().getWebDriver().findElements(By.xpath(group_list_identifier));
		for(WebElement groupName : groupList){
			if(group.equals(groupName.getText())){
				groupName.findElement(By.xpath(delete_icon_identifier)).click();
				break;
			}
		}
	}

	/**Check group is present after creation and is not displayed after deletion
	 * @throws Exception **/
	public boolean isGroupPresent(String group) throws Exception{
		boolean result = false;
		this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(GROUPDROPDOWN_IDENTIFIER)));
		//Opens the dropdown
		if(this.getAgent().getWebDriver().findElement(By.cssSelector("div.dropdown-menu[role='menu']")).getAttribute("aria-hidden").equals("true")){
			this.getAgent().getWebDriver().findElement(By.xpath(GROUPDROPDOWN_IDENTIFIER)).click();
		};
		List<WebElement> groupList = this.getAgent().getWebDriver().findElements(By.xpath(group_list_identifier));
		for(WebElement groupName : groupList){
			if(group.equals(groupName.getText())){
				result = true;
				break;
			}
		}
		// Close the groups dropdown
		if(this.getAgent().getWebDriver().findElement(By.cssSelector("div.dropdown-menu[role='menu']")).getAttribute("aria-hidden").equals("false")){
			this.getAgent().getWebDriver().findElement(By.xpath(GROUPDROPDOWN_IDENTIFIER)).click();
		};
		return result;
	}
	
	/**Select any specified group in BaseList Page
	 * @throws Exception **/
	public void selectGroup(String group) throws Exception{
		this.getAgent().getWebDriver().findElement(By.xpath(GROUPDROPDOWN_IDENTIFIER)).click();
		List<WebElement> groupList = this.getAgent().getWebDriver().findElements(By.xpath(group_list_identifier));
		boolean isGroupSelected = false;
		for(WebElement groupName : groupList){
			if(group.equals(groupName.getText())){
				groupName.click();
				isGroupSelected = true;
				break;
			}
		}
		Assert.assertTrue(isGroupSelected, "Group is not selected/ available");
		getPage().assertPageLoad();
	}
	
	/**Returns  group name
	 * @throws Exception **/
	public String getSelectedGroupName() throws Exception{
		return this.getAgent().getWebDriver().findElement(By.xpath(GROUPDROPDOWN_IDENTIFIER)).getText();
		}

}
		
