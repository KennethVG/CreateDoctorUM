package be.createdoctor;

import be.createdoctor.model.Configuration;
import be.createdoctor.model.User;
import be.createdoctor.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
public class CreateDoctorApplication extends Application {

    private ConfigurableApplicationContext context;
    private Parent root;

    public static void main(String[] args) {
        launch(CreateDoctorApplication.class, args);
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(CreateDoctorApplication.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create_doctor.fxml"));
        loader.setControllerFactory(context::getBean);
        root = loader.load();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/recip-e-logo.png")));
        primaryStage.setResizable(true);
        primaryStage.setTitle("e-Health certificaat toevoegen aan UM");
        primaryStage.show();
    }

    @Override
    public void stop() {
        context.stop();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
