package be.createdoctor.javafx.controller;

import be.createdoctor.model.Configuration;
import be.createdoctor.model.Credential;
import be.createdoctor.model.User;
import be.createdoctor.model.request.CreateDoctorRequestImpl;
import be.createdoctor.model.request.CredentialRequestImpl;
import be.createdoctor.model.request.GetPublicKeyRequestImpl;
import be.createdoctor.model.response.CreateDoctorResponseImpl;
import be.createdoctor.model.response.CredentialResponseImpl;
import be.createdoctor.model.response.GetPublicKeyResponseImpl;
import be.createdoctor.model.response.Response;
import be.createdoctor.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Controller
public class CreateDoctorController {

    private final UserService userService;

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSSIN;
    @FXML
    private TextField txtNIHII;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblInfo;
    @FXML
    private Label lblLookUp;
    @FXML
    private Label lblDeleteResult;
    @FXML
    private PasswordField txtCertificatePassword;
    @FXML
    private TextField txtCertificatePath;

    @Autowired
    public CreateDoctorController(UserService userService) {
        this.userService = userService;
    }

    @FXML
    private void lookUpDoctor() {
        if (txtSSIN.getText() == null || txtSSIN.getText().equals("")) {
            lblLookUp.setText("Rrn van de dokter is niet ingevuld!");
        } else {
            User user = userService.findbySsin(txtSSIN.getText());
            lblLookUp.setText(user != null ? "Dr. " + user.getLastName() + "(id:" + user.getId() + ") zit in de databank" : "Deze dokter zit niet in de databank");
        }
    }

    @FXML
    private void deleteDoctor(){
        if (txtSSIN.getText() == null || txtSSIN.getText().equals("")) {
            lblDeleteResult.setText("Rrn van de dokter is niet ingevuld!");
        } else {
            User user = userService.findbySsin(txtSSIN.getText());
            lblDeleteResult.setText(userService.deleteUser(txtSSIN.getText()) ? "Dr. " + user.getLastName() + " zit niet meer in de databank" : "Dokter niet verwijderd!");
        }
    }

    @FXML
    private void createDoctor() {
        if (!new File(txtCertificatePath.getText()).exists()) {
            lblStatus.setText("Validation error");
            lblInfo.setText("Path to certificate is invalid!");
        } else {
            Response<User> createDoctorResponse = new CreateDoctorResponseImpl();
            User doctor = new User();
            doctor.setType("PERSON");
            doctor.setSsin(txtSSIN.getText());
            doctor.setEmail(txtEmail.getText());
            doctor.setLastName(txtLastName.getText());
            doctor.setFirstName(txtFirstName.getText());
            doctor.setPhone(txtPhone.getText());

            String xml = createDoctorResponse.callSoapWebService(new CreateDoctorRequestImpl(), Configuration.getProperty("soap_endpoint"),
                    Configuration.getProperty("soap_register_user"), doctor);

            String statusCode = StringUtils.substringBetween(xml, "<Code>", "</Code>");
            lblStatus.setText("Status code: " + statusCode);
            lblInfo.setText("Info: " + StringUtils.substringBetween(xml, "<info>", "</info>"));
            String userId = StringUtils.substringBetween(xml, "<id>", "</id>");

            if (StringUtils.equals(statusCode, "100")) {
                String publicKey = getPublicKey();

                Response<Credential> response = new CredentialResponseImpl();

                Credential credential = new Credential();
                credential.setChannelType("EHEALTH");
                //false because we don't want to flag all as default
                credential.setSendingEntity("false");
                credential.setAlias("authentication");
                credential.setQuality("DOCTOR");
                credential.setId(userId);

                try {
                    credential.setPassword(encryptPasswordWithPublicKey(publicKey, txtCertificatePassword.getText()));
                    Path path = Paths.get(txtCertificatePath.getText());
                    Path pathToWritePassWordFile = Paths.get(FilenameUtils.removeExtension(path.toString()) + ".txt");
                    Files.write(pathToWritePassWordFile, txtCertificatePassword.getText().getBytes());
                    byte[] data = Files.readAllBytes(path);
                    credential.setKeyStore(Base64.getEncoder().encodeToString(data));

                    credential.setIdentifier(txtNIHII.getText());
                    credential.setSsin(txtSSIN.getText());

                    xml = response.callSoapWebService(new CredentialRequestImpl(), Configuration.getProperty("soap_endpoint"),
                            Configuration.getProperty("soap_add_credential"), credential);
                    statusCode = StringUtils.substringBetween(xml, "<Code>", "</Code>");
                    lblStatus.setText("Status code: " + statusCode);
                    lblInfo.setText("Info: " + StringUtils.substringBetween(xml, "<info>", "</info>"));
                } catch (Exception e) {
                    lblInfo.setText("Error while processing credentials: " + e.getMessage());
                }

            }
        }
    }

    private static String encryptPasswordWithPublicKey(String publicKey, String eHealthKeyStorePassword) throws Exception {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(publicKey);
        PublicKey key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyBytes));

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(eHealthKeyStorePassword.getBytes());

        return Base64.getEncoder().encodeToString(result);
    }

    private static String getPublicKey() {
        Response<Void> getPublicKeyResponse = new GetPublicKeyResponseImpl();
        String resp = getPublicKeyResponse.callSoapWebService(new GetPublicKeyRequestImpl(), Configuration
                .getProperty("soap_endpoint"), Configuration.getProperty("soap_get_public_key"), null);
        return StringUtils.substringBetween(resp, "<publicKey>", "</publicKey>");
    }
}
