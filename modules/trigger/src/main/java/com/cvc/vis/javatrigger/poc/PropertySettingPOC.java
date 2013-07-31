package com.cvc.vis.javatrigger.poc;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: rfield
 * Date: 7/29/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertySettingPOC {

    /*
     *  Oddly, this seemingly simple code does not work within the Oracle Database JVM runtime.
     *  Static variables don't hang around between method invocations with their proper values
     *  in the context of an Oracle Database Java Procedure or Function.
     *  Probably the Oracle DB JVM instantiates the class for each invocation.
     *
     *  So the best way to parameterize host and port would appear to be to read them
     *  from a reference table.
     */
    private static String hostName = "puma-a";
    private static int portNumber = 9989;

    public static void setHostName( String host ) throws SQLException {
        hostName = host;
    }

    public static void setPortNumber( int port ) throws SQLException {
        portNumber = port;
    }

    public static String dump() throws SQLException {
        return "result: " + hostName + ":" + portNumber++;
    }

    public static void main(String args[]) {
            try {
                PropertySettingPOC.setHostName("localhost");
                PropertySettingPOC.setPortNumber(80);
                System.out.println(PropertySettingPOC.dump());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
}
