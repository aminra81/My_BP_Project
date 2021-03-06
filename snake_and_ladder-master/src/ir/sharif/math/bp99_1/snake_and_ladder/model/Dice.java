package ir.sharif.math.bp99_1.snake_and_ladder.model;
import java.util.Random;


public class Dice {

    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */
    static final int diceSize = 6;
    int[] chance = new int[diceSize + 1];
    private int lastNumber;
    public Dice() {
        for (int diceNumber = 1; diceNumber <= diceSize; diceNumber++)
            chance[diceNumber] = 1;
        lastNumber = -1;
    }
    public Dice(int []chance, int lastNumber) {
        this.chance = chance;
        this.lastNumber = lastNumber;
    }

    public int[] getChances() {
        return chance;
    }

    public int getLastNumber() {
        return lastNumber;
    }

    public void reset() {
        for (int diceNumber = 1; diceNumber <= diceSize; diceNumber++)
            chance[diceNumber] = 1;
        lastNumber = -1;
    }
    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public int roll() {
        //generating random number
        int sumOfChances = 0;
        for (int diceNumber = 1; diceNumber <= diceSize; diceNumber++)
            sumOfChances += chance[diceNumber];
        Random randomDiceGenerator = new Random();
        int curRandomDice = randomDiceGenerator.nextInt(sumOfChances);

        //finding the diceNumber
        int curSum = 0;
        int Answer = 0;
        for (int diceNumber = 1; diceNumber <= diceSize; diceNumber++){
            curSum += chance[diceNumber];
            if(curRandomDice < curSum && Answer == 0)
                Answer = diceNumber;
        }
        if(Answer == 0)
            Answer = diceSize;

        //consecutive equal dice numbers.
        if(lastNumber == Answer) {
            addChance(Answer, 1);
            lastNumber = -1;
        }
        else
            lastNumber = Answer;
        return Answer;
    }
    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negative(it can be zero)
     */
    public void addChance(int number, int chance) {
        this.chance[number] += chance;

        //chances must be non negative.
        if(this.chance[number] < 0)
            this.chance[number] = 0;

        //chances must be at most 8.
        if(this.chance[number] > 8)
            this.chance[number] = 8;
    }


    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public String getDetails() {
        String details = "";
        for (int i = 1; i <= diceSize; i++){
            details += "Dice " + i + " with " + chance[i] + " chance.\n";
        }
        return details;
    }
}
