package BattleshipFX.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private ShipCell[][] shipsCells = new ShipCell[10][10];
    private AttackCell[][] attackCells = new AttackCell[10][10];
    private List<Ship> ListOfShips = new ArrayList<>();
    private Map<Integer, Integer> shipCount = new HashMap<>();

    public void addShipToList(Ship ship) {
        ListOfShips.add(ship);
    }

    public boolean loss() {
        boolean loss = true;
        for (Ship ship : ListOfShips) {
            if (!ship.isShipSunk()) {
                loss = false;
                break;
            }
        }
        return loss;
    }

    public void setAttack(int x, int y, boolean bHit) {
        if (x >= 0 && x <= shipsCells.length - 1 && y >= 0 && y <= shipsCells.length - 1) {
            AttackCell aCell = new AttackCell(x, y, bHit);
            attackCells[x][y] = aCell;
        }
    }

    public ShipCell getShipsCell(int x, int y) {
        if (x >= 0 && x <= shipsCells.length - 1 && y >= 0 && y <= shipsCells.length - 1) {
            return shipsCells[x][y];
        } else return null;
    }

    public void setShipsCell(int x, int y, Ship ship) {
        ShipCell sCell = new ShipCell(x, y, ship);
        ship.addShipCellToList(sCell);
        this.shipsCells[x][y] = sCell;
    }

    public AttackCell getAttackCell(int x, int y) {
        return attackCells[x][y];

    }

    public void clearAll() {
        for (int i = 1; i <= 4; i++)
            shipCount.put(i, 0);
        for (int x = 0; x <= shipsCells.length - 1; x++) {
            for (int y = 0; y <= shipsCells.length - 1; y++) {
                shipsCells[x][y] = null;
                attackCells[x][y] = null;
            }
        }
    }

    public void increaseShipsCount(int shipSize) {
        shipCount.put(shipSize, shipCount.get(shipSize) + 1);
    }

    public int getShipCount(int shipSize) {
        return shipCount.get(shipSize);
    }
}
