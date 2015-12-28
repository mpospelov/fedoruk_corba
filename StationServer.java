import Cell.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.HashMap;

public class StationServer {
  public static void main(String args[])
  {
    try
    {
      ORB orb = ORB.init(args, null);
      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      StationServant servant = new StationServant();

            // Получение объектной ссылки на сервант
      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(servant);
      Station sref = StationHelper.narrow(ref);

      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // Связывание объектной ссылки с именем
      String name = args[4];
      NameComponent path[] = ncRef.to_name( name );
      ncRef.rebind(path, sref);

      System.out.println("Station server is ready and waiting for messages ...");

      if(args.length > 5){
        String neighbor_name = args[5];
        NameComponent nc = new NameComponent(neighbor_name, "");
        NameComponent path1[] = {nc};
        Station stationRef = StationHelper.narrow(ncRef.resolve(path1));
        stationRef.registerStation(sref, name);
        sref.setStation(stationRef, neighbor_name);
      }

      // Ожидание обращений от клиентов (трубок)
      orb.run();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}

// Класс, реализующий IDL-интерфейс базовой станции
class StationServant extends StationPOA
{
  private HashMap<String, TubeCallback> hm = new HashMap<String, TubeCallback>();
  private HashMap<String, String> hm_money = new HashMap<String, String>();

  Station nStationReference;
  String nStationName;

    // Метод регистрации трубки в базовой станции
  public void register (TubeCallback objRef, String phoneNum)
  {
    hm.put(phoneNum, objRef);
    hm_money.put(phoneNum, "50");

    System.out.println("Server: find phone " + phoneNum);
  }

  public int setStation(Station stationRef, String name) {
    nStationReference = stationRef;
    nStationName = name;
    return 0;
  }

  public int registerStation(Station objRef, String phoneNum)
  {
    nStationReference = objRef;
    nStationName = phoneNum;
    System.out.println("Station " + nStationName + " was connected");
    return (1);
  }

    // Метод пересылки сообщения от трубки к трубке
  public void sendSMS (String fromNum, String toNum, String message, String ttl)
  {
    if(ttl.isEmpty())
      ttl = "64";
    if( !hm.containsKey(toNum) )
    {
      Integer int_ttl = Integer.parseInt(ttl);
      if(int_ttl <= 0){
        System.out.println("Stop sending!");
        return;
      }
      int_ttl--;
      System.out.println("Server: phone "+fromNum+" can't send sms to "+toNum+" ("+toNum+" not found!)\nSending to neighbor!");
      nStationReference.sendSMS(fromNum, toNum, message, Integer.toString(int_ttl));
      return;
    }

        // Здесь должен быть поиск объектной ссылки по номеру toNum

    int len = 0;

    for(int i=0 ; i < message.length(); i++){
      char c = message.charAt(i);
      if((c>=65 && c <= 90) || (c>=97 && c <= 122) ){
        len++;
      } else {
        len = len + 2;
      }
    }

    hm.get(toNum).sendSMS(fromNum, message);

    System.out.println("Server: phone "+fromNum+" send sms to "+toNum);
    System.out.println("Server: phone "+fromNum);
  }
}

