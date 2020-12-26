package BattleshipFX.service;

import BattleshipFX.model.Player;


public class PlayerService {
    Player player1 = new Player();
    Player player2 = new Player();
    Player currentPlayer = new Player();
    Player nextPlayer = new Player();

    public void start() {
        player1.clearAll();
        player2.clearAll();
    }

    public void setCurrentPlayer(int playerNum) {
        currentPlayer = player1;
        nextPlayer = player2;
        if (playerNum == 2) {
            currentPlayer = player2;
            nextPlayer = player1;
        }
    }

    public void addShip(int x, int y, int shipSize, boolean bVertical, int playerNum) {
        setCurrentPlayer(playerNum);
        currentPlayer.increaseShipsCount(shipSize);
        if (bVertical) {
            for (int cY = y; cY <= y + shipSize - 1; cY++) {
                currentPlayer.setShipsCell(x, cY);
            }
        } else {
            for (int cX = x; cX <= x + shipSize - 1; cX++) {
                currentPlayer.setShipsCell(cX, y);
            }
        }
    }

    public void doAttack(int x, int y, int playerNum) {
        setCurrentPlayer(playerNum);
        boolean bHit = nextPlayer.getShipsCell(x, y) != null;
        currentPlayer.setAttack(x, y, bHit);
        nextPlayer.setEnemyAttack(x, y, bHit);
    }

    public boolean isAttack(int x, int y, int playerNum) {
        setCurrentPlayer(playerNum);
        return (currentPlayer.getAttackCell(x, y) != null);
    }

    public boolean getAttackHit(int x, int y, int playerNum) {
        setCurrentPlayer(playerNum);
        return currentPlayer.getAttackCell(x, y).isbHit();
    }

    public boolean isShipCell(int x, int y, int playerNum) {
        setCurrentPlayer(playerNum);
        return (currentPlayer.getShipsCell(x, y) != null);
    }

    public boolean canAddShip(int x, int y, int shipSize, boolean bVertical, int playerNum) {
        currentPlayer = player1;
        if (playerNum == 2) {
            currentPlayer = player2;
        }
        if (currentPlayer.getShipCount(shipSize) > 4 - shipSize) return false;
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            boolean isShip = false;
            if (bVertical) {
                if (y + shipSize - 1 <= 9) {
                    for (int cY = y - 1; cY <= y + shipSize; cY++) {
                        if (currentPlayer.getShipsCell(x, cY) != null
                                || currentPlayer.getShipsCell(x - 1, cY) != null
                                || currentPlayer.getShipsCell(x + 1, cY) != null) {
                            isShip = true;
                        }
                    }
                    return !isShip;
                }
            } else {
                if (x + shipSize - 1 <= 9) {
                    for (int cX = x - 1; cX <= x + shipSize; cX++) {
                        if (currentPlayer.getShipsCell(cX, y) != null
                                || currentPlayer.getShipsCell(cX, y - 1) != null
                                || currentPlayer.getShipsCell(cX, y + 1) != null) {
                            isShip = true;
                        }
                    }
                    return !isShip;
                }
            }
        }
        return false;
    }
}

