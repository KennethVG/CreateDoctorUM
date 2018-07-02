package be.createdoctor.model.response;

import be.createdoctor.model.request.Request;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface Response<T> {

    default String callSoapWebService(Request<T> request, String soapEndpointUrl, String soapAction, T type) {
        SOAPConnection soapConnection = null;
        ByteArrayOutputStream outputStream = null;

        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(request.createSOAPRequest(soapAction, type),
                    soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            outputStream = new ByteArrayOutputStream();
            soapResponse.writeTo(outputStream);

        } catch (SOAPException e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the " +
                    "correct endpoint URL and SOAPAction!\n");
            try {

                if (soapConnection != null) {
                    soapConnection.close();
                }
            } catch (SOAPException e1) {
                throw new RuntimeException("SoapConnection Failed!");
            }
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException("Cannot write response to console: " + e.getMessage());
        }

        return outputStream != null ? outputStream.toString() : null;
    }
}