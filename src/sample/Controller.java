package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import java.util.Stack;

public class Controller {
    private final AStarGraph model;
    private final AstarFormView view;

    // Actions of the views buttons
    public Controller(AStarGraph GraphModel, AstarFormView aStarFormView) {
        this.model = GraphModel;
        this.view = aStarFormView;
        view.exitBtn.setOnAction(e -> Platform.exit());
        // Gets value from combBox
        view.euclideanCalculatorButton.setOnAction(e -> model.A_Star(view.startVertexComB.getValue(), view.endVertexComB.getValue(),true));
        view.manhattanCalculatorButton.setOnAction(e -> model.A_Star(view.startVertexComB.getValue(), view.endVertexComB.getValue(),false));
        EventHandler<ActionEvent> PrintRequestHndl = e -> printPath(view.endVertexComB.getValue(), view.shortestpathTA);
        view.PrintBtn.setOnAction(PrintRequestHndl);
    }

    /* Our runAstar prints the shortest distances from one vertex to another, and prints all our the Vertex it passes through */
    public void printPath (Vertex destination, TextArea TArea){
        Vertex pvertex = destination;
        TArea.appendText("To " + destination.getid() + " Shortest length: \n");
        Stack<Vertex> Path = new Stack<>();
        int limit =0;
        while (pvertex!=null) {
            Path.push(pvertex);
            pvertex = pvertex.getPrev();
        }
        if (!Path.isEmpty()) {
            limit = Path.size();
        }
        for (int i=0; i<limit -1; i++) {
            TArea.appendText(Path.pop().getid() + " -> \n");
        }
        if (limit >0) {
            TArea.appendText(Path.pop().getid() + "\n");
        }
    }
}