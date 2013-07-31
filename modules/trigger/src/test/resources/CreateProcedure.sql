CREATE OR REPLACE
PROCEDURE PUBLISH_UPDATE (
  id    NUMBER,
  price NUMBER,
  title VARCHAR2
)
as language JAVA
NAME 'com.cvc.vis.javatrigger.poc.HTTPPostDBTrigger.publish(int, float, java.lang.String)';

CREATE OR REPLACE
FUNCTION POC_DUMP RETURN VARCHAR2
as language JAVA
NAME 'com.cvc.vis.javatrigger.poc.PropertySettingPOC.dump() return java.lang.String';

CREATE OR REPLACE
PROCEDURE SET_HOST (
    host VARCHAR2
)
as language JAVA
NAME 'com.cvc.vis.javatrigger.poc.PropertySettingPOC.setHostName( java.lang.String )';

CREATE OR REPLACE
PROCEDURE SET_PORTNUM (
    portnum NUMBER
)
as language JAVA
NAME 'com.cvc.vis.javatrigger.poc.PropertySettingPOC.setPortNumber( int )';