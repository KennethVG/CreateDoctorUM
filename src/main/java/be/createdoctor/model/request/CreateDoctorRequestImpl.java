package be.createdoctor.model.request;

import be.createdoctor.model.User;

import javax.xml.soap.*;

import static be.createdoctor.model.SoapConstants.*;

public class CreateDoctorRequestImpl implements Request<User> {

    @Override
    public void createSoapEnvelope(SOAPMessage soapMessage, User doctor) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(MY_NAMESPACE, MY_NAMESPACE_URI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(REGISTER_USER_REQUEST, MY_NAMESPACE);

        SOAPElement requestInfo = soapBodyElem.addChildElement(REQUEST_INFO, MY_NAMESPACE);

        SOAPElement applicationToken = requestInfo.addChildElement(APPLICATION_TOKEN, MY_NAMESPACE);
        applicationToken.addTextNode(TOKEN_VALUE);

        SOAPElement user = soapBodyElem.addChildElement(USER, MY_NAMESPACE);

        SOAPElement lastName = user.addChildElement(LAST_NAME, MY_NAMESPACE);
        lastName.addTextNode(doctor.getLastName());

        SOAPElement firstName = user.addChildElement(FIRST_NAME, MY_NAMESPACE);
        firstName.addTextNode(doctor.getFirstName());

        SOAPElement email = user.addChildElement(EMAIL, MY_NAMESPACE);
        email.addTextNode(doctor.getEmail());

        SOAPElement phone = user.addChildElement(PHONE, MY_NAMESPACE);
        phone.addTextNode(doctor.getPhone());

        SOAPElement ssin = user.addChildElement(SSIN, MY_NAMESPACE);
        ssin.addTextNode(doctor.getSsin());

        SOAPElement type = user.addChildElement(TYPE, MY_NAMESPACE);
        type.addTextNode(doctor.getType());
    }

}