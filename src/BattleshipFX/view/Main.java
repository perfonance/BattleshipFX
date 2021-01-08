package BattleshipFX.view;


import BattleshipFX.model.PlayerEnum;
import BattleshipFX.model.ShipCell;
import BattleshipFX.service.PlayerService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main extends Application {
    private double mouseX, mouseY;
    private boolean bSingleDeck = false;
    private boolean bDoubleDeck = false;
    private boolean bTripleDeck = false;
    private boolean bQuadroDeck = false;
    private boolean bHorizontal = true;
    private boolean bGameOver = false;
    PlayerService ps = new PlayerService();
    private Map<Integer, Group> roots = new HashMap<>();
    private Map<Integer, Scene> scenes = new HashMap<>();
    private Map<Integer, Canvas> canvases = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        ps.start();

        initRoots();

        primaryStage.setTitle("BattleShip");

        Label labelEntryText = new Label("Игра морской бой");
        labelEntryText.setFont(new Font("Arial", 30));
        labelEntryText.relocate(260, 50);

        Label labelCreateShip1 = new Label("Расстановка кораблей Игрока №1");
        labelCreateShip1.setFont(new Font("Arial", 20));
        labelCreateShip1.relocate(260, 50);

        Label labelCreateShip2 = new Label("Расстановка кораблей Игрока №2");
        labelCreateShip2.setFont(new Font("Arial", 20));
        labelCreateShip2.relocate(260, 50);

        Label firstPlayer = new Label("Игрок №1");
        firstPlayer.setFont(new Font("Arial", 20));
        firstPlayer.relocate(330, 30);

        Label secondPlayer = new Label("Игрок №2");
        secondPlayer.setFont(new Font("Arial", 20));
        secondPlayer.relocate(330, 30);

        Button buttonRules = new Button();
        Button buttonStart = new Button();
        Button buttonNextScene2 = new Button();
        Button buttonNextScene3 = new Button();

        buttonRules.relocate(460, 300);
        buttonStart.relocate(200, 300);
        buttonNextScene2.relocate(400, 500);
        buttonNextScene3.relocate(400, 500);

        buttonRules.setText("Правила игры");
        buttonStart.setText("Старт игры");
        buttonNextScene2.setText("Далее");
        buttonNextScene3.setText("Далее");


        GraphicsContext gc2 = canvases.get(2).getGraphicsContext2D();
        GraphicsContext gc3 = canvases.get(3).getGraphicsContext2D();
        GraphicsContext gc4 = canvases.get(4).getGraphicsContext2D();
        GraphicsContext gc5 = canvases.get(5).getGraphicsContext2D();
        drawShapes(gc2);


        EventHandler<MouseEvent> eventButtonRulesClicked = e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Правила игры");
            alert.setHeaderText("Клавиша V - развернуть корабль вертикально\n" +
                    "Клавиша H - развернуть корабль горизонтально");
            alert.setContentText("Размещаются:\n" + "1 корабль - «четырёхпалубный» \n" +
                    "2 корабля - «трёхпалубные» \n" + "3 корабля - «двухпалубные»\n" +
                    "4 корабля - «однопалубные»");
            alert.showAndWait();
        };


        EventHandler<MouseEvent> eventButtonStartClicked = e -> {
            roots.get(1).getChildren().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Расстановка кораблей");
            alert.setHeaderText("Игроку №1 приготовиться");
            alert.showAndWait();
            primaryStage.setScene(scenes.get(2));
            primaryStage.show();
        };


        EventHandler<MouseEvent> eventButtonNextScene2 = e -> {
            roots.get(2).getChildren().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Расстановка кораблей");
            alert.setHeaderText("Игроку №2 приготовиться");
            alert.showAndWait();
            drawShapes(gc3);
            primaryStage.setScene(scenes.get(3));
            primaryStage.show();

        };


        EventHandler<MouseEvent> eventButtonNextScene3 = e -> {
            roots.get(3).getChildren().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Начало боя");
            alert.setHeaderText("Игроку №1 приготовиться");
            alert.showAndWait();
            drawShapes2(gc4);
            drawShips(gc4, PlayerEnum.FIRST);
            drawShapes2(gc5);
            drawShips(gc5, PlayerEnum.SECOND);
            primaryStage.setScene(scenes.get(4));
            primaryStage.show();

        };


        buttonRules.setOnMouseClicked(eventButtonRulesClicked);
        buttonStart.setOnMouseClicked(eventButtonStartClicked);
        buttonNextScene2.setOnMouseClicked(eventButtonNextScene2);
        buttonNextScene3.setOnMouseClicked(eventButtonNextScene3);
        roots.get(1).getChildren().add(labelEntryText);
        roots.get(1).getChildren().add(buttonRules);
        roots.get(1).getChildren().add(buttonStart);
        roots.get(2).getChildren().add(buttonNextScene2);
        roots.get(2).getChildren().add(labelCreateShip1);
        roots.get(3).getChildren().add(buttonNextScene3);
        roots.get(3).getChildren().add(labelCreateShip2);
        roots.get(4).getChildren().add(firstPlayer);
        roots.get(5).getChildren().add(secondPlayer);
        primaryStage.setScene(scenes.get(1));


        canvases.get(2).setOnMouseClicked(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            canvasMouseClickedAction(gc2, mouseX, mouseY, PlayerEnum.FIRST);
        });


        scenes.get(2).setOnKeyPressed((keyEvent -> {
            drawCell(gc2, 50, 100);
            bHorizontal = keyPressedIdentification(keyEvent.getCode());
        }));


        canvases.get(3).setOnMouseClicked(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            canvasMouseClickedAction(gc3, mouseX, mouseY, PlayerEnum.SECOND);
        });

        scenes.get(3).setOnKeyPressed((keyEvent -> {
            drawCell(gc3, 50, 100);
            bHorizontal = keyPressedIdentification(keyEvent.getCode());
        }));

        canvases.get(4).setOnMouseClicked(event -> {
            if (!bGameOver) {
                mouseX = event.getX();
                mouseY = event.getY();
                enemyMouseAction(primaryStage, mouseX, mouseY, PlayerEnum.FIRST);
            }
        });


        canvases.get(5).setOnMouseClicked(event -> {
            if (!bGameOver) {
                mouseX = event.getX();
                mouseY = event.getY();
                enemyMouseAction(primaryStage, mouseX, mouseY, PlayerEnum.SECOND);
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void drawShapes(GraphicsContext gc) {
        int x0 = 50;
        int y0 = 100;
        int gridSideX = 200;
        int gridSideY = 200;
        int dividingLine = 20;
        gc.setStroke(Color.BLUE);
        gc.strokeRect(x0, y0, gridSideX, gridSideY); // левое поле


        for (int i = 0; i < 9; i++) {
            gc.fillText(String.valueOf(i), x0 - 15, y0 - 5 + dividingLine); // цифры слева
            gc.strokeLine(x0 + dividingLine, y0, x0 + dividingLine, y0 + gridSideY); // вертикальные линии
            gc.strokeLine(x0, y0 + dividingLine, x0 + gridSideY, y0 + dividingLine);// горизонтальные линии
            dividingLine += 20;
        }
        gc.fillText("9", 35, 295);
        gc.fillText("A    B    C    D    E    F    G    H    I    J", 56, 95); // верхние буквы
        int w = 0;
        for (int i = 4; i >= 1; i--) {
            gc.strokeRect(50, 330 + w, 20 * i, 20);   // примеры кораблей
            w += 30;
        }
    }

    private void drawShapes2(GraphicsContext gc) {


        int x0 = 50;
        int y0 = 100;
        int gridSideX = 200;
        int gridSideY = 200;
        int dividingLine = 20;
        gc.setStroke(Color.BLUE);
        gc.strokeRect(x0, y0, gridSideX, gridSideY); // левое поле
        for (int i = 0; i < 9; i++) {
            gc.fillText(String.valueOf(i), x0 - 15, y0 - 5 + dividingLine); // цифры слева
            gc.strokeLine(x0 + dividingLine, y0, x0 + dividingLine, y0 + gridSideY); // вертикальные линии
            gc.strokeLine(x0, y0 + dividingLine, x0 + gridSideY, y0 + dividingLine);// горизонтальные линии
            dividingLine += 20;
        }
        gc.fillText("9", 35, 295);
        gc.fillText("A    B    C    D    E    F    G    H    I    J", 56, 95); // верхние буквы

        dividingLine = 20;
        x0 = 450;
        y0 = 100;

        gc.setStroke(Color.BLUE);
        gc.strokeRect(x0, y0, gridSideX, gridSideY); // правое поле
        for (int i = 0; i < 9; i++) {
            gc.fillText(String.valueOf(i), x0 - 15, y0 - 5 + dividingLine); // цифры слева
            gc.strokeLine(x0 + dividingLine, y0, x0 + dividingLine, y0 + gridSideY); // вертикальные линии
            gc.strokeLine(x0, y0 + dividingLine, x0 + gridSideY, y0 + dividingLine);// горизонтальные линии
            dividingLine += 20;
        }
        gc.fillText("9", 435, 295);
        gc.fillText("A    B    C    D    E    F    G    H    I    J", 456, 95); // верхние буквы

    }

    private void drawCell(GraphicsContext gc, double x, double y) {
        if (x > 50 && x < 250 && y > 100 && y < 300) {
            int i = (int) (x - 50) / 20;
            int j = (int) (y - 100) / 20;
            int w = 20;
            int rect_x = i * w;
            int rect_y = j * w;
            gc.setFill(Color.GREEN);
            gc.fillRect(rect_x + 50, rect_y + 100, w, w);

        }
    }

    private boolean singleDeck(GraphicsContext gc, double x, double y) {
        if (x > 50 && x < 70 && y > 420 && y < 440) {
            gc.setFill(Color.RED);
            gc.fillRect(51, 421, 18, 18);
            return true;
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(51, 421, 18, 18);
            return false;
        }
    }

    private boolean doubleDeck(GraphicsContext gc, double x, double y) {
        if (x > 50 && x < 90 && y > 390 && y < 410) {
            gc.setFill(Color.RED);
            gc.fillRect(51, 391, 38, 18);
            return true;
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(51, 391, 38, 18);
            return false;
        }
    }

    private boolean tripleDeck(GraphicsContext gc, double x, double y) {
        if (x > 50 && x < 110 && y > 360 && y < 380) {
            gc.setFill(Color.RED);
            gc.fillRect(51, 361, 58, 18);
            return true;
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(51, 361, 58, 18);
            return false;
        }
    }

    private boolean quadroDeck(GraphicsContext gc, double x, double y) {
        if (x > 50 && x < 130 && y > 330 && y < 350) {
            gc.setFill(Color.RED);
            gc.fillRect(51, 331, 78, 18);
            return true;
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(51, 331, 78, 18);
            return false;
        }
    }

    private void attack(GraphicsContext gc, double x, double y, boolean bHit, boolean isEnemyAttack) {
        if (x > 450 && x < 650 && y > 100 && y < 300) {
            int i = (int) (x - 50) / 20;
            int j = (int) (y - 100) / 20;
            int w = 20;
            if (!bHit) {
                int rect_x;
                if (!isEnemyAttack) {
                    rect_x = i * w + 6;
                } else {
                    rect_x = i * w + 6 - 400;
                }
                int rect_y = j * w + 6;
                gc.setFill(Color.GREEN);
                gc.fillRect(rect_x + 50, rect_y + 100, w - 12, w - 12);
            } else {
                int rect_x;
                if (!isEnemyAttack) {
                    rect_x = i * w;
                } else {
                    rect_x = i * w - 400;
                }
                int rect_y = j * w;
                gc.setFill(Color.RED);
                gc.fillRect(rect_x + 50, rect_y + 100, w, w);
            }
        }
    }

    private int getShipX(double mouseX) {
        return (int) (mouseX - 50) / 20;
    }

    private int getShipY(double mouseY) {
        return (int) (mouseY - 100) / 20;
    }

    private void drawShips(GraphicsContext gc, PlayerEnum playerNum) {
        gc.setFill(Color.GREEN);
        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 9; y++) {
                if (ps.isShipCell(x, y, playerNum)) {
                    gc.fillRect(50 + x * 20, 100 + y * 20, 20, 20);
                }
            }
        }
    }

    private int getAttackX(double mouseX) {
        return (int) (mouseX - 450) / 20;
    }

    private int getAttackY(double mouseY) {
        return (int) (mouseY - 100) / 20;
    }

    private void initRoots() {
        for (int i = 1; i <= 5; i++) {
            Group root = new Group();
            Scene scene = new Scene(root);
            Canvas canvas = new Canvas(800, 600);
            root.getChildren().add(canvas);
            roots.put(i, root);
            scenes.put(i, scene);
            canvases.put(i, canvas);
        }
    }

    private void canvasMouseClickedAction(GraphicsContext gc, double mouseX, double mouseY, PlayerEnum player) {
        int x = getShipX(mouseX);
        int y = getShipY(mouseY);
        if (bSingleDeck) {
            if (ps.canAddShip(x, y, 1, !bHorizontal, player)) {
                ps.addShip(x, y, 1, !bHorizontal, player);
                drawCell(gc, mouseX, mouseY);
            }
        }
        bSingleDeck = singleDeck(gc, mouseX, mouseY);
        if (bDoubleDeck) {
            if (ps.canAddShip(x, y, 2, !bHorizontal, player)) {
                ps.addShip(x, y, 2, !bHorizontal, player);
                if (bHorizontal) {
                    for (int i = 0; i < 2; i++) {
                        drawCell(gc, mouseX, mouseY);
                        mouseX += 20;
                    }
                } else {
                    for (int i = 0; i < 2; i++) {
                        drawCell(gc, mouseX, mouseY);
                        mouseY += 20;
                    }
                }
            }
        }
        bDoubleDeck = doubleDeck(gc, mouseX, mouseY);
        if (bTripleDeck) {
            if (ps.canAddShip(x, y, 3, !bHorizontal, player)) {
                ps.addShip(x, y, 3, !bHorizontal, player);
                if (bHorizontal) {
                    for (int i = 0; i < 3; i++) {
                        drawCell(gc, mouseX, mouseY);
                        mouseX += 20;
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        drawCell(gc, mouseX, mouseY);
                        mouseY += 20;
                    }
                }
            }
        }
        bTripleDeck = tripleDeck(gc, mouseX, mouseY);
        if (bQuadroDeck) {
            if (ps.canAddShip(x, y, 4, !bHorizontal, player)) {
                ps.addShip(x, y, 4, !bHorizontal, player);
                if (bHorizontal) {
                    for (int i = 0; i < 4; i++) {
                        drawCell(gc, mouseX, mouseY);
                        mouseX += 20;
                    }
                } else {
                    for (int i = 0; i < 4; i++) {
                        drawCell(gc, mouseX, mouseY);
                        mouseY += 20;
                    }
                }
            }
        }
        bQuadroDeck = quadroDeck(gc, mouseX, mouseY);
    }

    private boolean keyPressedIdentification(KeyCode keyCode) {
        if (keyCode == KeyCode.V) {
            bHorizontal = false;
        } else if (keyCode == KeyCode.H) {
            bHorizontal = true;
        }
        return bHorizontal;
    }

    private void enemyMouseAction(Stage primaryStage, double mouseX, double mouseY, PlayerEnum currentPlayer) {
        if (mouseX > 450 && mouseX < 650 && mouseY > 100 && mouseY < 300) {
            PlayerEnum nextPlayer;
            GraphicsContext currentGC;
            GraphicsContext nextGC;
            String txt01;
            String txt02;
            Group currentGroup;
            Group nextGroup;
            Scene nextScene;

            if (currentPlayer == PlayerEnum.FIRST) {
                currentGC = canvases.get(4).getGraphicsContext2D();
                nextPlayer = PlayerEnum.SECOND;
                nextGC = canvases.get(5).getGraphicsContext2D();
                txt01 = "№1";
                txt02 = "№2";
                currentGroup = roots.get(4);
                nextGroup = roots.get(5);
                nextScene = scenes.get(5);

            } else {
                currentGC = canvases.get(5).getGraphicsContext2D();
                nextPlayer = PlayerEnum.FIRST;
                nextGC = canvases.get(4).getGraphicsContext2D();
                txt01 = "№2";
                txt02 = "№1";
                currentGroup = roots.get(5);
                nextGroup = roots.get(4);
                nextScene = scenes.get(4);
            }


            boolean bHit;
            int x = getAttackX(mouseX);
            int y = getAttackY(mouseY);
            if (!ps.isAttack(x, y, currentPlayer)) {
                ps.doAttack(x, y, currentPlayer);
                bHit = ps.isShipCell(x, y, nextPlayer);
                attack(currentGC, mouseX, mouseY, bHit, false);
                attack(nextGC, mouseX, mouseY, bHit, true);
                if (bHit) {
                    if (ps.isShipSunk(x, y, nextPlayer)) {
                        ps.doShipSunk(x, y, nextPlayer);
                        List<ShipCell> listOfShipCells = ps.getListOfShipCells(x, y, nextPlayer);
                        for (ShipCell sc : listOfShipCells) {
                            int x0 = sc.getX();
                            int y0 = sc.getY();
                            int mouseX0 = x0 * 20 + 450 + 5;
                            int mouseY0 = y0 * 20 + 100 + 5;
                            attack(currentGC, mouseX0, mouseY0, false, false);
                            attack(currentGC, mouseX0 + 20, mouseY0 + 20, false, false);
                            attack(currentGC, mouseX0 + 20, mouseY0, false, false);
                            attack(currentGC, mouseX0 + 20, mouseY0 - 20, false, false);
                            attack(currentGC, mouseX0, mouseY0 + 20, false, false);
                            attack(currentGC, mouseX0, mouseY0 - 20, false, false);
                            attack(currentGC, mouseX0 - 20, mouseY0 + 20, false, false);
                            attack(currentGC, mouseX0 - 20, mouseY0, false, false);
                            attack(currentGC, mouseX0 - 20, mouseY0 - 20, false, false);
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Корабль потоплен.");
                        alert.setHeaderText("Корабль потоплен.");
                        alert.showAndWait();

                    }
                }
                if (ps.isPlayerLoss(nextPlayer)) {
                    bGameOver = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Конец игры");
                    alert.setHeaderText("Игрок " + txt01 + " выиграл");
                    alert.showAndWait();
                }
                if (!bHit) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Промах");
                    alert.setHeaderText("Промах!");
                    alert.showAndWait();
                    currentGroup.setVisible(false);
                    alert.setTitle("Смена игрока");
                    alert.setHeaderText("Игроку " + txt02 + " приготовиться");
                    alert.showAndWait();
                    nextGroup.setVisible(true);
                    primaryStage.setScene(nextScene);
                    primaryStage.show();
                }
            }
        }

    }
}
