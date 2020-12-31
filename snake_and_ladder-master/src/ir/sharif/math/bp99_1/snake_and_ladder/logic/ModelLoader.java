package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ModelLoader {
    private final File boardFile, playersDirectory, archiveFile;


    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader() {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
    }


    /**
     * read file "boardFile" and craete a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     */
    public Board loadBord() {
        try {
            Scanner scanner = new Scanner(boardFile);
            // Code Here
            BoardBuilder myBoard = new BoardBuilder(scanner);
            return myBoard.build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber) {
        try {
            File playerFile = getPlayerFile(name);
            Scanner scanner = new Scanner(playerFile);


            // Code in this part

            return null;

        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player) {
        try {
            // add your codes in this part
            File file = getPlayerFile(player.getName());
            PrintStream printStream = new PrintStream(new FileOutputStream(file));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
    }

    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name) {

        return null;
    }

    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2) {
        try {
            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
