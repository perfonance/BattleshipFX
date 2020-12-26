package BattleshipFX.view;


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


public class Main extends Application {
    private double mouseX, mouseY;
    private boolean bSingleDeck = false;
    private boolean bDoubleDeck = false;
    private boolean bTripleDeck = false;
    private boolean bQuadroDeck = false;
    private boolean bHorizontal = true;
    PlayerService ps = new PlayerService();

    @Override
    public void start(Stage primaryStage) {
        ps.start();
        Group root1 = new Group();
        Group root2 = new Group();
        Group root3 = new Group();
        Group root4 = new Group();
        Group root5 = new Group();

        Scene scene1 = new Scene(root1);
        Scene scene2 = new Scene(root2);
        Scene scene3 = new Scene(root3);
        Scene scene4 = new Scene(root4);
        Scene scene5 = new Scene(root5);

        Canvas canvas1 = new Canvas(800, 600);
        Canvas canvas2 = new Canvas(800, 600);
        Canvas canvas3 = new Canvas(800, 600);
        Canvas canvas4 = new Canvas(800, 600);
        Canvas canvas5 = new Canvas(800, 600);

        root1.getChildren().add(canvas1);
        root2.getChildren().add(canvas2);
        root3.getChildren().add(canvas3);
        root4.getChildren().add(canvas4);
        root5.getChildren().add(canvas5);

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


        GraphicsContext gc2 = canvas2.getGraphicsContext2D();
        GraphicsContext gc3 = canvas3.getGraphicsContext2D();
        GraphicsContext gc4 = canvas4.getGraphicsContext2D();
        GraphicsContext gc5 = canvas5.getGraphicsContext2D();
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
            root1.getChildren().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Расстановка кораблей");
            alert.setHeaderText("Игроку №1 приготовиться");
            alert.showAndWait();
            primaryStage.setScene(scene2);
            primaryStage.show();
        };


        EventHandler<MouseEvent> eventButtonNextScene2 = e -> {
            root2.getChildren().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Расстановка кораблей");
            alert.setHeaderText("Игроку №2 приготовиться");
            alert.showAndWait();
            drawShapes(gc3);
            primaryStage.setScene(scene3);
            primaryStage.show();

        };


        EventHandler<MouseEvent> eventButtonNextScene3 = e -> {
            root3.getChildren().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Начало боя");
            alert.setHeaderText("Игроку №1 приготовиться");
            alert.showAndWait();
            drawShapes2(gc4);
            drawShips(gc4, 1);
            drawShapes2(gc5);
            drawShips(gc5, 2);
            primaryStage.setScene(scene4);
            primaryStage.show();

        };


        buttonRules.setOnMouseClicked(eventButtonRulesClicked);
        buttonStart.setOnMouseClicked(eventButtonStartClicked);
        buttonNextScene2.setOnMouseClicked(eventButtonNextScene2);
        buttonNextScene3.setOnMouseClicked(eventButtonNextScene3);
        root1.getChildren().add(labelEntryText);
        root1.getChildren().add(buttonRules);
        root1.getChildren().add(buttonStart);
        root2.getChildren().add(buttonNextScene2);
        root2.getChildren().add(labelCreateShip1);
        root3.getChildren().add(buttonNextScene3);
        root3.getChildren().add(labelCreateShip2);
        root4.getChildren().add(firstPlayer);
        root5.getChildren().add(secondPlayer);
        primaryStage.setScene(scene1);


        canvas2.setOnMouseClicked(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            int x = getShipX(mouseX);
            int y = getShipY(mouseY);
            if (bSingleDeck) {
                if (ps.canAddShip(x, y, 1, !bHorizontal, 1)) {
                    ps.addShip(x, y, 1, !bHorizontal, 1);
                    drawCell(gc2, mouseX, mouseY);
                }
            }
            bSingleDeck = singleDeck(gc2, mouseX, mouseY);
            if (bDoubleDeck) {
                if (ps.canAddShip(x, y, 2, !bHorizontal, 1)) {
                    ps.addShip(x, y, 2, !bHorizontal, 1);
                    if (bHorizontal) {
                        for (int i = 0; i < 2; i++) {
                            drawCell(gc2, mouseX, mouseY);
                            mouseX += 20;
                        }
                    } else {
                        for (int i = 0; i < 2; i++) {
                            drawCell(gc2, mouseX, mouseY);
                            mouseY += 20;
                        }
                    }
                }
            }
            bDoubleDeck = doubleDeck(gc2, mouseX, mouseY);
            if (bTripleDeck) {
                if (ps.canAddShip(x, y, 3, !bHorizontal, 1)) {
                    ps.addShip(x, y, 3, !bHorizontal, 1);
                    if (bHorizontal) {
                        for (int i = 0; i < 3; i++) {
                            drawCell(gc2, mouseX, mouseY);
                            mouseX += 20;
                        }
                    } else {
                        for (int i = 0; i < 3; i++) {
                            drawCell(gc2, mouseX, mouseY);
                            mouseY += 20;
                        }
                    }
                }
            }
            bTripleDeck = tripleDeck(gc2, mouseX, mouseY);
            if (bQuadroDeck) {
                if (ps.canAddShip(x, y, 4, !bHorizontal, 1)) {
                    ps.addShip(x, y, 4, !bHorizontal, 1);
                    if (bHorizontal) {
                        for (int i = 0; i < 4; i++) {
                            drawCell(gc2, mouseX, mouseY);
                            mouseX += 20;
                        }
                    } else {
                        for (int i = 0; i < 4; i++) {
                            drawCell(gc2, mouseX, mouseY);
                            mouseY += 20;
                        }
                    }
                }
            }
            bQuadroDeck = quadroDeck(gc2, mouseX, mouseY);
        });


        scene2.setOnKeyPressed((keyEvent -> {
            drawCell(gc2, 50, 100);
            if (keyEvent.getCode() == KeyCode.V) {
                bHorizontal = false;
                System.out.print("v");

            } else if (keyEvent.getCode() == KeyCode.H) {
                bHorizontal = true;
                System.out.println("h");
            }
        }));


        canvas3.setOnMouseClicked(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            int x = getShipX(mouseX);
            int y = getShipY(mouseY);
            if (bSingleDeck) {
                if (ps.canAddShip(x, y, 1, !bHorizontal, 2)) {
                    ps.addShip(x, y, 1, !bHorizontal, 2);
                    drawCell(gc3, mouseX, mouseY);
                }
            }
            bSingleDeck = singleDeck(gc3, mouseX, mouseY);
            if (bDoubleDeck) {
                if (ps.canAddShip(x, y, 2, !bHorizontal, 2)) {
                    ps.addShip(x, y, 2, !bHorizontal, 2);
                    if (bHorizontal) {
                        for (int i = 0; i < 2; i++) {
                            drawCell(gc3, mouseX, mouseY);
                            mouseX += 20;
                        }
                    } else {
                        for (int i = 0; i < 2; i++) {
                            drawCell(gc3, mouseX, mouseY);
                            mouseY += 20;
                        }
                    }
                }
            }
            bDoubleDeck = doubleDeck(gc3, mouseX, mouseY);
            if (bTripleDeck) {
                if (ps.canAddShip(x, y, 3, !bHorizontal, 2)) {
                    ps.addShip(x, y, 3, !bHorizontal, 2);
                    if (bHorizontal) {
                        for (int i = 0; i < 3; i++) {
                            drawCell(gc3, mouseX, mouseY);
                            mouseX += 20;
                        }
                    } else {
                        for (int i = 0; i < 3; i++) {
                            drawCell(gc3, mouseX, mouseY);
                            mouseY += 20;
                        }
                    }
                }
            }
            bTripleDeck = tripleDeck(gc3, mouseX, mouseY);
            if (bQuadroDeck) {
                if (ps.canAddShip(x, y, 4, !bHorizontal, 2)) {
                    ps.addShip(x, y, 4, !bHorizontal, 2);
                    if (bHorizontal) {
                        for (int i = 0; i < 4; i++) {
                            drawCell(gc3, mouseX, mouseY);
                            mouseX += 20;
                        }
                    } else {
                        for (int i = 0; i < 4; i++) {
                            drawCell(gc3, mouseX, mouseY);
                            mouseY += 20;
                        }
                    }
                }
            }
            bQuadroDeck = quadroDeck(gc3, mouseX, mouseY);
        });

        scene3.setOnKeyPressed((keyEvent -> {
            drawCell(gc3, 50, 100);
            if (keyEvent.getCode() == KeyCode.V) {
                bHorizontal = false;
                System.out.print("v");

            } else if (keyEvent.getCode() == KeyCode.H) {
                bHorizontal = true;
                System.out.println("h");
            }
        }));

        canvas4.setOnMouseClicked(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            if (mouseX > 450 && mouseX < 650 && mouseY > 100 && mouseY < 300) {
                boolean bHit = false;
                int x = getAttackX(mouseX);
                int y = getAttackY(mouseY);
                if (!ps.isAttack(x, y, 1)) {
                    ps.doAttack(x, y, 1);
                    bHit = ps.isShipCell(x, y, 2);
                    attack(gc4, mouseX, mouseY, bHit);
                    enemyAttack(gc5,mouseX,mouseY,bHit);
                    if (!bHit) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Промах");
                        alert.setHeaderText("Промах!");
                        alert.showAndWait();
                        root4.setVisible(false);
                        alert.setTitle("Смена игрока");
                        alert.setHeaderText("Игроку №2 приготовиться");
                        alert.showAndWait();
                        root5.setVisible(true);
                        primaryStage.setScene(scene5);
                        primaryStage.show();
                    }
                }
            }
        });


        canvas5.setOnMouseClicked(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            if (mouseX > 450 && mouseX < 650 && mouseY > 100 && mouseY < 300) {
                boolean bHit;
                int x = getAttackX(mouseX);
                int y = getAttackY(mouseY);
                if (!ps.isAttack(x, y, 2)) {
                    ps.doAttack(x, y, 2);
                    bHit = ps.isShipCell(x, y, 1);
                    attack(gc5, mouseX, mouseY, bHit);
                    enemyAttack(gc4, mouseX, mouseY, bHit);
                    if (!bHit) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Промах");
                        alert.setHeaderText("Промах!");
                        alert.showAndWait();
                        root5.setVisible(false);
                        alert.setTitle("Смена игрока");
                        alert.setHeaderText("Игроку №2 приготовиться");
                        alert.showAndWait();
                        root4.setVisible(true);
                        primaryStage.setScene(scene4);
                        primaryStage.show();
                    }
                }
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

    private void attack(GraphicsContext gc, double x, double y, boolean bHit) {
        if (x > 450 && x < 650 && y > 100 && y < 300) {
            int i = (int) (x - 50) / 20;
            int j = (int) (y - 100) / 20;
            int w = 20;
            if (!bHit) {
                int rect_x = i * w + 6;
                int rect_y = j * w + 6;
                gc.setFill(Color.GREEN);
                gc.fillRect(rect_x + 50, rect_y + 100, w - 12, w - 12);
            } else {
                int rect_x = i * w;
                int rect_y = j * w;
                gc.setFill(Color.RED);
                gc.fillRect(rect_x + 50, rect_y + 100, w, w);
            }
        }
    }

    private void enemyAttack(GraphicsContext gc, double x, double y, boolean bHit) {
        if (x > 450 && x < 650 && y > 100 && y < 300) {
            int i = (int) (x - 50) / 20;
            int j = (int) (y - 100) / 20;
            int w = 20;
            if (!bHit) {
                int rect_x = i * w + 6 - 400;
                int rect_y = j * w + 6;
                gc.setFill(Color.GREEN);
                gc.fillRect(rect_x + 50, rect_y + 100, w - 12, w - 12);
            } else {
                int rect_x = i * w-400;
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

    private void drawShips(GraphicsContext gc, int playerNum) {
        gc.setFill(Color.GREEN);
        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 9; y++) {
                if (ps.isShipCell(x, y, playerNum)) {
                    gc.fillRect(50 + x * 20, 100 + y * 20, 20, 20);
                }
            }
        }
       /* int nextPlayerNum = 1;
        if (playerNum == 1) {
            nextPlayerNum = 2;
        }
        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 9; y++) {
                if (ps.isAttack(x, y, nextPlayerNum)) {
                    if (ps.getAttackHit(x, y, nextPlayerNum)) {
                        int rect_x = x * 20;
                        int rect_y = y * 20;
                        gc.setFill(Color.RED);
                        gc.fillRect(rect_x + 50, rect_y + 100, 20, 20);
                    } else {
                        int rect_x = x * 20 + 6;
                        int rect_y = y * 20 + 6;
                        gc.setFill(Color.GREEN);
                        gc.fillRect(rect_x + 50, rect_y + 100, 20 - 12, 20 - 12);

                    }
                }
            }
        }*/
    }

    private int getAttackX(double mouseX) {
        return (int) (mouseX - 450) / 20;
    }

    private int getAttackY(double mouseY) {
        return (int) (mouseY - 100) / 20;
    }
}


/*for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (!game.getBlueGrid().isEmptyForInt(x, y)) {
                    gc.fillRect(50 + x * 20, 100 + y * 20, 20, 20);
                }
            }
        }*/