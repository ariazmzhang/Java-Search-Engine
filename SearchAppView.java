import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class SearchAppView extends Pane{
    TextField searchField;
    TextArea answerField;
    Button aButton, crawlButton;
    RadioButton boostTrue;
    ComboBox addressBox;

    public SearchAppView() {
        Text text = new Text("Doogle");
        text.setStyle("-fx-font: 50 Verdana; -fx-text-alignment: center;");
        text.relocate(300,80);
        text.setFill(Color.SANDYBROWN);
        getChildren().add(text);


        searchField = new TextField();
        searchField.relocate(200,170);
        searchField.setPrefSize(400, 30);
        getChildren().add(searchField);

        // Add the search button
        aButton = new Button("Doogle");
        aButton.relocate(650, 170);
        aButton.setPrefSize(70, 30);
        getChildren().add(aButton);


        crawlButton = new Button("Crawl");
        crawlButton.relocate(650, 120);
        crawlButton.setPrefSize(70, 30);
        getChildren().add(crawlButton);


        // Add the drop-down list
        ObservableList<String> options = FXCollections.observableArrayList(
                "http://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html",
                "http://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html",
                "http://people.scs.carleton.ca/~davidmckenney/fruits2/N-0.html",
                "http://people.scs.carleton.ca/~davidmckenney/fruits3/N-0.html",
                "http://people.scs.carleton.ca/~davidmckenney/fruits4/N-0.html",
                "http://people.scs.carleton.ca/~davidmckenney/fruits5/N-0.html");
        addressBox = new ComboBox(options);
        //addressBox.setPromptText("Choose address type");
        addressBox.setValue("http://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");
        addressBox.relocate(200,120);
        addressBox.setPrefSize(400,30);
        getChildren().add(addressBox);

        boostTrue=new RadioButton("Boost?");
        boostTrue.relocate(380, 220);
        boostTrue.setPrefSize(150, 20);
        getChildren().add(boostTrue);


        answerField = new TextArea();
        answerField.relocate(150,250);
        answerField.setPrefSize(500, 300);
        answerField.setEditable(false);
        getChildren().add(answerField);

    }
}
