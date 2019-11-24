package budget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {

    private static double balance = 0;
    private static Map<String, Double> map = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        boolean play = true;

        while (play) {
            printMenu();
            int action = Integer.parseInt(scanner.nextLine());
            System.out.println();

            switch (action) {
                case 1:
                    addIncome(scanner);
                    break;
                case 2:
                    addPurchase(scanner);
                    break;
                case 3:
                    list();
                    break;
                case 4:
                    showBalance(scanner);
                    break;
                case 0:
                    play = false;
                    break;
                default:
                    throw new IllegalArgumentException("Its only could be 0-4");
            }
            System.out.println();
        }

        System.out.println("Bye!");
    }

    private static void addPurchase(Scanner scanner) {
        System.out.println("Enter purchase name:");
        String name = scanner.nextLine();
        System.out.println("Enter its price:");
        Double value = Double.parseDouble(scanner.nextLine());
        System.out.println("Purchase was added!");
        map.put(name, value);
        balance -= value;
    }

    private static void list() {
        if (map.isEmpty()) {
            System.out.println("Purchase list is empty");
        } else {
            map.forEach((k, v) -> System.out.println(k + " $" + v));
        }
    }

    private static void showBalance(Scanner scanner) {
        System.out.println("Balance: $" + balance);
    }

    private static void addIncome(Scanner scanner) {
        System.out.println("Enter income:");
        balance += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added!");
    }

    private static void printMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("0) Exit");
    }

    private static double getPriceFromLine(String line) {
        return Double.parseDouble(line.split("\\$")[1]);
    }

    private static void readTillEOF() throws IOException {
        double total = 0;
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while((line = br.readLine()) != null) {
            list.add(line);
            total += getPriceFromLine(line);
        }
        br.close();
    }
}
