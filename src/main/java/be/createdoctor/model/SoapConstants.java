package be.createdoctor.model;

public interface SoapConstants {

    // Header
    String SOAP_ACTION = "SOAPAction";

    // Envelope info:
    String MY_NAMESPACE = "urn";
    String MY_NAMESPACE_URI = "urn:be:healthconnect:unifiedmessagingservice:1_0:common";

    // createDoctor:
    String REGISTER_USER_REQUEST = "RegisterUserRequest";
    String REQUEST_INFO = "RequestInfo";
    String APPLICATION_TOKEN = "ApplicationToken";
    String USER = "User";
    String LAST_NAME = "lastName";
    String FIRST_NAME = "firstName";
    String EMAIL = "email";
    String PHONE = "phone";
    String SSIN = "ssin";
    String TYPE = "type";
    String TOKEN_VALUE = "12DAD342RD";

    // GetPublicKey
    String GET_PUBLIC_KEY_REQUEST = "GetPublicKeyRequest";

    // AddCredential
    String ADD_CREDENTIAL_REQUEST = "AddCredentialRequest";
    String CREDENTIAL = "Credential";
    String ID = "id";
    String CHANNEL_TYPE = "channelType";
    String SENDING_ENTITY = "sendingEntity";
    String KEYSTORE_CREDENTIAL = "KeystoreCredential";
    String CERTIFICATE = "Certificate";
    String PASSWORD = "Password";
    String ALIAS = "Alias";
    String KEYSTORE = "Keystore";
    String IDENTIFICATION = "Identification";
    String IDENTIFIER = "identifier";
    String QUALITY = "quality";
}
