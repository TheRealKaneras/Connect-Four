package com.kaneras.connectfour;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ConnectFour extends Application {
    private Stage primaryStage;
    private Canvas canvas;
    private GraphicsContext graphics;
    private int gridSize;
    private EnumSelection[][] selections;
    private double mouseX = 0;
    private double mouseY = 0;
    private EnumSelection currentPlayer;

    private void displayWin(EnumSelection player) {
        AlertBox.showAlert("Game over", "The " + currentPlayer.toString().toLowerCase() + " player wins.");
    }

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
                if (selections[i][j] == EnumSelection.RED) {
                    graphics.setFill(Color.RED);
                } else if (selections[i][j] == EnumSelection.YELLOW) {
                    graphics.setFill(Color.GOLDENROD);
                } else {
                    graphics.setFill(Color.WHITE);
                }
                graphics.fillOval(i * cellWidth + 6, j * cellHeight + 6, cellWidth - 12, cellHeight - 12);
            }
        }
        if (currentPlayer == EnumSelection.RED) {
            graphics.setFill(Color.RED);
        } else {
            graphics.setFill(Color.GOLDENROD);
        }
        graphics.fillOval(mouseX - ((cellWidth - 12) / 2), mouseY - ((cellHeight - 12) / 2), cellWidth - 12, cellHeight - 12);
    }

    private void handleMouseClick(MouseEvent e) {
        int cellX = (int) (e.getX() / (canvas.getWidth() / gridSize));
        int cellY = -1;
        for (int i = selections[cellX].length - 1; i >= 0; i--) {
            if (selections[cellX][i] == null) {
                cellY = i;
                break;
            }
        }
        if (cellY >= 0) {
            selections[cellX][cellY] = currentPlayer;
            if(checkForWin(currentPlayer)) {
                drawGrid();
                displayWin(currentPlayer);
            }
            currentPlayer = (currentPlayer == EnumSelection.RED ? EnumSelection.YELLOW : EnumSelection.RED);
            drawGrid();
        } else {
            System.out.println("Column full");
        }
    }

    private void handleMouseMove(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        drawGrid();
    }

    private boolean checkForWin(EnumSelection player) {
        // Check horizontal
        for (int i = 0; i < gridSize - 3; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (selections[i][j] == player && selections[i + 1][j] == player && selections[i + 2][j] == player && selections[i + 3][j] == player)
                    return true;
            }
        }
        // Check vertical
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize - 3; j++) {
                if (selections[i][j] == player && selections[i][j + 1] == player && selections[i][j + 2] == player && selections[i][j + 3] == player)
                    return true;
            }
        }
        // Check \ diagonally
        for (int i = 0; i < gridSize - 3; i++) {
            for (int j = 0; j < gridSize - 3; j++) {
                if (selections[i][j] == player && selections[i + 1][j + 1] == player && selections[i + 2][j + 2] == player && selections[i + 3][j + 3] == player)
                    return true;
            }
        }
        // Check / diagonally
        for (int i = 0; i < gridSize - 3; i++) {
            for (int j = 3; j < gridSize; j++) {
                if (selections[i][j] == player && selections[i + 1][j - 1] == player && selections[i + 2][j - 2] == player && selections[i + 3][j - 3] == player)
                    return true;
            }
        }
        return false;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Connect 4");

        gridSize = 6;
        currentPlayer = EnumSelection.YELLOW;
        selections = new EnumSelection[gridSize][gridSize];

        canvas = new Canvas(600.0f, 600.0f);
        canvas.setOnMouseClicked(e -> handleMouseClick(e));
        canvas.setOnMouseMoved(e -> handleMouseMove(e));
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
