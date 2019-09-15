package hw2.Trouble;

public class Dice {

    public int roll() {
        //(Math.random() * ((max - min) + 1)) + min
        return (int)((Math.random() * ((6 - 1) + 1)) + 1);
    }
}
