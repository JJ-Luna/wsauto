/**
 * Copyright (C) 2016 Wearsafe. - All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package master;

import sincerare.Fctes.*;
import sincerare.Fvars;
import sincerare.Tctes.*;
import sincerare.Tvars;

public class Main extends sincerare.Fmets{
    /**
     * @Description Main class that drives all the tests.
     * @Param:         1: Environment : Has to be a ENUM from Tests_vars.testEnvironment : Default: ts_Environment = QA
     * @Param:         2: Browser     : Has to be a ENUM from Frame_ctes.browserName     : Default: ts_Browser     = FIREFOX
     * @Param:         3: Log Level   : Has to be a ENUM from Frame_ctes.logLevel        : Default: ts_Log         = MAXIMUM
     * @Param:         4: Scope       : Has to be a ENUM from Frame_ctes.testType        : Default: ts_Type        = REGRESSION
     * @Param:         5: System      : Has to be a ENUM from Tests_ctes.appUnderTest    : Default: ts_Application = FULL
     */
    public static void main(String[] args) {
        getTestEnvironment();
        
        //Evaluate arguments: ARG 1 - ENVIRONMENT
        if (args.length > 0 && args[0] != null) {
            try {
                Tvars.ts_Environment = testEnvironment.valueOf(args[0].toUpperCase());
            } catch(Exception e) {
                Tvars.ts_Environment = testEnvironment.QA;
            }
        }
        
        //Evaluate arguments: ARG 2 - BROWSER
        if (args.length > 1 && args[1] != null) {
            try {
                Fvars.ts_Browser = browserName.valueOf(args[1].toUpperCase());
            } catch(Exception e) {
                Fvars.ts_Browser = browserName.FIREFOX;
            }
        }
            
        //Evaluate arguments: ARG 3 - LOG LEVEL
        if (args.length > 2 && args[2] != null) {
            try {
                Fvars.ts_Log = logLevel.valueOf(args[2].toUpperCase());
            } catch(Exception e) {
                Fvars.ts_Log = logLevel.MAXIMUM;
            }
        }
            
        //Evaluate arguments: ARG 4 - SCOPE
        if (args.length > 3 && args[3] != null) {
            try {
                Fvars.ts_Type = testType.valueOf(args[3].toUpperCase());
            } catch(Exception e) {
                Fvars.ts_Type = testType.REGRESSION;
            }
        }

        //Evaluate arguments: ARG 5 - APPLICATION
        if (args.length > 4 && args[4] != null) {
            try {
                Tvars.ts_Application = appUnderTest.valueOf(args[4].toUpperCase());
            } catch(Exception e) {
                Tvars.ts_Application = appUnderTest.FULL;
            }
        }
            
        //Revalidation for PRODUCTION
        Fvars.ts_Type = Tvars.ts_Environment.equals(testEnvironment.PRODUCTION) ?
                        testType.SMOKE : Fvars.ts_Type;
            
        //Initialize tests
        logMessage(logEntry.EXEC, "Main Script.");
        logMessage(logEntry.INFO, "Environment under test: "   + Tvars.ts_Environment + 
                                             ". Test Scope: "  + Fvars.ts_Type + 
                                             ". Test System: " + Tvars.ts_Application + 
                                             ". Browser: "     + Fvars.ts_Browser + 
                                             ". Log level: "   + Fvars.ts_Log);
        
        //Calling Scripts
        openBrowser(Fvars.ts_Browser);
        
        //Test execution
        switch (Tvars.ts_Application) {
               case API: 
                   //Main_API.execute();
                   break;
               case WWW: 
                   //Main_INTERNAL.execute();
                   break;
               case HELP:
                   //Main_EXTERNAL.execute();
                   break;
               default:
                   Main_Full.execute();
                   break;
        }
            
        //Closing browser and getting metrics
        closeBrowser();
        getTestMetrics();
    			
        logMessage(logEntry.INFO, "******************** END ********************");
        logMessage(logEntry.INFO, "*********** MAIN SCRIPT COMPLETED ***********");
        logMessage(logEntry.INFO, "******************** END ********************");
        
        Emailer("", "", "juan@wearsafe.com", "",1,1);
    }
}
