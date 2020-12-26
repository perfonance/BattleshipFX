package BattleshipFX.model;

public class Player {
    private ShipCell[][] shipsCells = new ShipCell[10][10];
    private AttackCell[][] attackCells = new AttackCell[10][10];
    private AttackCell[][] enemyAttackCells = new AttackCell[10][10];
    private int num;
    private int singleDeskCount;
    private int doubleDeskCount;
    private int tripleDeskCount;
    private int quadroDeskCount;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setAttack(int x, int y, boolean bHit) {
        AttackCell aCell = new AttackCell(x, y, bHit);
        attackCells[x][y] = aCell;
    }

    public void setEnemyAttack(int x, int y, boolean bHit) {
        AttackCell aCell = new AttackCell(x, y, bHit);
        enemyAttackCells[x][y] = aCell;
    }

    public ShipCell getShipsCell(int x, int y) {
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            return shipsCells[x][y];
        } else return null;
    }

    public void setShipsCell(int x, int y) {
        ShipCell sCell = new ShipCell(x, y);
        this.shipsCells[x][y] = sCell;
    }

    public AttackCell getAttackCell(int x, int y) {
        return attackCells[x][y];

    }

    public AttackCell getEnemyAttackCell(int x, int y) {
        return enemyAttackCells[x][y];
    }

    public void clearAll() {
        singleDeskCount = 0;
        doubleDeskCount = 0;
        tripleDeskCount = 0;
        quadroDeskCount = 0;
        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 9; y++) {
                shipsCells[x][y] = null;
                attackCells[x][y] = null;
                enemyAttackCells[x][y] = null;
            }
        }
    }

    public void increaseShipsCount(int shipSize) {
        if (shipSize == 1) {
            singleDeskCount += 1;
        } else if (shipSize == 2) {
            doubleDeskCount += 1;
        } else if (shipSize == 3) {
            tripleDeskCount += 1;
        } else {
            quadroDeskCount += 1;
        }
    }

    public int getShipCount(int shipSize) {
        if (shipSize == 1) {
            return singleDeskCount;
        } else if (shipSize == 2) {
            return doubleDeskCount;
        } else if (shipSize == 3) {
            return tripleDeskCount;
        } else {
            return quadroDeskCount;
        }
    }
}
