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

import org.openqa.selenium.By;

/**
 * @Description Java interface that contains a set of constants and enums to be used by the application tests. 
 *              These are specific to the project tests.
 */
public interface Tctes {
	public enum appUnderTest implements Fctes {
        API, WWW, HELP, FULL;
		
		//Returns the URL for a given application and environment.
        public String getAppURL() {
		    switch (Tvars.ts_Environment) {
			    case DEV:
					switch (this) {
						case API: 
							return "https://wearsafe-dev.herokuapp.com";
			            case WWW: 
			            	return "http://wsdevsetup1429.wearsafe.com";
			            case HELP:
			            	return "wearsafe-help-dev.herokuapp.com";
			            default: 
			            	return "";
					}
			case QA:
					switch (this) {
						case API: 
							return "https://wearsafe-test.herokuapp.com";
			            case WWW: 
			            	return "http://wstestsetup1429.wearsafe.com";
			            case HELP:
			            	return "wearsafe-help-test.herokuapp.com";
			            default: 
			            	return "";
					}
			case STAGING:
				switch (this) {
					case API: 
						return "https://wearsafe-stage.herokuapp.com";
		            case WWW: 
		            	return "http://wsstagesetup1429.wearsafe.com";
		            case HELP:
		            	return "wearsafe-help-stage.herokuapp.com";
		            default: 
		            	return "";
				}
			case PRODUCTION:
				switch (this) {
					case API: 
						return "https://wearsafe-production.herokuapp.com";
		            case WWW: 
		            	return "http://www.wearsafe.com";
		            case HELP:
		            	return "wearsafe-help-production.herokuapp.com";
		            default: 
		            	return "";
				}
			default: 
				return "";
		    }
        } 
    }

	public enum testEnvironment {
        DEV, QA, STAGING, PRODUCTION
	}

	public enum appUsers {
        REFERENCE, ID, EMAIL, PASSWORD, NAME, LAST, PHONE, ROLE
	}

	public enum appPages {
        LOGIN, REGISTRATION, RESETPWD, CONTACTUS, WELCOME
	}

	// ****************** EMAILS    SECTION *************************
	String   eml_Prefix       = "qa.wearsafe+";
	String   eml_Postfix      = "@gmail.com";
	String   eml_From         = "From:Wearsafe <noreply@wearsafe.com>";
	String   eml_To           = "To:qa.wearsafe+development@gmail.com";
	String   eml_ToLabel      = "To:";

	//USER REGISTRATION
	String   eml_RegSubject   = "A new user registration request was received for ";

	//CONTACT US
	String   eml_CusSubject   = "A help request has been submitted by ";

	// ****************** CONSTANTS SECTION *************************
	public String    ap_GenericUser     = "qa.wearsafe@gmail.com";
	public String    ap_DefaultPassword = "Pa55w0rd!";

	// ****************** API      SECTION **************************
	public String    api_ping_resp1     = "pong";
    public String    api_ping_resp2     = "Forbidden (403)";

    // ****************** COMMON   SECTION **************************
    //LOGIN
  	String 	 lup_PgTitle      = "Login";
  	By		 lup_AlertB  = By.id("alertCodeInp1");
  	String[] lup_AlertS  = new String[] {"placeholder","Enter Alert Code", "Lookup -- Logo titleAlert textbox."};
  	By		 lup_FnameB  = By.id("ws-form-input-name");
  	String[] lup_FnameS  = new String[] {"placeholder","First Name", "Lookup -- First Name textbox."};
  	By		 lup_LnameB  = By.id("ws-form-input-name");
  	String[] lup_LnameS  = new String[] {"placeholder","Last Name", "Lookup -- Last Name textbox."};
  	By		 lup_AgreeB  = By.name("agreement");
  	String[] lup_AgreeS  = new String[] {"type","checkbox", "Lookup -- Agreement checkbox."};
  	By		 lup_AccessB  = By.xpath("//*[@id='ws-form-alert-find']/div[3]/button");
  	String[] lup_AccessS  = new String[] {"type","submit", "Lookup -- Submit button."};
	
	//HOMEPAGE
	
	// ****************** INTERNAL SECTION **************************
  	
	// ****************** OTHER    SECTION **************************
}
