import controller.TestController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import utils.Configs;
import views.screen.BoardScreenHandler;
import views.screen.LoginScreenHandler;
import views.screen.TestHandler;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game extends Application {
    public static void main(String[] args)  {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            // initialize the scene
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/fxml/SplashScreen.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Bang!");
            primaryStage.show();
            // Load splash screen with fade in effect
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            // Finish splash with fade out effect
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            // After fade in, start fade out
            fadeIn.play();
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            // After fade out, load actual content
            fadeOut.setOnFinished((e) -> {
                //change to home screen
                try {
//                    LoginScreenHandler loginScreenHandler = new LoginScreenHandler(Configs.LOGIN_SCREEN_PATH,primaryStage);
//                    loginScreenHandler.show();
//                    BoardScreenHandler boardScreenHandler = new BoardScreenHandler(Configs.BOARD_SCREEN_PATH, primaryStage);
//                    boardScreenHandler.show();
                    TestHandler testHandler = new TestHandler(Configs.TEST_PATH,primaryStage);
                    testHandler.show();

                    new Thread(() -> {
                        String a = null;
                        while (a == null || a != "exit\n") {
                            TestController testController = testHandler.getTestController();
                            Scanner sc = new Scanner(System.in);
                            a = sc.nextLine();
                            System.out.println(a);
                            testController.update(a);
                        }
                    }).start();


                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
