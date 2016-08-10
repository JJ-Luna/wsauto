/**
 * Copyright (C) 2016 Wearsafe. - All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package www;

import org.openqa.selenium.WebDriver;

import sincerare.Fctes.*;
import sincerare.Fvars;
import sincerare.Tctes;
import sincerare.Tctes.*;
import sincerare.Tmets;

public class Www extends sincerare.Fmets {
    private static WebDriver driver      = null;
    private static appUnderTest systemut = null;
    /**
     * @Description Main class that drives all the tests for the login page -- common piece for all apps.
     */
    public static void execute() {
    	//Initialize tests
        logMessage(logEntry.EXEC, "Main lookup Script.");
        
        //Redirect Browser
        redirectTo();
        
        //Verifying page loaded
        //verifyBrowserPage (Tctes.lgn_PgTitle, Tctes.lgn_EmailTxtB);
        
        //Verify UI
        verifyUI();
        
        //Verify UI Validations
        verifyUIValidations ();
        
        //Verify available actions
        verifyActions(appPages.LOGIN);
        
        //Verify alert access
        //Here it goes next script
    }
	/**
	 * @Description Main class that redirects the browser to the login page based on the app under test.
	 */
	static void redirectTo () {
	    logMessage(logEntry.EXEC, "Executiong login redirection to: " + systemut.getAppURL());
	    try {
	        driver.navigate().to(systemut.getAppURL());
	    } catch (Exception e) {
	        frmError ("navigating to the login page ("+ systemut + ").", e.getMessage(), logSeverity.HIGH);
	    }
	}
	//METHOD OVERLOAD: To use specific browser
	public static void redirectTo (WebDriver wdriver, appUnderTest ts_System) {
		driver   = wdriver;
	    systemut = ts_System;
	    redirectTo();
	}
	//METHOD OVERLOAD: To use default browser
	public static void redirectTo (appUnderTest ts_System) {
		driver   = Fvars.se_PBrowser;
	    systemut = ts_System;
	    redirectTo();
	}
    /**
     * @Description Main class that verifies the element of the UI for the login page.
     */
    static void verifyUI () {
        logMessage(logEntry.EXEC, "Executing verification of login UI controls.");
        try {
            //Verifying header
        	Tmets.checkHeaderElements(systemut, appPages.LOGIN);
            
            //Verifying body
            //verifyControl (Tctes.lgn_EmailTxtB,    Tctes.lgn_EmailTxtS);
            //verifyControl (Tctes.lgn_PasswordTxtB, Tctes.lgn_PasswordTxtS);
            //verifyControl (Tctes.lgn_LoginBtnB,    Tctes.lgn_LoginBtnS);
            //verifyControl (Tctes.lgn_ForgotPassB,  Tctes.lgn_ForgotPassS);
        } catch (Exception e) {
            frmError ("verifying the UI for the login page ("+ systemut + ").", e.getMessage(), logSeverity.HIGH);
        }
    }
    /**
     * @Description Main class that verifies UI validations triggered based on user inputs.
     */
    static void verifyUIValidations () {
        logMessage(logEntry.EXEC, "Execution verifying of login UI validations...");
        try {
            //EMPTY FORM
            //manipulateObject(Tctes.lgn_EmailTxtB,    objectAction.CLEAR);
            //manipulateObject(Tctes.lgn_PasswordTxtB, objectAction.CLEAR);
            //manipulateObject(Tctes.lgn_LoginBtnB,    objectAction.CLICK);
            //verifyControl   (Tctes.lgn_EmailValB,    Tctes.lgn_EmailValSME);
        } catch (Exception e) {
            frmError ("verifying the form data validations on the login page ("+ systemut + ").", e.getMessage(), logSeverity.HIGH);
        }
    }
    /**
     * @Description Main class that verifies UI available actions from the login page.
     */
    static void verifyActions (appPages apPage) {
        try {
            logMessage(logEntry.EXEC, "Executing script for login actions verification");
            driver.navigate().refresh();
            
            //Action
            Tmets.checkHeaderActions(driver, systemut, appPages.LOGIN);
            
        } catch (Exception e) {
            frmError ("verifying the available actions on the login page ("+ systemut + ").", e.getMessage(), logSeverity.HIGH);
        }
    }
	
}
