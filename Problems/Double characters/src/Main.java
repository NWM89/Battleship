import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String outputString = scanner.nextLine();
        System.out.print("\n");
        for (int i = 0; i < outputString.length(); i++) {
            System.out.print(outputString.charAt(i) + "" + outputString.charAt(i));
        }
    }
}