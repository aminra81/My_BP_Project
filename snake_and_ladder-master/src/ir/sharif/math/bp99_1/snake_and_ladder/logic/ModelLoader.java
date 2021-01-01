package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.Scanner;

public class ModelLoader {
    private final File boardFile, playersDirectory, archiveFile;
    private static int lastID = 0;

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
     * read file "boardFile" and create a Board
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
            if (playerFile == null) {
                playerFile = new File(playersDirectory, name + ".txt");
                playerFile.createNewFile();
                PrintStream printStream = new PrintStream(new FileOutputStream(playerFile));
                printStream.println("ID: " + (++lastID));
                printStream.println("Name: " + name);
                printStream.println("AllScore: " + 0);
            }
            Scanner scanner = new Scanner(playerFile);
            scanner.next();
            int id = scanner.nextInt();
            return new Player(name, 0, id, playerNumber);

        } catch (IllegalArgumentException | IOException e) {
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
            Scanner scanner = new Scanner(file);
            scanner.next();
            int id = scanner.nextInt();
            scanner.next();
            String name = scanner.next();
            scanner.next();
            int allScore = scanner.nextInt();
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.println("ID: " + id);
            printStream.println("Name: " + name);
            printStream.println("AllScore: " + (allScore + player.getScore()));

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
        name += ".txt";
        File tempFile = new File(playersDirectory, name);
        boolean exists = tempFile.exists();
        if (exists)
            return tempFile;
        else
            return null;
    }
    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2) {
        try {
            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));
            printStream.println(player1.getName() + " vs " + player2.getName() + " : ");
            printStream.println("   " + player1.getName() + " scored " + player1.getScore() + " scores.");
            printStream.println("   " + player2.getName() + " scored " + player2.getScore() + " scores.");
            printStream.print("   " + "Game Verdict : ");
            if (player1.getScore() == player2.getScore())
                printStream.println("Draw.");
            else if (player1.getScore() > player2.getScore())
                printStream.println(player1.getName() + " Won.");
            else
                printStream.println(player2.getName() + " Won.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
