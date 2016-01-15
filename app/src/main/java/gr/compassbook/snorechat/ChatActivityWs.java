package gr.compassbook.snorechat;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike&Katerina on 11/12/2015.
 */
public class ChatActivityWs {
    //Namespace of the Webservice - can be found in WSDL
    private static String NAMESPACE = "http://WebServices.com/";
    //Webservice URL - WSDL File location
    private static String URL = "http://83.212.117.95:8080/SnoreChatWeb-1.0-SNAPSHOT/SnoreChatWebServices?wsdl";
    //SOAP Action URI again Namespace + Web method name
    private static String SOAP_ACTION = "http://WebServices.com/";

    public static void invokeChatWS(String username, String message, String webMethName) {
        //String resTxt = null;
        // Create request!
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo params = new PropertyInfo();
        params.setName("username");
        params.setValue(username);
        params.setType(String.class);
        PropertyInfo params2 = new PropertyInfo();
        // Set Name
        params2.setName("message");
        // Set Value
        params2.setValue(message);
        // Set dataType
        params2.setType(String.class);
        // Add the property to request object
        request.addProperty(params);
        request.addProperty(params2);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            //resTxt = response.toString();

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
           // resTxt = "Error occured";
        }
        //Return resTxt to calling object

    }

    public static List<String> MessArchive(String webMethName){
        List<String> messages = new ArrayList<String>();
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            KvmSerializable kw = (KvmSerializable)envelope.bodyIn;
            for(int i=0;i<kw.getPropertyCount();i++){
                messages.add(kw.getProperty(i).toString());
            }

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
        }
        return messages;
    }


}

