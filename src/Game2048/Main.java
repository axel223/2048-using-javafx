package Game2048;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;


public class Main extends Application {

    private static final int TILE_SIZE = 107;
    private static final int TILES_MARGIN = 14;
    private static final int GAME_SIZE = 4*TILE_SIZE + 5*TILES_MARGIN;
    private static final String FONT_NAME = "Arial";
    private static final int GAME_X = 50;
    private static final int GAME_Y = 120;
    private static final int WIDTH = GAME_SIZE+100;
    private static final int HEIGHT = GAME_SIZE+165;
//    private final KeyCombination kb = new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) {
        myStage.setTitle("2048");

        FlowPane root = new FlowPane();

        Game game = new Game(WIDTH,HEIGHT);
        Scene myScene = new Scene(root, game.getWidth(), game.getHeight());

        myStage.setScene(myScene);
        myStage.setResizable(false);

        myScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                game.resetGame();
            }
            if(event.getCode() == KeyCode.TAB && game.win){
                game.win = false;
                game.continued();
            }
//            if (kb.match(event)) {          //CheAt COde
//                game.lose = true;
//                game.win = true;
//                game.cheat();
//                game.score = Integer.MAX_VALUE;
//            }

            if (game.cannotMove() || (!game.win && game.cannotMove())) {
                game.lose = true;
            }

            if (!game.win && !game.lose) {
                switch (event.getCode()) {
                    case LEFT:
                        game.left();
                        break;
                    case RIGHT:
                        game.right();
                        break;
                    case DOWN:
                        game.down();
                        break;
                    case UP:
                        game.up();
                        break;
                }
            }
            game.relocate(WIDTH, HEIGHT);
        });

        root.getChildren().add(game);
        myStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long h) {
                GraphicsContext gc = game.getGraphicsContext2D();

                gc.setFill(Color.TAN);
                gc.fillRect(0,0,game.getWidth(),game.getHeight());

                gc.setFont(Font.loadFont("file:resources/font.ttf", 75));
                gc.setFill(Color.rgb(119, 110, 101, 1.0));
                gc.setTextAlign(TextAlignment.CENTER);
                gc.fillText("2048",120,80);

                gc.setFill(Color.rgb(187, 173, 160));
                gc.fillRoundRect(390,15,180, 80, 14, 14);

                gc.setFont(Font.loadFont("file:resources/font.ttf", 37));
                gc.setFill(Color.rgb(119, 110, 101, 1.0));
                gc.setTextAlign(TextAlignment.CENTER);
                gc.fillText("Score",480,55);


                gc.setFont(Font.loadFont("file:resources/font.ttf", 25));
                gc.setFill(Color.rgb(119, 110, 101, 1.0));
                gc.fillText(""+game.score, 480, 85);

                gc.setFill(Color.rgb(187, 173, 160));
                gc.fillRoundRect(GAME_X,GAME_Y, GAME_SIZE, GAME_SIZE,10,10);

                for(int y = 0; y < 4; y++) {
                    for (int x = 0; x < 4; x++) {
                        Tile tile = game.getTiles()[x + y * 4];
                        int value = tile.number;
                        int xOffset = Coordinates(x) + GAME_X;
                        int yOffset = Coordinates(y) + GAME_Y;

                        gc.setFill(tile.getBackground());
                        gc.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 5, 5);
                        gc.setFill(tile.getForeground());

                        final int size = value < 100 ? 50 : value < 1000 ? 40 : value < 10000 ? 30 : 25;
                        gc.setFont(Font.loadFont("file:resources/font.ttf", size));

                        if (value != 0) {
                            gc.setTextAlign(TextAlignment.CENTER);
                            gc.fillText("" + value, xOffset + (TILE_SIZE / 2.0), yOffset + (TILE_SIZE / 2.0) + (size / 3.0));
                        }
                    }
                }
                if(game.win || game.lose) {
                    gc.setFill(Color.rgb(187, 173, 160,0.7));
                    gc.fillRect(GAME_X, GAME_Y, GAME_SIZE, GAME_SIZE);
                    gc.setFill(Color.rgb(119, 110, 101));
                    gc.setFont(Font.font(FONT_NAME, FontWeight.EXTRA_LIGHT, 40));

                    if(game.win){
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.fillText("YOU WON",GAME_X+(GAME_SIZE/2.0),GAME_Y+(GAME_SIZE/2.0));
                        gc.setFont(Font.font(FONT_NAME, FontWeight.MEDIUM, 16));
                        gc.setFill(Color.rgb(249, 246, 242, 1.0));
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.fillText("Press ENTER to play again \n or Press TAB to continue",GAME_X+(GAME_SIZE/2.0),GAME_Y+(GAME_SIZE/2.0)+30);
                    }

                    if(game.lose) {
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.fillText("YOU LOSE",GAME_X+(GAME_SIZE/2.0),GAME_Y+(GAME_SIZE/2.0));
                        gc.setFont(Font.font(FONT_NAME, FontWeight.MEDIUM, 16));
                        gc.setFill(Color.rgb(249, 246, 242, 1.0));
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.fillText("Press ENTER to play again",GAME_X+(GAME_SIZE/2.0),GAME_Y+(GAME_SIZE/2.0)+30);
                    }
                }
            }
        }.start();
    }

    private static int Coordinates(int arg) {
        return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
    }

}
