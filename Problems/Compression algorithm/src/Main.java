import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        StringBuilder outputString = new StringBuilder("");
        char currentChar = inputString.charAt(0);
        int count = 0;
        for (int i = 0; i < inputString.length(); i++) {
            if (currentChar == inputString.charAt(i)) {
                count++;
            } else {
                outputString.append(currentChar).append(count);
                currentChar = inputString.charAt(i);
                count = 1;
            }
            if (i == inputString.length() - 1) {
                outputString.append(currentChar).append(count);
            }
        }
        System.out.println(outputString.toString());
    }
}