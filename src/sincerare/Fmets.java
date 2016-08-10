/*
 The MIT License (MIT)
 
 Copyright (c) 2016 JJ Luna
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package sincerare;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import sincerare.Fctes.browserName;
import sincerare.Fctes.logEntry;
import sincerare.Fctes.logLevel;
import sincerare.Fctes.logSeverity;
import sincerare.Fctes.objectAction;
import sincerare.Fctes.objectScope;
import sincerare.Fctes.testOperators;
import sincerare.Fctes.uiAction;

/**
 * @author JJ Luna (Juan Luna)
 * @Description Java class that contains a set of methods which are the framework. 
 *              These are generic framework methods
 */
public class Fmets {
    /**
     * @Description Method that identifies the test host and finds/creates corresponding paths.
     */
    public static void getTestEnvironment () {
        //Start timer
        Fvars.os_Timer       = System.currentTimeMillis();
        
        //OS analysis
        Fvars.os_Name        = System.getProperty("os.name").toLowerCase();
        
        //Paths analysis
        Fvars.os_PathIn      = System.getProperty("user.dir") + File.separator  + "input"   + File.separator;
        Fvars.os_PathOut     = System.getProperty("user.dir") + File.separator  + "output"  + File.separator;
        Fvars.os_PathRef     = System.getProperty("user.dir") + File.separator  + "rsc"     + File.separator;
        Fvars.ts_Uniqueness  = getTime(Fctes.dtSHORT);
        
        //Create directories if they do not exist
        createDirectory(Fvars.os_PathIn);
        createDirectory(Fvars.os_PathOut);
        createDirectory(Fvars.os_PathRef);
        
        //Clear output directory before use.
        deleteDirectoryContent(Fvars.os_PathOut);
    }
    /**
     * @Description Method that returns current time in a given format.
     */
    public static String getTime (String sDateFormat) {
        //Data validation
        if (sDateFormat == null) {return "";}
        
        //Date generation
        try {
            DateFormat dfDateFormat = new SimpleDateFormat(sDateFormat);
            return dfDateFormat.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            frmError ("generating a date format (" + sDateFormat + ")", e.getMessage(), logSeverity.HIGH);
            return "";
        }
    }
    /**
     * @Description Method that creates a directory.
     */
    static void createDirectory (String sDirectory) {
        try {
            //Creating Directory if does not exist
            File pathIn = new File(sDirectory);
            if (!pathIn.exists()) {pathIn.mkdirs();}
        } catch (Exception e) {
            frmError ("creating a directory (" + sDirectory + ")", e.getMessage(), logSeverity.HIGH);
        }
    }
    /**
     * @Description Method that deletes the content of a directory.
     */
    static void deleteDirectoryContent (String sDirectory) {
        try {
            //Clear output directory before use.
            FileUtils.cleanDirectory(new File(sDirectory));
        } catch (Exception e) {
            frmError ("deleting the directory content (" + sDirectory + ")", e.getMessage(), logSeverity.HIGH);
        }
    }
    /**
     * @Description Method that logs into the console and log file
     */
    public static void logMessage (logEntry sLogAs, String sMsgValue) {
        //Variables needed
        boolean bLog = true;
        String  sLog = ", -- ";
        
        //Increasing counters and detecting to log or not the message
        switch (sLogAs) {
            case PASS:
                Fvars.log_Step++;
                Fvars.log_Pass++;
                break;
            case FAIL:
                Fvars.log_Step++;
                Fvars.log_Fail++;
                sLog = ", xx ";
                getWebScreenshot();
                break;
            case WARNING:
                bLog = Fvars.ts_Log.equals(logLevel.MINIMUM) ? false : true;
                if (bLog) {Fvars.log_Step++;}
                Fvars.log_Warning++;
                break;
            case ERROR:
                bLog = Fvars.ts_Log.equals(logLevel.MINIMUM) ? false : true;
                if (bLog) {Fvars.log_Step++;}
                Fvars.log_Error++;
                sLog = ", XX ";
                getScreenshot();
                break;
            case INFO:
                bLog = Fvars.ts_Log.equals(logLevel.MAXIMUM) ? true : false;
                break;
            case EXEC:
                sLog = ", ~~ ";
                break;
            default:
                break;
        }
        
        String sLogEntry = getTime(Fctes.dtLONG) + sLog + sLogAs + ": " + sMsgValue;
        try {
            //Printing log
            if (bLog) {
                System.out.println(sLogEntry);
                
                //Printing log to a file
                File file = new File(Fvars.os_PathOut + Fctes.ioLogFile);
                if (!file.exists()) {file.createNewFile();}
                FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.newLine();
                bw.write(sLogEntry);
                bw.close();
                return;
            }
        }  catch (Exception e) {
            frmError ("login a message! (" + sLogEntry + ")", e.getMessage(), logSeverity.HIGH);
        }
    }
    /**
     * @Description Method that opens a specified browser
     */
    static WebDriver openABrowser (browserName bBrowserName) {
        WebDriver driver = null;
        String sDriverName = null;
        DesiredCapabilities DesireCaps = new DesiredCapabilities();
        
        try {
            //Creating browser instance
            switch (bBrowserName) {
                case FIREFOX:
                    //Initializing Driver
                	//Determine driver to be used
                    sDriverName =  Fvars.os_Name.toLowerCase().contains("win")    ? "geckodriver.exe": 
                                   (Fvars.os_Name.toLowerCase().contains("mac")   ? "geckodriver"    : 
                                   "geckodriver-lnx");
                    
                    //Initializing Driver
                    System.setProperty("webdriver.gecko.driver", Fvars.os_PathRef + sDriverName);
                    FirefoxProfile browserProfile = new FirefoxProfile();
                    
                    //Adding following preferences to download files
                    browserProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/octet-stream");
                    browserProfile.setPreference("browser.download.folderList", 2);
                    browserProfile.setPreference("browser.download.dir", Fvars.os_PathOut);
                    browserProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                    browserProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword, application/csv, "
                                                    + "application/ris, text/csv, image/png, application/pdf, text/html, "
                                                    + "text/plain, application/zip, application/x-zip, application/x-zip-compressed, "
                                                    + "application/download, application/octet-stream");
                    browserProfile.setPreference("browser.download.manager.showWhenStarting", false);
                    browserProfile.setPreference("browser.download.manager.focusWhenStarting", false);  
                    browserProfile.setPreference("browser.download.useDownloadDir", true);
                    browserProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
                    browserProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                    browserProfile.setPreference("browser.download.manager.closeWhenDone", true);
                    browserProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
                    browserProfile.setPreference("browser.download.manager.useWindow", false);
                    browserProfile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
                    browserProfile.setPreference("pdfjs.disabled", true);
                    browserProfile.setPreference("browser.download.manager.openDelay",100000);
                    browserProfile.setPreference("browser.download.animateNotifications", false);
                    driver = new FirefoxDriver(browserProfile);
                    break;
                case CHROME:
                    //Determine driver to be used
                    sDriverName =  Fvars.os_Name.toLowerCase().contains("win")    ? "chromedriver-win.exe": 
                                   (Fvars.os_Name.toLowerCase().contains("mac")   ? "chromedriver-mac"    : 
                                   "chromedriver-lnx");
                    
                    //Initializing Driver
                    System.setProperty("webdriver.chrome.driver", Fvars.os_PathRef + sDriverName);
                    DesireCaps = DesiredCapabilities.chrome();
                    driver = new ChromeDriver(DesireCaps);
                    break;
                case IE:
                    //Determine driver to be used
                    System.setProperty("webdriver.ie.driver", Fvars.os_PathRef + "IEDriverServer-win.exe");
                    DesireCaps = Fvars.os_Name.toLowerCase().contains("win") ? DesiredCapabilities.internetExplorer() : null;
                    if (DesireCaps == null) {throw new Exception ("Invalid OS for browser.");}
                    
                    //Create instance
                    driver = new InternetExplorerDriver(DesireCaps);
                    break;
                case OPERA:
                    //Determine driver to be used
                    sDriverName =  Fvars.os_Name.toLowerCase().contains("win")    ? "operadriver-win.exe" : 
                                   (Fvars.os_Name.toLowerCase().contains("mac")   ? "operadriver-mac"     : 
                                   "operadriver-lnx");
                    
                    //Initializing Driver
                    System.setProperty("webdriver.chrome.driver", Fvars.os_PathRef + sDriverName);
                    driver = new ChromeDriver();
                    break;
                case SAFARI:
                    //Initializing Driver
                    driver = new SafariDriver();
                    break;
                case PHANTOMJS:
                    //Determine driver to be used
                    sDriverName =  Fvars.os_Name.toLowerCase().contains("win")    ? "phantomjs-win.exe"   : 
                                   (Fvars.os_Name.toLowerCase().contains("mac")   ? "phantomjs-mac"       : 
                                   "phantomjs-lnx");
                    
                    //Initializing Driver
                    DesireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, Fvars.os_PathRef + sDriverName);
                    driver = new PhantomJSDriver();
                    break;
                default:
                    throw new Exception ("Unsupported browser.");
            }
            
            //Getting additional browser information
            Fvars.ts_BHandler = driver.getWindowHandle();
            
        } catch (Exception e) {
            frmError ("creating a browser instance (" + bBrowserName + ")", e.getMessage(), logSeverity.HIGH);
        }
        return driver;    
    }
    //METHOD OVERLOAD: Defaulting to primary browser
    public static void openBrowser (browserName bBrowserName) {
        Fvars.se_PBrowser = openABrowser (bBrowserName);
    }
    //METHOD OVERLOAD: Allowing user to have it own WebDriver instance variable
    public static void openBrowser (browserName bBrowserName, WebDriver driver) {
        driver = openABrowser (bBrowserName);
    }
    /**
     * @Description Method that closes a browser instance
     */
    public static void closeBrowser (WebDriver driver) {
        //Closing browser
        try {
            driver.close();
            driver.quit();
        } catch (Exception e) {
            frmError ("closing a browser instance.", e.getMessage(), logSeverity.HIGH);
        }
    }
    //METHOD OVERLOAD: Closes default WebDriver instance of this framework
    public static void closeBrowser () {
        closeBrowser (Fvars.se_PBrowser);
    }
    /**
     * @Description Method that verifies o condition is true between two strings (or integers). This PASSES or FAILS.
     */
    public static boolean verifyTest (String sPCondition, testOperators toAction, String sSCondition, String sMessage) {
        //Variables
        boolean bPass = false;
        
        try {
            //Perform comparison
            switch (toAction) {
                case EQUALS:
                    bPass = (sPCondition == null  ? "" : sPCondition).equals(sSCondition == null   ? "" : sSCondition) ? true : false;
                    break;
                case CONTAINS:
                    bPass = (sPCondition == null  ? "" : sPCondition).contains(sSCondition == null ? "" : sSCondition) ? true : false;
                    break;
                case ISNULL:
                    bPass = (sPCondition == null) ? true : false;
                    break;
                case GREATER:
                    bPass = ((sPCondition == null ? 0  : Integer.parseInt(sPCondition))  > 
                            ( sSCondition == null ? 0  : Integer.parseInt(sSCondition))) ? 
                            true : false;
                    break;
                case SMALLER:
                    bPass = ((sPCondition == null ? 0  : Integer.parseInt(sPCondition))  < 
                            ( sSCondition == null ? 0  : Integer.parseInt(sSCondition))) ? 
                            true : false;
                    break;
                default:
                    break;
            }
            
            //Sending results to the log
            logMessage( bPass ? logEntry.PASS: (Fvars.ts_WarningEnabled ? logEntry.WARNING : logEntry.FAIL), 
                        bPass ? sMessage : sMessage  + " (" + sPCondition + " -- v.s.-- " + sSCondition + " )");
            return bPass;
        } catch (Exception e) {
            frmError ("verifying tests (" + sPCondition + " -- " + toAction + " -- " + sSCondition + ").", e.getMessage(), logSeverity.HIGH);
            return false;
        }
    }
    //METHOD OVERLOAD: Verifies only one variable (string)....commonly for ISNULL
    public static boolean verifyTest (String sPCondition, testOperators toAction, String sMessage) {
        return verifyTest (sPCondition, toAction, null, sMessage);
    }
    //METHOD OVERLOAD: Verifies the same condition but instead of failing it it will send a WARNING if fails.
    public static void warningTest (String sPCondition, testOperators toAction, String sSCondition, String sMessage) {
        Fvars.ts_WarningEnabled = true;
        verifyTest (sPCondition, toAction, sSCondition, sMessage);
        Fvars.ts_WarningEnabled = false;
    }
    /**
     * @Description Method that throws and error generated by the framework
     */
    public static void frmError (String sMessage, String sError, logSeverity lsSeverity) {
        //Log Error
        //TODO: Leaving severity for now, but to implement logic to skip, stop and terminate tests.
        logMessage(logEntry.ERROR, Fctes.sErrorMessage + sMessage + " -- " + sError);
        return;
    }
    /**
     * @Description Method that pauses the execution of the tests, yes, agree Yikes! but needed.
     */
    public static void pauseExecution(Double dTime) {
        //Pausing code execution
        try {
            dTime = dTime * 1000;
            Thread.sleep(dTime.intValue());
            return;
        } catch (Exception e) {
            frmError ("pausing the test execution.", e.getMessage(), logSeverity.HIGH);
        }
    }
    /**
     * @Description Method that find an object by a BY.
     */
    public static WebElement findObject(By byObject, WebDriver driver, objectScope osScope) {
    	WebElement wElement;
    	try {
            WebDriverWait waitForElement = new WebDriverWait(driver, Fctes.gTimeOut);
            waitForElement.until(ExpectedConditions.presenceOfElementLocated(byObject));
            wElement = driver.findElement(byObject);
            waitForElement.until(ExpectedConditions.visibilityOf(wElement));
            if (osScope.equals(objectScope.CLICKABLE)) {
                waitForElement.until( ExpectedConditions.elementToBeClickable(wElement));
            }
            if (!osScope.equals(objectScope.PRESENT)) {
                logMessage(logEntry.PASS, "Element (" + byObject + ") found.");
            }
            pauseExecution(Fctes.fTimeQSecond);
            return wElement;
        } catch (Exception e) {
            if (!osScope.equals(objectScope.VISIBLE)) {
                logMessage(logEntry.FAIL, "Element (" + byObject + ") not found!");
            }
            return null;
        }
    }
    //METHOD OVERLOAD: to use primary browser and WebElement. Is object present?
    public static boolean getObject(By byObject) {
    	Fvars.se_PWebElement = findObject(byObject, Fvars.se_PBrowser, objectScope.PRESENT);
    	return Fvars.se_PWebElement != null ? true : false;
    }
    //METHOD OVERLOAD: to use primary browser and WebElement. Is object visible?
    public static boolean findObject(By byObject) {
    	Fvars.se_PWebElement = findObject(byObject, Fvars.se_PBrowser, objectScope.VISIBLE);
    	return Fvars.se_PWebElement != null ? true : false;
    }
    //METHOD OVERLOAD: to use primary browser and WebElement. Is object clickable?
    public static boolean readyObject(By byObject) {
    	Fvars.se_PWebElement = findObject(byObject, Fvars.se_PBrowser, objectScope.CLICKABLE);
    	return Fvars.se_PWebElement != null ? true : false;
    }
    /**
     * @Description Method that manipulates a given object.
     */
    public static void manipulateObject(WebElement element, objectAction oAction, String sData) {
    	try {
	    	//Manipulate Object
	    	switch (oAction) {
		        case CLEAR:
		            element.clear();
		            break;
		        case CLICK:
		            element.click();
		            break;
		        case TYPE:
		            element.sendKeys(sData);
		            break;
		        case REPLACE:
		        	element.clear();
		            element.sendKeys(sData);
		            break;
		        case SELECT:
		        	Select dropDown = new Select(element);
		        	dropDown.selectByVisibleText(sData);
		            break;
		        case GETATT:
		        	Fvars.g_sTemp = element.getAttribute(sData);
		            break;
		        case GETPOS:
		        	Fvars.g_sTemp = element.getLocation().toString();
		            break;
		        case GETSIZE:
		        	Fvars.g_sTemp = element.getSize().toString();
		            break;
		        case GETTEXT:
		        	Fvars.g_sTemp = element.getText();
		            break;
		        default:
		            break;
	    	}
	    	//pauseExecution(Fctes.fTimeQSecond);
    	} catch (Exception e) {
    		frmError ("manipulating an object with action ("+ oAction + ").", e.getMessage(), logSeverity.HIGH);
    	}
    }
    //METHOD OVERLOAD: For no additional data needed
    public static void manipulateObject(By byObject, objectAction oAction) {
    	manipulateObject(byObject, oAction, "");
    }
    //METHOD OVERLOAD: To use default webElement
    public static void manipulateObject(By byObject, objectAction oAction, String sData) {
    	//Get object
    	readyObject(byObject);
    	
    	//Manipulate Object
    	manipulateObject(Fvars.se_PWebElement, oAction, sData);
    }
    /**
     * @Description Method that verifies a page by its title and a given object.
     */
    public static boolean verifyBrowserPage (String sPageTitle, By byObject, WebDriver driver) {
        try {
        	getObject(byObject);
        	//Performing checks
            return (verifyTest(driver.getTitle().toString(),testOperators.CONTAINS,sPageTitle,"Page identified (" + sPageTitle + ").") &&
                   findObject(byObject));
        } catch (Exception e) {
            frmError ("verifying page title and object ("+ sPageTitle + ").", e.getMessage(), logSeverity.HIGH);
            return false;
        }
    }
    //METHOD OVERLOAD: to use primary browser
    public static boolean verifyBrowserPage (String sPageTitle, By byObject) {
        return verifyBrowserPage (sPageTitle, byObject, Fvars.se_PBrowser);
    }
    /**
     * @Description Method that retrieve test metrics.
     */
    public static void getTestMetrics() {
        //Generating Metrics
        Fvars.em_EmailBodyMetrics = "Test Metrics ::" + Fctes.sNewLine +
                                         "Exec. Time: " + (((new Date()).getTime() - Fvars.os_Timer) / 1000) + " secs." + Fctes.sNewLine +
                                         "Tests     : " + Fvars.log_Step    + Fctes.sNewLine +
                                         "Pass      : " + Fvars.log_Pass    + Fctes.sNewLine +
                                         "Fail      : " + Fvars.log_Fail    + Fctes.sNewLine +
                                         "Warnings  : " + Fvars.log_Warning + Fctes.sNewLine +
                                         "Errors    : " + Fvars.log_Error;
        logMessage(logEntry.INFO, Fvars.em_EmailBodyMetrics);
        return;
    }
    /**
     * @Description Method that verifies a file exist in the output folder
     */
    public static void checkFiles(String sFileName) {
        File     fFile        = null;
        File[]   fListOfFiles = null;
        boolean  bSearchFile  = true;
        int      iCounter     = 0;
        
        try { 
	        do {
	            //Getting IO information
	            fFile        = new File(Fvars.os_PathOut);
	            fListOfFiles = fFile.listFiles();
	            iCounter++;
	            
	            //Looking for file
	            for (File fFileOn : fListOfFiles) {
	                if (fFileOn.isFile() && fFileOn.getName().contains(sFileName)) {
	                    logMessage(logEntry.PASS, fFileOn.getName() + " - Found! File Size:" + fFileOn.getTotalSpace()/1024 + "KB.");
	                    return;
	                }
	            }
	            
	            //Clean variables for next search
	            bSearchFile  = iCounter > 30 ? false : true;
	            fFile        = null;
	            fListOfFiles = null;
	            pauseExecution(Fctes.fTimeSecond);
	            
	        } while (bSearchFile);
	        logMessage(logEntry.FAIL, " File not found! (" + sFileName + ").");
        } catch (Exception e) {
            frmError ("checking file prescence in the output folder (" + sFileName + ").", e.getMessage(), logSeverity.HIGH);
        }
    }
    /**
     * @Description Method that send an ACTION to a selected object
     */
    public static void performAction(uiAction uiAction, String sValue, WebDriver driver, WebElement wElement) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(wElement);
            actions.click();
            pauseExecution(Fctes.fTimeQSecond);
            switch (uiAction) {
                case ENTER:
                    actions.sendKeys(sValue);
                    actions.build().perform();
                    break;
                case SELECT:
                    actions.sendKeys(sValue);
                    actions.build().perform();
                    pauseExecution(Fctes.fTimeQSecond);
                    actions.sendKeys(Keys.ENTER);
                    actions.build().perform();
                    break;
                case CHOOSE:
                    actions.sendKeys(sValue);
                    actions.sendKeys(Keys.ENTER);
                    actions.build().perform();
                    pauseExecution(Fctes.fTimeQSecond);
                    break;
                default:
                    break;
            }
            return;
        } catch (Exception e) {
        	frmError ("performing action " + uiAction + " with: " + sValue, e.getMessage(), logSeverity.HIGH);
        }
    }
    //METHOD OVERLOAD: To use the default browser and WebElement.
    public static void performAction(uiAction uiAction, String sValue) {
    	performAction(uiAction, sValue, Fvars.se_PBrowser, Fvars.se_PWebElement);
    }
    /**
     * @Description Method that tries to interact with simple HTML tables
     */
    public static String useTable(By sType, uiAction  sAction, int getRow, int getCol, String sAdditionalInfo) {
		//Variable definition
		int iColumns = 0;
		int iRows = 0;
		
		try {
			//Get Table
			if (!getObject(sType)) {
				throw new Exception ("Unable fo find table, cannot manipulate it.");
			}
			
			//Sweep table
			List<WebElement> rows = Fvars.se_PWebElement.findElements(Fctes.byRow); 
			Iterator<WebElement> rowsIT = rows.iterator(); 
			
			//Performing Action / /Analyzing table
			Fvars.g_sTemp = "";
			while(rowsIT.hasNext()) {
				iColumns = 0;
				iRows++;
				WebElement row = rowsIT.next();
				List<WebElement> columns = row.findElements(Fctes.byColumn); 
				Iterator<WebElement> colIT = columns.iterator();
				if (sAction.equals(uiAction.COUNTROWS)) {continue;}
				while(colIT.hasNext()) { 
					iColumns++;
					WebElement column = colIT.next();
					switch (sAction) {
			            case PRINT:
			            	Fvars.g_sTemp = Fvars.g_sTemp + column.getText() + Fctes.sSeparator; 
			                break;
			            case GETROW:
			            	Fvars.g_sTemp = iRows == getRow ? Fvars.g_sTemp + column.getText() + Fctes.sSeparator : Fvars.g_sTemp;
			                break;
			            case GETCOL:
			            	Fvars.g_sTemp = getCol == iColumns ? Fvars.g_sTemp + column.getText() + Fctes.sSeparator: Fvars.g_sTemp;
			                break;
			            case GETCEL:
					        Fvars.g_sTemp = (getRow == iRows && getCol == iColumns) ? column.getText() : Fvars.g_sTemp;
			                break;
			            case FINDTEXT:
					        Fvars.g_sTemp = column.getText().contains(sAdditionalInfo) ? iRows + Fctes.sSeparator + iColumns : Fvars.g_sTemp;
					        break;
			            default:
			                break;
					}	
				}
			}
			
			//Returning possible needed value
			switch (sAction) {
			    case COUNTROWS:
			    	return Integer.toString(iRows);
			    case COUNTCOLS:
			    	return Integer.toString(iColumns);
                default:
                    return Fvars.g_sTemp;
			}
			
		} catch (Exception e) {
			frmError ("performing action " + sAction + " for table.", e.getMessage(), logSeverity.HIGH);
			return null;
		}
	}
    /**
     * @Description Method that checks for an email on GMAIL
     */
	public static void checkEmail() {
		int iTotalNewMessages = 0;
		Fvars.g_sTemp = "";
        try {
        	//Initialize
        	Properties props = System.getProperties();
            props.setProperty(Fctes.sEmailProtocol[0], Fctes.sEmailProtocol[1]);
            
            //Wait 10 seconds to make sure you have the email on the inbox
	        pauseExecution(Fctes.fTimeSecond * 10);
        	
	        //Open session
        	Session session = Session.getDefaultInstance(props, null);
	        Store store = session.getStore(Fctes.sEmailProtocol[1]);
	        store.connect(Fctes.sEmailServers[0], Fvars.em_EmailAdd,Fvars.em_EmailPass);
            
	        //Open folder
	        Folder folder = store.getFolder(Fctes.sInboxFolderName);
	        folder.open(Folder.READ_WRITE);
            
	        //Retrieve general metrics
	        logMessage(logEntry.INFO, "Total  Message:" + folder.getMessageCount());
	        logMessage(logEntry.INFO, "Unread Message:" + folder.getUnreadMessageCount());
	        
	        //Filter new emails
	        Flags seen = new Flags(Flags.Flag.SEEN);
	        FlagTerm unseenFlagTerm = new FlagTerm(seen,false);
	        Message message[] = folder.search(unseenFlagTerm); 
	        iTotalNewMessages = message.length - 1;
	        
	        //Loop on new messages
	        for (int iCounter = iTotalNewMessages; iCounter >= 0; iCounter --) {
	        	//Get Email information
	        	Fvars.g_sTemp = "Message:" + (iCounter + 1) + Fctes.sNewLine + 
                                     "From:"    + message[iCounter].getFrom()[0].toString() + Fctes.sNewLine +
                                     "To:" + message[iCounter].getAllRecipients()[0].toString() + Fctes.sNewLine +
                                     "Subject:" + message[iCounter].getSubject() + Fctes.sNewLine;
	            try {
	            	Fvars.g_sTemp = Fvars.g_sTemp + "Body:" + message[iCounter].getContent();
	            } catch (Exception e) {
	            	Fvars.g_sTemp = "Body: ** UNABLE TO GET EMAIL BODY **";
	            }
	            
	            //Set new email flags
	            message[iCounter].setFlag(Flags.Flag.SEEN, true);
	            //message[i].setFlag(Flags.Flag.DELETED, true);
	        }
        } catch (Exception e) {
        	frmError ("trying to retrieve emails.", e.getMessage(), logSeverity.HIGH);
        }
    }
	/**
     * @Description Method that creates a screenshot on the output folder for the browser
     */
	public static void getWebScreenshot (WebDriver driver) {
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(Fvars.os_PathOut + getTime(Fctes.dtSHORT) + ".jpg"));
			}
		catch (Exception e){
			frmError ("trying to get a browser screenshot.", e.getMessage(), logSeverity.HIGH);
		}
	}
	//METHOD OVERLOAD: To use the default browser.
	public static void getWebScreenshot () {
		getWebScreenshot (Fvars.se_PBrowser);
	}
	/**
     * @Description Method that creates a screenshot on the output folder for the entire screen
     */	
	public static void getScreenshot () {
		try {
			//Get resolution
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture;
			
			//Get screenshot
			capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, "jpg", new File(Fvars.os_PathOut + getTime(Fctes.dtSHORT) + ".jpg"));
			} 
		catch (Exception e){
			frmError ("trying to get a full screenshot.", e.getMessage(), logSeverity.HIGH);
		}
	}
	/**
     * @Description Method that creates a screenshot on the output folder for the entire screen
     */
	public static boolean Emailer(String sAdditionalSubject, String sAdditionalBody, String sEmailAddresses, String sAdditionalAttachment,
			int iAttachLog, int iAttachLastImage) {
		try {
			//Email configuration settings
			Properties props = new Properties();
			props.put(Fctes.sEmailAuth[0], Fctes.sEmailAuth[1]);
			props.put(Fctes.sEmailTls[0], Fctes.sEmailTls[1]);
			props.put("mail.smtp.host", Fctes.sEmailServers[1]);
			props.put(Fctes.sEmailPort[0], Fctes.sEmailPort[1]);
			
			//Open session
	    	Session session = Session.getInstance(props,new javax.mail.Authenticator() {
						        protected PasswordAuthentication getPasswordAuthentication() {
						          return new PasswordAuthentication(Fvars.em_EmailAdd, Fvars.em_EmailPass);
						        }
			                  });
	    	
	    	//Data formation
	    	sEmailAddresses = sEmailAddresses.isEmpty() ? Fvars.em_EmailAddDefault : sEmailAddresses;
			sAdditionalSubject = "Selenium Execution Results for: " + Fvars.ts_Uniqueness + " run. -- " + sAdditionalSubject;
			
			//Setting email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Fvars.em_EmailAdd));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sEmailAddresses));
			message.setSubject(sAdditionalSubject);
			message.setText("");
		 
			MimeBodyPart messagePart = new MimeBodyPart();
		    messagePart.setText("Hello," + Fctes.sNewLine  + Fctes.sNewLine + sAdditionalBody + 
		    		            Fvars.em_EmailBodyMetrics  + Fctes.sEmailBody);
		            
		    //Building email
		    Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(messagePart);
		    MimeBodyPart logAttachment = new MimeBodyPart();
	    	
            //Setting attachment for the log
		    if (iAttachLog == 1) {
		    	File fFile = new File(Fvars.os_PathOut + Fctes.ioLogFile);
			    if (fFile.exists()) {
			    	FileDataSource fileDataSource = new FileDataSource(Fvars.os_PathOut + Fctes.ioLogFile) {
			    		@Override
				        public String getContentType() {
			    			return "application/octet-stream";
				        }
			    	};
			        logAttachment.setDataHandler(new DataHandler(fileDataSource));
			        logAttachment.setFileName(Fctes.ioLogFile);
			        multipart.addBodyPart(logAttachment);
			    }
		    }
		    
		    //Sending Email
		    message.setContent(multipart);
			Transport.send(message);
			logMessage(logEntry.INFO, "Email Sent.");
			return true;
		} catch (Exception e) {
			frmError ("trying to send the email.", e.getMessage(), logSeverity.HIGH);
			return false;
		}
		//TODO: ZIP output folder and attach it as an additional file.
	}
	/**
     * @Description Method that gets a control and verify its text or one of its properties
     * @param By.Object -- By identifier of the object
     * @param Object Props -- Array as {"attribute name", "attribute value", "text to use for logs"}. 
     *        If Element(0) = text -- get the text instead the property
     */
	public static void verifyControl (WebElement wElement, By byObject, String objAttributes[]) {
		try {
			if (wElement == null) {
				throw new Exception ("WebElement object is missing (null). - " + objAttributes[2].toString());
			}
			
			//Verifying object
			if (objAttributes[0].toString().toLowerCase().equals("text")) {
				verifyTest(wElement.getText(), 
						testOperators.CONTAINS, objAttributes[1].toString(), objAttributes[2].toString());
			} else {
				verifyTest(wElement.getAttribute(objAttributes[0].toString()), 
						testOperators.CONTAINS, objAttributes[1].toString(), objAttributes[2].toString());
			}
			return;
		} catch (Exception e) {
			frmError ("trying to verify an object.", e.getMessage(), logSeverity.HIGH);
		}
	}
	//METHOD OVERLOAD: To use default webElement
	public static void verifyControl (By byObject, String objAttributes[]) {
		//Getting Object
		Fmets.getObject(byObject);
		verifyControl (Fvars.se_PWebElement, byObject, objAttributes);
	}
	/**
     * @Description Method that gets current browser cookies and veries it number based on the inputs
     * @param driver -- Browser object to be used to gather the information
     * @param numberCookies  -- Expected number of cookies expected. 
     */
    public static void checkCookies (WebDriver driver, int numberCookies) {
    	//Getting cookies information
        Set<Cookie> seleniumCookies = driver.manage().getCookies();
        int currentCookies = driver.manage().getCookies().size();
        if (currentCookies == numberCookies) {
            logMessage(logEntry.PASS, "Number of cookies is correct (" + currentCookies + ").");
            for(Cookie seleniumCookie : seleniumCookies){
                logMessage(logEntry.INFO, "c.Name: " + seleniumCookie.getName()
                    + " ||| c.Value: "  + seleniumCookie.getValue()
                    + " ||| c.Domain: " + seleniumCookie.getDomain()
                    + " ||| c.Path: "   + seleniumCookie.getPath()
                    + " ||| c.Expiry: " + seleniumCookie.getExpiry()
                    + " ||| c.Class: "  + seleniumCookie.getClass());
            }
        } else {
            logMessage(logEntry.WARNING, "Number of cokkies is not the expected: " + currentCookies);
        }	
    }
	
	//TODO: DB Integration: From a any database with the minimal information to exec SP and queries
	//TODO: IO features: Read of files, manipulation of XLS and CSVs.
}
