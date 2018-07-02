package be.createdoctor.model.request;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;

import static be.createdoctor.model.SoapConstants.SOAP_ACTION;

public interface Request<T> {

    default SOAPMessage createSOAPRequest(String soapAction, T type) throws SOAPException, IOException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, type);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader(SOAP_ACTION, soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    void createSoapEnvelope(SOAPMessage soapMessage, T type) throws SOAPException;


}
