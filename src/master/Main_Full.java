/**
 * Copyright (C) 2016 Wearsafe. - All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package master;

import help.Lookup;
import sincerare.Fctes.*;
import sincerare.Tctes;
import sincerare.Tctes.appPages;
import sincerare.Fvars;
import sincerare.Tmets;
import www.Www;

public class Main_Full extends sincerare.Fmets {
    /**
     * @Description Main class that drives all the tests for the regression tests.
     */
    public static void execute() {
        //Initialize tests
        logMessage(logEntry.EXEC, "Main FULL Script.");
        
        //Default tests
        //Add any default test to be included
        
        switch (Fvars.ts_Type) {
            case REGRESSION:
            	
            	
            	//THIS IS FOR PoC ONLY
            	
            	logMessage(logEntry.EXEC, "Executiong login redirection to: .help");
        	    try {
        	    	Fvars.se_PBrowser.navigate().to("http://wearsafe-help-dev.herokuapp.com");
        	    } catch (Exception e) {
        	        frmError ("navigating to the login page (http://wearsafe-help-dev.herokuapp.com).", e.getMessage(), logSeverity.HIGH);
        	    }
        	    
        	    logMessage(logEntry.EXEC, "Executing verification of the lookup page");
            	verifyControl (Tctes.lup_AlertB,    Tctes.lup_AlertS);
            	verifyControl (Tctes.lup_FnameB,    Tctes.lup_FnameS);
            	verifyControl (Tctes.lup_LnameB,    Tctes.lup_LnameS);
            	verifyControl (Tctes.lup_AgreeB,    Tctes.lup_AgreeS);
            	verifyControl (Tctes.lup_AccessB,   Tctes.lup_AccessS);
            	
            	
            	logMessage(logEntry.EXEC, "Executiong redirection to: www");
        	    try {
        	    	Fvars.se_PBrowser.navigate().to("http://www.wearsafe.com");
        	    } catch (Exception e) {
        	        frmError ("navigating to the login page (http://www.wearsafe.com).", e.getMessage(), logSeverity.HIGH);
        	    }
            	verifyTest (Fvars.se_PBrowser.getTitle(), testOperators.EQUALS, "Get Wearsafe for only $5 a month, and your Tag is on us. - Wearsafe", "www title");
            	
            	try {
        	    	Fvars.se_PBrowser.navigate().to("http://www.wearsafe.com/support");
        	    } catch (Exception e) {
        	        frmError ("navigating to the login page (http://www.wearsafe.com/support).", e.getMessage(), logSeverity.HIGH);
        	    }
            	verifyTest (Fvars.se_PBrowser.getTitle(), testOperators.EQUALS, "Support - Wearsafe", "support title");
            	
            	try {
        	    	Fvars.se_PBrowser.navigate().to("http://www.wearsafe.com/checkout");
        	    } catch (Exception e) {
        	        frmError ("navigating to the login page (http://www.wearsafe.com/checkout).", e.getMessage(), logSeverity.HIGH);
        	    }
            	verifyTest (Fvars.se_PBrowser.getTitle(), testOperators.EQUALS, "Checkout - Wearsafe", "cart title");
            	
            	
            	//SECTION #1: www
                //Lookup.execute();
                
            	//SECTION #2: .help
            	//Www.execute();
            	
                break;
            case SMOKE:
                logMessage(logEntry.INFO, "No Full smoke test defined yet.");
                break;
            case SANITY:
                logMessage(logEntry.INFO, "No Full sanity test defined yet.");
                break;
            default:
                logMessage(logEntry.ERROR, "Undefined test level requested.");
                break;
        }    
    }
}
