package facebook.src;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class feed_Controller {
    @FXML
    private BorderPane mainContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox feedContainer;

    //privateArrayList<post>recommended_posts = new ArrayList<>();




    private final User logged_in_user = DATA.currentUser;
    recommendation_system recommend_posts = new recommendation_system(logged_in_user);
   ArrayList<Post>Feed = recommend_posts.get_logged_in_user_feed();


    public void initialize() {
        // initialization logic


        Feed.sort((post1, post2) -> post2.Date.compareTo(post1.Date));
        for(Post p:Feed){
            PostLayout postLayout = new PostLayout(p.author_id,p.post_id, logged_in_user.id);
            VBox postVBox = postLayout.setPostData(p);
            feedContainer.getChildren().add(postVBox);
        }

        homeIcon.setImage(homeiconSelected);
    }
    @FXML
    private ImageView homeIcon;
    @FXML
    private ImageView searchIcon;
    @FXML
    private ImageView chatIcon;
    @FXML
    private Button chat_btn;
    @FXML
    private Button search_btn;
    @FXML
    private Button home_btn;

    private final Image homeiconDefault = new Image(getClass().getResourceAsStream("/images/home.png"));
    private final Image homeiconSelected = new Image(getClass().getResourceAsStream("/images/home_blue.png"));

    private final Image searchiconDefault = new Image(getClass().getResourceAsStream("/images/search.png"));

    private final Image searchiconSelected = new Image(getClass().getResourceAsStream("/images/search_blue.png"));

    private final Image chaticonDefault = new Image(getClass().getResourceAsStream("/images/chat.png"));
    private final Image chaticonSelected = new Image(getClass().getResourceAsStream("/images/chat_blue.png"));


    public void home_btn_clicked(MouseEvent mouseEvent) {
        if (homeIcon.getImage().equals(homeiconDefault)) {
            homeIcon.setImage(homeiconSelected);

            // home_btn.setStyle("-fx-border-color: #1877f2;");

        } else {
            homeIcon.setImage(homeiconDefault);
            //home_btn.setStyle("-fx-border-color: black;");
        }


    }

    public void search_btn_clicked(Event mouseEvent) throws IOException{
        if (searchIcon.getImage().equals(searchiconDefault)) {
            searchIcon.setImage(searchiconSelected);
            Scene_Changer scene_changer = new Scene_Changer();
            scene_changer.loadAndSetScene(mouseEvent,"Search_For_Friends.fxml");
            // search_btn.setStyle("-fx-border-color: #1877f2;");

        } else {
            searchIcon.setImage(searchiconDefault);
           // search_btn.setStyle("-fx-border-color: black;");
        }


    }

    public void chat_btn_clicked(MouseEvent mouseEvent) {

        if (chatIcon.getImage().equals(chaticonDefault)) {
            chatIcon.setImage(chaticonSelected);
            //chat_btn.setStyle("-fx-border-color: #1877f2;");

        } else {
            chatIcon.setImage(chaticonDefault);
            //chat_btn.setStyle("-fx-border-color: black;");

        }


    }

    public void open_write_post_scene(ActionEvent event) throws IOException{
        Scene_Changer scene_changer = new Scene_Changer();
      scene_changer.loadAndSetScene(event,"write_post.fxml");
    //scene_changer.loadAndSetScene("write_post.fxml","New Post!");
    }
}
