package BattleshipFX.service;

import BattleshipFX.model.Player;
import BattleshipFX.model.PlayerEnum;
import BattleshipFX.model.Ship;
import BattleshipFX.model.ShipCell;

import java.util.List;


public class PlayerService {
    Player player1 = new Player();
    Player player2 = new Player();

    public void start() {
        player1.clearAll();
        player2.clearAll();
    }

    public Player getCurrentPlayer(PlayerEnum playerNum) {
        if (playerNum == PlayerEnum.FIRST) return player1;
        else return player2;
    }

    public Player getNextPlayer(PlayerEnum playerNum) {
        if (playerNum == PlayerEnum.FIRST) return player2;
        else return player1;
    }

    public void addShip(int x, int y, int shipSize, boolean bVertical, PlayerEnum playerNum) {
        Player currentPlayer = getCurrentPlayer(playerNum);
        currentPlayer.increaseShipsCount(shipSize);
        Ship ship = new Ship();
        if (bVertical) {
            for (int cY = y; cY <= y + shipSize - 1; cY++) {
                currentPlayer.setShipsCell(x, cY, ship);
            }
        } else {
            for (int cX = x; cX <= x + shipSize - 1; cX++) {
                currentPlayer.setShipsCell(cX, y, ship);
            }
        }
        currentPlayer.addShipToList(ship);
    }

    public void doAttack(int x, int y, PlayerEnum playerNum) {
        Player currentPlayer = getCurrentPlayer(playerNum);
        Player nextPlayer = getNextPlayer(playerNum);
        boolean bHit = nextPlayer.getShipsCell(x, y) != null;
        if (bHit) nextPlayer.getShipsCell(x, y).setbHit(bHit);
        currentPlayer.setAttack(x, y, bHit);
    }

    public boolean isAttack(int x, int y, PlayerEnum playerNum) {
        return getCurrentPlayer(playerNum).getAttackCell(x, y) != null;
    }

    public boolean isShipCell(int x, int y, PlayerEnum playerNum) {
        return getCurrentPlayer(playerNum).getShipsCell(x, y) != null;
    }

    public boolean canAddShip(int x, int y, int shipSize, boolean bVertical, PlayerEnum playerNum) {
        Player currentPlayer = getCurrentPlayer(playerNum);
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

    public boolean isPlayerLoss(PlayerEnum player) {
        return getCurrentPlayer(player).loss();
    }

    public boolean isShipSunk(int x, int y, PlayerEnum player) {
        return getCurrentPlayer(player).getShipsCell(x, y).getShip().isShipSunk();
    }

    public void doShipSunk(int x, int y, PlayerEnum player) {
        PlayerEnum nextPlayer;
        if (player == PlayerEnum.FIRST) {
            nextPlayer = PlayerEnum.SECOND;
        } else {
            nextPlayer = PlayerEnum.FIRST;
        }
        Ship ship = getCurrentPlayer(player).getShipsCell(x, y).getShip();
        List<ShipCell> shipCells = ship.getListOfShipCells();
        for (ShipCell sc : shipCells) {
            int x0 = sc.getX();
            int y0 = sc.getY();
            doAttack(x0 + 1, y0 + 1, nextPlayer);
            doAttack(x0 + 1, y0, nextPlayer);
            doAttack(x0 + 1, y0 - 1, nextPlayer);
            doAttack(x0, y0 + 1, nextPlayer);
            doAttack(x0, y0 - 1, nextPlayer);
            doAttack(x0 - 1, y0 + 1, nextPlayer);
            doAttack(x0 - 1, y0, nextPlayer);
            doAttack(x0 - 1, y0 - 1, nextPlayer);
        }
    }

    public List<ShipCell> getListOfShipCells(int x, int y, PlayerEnum player) {
        Ship ship = getCurrentPlayer(player).getShipsCell(x, y).getShip();
        return ship.getListOfShipCells();
    }
}

