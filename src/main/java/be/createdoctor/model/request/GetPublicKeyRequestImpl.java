package be.createdoctor.model.request;

import javax.xml.soap.*;

import static be.createdoctor.model.SoapConstants.*;

public class GetPublicKeyRequestImpl implements Request<Void> {

    @Override
    public void createSoapEnvelope(SOAPMessage soapMessage, Void nothing) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(MY_NAMESPACE, MY_NAMESPACE_URI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(GET_PUBLIC_KEY_REQUEST, MY_NAMESPACE);

        SOAPElement requestInfo = soapBodyElem.addChildElement(REQUEST_INFO, MY_NAMESPACE);

        SOAPElement applicationToken = requestInfo.addChildElement(APPLICATION_TOKEN, MY_NAMESPACE);
        applicationToken.addTextNode(TOKEN_VALUE);
    }
}