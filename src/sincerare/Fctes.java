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
 * @author JJ (Juan Luna)
 * @Description Java interface that contains a set of constants and enums to be used by the framework. 
 *                 These are generic to this framework.
 */
public interface Fctes {
    
    public enum testType implements Fctes {
        SANITY, SMOKE, REGRESSION
    }
    
    public enum logLevel implements Fctes {
        MINIMUM, NORMAL, MAXIMUM
    }
    
    public enum logSeverity implements Fctes {
        LOW, NORMAL, HIGH, BLOCKER
    }
    
    public enum logEntry implements Fctes {
        INFO, PASS, FAIL, WARNING, ERROR, EXEC
    }
    
    public enum browserName implements Fctes {
        IE, FIREFOX, CHROME, SAFARI, OPERA, PHANTOMJS
    }
    
    public enum uiAction implements Fctes {
        ENTER, SELECT, CHOOSE, PRINT, GETROW, GETCOL, GETCEL, COUNTROWS, COUNTCOLS, FINDTEXT
    }
    
    public enum testOperators implements Fctes {
        EQUALS, CONTAINS, ISNULL, GREATER, SMALLER
    }
    
    public enum testAction implements Fctes {
        TESTUI, TESTACTIONS, TESTVALIDATIONS, PERFORM
    }
    
    public enum objectScope implements Fctes {
        PRESENT, VISIBLE, CLICKABLE
    }
    
    public enum objectAction implements Fctes {
        CLICK, CLEAR, TYPE, REPLACE, SELECT, GETATT, GETPOS, GETSIZE, GETTEXT
    }
    
    //Email information
    public String[]  sEmailProtocol  = new String[] {"mail.store.protocol","imaps"};
    public String[]  sEmailServers   = new String[] {"imap.gmail.com","smtp.gmail.com"};
    public String[]  sEmailAuth      = new String[] {"mail.smtp.auth","true"};
    public String[]  sEmailTls       = new String[] {"mail.smtp.starttls.enable","true"};
    public String[]  sEmailPort      = new String[] {"mail.smtp.port","587"};
    public String    sInboxFolderName= "INBOX";
    public String    sEmailBody      = Fctes.sNewLine + "This is an unmonitored email inbox. Do not reply to this email." + Fctes.sNewLine
                                     + "----------------" + Fctes.sNewLine + "The QA Team";
	
    //Generic constants
    public String    dtLONG           = "MMM dd, yyyy hh:mm:ss z";
    public String    dtSHORT          = "MMddyyyyHHmmss";
    public String    dtSTD            = "MM/dd/yyyy";
    public String    ioLogFile        = "testlogs.log";
    
    //Timing
    public int       gTimeOut         = 3;
    public int       gTimeOutLow      = 1;
    public int       gTimeOutHigh     = 5;
    public double    fTimeSecond      = 1;
    public double    fTimeHSecond     = 0.5;
    public double    fTimeQSecond     = 0.25;
    
    //Messages
    public String    sNewLine         = "\n";
    public String    sSeparator       = " || ";
    public String    sErrorMessage    = "An error ocurred while ";
    
    //Basic objects properties
    public By        byColumn         = By.tagName("td");
    public By        byRow            = By.tagName("tr");
}
