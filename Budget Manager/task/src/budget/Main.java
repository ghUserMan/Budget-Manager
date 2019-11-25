package budget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {

    private static double balance = 0;
    private static Map<String, Double> food = new LinkedHashMap<>();
    private static Map<String, Double> clothes = new LinkedHashMap<>();
    private static Map<String, Double> entertainment = new LinkedHashMap<>();
    private static Map<String, Double> other = new LinkedHashMap<>();
    private static Map<String, Double> fullMap = new LinkedHashMap<>();

    private static List<Map<String, Double>> bucket = new ArrayList<>();

    static {
        bucket.add(food);
        bucket.add(clothes);
        bucket.add(entertainment);
        bucket.add(other);
        bucket.add(fullMap);
    }

    private static String[] labels = new String[] {"Food:", "Clothes:", "Entertainment:", "Other:", "All:"};

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean play = true;

        while (play) {
            printMenu();
            int action = Integer.parseInt(scanner.nextLine());
            System.out.println();

            switch (action) {
                case 1:
                    addIncome(scanner);
                    System.out.println();
                    break;
                case 2:
                    addPurchase(scanner);
                    System.out.println();
                    break;
                case 3:
                    showPurchases(scanner);
                    System.out.println();
                    break;
                case 4:
                    showBalance(scanner);
                    System.out.println();
                    break;
                case 0:
                    play = false;
                    break;
                default:
                    throw new IllegalArgumentException("Its only could be 0-4");
            }
        }

        System.out.println("Bye!");
    }

    private static void addPurchase(Scanner scanner) {

        while (true) {
            System.out.println("Choose the type of purchase");
            System.out.println("1) Food");
            System.out.println("2) Clothes");
            System.out.println("3) Entertainment");
            System.out.println("4) Other");
            System.out.println("5) Back");

            int action = Integer.parseInt(scanner.nextLine());

            if (action < 1 || action > 4) {
                return;
            }

//            Map<String, Double> currentMap = bucket.get(action - 1);
            Map<String, Double> currentMap = getRightMap(action);

            System.out.println();
            System.out.println("Enter purchase name:");
            String name = scanner.nextLine();
            System.out.println("Enter its price:");
            Double value = Double.parseDouble(scanner.nextLine());
            System.out.println("Purchase was added!");

            fullMap.put(name, value);
//            System.out.print("full ");
//            fullMap.forEach((k,v) -> System.out.print(k + ":" + v + " "));
            currentMap.put(name, value);

            balance -= value;
            System.out.println();
        }

    }

    private static Map<String, Double> getRightMap(int action) {
        switch (action) {
            case 1 :
                return food;
            case 2 :
                return clothes;
            case 3 :
                return entertainment;
            case 4 :
                return other;
            case 5 :
                return fullMap;
            default:
                throw new IllegalArgumentException("to much");
        }
    }

    private static void showPurchases(Scanner scanner) {

        while (true) {

            System.out.println("Choose the type of purchases");
            System.out.println("1) Food");
            System.out.println("2) Clothes");
            System.out.println("3) Entertainment");
            System.out.println("4) Other");
            System.out.println("5) All");
            System.out.println("6) Back");

            int action = Integer.parseInt(scanner.nextLine());

            if (action < 1 || action > 5) {
                return;
            }

//            Map<String, Double> currentMap = bucket.get(action - 1);
            Map<String, Double> currentMap = getRightMap(action);

//            System.out.print("action" + action + " map: " );
//            currentMap.forEach((k,v) -> System.out.print(k + ":" + v + " "));
            System.out.println();
            System.out.println(labels[action - 1]);
            if (currentMap == null || currentMap.size() < 1) {
                System.out.println("Purchase list is empty");
            } else {
                currentMap.forEach((k, v) -> System.out.println(k + " $" + v));
            }
            showTotalSum(currentMap);
            System.out.println();
        }
    }

    private static void showTotalSum(Map<String, Double> map) {
        Double sum = map.values().stream().reduce(0.0, Double::sum);
        System.out.println("Total sum: $" + sum);
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
        String line;
        while((line = br.readLine()) != null) {
            list.add(line);
            total += getPriceFromLine(line);
        }
        br.close();
    }
}
