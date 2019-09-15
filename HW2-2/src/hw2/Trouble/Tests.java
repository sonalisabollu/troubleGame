package hw2.Trouble;

public class Tests {

    void testcase1() {
        System.out.println("Testcase1: Testing BoardGraphics Insertion in Circular Cell List");
        BoardGraphics gameBoard = new BoardGraphics();
        gameBoard.insertInCircularCell(0, "XX");
        if(gameBoard.grid.get(9).charAt(33) == 'X' && gameBoard.grid.get(9).charAt(34) == 'X')
            System.out.println("Testcase1: Passed");
        else
            System.out.println("Testcase1: Failed");
    }

    void testcase2() {
        System.out.println("Testcase2: Testing BoardGraphics Insertion in Home Cell List");
        BoardGraphics gameBoard = new BoardGraphics();
        gameBoard.insertInHomeCell(0,0, "XX");
        if(gameBoard.grid.get(4).charAt(20) == 'X' && gameBoard.grid.get(4).charAt(21) == 'X')
            System.out.println("Testcase2: Passed");
        else
            System.out.println("Testcase2: Failed");
    }

    void testcase3() {
        System.out.println("Testcase3: Testing BoardGraphics Insertion in Finish Cell List");
        BoardGraphics gameBoard = new BoardGraphics();
        gameBoard.insertInFinishCell(0,0, "XX");
        if(gameBoard.grid.get(13).charAt(29) == 'X' && gameBoard.grid.get(13).charAt(29) == 'X')
            System.out.println("Testcase3: Passed");
        else
            System.out.println("Testcase3: Failed");
    }

    void testcase4() {
        System.out.println("Testcase4: Testing applyMove method in Trouble Class with diceValue = 6 and pegNumber = 1");
        Trouble game = new Trouble();
        game.applyMove(1, 6);
        if(game.gameBoardGraphics.grid.get(9).charAt(33) == 'R' && game.gameBoardGraphics.grid.get(9).charAt(34) == '1')
            System.out.println("Testcase4: Passed");
        else
            System.out.println("Testcase4: Failed");
    }

    public static void main(String[] args) {
        Tests testSuite = new Tests();
        testSuite.testcase1();
        testSuite.testcase2();
        testSuite.testcase3();
        testSuite.testcase4();
    }
}
