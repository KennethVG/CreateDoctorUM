package be.createdoctor.model.request;

import be.createdoctor.model.Credential;

import javax.xml.soap.*;

import static be.createdoctor.model.SoapConstants.*;

public class CredentialRequestImpl implements Request<Credential> {

    @Override
    public void createSoapEnvelope(SOAPMessage soapMessage, Credential type) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(MY_NAMESPACE, MY_NAMESPACE_URI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(ADD_CREDENTIAL_REQUEST, MY_NAMESPACE);

        SOAPElement requestInfo = soapBodyElem.addChildElement(REQUEST_INFO, MY_NAMESPACE);

        SOAPElement applicationToken = requestInfo.addChildElement(APPLICATION_TOKEN, MY_NAMESPACE);
        applicationToken.addTextNode(TOKEN_VALUE);

        SOAPElement credential = soapBodyElem.addChildElement(CREDENTIAL, MY_NAMESPACE);

        SOAPElement channeltype = credential.addChildElement(CHANNEL_TYPE, MY_NAMESPACE);
        channeltype.addTextNode(type.getChannelType());

        SOAPElement sendingEntity = credential.addChildElement(SENDING_ENTITY, MY_NAMESPACE);
        sendingEntity.addTextNode(type.getSendingEntity());

        SOAPElement keyStoreCredential = credential.addChildElement(KEYSTORE_CREDENTIAL, MY_NAMESPACE);
        SOAPElement certificate = keyStoreCredential.addChildElement(CERTIFICATE, MY_NAMESPACE);
        SOAPElement password = certificate.addChildElement(PASSWORD, MY_NAMESPACE);
        password.addTextNode(type.getPassword());
        SOAPElement alias = certificate.addChildElement(ALIAS, MY_NAMESPACE);
        alias.addTextNode(type.getAlias());
        SOAPElement keystore = certificate.addChildElement(KEYSTORE, MY_NAMESPACE);
        keystore.addTextNode(type.getKeyStore());

        SOAPElement identification = keyStoreCredential.addChildElement(IDENTIFICATION, MY_NAMESPACE);
        SOAPElement identifier = identification.addChildElement(IDENTIFIER, MY_NAMESPACE);
        identifier.addTextNode(type.getIdentifier());
        SOAPElement quality = identification.addChildElement(QUALITY, MY_NAMESPACE);
        quality.addTextNode(type.getQuality());
        SOAPElement ssin = identification.addChildElement(SSIN, MY_NAMESPACE);
        ssin.addTextNode(type.getSsin());

        SOAPElement id = soapBodyElem.addChildElement(ID, MY_NAMESPACE);
        id.addTextNode(type.getId());
    }
}