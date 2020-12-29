package BattleshipFX.model;

public class ShipCell {
    private int x;
    private int y;
    private boolean bHit = false;

    private Ship ship;

    public ShipCell(int x, int y, Ship ship) {
        this.x = x;
        this.y = y;
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setbHit(boolean bHit) {
        this.bHit = bHit;
    }

    public boolean isbHit() {
        return bHit;
    }
}
