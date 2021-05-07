import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fta.App;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Text loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox Role;

    @@ -25,5 +32,15 @@
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Role.getItems().addAll("Client", "Trainer");
    }

    @FXML
    public void handleLoginPageAction(ActionEvent event) throws Exception{
        Stage stage=(Stage)loginMessage.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("Register.fxml"));
        stage.setTitle("Register");
        stage.setScene(new Scene(root, 350, 300));
        stage.show();
    }

}

