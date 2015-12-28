package Cell;


/**
* Cell/TubeCallbackPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Cell.idl
* 28 декабря 2015 г. 15:46:46 MSK
*/

public abstract class TubeCallbackPOA extends org.omg.PortableServer.Servant
 implements Cell.TubeCallbackOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("sendSMS", new java.lang.Integer (0));
    _methods.put ("getNum", new java.lang.Integer (1));
    _methods.put ("blocked", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Cell/TubeCallback/sendSMS
       {
         String fromNum = in.read_string ();
         String message = in.read_string ();
         this.sendSMS (fromNum, message);
         out = $rh.createReply();
         break;
       }

       case 1:  // Cell/TubeCallback/getNum
       {
         String $result = null;
         $result = this.getNum ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // Cell/TubeCallback/blocked
       {
         this.blocked ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Cell/TubeCallback:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public TubeCallback _this() 
  {
    return TubeCallbackHelper.narrow(
    super._this_object());
  }

  public TubeCallback _this(org.omg.CORBA.ORB orb) 
  {
    return TubeCallbackHelper.narrow(
    super._this_object(orb));
  }


} // class TubeCallbackPOA
