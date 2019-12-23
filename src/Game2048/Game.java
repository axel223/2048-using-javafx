package Game2048;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Game extends javafx.scene.canvas.Canvas {

    private Tile[] tiles;
    boolean win = false;
    boolean lose = false;
    private boolean contd = false;
    int score = 0;

    Tile[] getTiles() {
        return tiles;
    }

    Game(double width, double height) {
        super(width,height);
        setFocused(true);
        resetGame();
    }
    void resetGame() {
        score = 0;
        win = false;
        lose = false;
        contd = false;
        tiles = new Tile[4 * 4];
        for (int cell = 0; cell < tiles.length; cell++) {
            tiles[cell] = new Tile();
        }
        addCell();
        addCell();
    }

    void continued(){
        contd = true;
    }

    private void addCell() {
        List<Tile> list = availableSpace();
        if(!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Tile emptyTile = list.get(index);
            emptyTile.number = Math.random() < 0.9 ? 2 : 4;
        }

    }

    private List<Tile> availableSpace() {
        List<Tile> list = new ArrayList<>(16);
        for(Tile c : tiles)
            if(c.isEmpty())
                list.add(c);
        return list;
    }

    private boolean isFull() {
        return availableSpace().size() == 0;
    }

    private Tile cellAt(int x, int y) {
        return tiles[x + y * 4];
    }

    boolean cannotMove() {
        if(!isFull()) return false;
        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if ((x < 3 && cellAt(x, y).number == cellAt(x + 1, y).number) || (y < 3) && cellAt(x, y).number == cellAt(x, y + 1).number) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean compare(Tile[] line1, Tile[] line2) {
        if(line1 == line2)
            return true;

        if (line1.length != line2.length)
            return false;

        for(int i = 0; i < line1.length; i++) {
            if(line1[i].number != line2[i].number) {
                return false;
            }
        }
        return true;
    }

    private Tile[] rotate(int angle) {
        Tile[] tiles = new Tile[4 * 4];
        int offsetX = 3;
        int offsetY = 3;
        if(angle == 90) {
            offsetY = 0;
        } else if(angle == 270) {
            offsetX = 0;
        }

        double rad = Math.toRadians(angle);
        int cos = (int) Math.cos(rad);
        int sin = (int) Math.sin(rad);
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                int newX = (x*cos) - (y*sin) + offsetX;
                int newY = (x*sin) + (y*cos) + offsetY;
                tiles[(newX) + (newY) * 4] = cellAt(x, y);
            }
        }
        return tiles;
    }

    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<>();
        for(int i = 0; i < 4; i++) {
            if(!oldLine[i].isEmpty()){
                list.addLast(oldLine[i]);
            }
        }

        if(list.size() == 0) {
            return oldLine;
        } else {
            Tile[] newLine = new Tile[4];
            while (list.size() != 4) {
                list.add(new Tile());
            }
            for(int j = 0; j < 4; j++) {
                newLine[j] = list.removeFirst();
            }
            return newLine;
        }
    }

    private Tile[] mergeLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<>();
        for(int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
            int num = oldLine[i].number;
            if (i < 3 && oldLine[i].number == oldLine[i+1].number) {
                num *= 2;
                score += num;
                if ( num == 2048 && !contd) {
                    win = true;
                }
                i++;
            }
            list.add(new Tile(num));
        }

        if(list.size() == 0) {
            return oldLine;
        } else {
            while (list.size() != 4) {
                list.add(new Tile());
            }
            return list.toArray(new Tile[4]);
        }
    }

    private Tile[] getLine(int index) {
        Tile[] result = new Tile[4];
        for(int i = 0; i < 4; i++) {
            result[i] = cellAt(i, index);
        }
        return result;
    }

    private void setLine(int index, Tile[] re) {
        System.arraycopy(re, 0, tiles, index * 4, 4);
    }

    private void Move(){
        boolean needAddCell = false;
        for(int i = 0; i < 4; i++) {
            Tile[] line = getLine(i);
            Tile[] merged = mergeLine(moveLine(line));
            setLine(i, merged);
            if( !needAddCell && !compare(line, merged)) {
                needAddCell = true;
            }
        }
        if(needAddCell) {
            addCell();
        }
    }

    void left() {
        Move();
    }

    void right() {
        tiles = rotate(180);
        Move();
        tiles = rotate(180);
    }

    void up() {
        tiles = rotate(270);
        Move();
        tiles = rotate(90);
    }

    void down() {
        tiles = rotate(90);
        Move();
        tiles = rotate(270);
    }
}