package com.kaneras.connectfour;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ConnectFour extends Application {
    private Stage primaryStage;
    private Canvas canvas;
    private GraphicsContext graphics;
    private int gridSize = 6;

    private void drawGrid() {
        graphics.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        double cellWidth = canvas.getWidth() / gridSize;
        double cellHeight = canvas.getHeight() / gridSize;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                graphics.setFill(Color.BLUE);
                graphics.fillRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
                graphics.setFill(Color.DARKBLUE);
                graphics.fillRect(i * cellWidth + 1, j * cellHeight + 1, cellWidth - 2, cellHeight - 2);
                graphics.setFill(Color.WHITE);
                graphics.fillOval(i * cellWidth + 6, j * cellHeight + 6, cellWidth - 12, cellHeight - 12);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Connect 4");

        canvas = new Canvas(600.0f, 600.0f);
        graphics = canvas.getGraphicsContext2D();
        drawGrid();

        VBox layout = new VBox();
        layout.getChildren().add(canvas);
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
