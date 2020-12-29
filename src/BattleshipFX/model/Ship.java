package BattleshipFX.model;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private List<ShipCell> ListOfShipCells = new ArrayList<ShipCell>();

    public Ship() {
    }

    public void addShipCellToList(ShipCell shipCell) {
        ListOfShipCells.add(shipCell);
    }

    public boolean isShipSunk() {
        boolean bSunk = true;
        for (ShipCell sc : ListOfShipCells) {
            if (!sc.isbHit()) {
                bSunk = false;
                break;
            }
        }
        return bSunk;
    }

    public List<ShipCell> getListOfShipCells() {
        return ListOfShipCells;
    }
}
