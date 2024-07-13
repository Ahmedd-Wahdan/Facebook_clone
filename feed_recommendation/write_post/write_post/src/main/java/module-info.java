module write_post.write_post {
    requires javafx.controls;
    requires javafx.fxml;


    opens write_post.write_post to javafx.fxml;
    exports write_post.write_post;
}