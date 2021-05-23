package controller;

import javafx.application.Platform;
import views.screen.TestHandler;

public class TestController {
    public static volatile String value;
    private TestHandler testHandler;

    public TestController(TestHandler testHandler) {
        this.testHandler = testHandler;
    }

    public void update(String value) {
        Platform.runLater(()->{
            testHandler.updateText(value);
        });
    }


    public static String getValue() {
        return value;
    }
}
