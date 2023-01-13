import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class SearchApp  extends Application {


    public SearchApp() {
    }

    public void start(Stage primaryStage) {
        Pane  aPane = new Pane();
        SearchAppView view = new SearchAppView();
        aPane.getChildren().add(view);


        view.searchField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });



        view.crawlButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ProjectTester tester = new ProjectTesterImp();
                tester.initialize();
                tester.crawl((String) view.addressBox.getValue());
            }
        });

        view.aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<SearchResult> result = (new SearchEngine(view.searchField.getText(),view.boostTrue.isSelected(),10)).search();
                String text = "";
                for (SearchResult s: result){
                    text = text + s.getTitle()+ "  " + s.getScore()+"\n";

                }
                view.answerField.setText(text);

            }
        });

        primaryStage.setTitle("Fake Google");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane, 800,600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}