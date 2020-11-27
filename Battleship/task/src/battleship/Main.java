package battleship;

import java.util.Scanner;

//inputMatrix[x][y]
// - 0 - empty
// - XX - battleship (Xgamer Xtypebattleship)
// - 2 - explode
// - 3 - miss

//Type of BattleShip
// - 0 - Aircraft Carrier (5 cells)
// - 1 - Battleship (4 cells)
// - 2 - Submarine (3 cells)
// - 3 - Cruiser (3 cells)
// - 4 - Destroyer (2 cells)

//Type of renderMatrix
// - false - fog of war off
// - true - for of war on

class Ship {

    int indx;
    int length;
    String name;
    int heal;

    public Ship(int indx, int length, String name) {
        this.indx = indx;
        this.length = length;
        this.name = name;
        this.heal = length;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public int getIndx() {
        return indx;
    }

    // Если после выстрела осталось 0 жизни, то возвращаем false
    public boolean shotHeal() {
        this.heal--;
        return this.heal == 0;
    }

    public int getHeal() {
        return heal;
    }
}


class Other {

    static int returnCharCord(String inputChar) {
        return ((inputChar.charAt(0) - 65));
    }

}


class Matrix {

    // Fields

    protected int[][] matrix;
    protected int x;
    protected int y;

    // Constructor

    public Matrix(int x, int y) {
        this.x = x;
        this.y = y;
        this.matrix = initMatrix();
    }

    // Getters & setters

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Other methods

    private int[][] initMatrix() {
        int[][] outputMatrix = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                outputMatrix[j][i] = 0;
            }
        }


        return outputMatrix;
    }

    public void inputCord(Ship battleShip) {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.next();
        String b = scanner.next();
        int ay = Other.returnCharCord(a);
        int ax = Integer.parseInt(a.substring(1)) - 1;
        int by = Other.returnCharCord(b);
        int bx = Integer.parseInt(b.substring(1)) - 1;
        switch (checkInputCoord(ax, ay, bx, by, battleShip, matrix)) {
            case 0: //its allright
                recordCord(matrix, ax, ay, bx, by, battleShip);
                break;
            case 1: //error corrd (wrong length)
                System.out.println("\nError! Wrong length of the " + battleShip.name + "! Try again:\n");
                inputCord(battleShip);
                break;
            case 2: //error coord (near coord battleships)
                System.out.println("\nError! You placed it too close to another one. Try again:\n");
                inputCord(battleShip);
                break;
            case 3: //error coord (same coord battleships)
            default:
                System.out.println("\nError! Wrong ship location! Try again:\n");
                inputCord(battleShip);
                break;
        }
        this.matrix = matrix;
    }

    private static int[][] recordCord(int[][] inputMatrix, int x1, int y1, int x2, int y2, Ship battleship) {
        if (x1 == x2) {
            if (y2 > y1) {
                for (int i = y1; i <= y2; i++) {
                    inputMatrix[x1][i] = battleship.getIndx();
                }
            } else {
                for (int i = y1; i >= y2; i--) {
                    inputMatrix[x1][i] = battleship.getIndx();
                }
            }
        } else {
            if (x2 > x1) {
                for (int i = x1; i <= x2; i++) {
                    inputMatrix[i][y1] = battleship.getIndx();
                }
            } else {
                for (int i = x1; i >= x2; i--) {
                    inputMatrix[i][y1] = battleship.getIndx();
                }
            }
        }
        return inputMatrix;
    }

    private static int checkInputCoord(int x1, int y1, int x2, int y2, Ship battleShip, int[][] inputMatrix) {
        if (Math.abs(x2 - x1) == 0 && Math.abs(y2 - y1) != 0) {
            if (battleShip.length == Math.abs(y2 - y1) + 1) {
                if (y2 > y1) {
                    for (int i = y1; i <= y2; i++) {
                        if(!checkNearCord(prepareForCheckNearCord(inputMatrix, x1, i))) {
                            return 2;
                        }
                    }
                } else {
                    for (int i = y2; i <= y1; i++) {
                        if(!checkNearCord(prepareForCheckNearCord(inputMatrix, x1, i))) {
                            return 2;
                        }
                    }
                }
                return 0;
            } else {
                return 1;
            }
        }
        if (Math.abs(y2 - y1) == 0 && Math.abs(x2 - x1) != 0) {
            if (battleShip.length == Math.abs(x2 - x1) + 1) {
                if (x2 > x1) {
                    for (int i = x1; i <= x2; i++) {
                        if(!checkNearCord(prepareForCheckNearCord(inputMatrix, i, y1))) {
                            return 2;
                        }
                    }
                } else {
                    for (int i = x2; i <= x1; i++) {
                        if(!checkNearCord(prepareForCheckNearCord(inputMatrix, i, y1))) {
                            return 2;
                        }
                    }
                }
                return 0;
            } else {
                return 1;
            }
        }
        return -1;
    }

    private static int[] prepareForCheckNearCord(int[][] inputMatrix, int x, int y) {
        int[] outputInt = new int[9];
        int count = 0;
        for (int anotherX = -1; anotherX < 2; anotherX++) {
            for (int anotherY = -1; anotherY < 2; anotherY++) {
                try {
                    outputInt[count] = inputMatrix[x + anotherX][y + anotherY];
                } catch (Exception ex) {
                    outputInt[count] = 0;
                }
                count++;
            }
        }
        return outputInt;
    }

    private static boolean checkNearCord(int[] inputmatrix) {
        for (int i = 0; i < inputmatrix.length; i++) {
            if (inputmatrix[i] != 0) {
                return false;
            }
        }
        return true;
    }
}

class Game {

    // Fields

    private int heal = 17;
    private int x = 10;
    private int y = 10;

    private final Matrix matrixOfGamer1 = new Matrix(x,y);
    private final Matrix matrixOfGamer2 = new Matrix(x,y);

    private final Gamer gamer1 = new Gamer(1, heal);
    private final Gamer gamer2 = new Gamer(2, heal);

    Scanner scanner = new Scanner(System.in);

    Ship aircraftCarrier1 = new Ship(11, 5, "Aircraft Carrier");
    Ship battleship1 = new Ship(12, 4, "Battleship");
    Ship submarine1 = new Ship(13, 3, "Submarine");
    Ship cruiser1 = new Ship(14, 3, "Cruiser");
    Ship destroyer1 = new Ship(15, 2, "Destroyer");

    Ship aircraftCarrier2 = new Ship(21, 5, "Aircraft Carrier");
    Ship battleship2 = new Ship(22, 4, "Battleship");
    Ship submarine2 = new Ship(23, 3, "Submarine");
    Ship cruiser2 = new Ship(24, 3, "Cruiser");
    Ship destroyer2 = new Ship(25, 2, "Destroyer");

    // Constructor

    public Game(int heal, int x, int y) {
        this.heal = heal;
        this.x = x;
        this.y = y;
    }

    // Methods to main

    public Ship returnShipFromIndex(int indx) {
        switch (indx) {
            case 12:
                return battleship1;
            case 13:
                return submarine1;
            case 14:
                return cruiser1;
            case 15:
                return destroyer1;
            case 21:
                return aircraftCarrier2;
            case 22:
                return battleship2;
            case 23:
                return submarine2;
            case 24:
                return cruiser2;
            case 25:
                return destroyer2;
            default:
                return aircraftCarrier1;
        }
    }

    public void run() {
        initMatrixFromUsers();

        renderMatrixToGamer(gamer1);
        int numberGamer = 1;
        do {
            System.out.printf("\nPlayer %d, it's your turn:\n", numberGamer);
            if (numberGamer == 1) {
                shot(matrixOfGamer2.matrix, gamer1.getIndx());
                renderMatrixToGamer(gamer2);
                numberGamer = 2;
            } else {
                shot(matrixOfGamer1.matrix, gamer2.getIndx());
                renderMatrixToGamer(gamer1);
                numberGamer = 1;
            }
            
        } while (true);
    }

    // Other methods

    private void passToMove() {
        System.out.print("\nPress Enter and pass the move to another player\n...");
        scanner.nextLine();
    }

    private void initMatrixFromUsers() {

        System.out.print("\nPlayer 1, place your ships on the game field\n");
        renderMatrix(matrixOfGamer1.matrix, false);
        inputUserMatrix(gamer1, aircraftCarrier1);
        renderMatrix(matrixOfGamer1.matrix, false);
        inputUserMatrix(gamer1, battleship1);
        renderMatrix(matrixOfGamer1.matrix, false);
        inputUserMatrix(gamer1, submarine1);
        renderMatrix(matrixOfGamer1.matrix, false);
        inputUserMatrix(gamer1, cruiser1);
        renderMatrix(matrixOfGamer1.matrix, false);
        inputUserMatrix(gamer1, destroyer1);
        renderMatrix(matrixOfGamer1.matrix, false);
        passToMove();

        System.out.print("Player 2, place your ships on the game field\n");
        renderMatrix(matrixOfGamer2.matrix, false);
        inputUserMatrix(gamer2, aircraftCarrier2);
        renderMatrix(matrixOfGamer2.matrix, false);
        inputUserMatrix(gamer2, battleship2);
        renderMatrix(matrixOfGamer2.matrix, false);
        inputUserMatrix(gamer2, submarine2);
        renderMatrix(matrixOfGamer2.matrix, false);
        inputUserMatrix(gamer2, cruiser2);
        renderMatrix(matrixOfGamer2.matrix, false);
        inputUserMatrix(gamer2, destroyer2);
        renderMatrix(matrixOfGamer2.matrix, false);
        passToMove();
    }

    private void renderMatrixToGamer(Gamer gamer) {
        switch (gamer.getIndx()) {
            case 1:
                renderMatrix(matrixOfGamer2.matrix, true);
                System.out.print("---------------------");
                renderMatrix(matrixOfGamer1.matrix, false);
                break;
            case 2:
                renderMatrix(matrixOfGamer1.matrix, true);
                System.out.print("---------------------");
                renderMatrix(matrixOfGamer2.matrix, false);
                break;
            default:
                break;
        }
    }

    public void inputUserMatrix(Gamer gamer, Ship battleShip) {
        System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", battleShip.name, battleShip.length);
        switch (gamer.getIndx()) {
            case 1:
                matrixOfGamer1.inputCord(battleShip);
                break;
            case 2:
                matrixOfGamer2.inputCord(battleShip);
                break;
            default:
                break;
        }
    }

    public char returnCharBattle(int inputInteger, boolean fogOfWar) {
        switch (inputInteger) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                if (!fogOfWar) {
                    return 'O';
                } else {
                    return '~';
                }
            case 3:
                return 'X';
            case 4:
                return 'M';
            default:
                return '~';
        }
    }

    public void renderMatrix(int[][] inputMatrix, boolean fogOfWar) {
        System.out.print("\n");
        for (int i = 0; i <= y; i++) {
            for (int j = 0; j <= x; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("  ");
                } else {
                    if (i == 0) {
                        System.out.print(j + " ");
                    } else {
                        if (j == 0) {
                            System.out.print((char) (64 + i) + " ");
                        } else {
                            System.out.print(returnCharBattle(inputMatrix[j - 1][i - 1], fogOfWar) + " ");
                        }
                    }
                }
            }
            System.out.print("\n");
        }
    }

    public int[][] shot(int[][] inputMatrix, int gamer) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        int y1 = Other.returnCharCord(input);
        int x1 = Integer.parseInt(input.substring(1)) - 1;

        if (x1 < x && x1 >= 0 && y1 < y && y1 >= 0) {
            switch (inputMatrix[x1][y1]) {
                case 0:
                    System.out.print("\nYou missed!");
                    inputMatrix[x1][y1] = 4;
                    passToMove();
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:


                    if (gamer == 1) {
                        gamer2.setHeal(gamer2.getHeal() - 1);
                        if (gamer2.getHeal() == 0) {
                            System.out.print("\nYou sank the last ship. You won. Congratulations!");
                            System.exit(0);
                        }
                    } else {
                        gamer1.setHeal(gamer1.getHeal() - 1);
                        if (gamer1.getHeal() == 0) {
                            System.out.print("\nYou sank the last ship. You won. Congratulations!");
                            System.exit(0);
                        }
                    }

                    if (returnShipFromIndex(inputMatrix[x1][y1]).shotHeal()) {
                        System.out.print("\nYou sank a ship!");
                    } else {
                        System.out.print("\nYou hit a ship!");
                    }

                    inputMatrix[x1][y1] = 3;

                    passToMove();
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            shot(inputMatrix, gamer);
        }
        return inputMatrix;
    }

}

class Gamer {

    private int indx;

    private int heal;

    public Gamer(int indx, int heal) {
        this.indx = indx;
        this.heal = heal;
    }

    public int getIndx() {
        return this.indx;
    }

    public void setIndx(int indx) {
        this.indx = indx;
    }

    public int getHeal() {
        return this.heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

}

public class Main {

    static int x = 10;
    static int y = 10;
    static int heal = 17;

    public static void main(String[] args) {
        Game game = new Game(heal,x,y);
        game.run();
    }
}
