package Game2048;

import javafx.scene.paint.Color;

class Tile {
    int number;

    Tile() {
        this.number = 0;
    }

    Tile(int number) {
        this.number = number;
    }

    boolean isEmpty() {
        return number == 0;
    }

    Color getBackground() {
        switch (number) {
            case 2      :	return Color.rgb(238, 228, 218, 1.0); //238 228 218 1.0     0xeee4da
            case 4      : 	return Color.rgb(237, 224, 200, 1.0); //237 224 200 1.0     0xede0c8
            case 8      : 	return Color.rgb(242, 177, 121, 1.0); //242 177 121 1.0     0xf2b179
            case 16     : 	return Color.rgb(245, 149, 99 , 1.0); //245 149 99  1.0     0xf59563
            case 32     : 	return Color.rgb(246, 124, 95 , 1.0); //246 124 95  1.0     0xf67c5f
            case 64     :	return Color.rgb(246, 94 , 59 , 1.0); //246 94  59  1.0     0xf65e3b
            case 128    :	return Color.rgb(237, 207, 114, 1.0); //237 207 114 1.0     0xedcf72
            case 256    : 	return Color.rgb(237, 204, 97 , 1.0); //237 204 97  1.0     0xedcc61
            case 512    : 	return Color.rgb(237, 200, 80 , 1.0); //237 200 80  1.0     0xedc850
            case 1024   : 	return Color.rgb(237, 197, 63 , 1.0); //237 197 63  1.0     0xedc53f
            case 2048   :   return Color.rgb(237, 194, 46 , 1.0); //237 194 46  1.0     0xedc22e
            case 4096   :   return Color.rgb(237, 190, 46 , 1.0); //237 194 46  1.0     0xedc22e
            case 8192   :   return Color.rgb(237, 187, 46 , 1.0); //237 194 46  1.0     0xedc22e
            case 16384  :   return Color.rgb(237, 184, 46 , 1.0); //237 194 46  1.0     0xedc22e
            case 32768  :   return Color.rgb(237, 180, 46 , 1.0); //237 194 46  1.0     0xedc22e
            case 65536  :   return Color.rgb(237, 177, 46 , 1.0); //237 194 46  1.0     0xedc22e
            case 131072 :   return Color.rgb(237, 174, 43 , 1.0); //237 194 46  1.0     0xedc22e
        }
        return Color.rgb(205, 193, 180, 1.0); //0xcdc1b4
    }

    Color getForeground() {
        Color foreground;
        if(number < 16) {
            foreground = Color.rgb(119, 110, 101, 1.0);     //0x776e65
        } else {
            foreground = Color.rgb(249, 246, 242, 1.0);    //0xf9f6f2
        }
        return foreground;
    }
}