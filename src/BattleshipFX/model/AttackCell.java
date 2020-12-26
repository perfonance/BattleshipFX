package BattleshipFX.model;

public class AttackCell {
    private int x;
    private int y;
    private boolean bHit;

    public AttackCell(int x, int y, boolean bHit) {
        this.x = x;
        this.y = y;
        this.bHit = bHit;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isbHit() {
        return bHit;
    }

    public void setbHit(boolean bHit) {
        this.bHit = bHit;
    }
}
