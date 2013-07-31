# 'cd' to the project's lib directory where there is a copy of the 3 required Apache libs for convenience
call loadjava -user rfield/rfield@10.251.229.40:1521:SDBDB1 -resolve -thin -force -verbose httpclient-4.0.1.jar
call loadjava -user rfield/rfield@10.251.229.40:1521:SDBDB1 -resolve -thin -force -verbose commons-logging-1.1.1.jar
call loadjava -user rfield/rfield@10.251.229.40:1521:SDBDB1 -resolve -thin -force -verbose commons-codec-1.3.jar
