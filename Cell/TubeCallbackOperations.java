package Cell;


/**
* Cell/TubeCallbackOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Cell.idl
* 27 декабря 2015 г. 22:02:17 MSK
*/

public interface TubeCallbackOperations 
{
  void sendSMS (String fromNum, String message);
  String getNum ();
  void blocked ();
} // interface TubeCallbackOperations
