package chilexplox;
import java.io.IOException;

import chilexplox.view.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainApp extends Application {
	
	private Stage primaryStage;
    private BorderPane ventanaPrincipal;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chilexplox");

        // Iniciar la ventana principal que contendrá todo
        IniciarVentanaPrincipal();

        // Iniciar la vista del login
        mostrarLogin();
	}
	
	/**
     * Inicializar ventana principal
     */
    public void IniciarVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            ventanaPrincipal = (BorderPane) loader.load();

            // Cargar escena de la venta principal
            Scene scene = new Scene(ventanaPrincipal);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Mostrar el login
     */
    public void mostrarLogin() {
        try {
            // Load login
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
            AnchorPane login = (AnchorPane) loader.load();

            // Poner la vista del login en el centro de la ventana principal
            ventanaPrincipal.setCenter(login);
            
            // Dar acceso al controlador de login
            LoginController controller = loader.getController();
            controller.setMainApp(this);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //se llama cuando se hizo login correcto
    public void MostrarMenu() {
    	try {
            // Load menu
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
            AnchorPane login = (AnchorPane) loader.load();

            // Poner la vista del login en el centro de la ventana principal
            ventanaPrincipal.setCenter(login);
            
            // Dar acceso al controlador de login
            LoginController controller = loader.getController();
            controller.setMainApp(this);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public static void main(String[] args) {
		launch(args);
	}
}
