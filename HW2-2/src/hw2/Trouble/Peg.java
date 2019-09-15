package hw2.Trouble;

/*
    This class stores all the information related to a peg. For example the index at which peg is stored is saved
    in currentIndex attribute of this class
 */
public class Peg {
    String symbol;
    int currentIndex;
    int currentState;

    public static final int IN_HOME = 0;
    public static final int IN_CIRCLE = 0;
    public static final int IN_FINISH = 0;

    public Peg(String symbol) {
        this.symbol = symbol;
    }


    public void updateState(int state) {
        currentState = state;
    }

    public void setCurrentIndex(int index) {
        currentIndex = index;
    }

    public String getSymbol() {
        return symbol;
    }

}
