clear:
	rm -r Cell;true
	rm -r orb.db
compile:
	idlj -fall Cell.idl
	javac StationServer.java Cell/*.java
	javac Tube.java
run_orb:
	orbd -ORBInitialPort 1050 -ORBInitialHost localhost
run_server:
	java StationServer -ORBInitialPort 1050 -ORBInitialHost localhost BaseStation
run_server2:
	java StationServer -ORBInitialPort 1050 -ORBInitialHost localhost BaseStation2 BaseStation
run_client:
	java Tube -ORBInitialPort 1050 -ORBInitialHost localhost ${PHONE_NUMBER} ${SERVER}
