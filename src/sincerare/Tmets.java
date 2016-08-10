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

import java.util.List;

import org.openqa.selenium.WebDriver;

import sincerare.Fctes.logEntry;
import sincerare.Fctes.objectAction;
import sincerare.Tctes.appPages;
import sincerare.Tctes.appUnderTest;
import sincerare.Tctes.appUsers;

/**
 * @Description Java class that contains a set of methods specific for the application under test. 
 *              These are specific to the project tests.
 */
public class Tmets extends sincerare.Fmets{
	/**
     * @Description Method that adds a user by its reference.
     */
    public static void addUser (List<String> tUser) {
        //Adding a user
    	try {
    		Tvars.users.add(tUser);
    	} catch (Exception e) {
    		Fmets.logMessage(logEntry.ERROR, "Unable to add user with refference id: " + tUser.get(appUsers.REFERENCE.ordinal()));
    	}
    }
	/**
     * @Description Method that returns a user by its reference.
     */
    public static void returnUser (String sUserReference) {
        //Looking for user
    	Tvars.user = null;
		for(int i = 0; i < Tvars.users.size(); i++)  {
			Tvars.user = Tvars.users.get(i);
			if (Tvars.user.get(0).equals(sUserReference)) {return;}
	    }
		Fmets.logMessage(logEntry.ERROR, "Unable to retieve user with refference id: " + sUserReference);
    }
    /**
     * @Description Method that returns a user by its reference.
     */
    public static void replaceUser (String sUserReference, List<String> tUser) {
        //Replacing for user
		for(int i = 0; i < Tvars.users.size(); i++)  {
			Tvars.user = Tvars.users.get(i);
			if (Tvars.user.get(0).equals(sUserReference)) {
				Tvars.users.remove(i);
				Tvars.users.add(tUser);
				return;
			}
	    }
		Fmets.logMessage(logEntry.ERROR, "Unable to retieve user with refference id: " + sUserReference);
    }
    /**
     * @Description Methods that check the header elements of the application based on the page
     */
    public static void checkHeaderElements (appUnderTest ApplicationUT, appPages fromPage) {
    	
    }
    /**
     * @Description Methods that check the header actions of the application based on the page
     */
    public static void checkHeaderActions (WebDriver driver, appUnderTest ApplicationUT, appPages fromPage) {
    	
    }
}
