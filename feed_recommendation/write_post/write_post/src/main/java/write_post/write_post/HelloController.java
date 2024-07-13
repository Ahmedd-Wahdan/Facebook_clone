package write_post.write_post;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private TextArea postTextArea;

    @FXML
    private CheckBox privacyCheckBox;

    @FXML
    private TextField tagFriendsTextField;

    @FXML
    private void handlePostButtonClick(ActionEvent event) {
        String postText = postTextArea.getText();
        boolean isPrivate = privacyCheckBox.isSelected();
        String taggedFriends = tagFriendsTextField.getText();

        // Your logic to handle the post, privacy, and tagged friends
        // For example, you might print them for testing purposes:
        System.out.println("Post Text: " + postText);
        System.out.println("Is Private: " + isPrivate);
        System.out.println("Tagged Friends: " + taggedFriends);

        // Add your logic to actually post the content to Facebook or perform other actions
        // ...

        // Clear the input fields after posting
        postTextArea.clear();
        privacyCheckBox.setSelected(false);
        tagFriendsTextField.clear();
    }
}