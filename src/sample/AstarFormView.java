package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class AstarFormView {

    // Instantiation of components
    private GridPane Startview;
    Button exitBtn = new Button("Exit");
    Label StartVertexLbl =new Label("Select Start Vertex:");
    ComboBox<Vertex> startVertexComB= new ComboBox<>();
    Button euclideanCalculatorButton = new Button("Run A* with Euclidean");
    Button manhattanCalculatorButton = new Button("Run A* with Manhattan");
    Label EndVertexLbl =new Label("Select End Vertex:");
    ComboBox<Vertex> endVertexComB= new ComboBox<>();
    Button PrintBtn = new Button("Print the shortest path");
    TextArea shortestpathTA=new TextArea();

    // AstarFormView
    public AstarFormView(AStarGraph GraphModel){
        Startview = new GridPane();
        Startview.setMinSize(300,200);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(1);

        // ComboB
        ObservableList<Vertex> VertexList = FXCollections.observableArrayList(GraphModel.getVertices());
        Callback<ListView<Vertex>, ListCell<Vertex>> VertexcellFactory = new Callback<>() {

            @Override
            public ListCell<Vertex> call(ListView<Vertex> vertexListView) {
                return new ListCell<>(){
                    @Override
                    protected void updateItem(Vertex vertex, boolean empty) {
                        super.updateItem(vertex, empty);// to call method from parent class
                        if ( vertex ==null || empty){
                            setText(null);
                        }
                        else{
                            setText(vertex.getid());
                        }
                    }
                };
            }
        };

        /* We set all of the start and end Vertex, and add our controls to the view. */
        startVertexComB.setItems(VertexList);
        startVertexComB.setButtonCell(VertexcellFactory.call(null));
        startVertexComB.setCellFactory(VertexcellFactory);
        startVertexComB.setValue(GraphModel.getVertices().get(0));
        endVertexComB.setItems(VertexList);
        endVertexComB.setButtonCell(VertexcellFactory.call(null));
        endVertexComB.setCellFactory(VertexcellFactory);
        endVertexComB.setValue(GraphModel.getVertices().get(0));
        shortestpathTA.setPrefColumnCount(1);
        // Add control to pane
        Startview.add(StartVertexLbl, 1,1);
        Startview.add(startVertexComB,15,1);
        Startview.add(euclideanCalculatorButton,15,3);
        Startview.add(manhattanCalculatorButton,20, 3);
        Startview.add(EndVertexLbl,1,2);
        Startview.add(endVertexComB,15,2);
        Startview.add(PrintBtn,15,4);
        Startview.add(shortestpathTA,15,5);

        Startview.add(exitBtn,15,8);

    }

    // Function to return parent
    public Parent asParent(){
        return Startview;
    }
}