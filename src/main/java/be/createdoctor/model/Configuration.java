package be.createdoctor.model;

import be.createdoctor.CreateDoctorApplication;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static Properties properties;
    private static final String SITE = "site";

    public static String getProperty(String key) {
        if (properties == null)
            loadProperties();
        return properties.getProperty(key);
    }

    private static void loadProperties() {
        properties = new Properties();
        String mysite = System.getProperty(SITE);

        if (mysite == null) {
            throw new RuntimeException("place -Dsite=somedi or -Dsite=practimed in your VM options when running the " +
                    "application!");
        } else {
            if (StringUtils.equalsIgnoreCase(mysite, "somedi")) {
                try {
                    properties.load(CreateDoctorApplication.class.getResourceAsStream("/somedi/soap.properties"));
                } catch (IOException e) {
                    throw new RuntimeException("Can not find properties of Somedi");
                }
            } else if (StringUtils.equalsIgnoreCase(mysite, "practimed")) {
                try {
                    properties.load(CreateDoctorApplication.class.getResourceAsStream("/practimed/soap.properties"));
                } catch (IOException e) {
                    throw new RuntimeException("Can not find properties of Practimed");
                }
            }else if (StringUtils.equalsIgnoreCase(mysite, "somedi-acc")) {
                try {
                    properties.load(CreateDoctorApplication.class.getResourceAsStream("/somedi-acc/soap.properties"));
                } catch (IOException e) {
                    throw new RuntimeException("Can not find ACC properties of Somedi");
                }
            }
            else if (StringUtils.equalsIgnoreCase(mysite, "practimed-acc")) {
                try {
                    properties.load(CreateDoctorApplication.class.getResourceAsStream("/practimed-acc/soap.properties"));
                } catch (IOException e) {
                    throw new RuntimeException("Can not find ACC properties of Practimed");
                }
            }
        }

    }
}
