package budget;

import java.io.*;
import java.util.*;


public class Main {

    private static double balance = 0;
    private static Map<String, Double> food = new LinkedHashMap<>();
    private static Map<String, Double> clothes = new LinkedHashMap<>();
    private static Map<String, Double> entertainment = new LinkedHashMap<>();
    private static Map<String, Double> other = new LinkedHashMap<>();
    private static Map<String, Double> fullMap = new LinkedHashMap<>();

    /*
        Формат файла
        _БАЛАНС_{int}
        _ТИП_СПИСКА_(один из : "Food", "Clothes", "Entertainment", "Other"){String}:_РАЗМЕР_{int}
        _НАЗВАНЕ_ТРАТЫ_{String}
        _СУММА_ТРАТЫ_{int}
        till EOF
     */
    private static String FILE_NAME = "purchases.txt";

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
                case 5:
                    save();
                    System.out.println();
                    break;
                case 6:
                    load();
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

    private static void save() {
        //todo сохранить мапы по файлу
        // 1 открыть файл
        // 2 сохранить баланс
        // 3 сохранить ("Food", "Clothes", "Entertainment", "Other")
        // 3.1 определить размер
        // 3.2 указать метку списка
        // 3.3 записать трату
        // 4 закрыть файл
        try (PrintWriter writer = new PrintWriter(new File(FILE_NAME))) {
            writer.println(balance);
            printMap(writer, "FOOD", food);
            printMap(writer, "CLOTHES", clothes);
            printMap(writer, "ENTERTAINMENT", entertainment);
            printMap(writer, "OTHER", other);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Purchases were saved!");
    }

    private static void printMap(PrintWriter writer, String type, Map<String, Double> map) {
        writer.println(type + ":" + map.size());
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            writer.println(entry.getKey());
            writer.println(entry.getValue());
        }
    }

    private static void load() {
        //todo проситать файл и распихать по мапам
        // 1 открыть файл
        // 2 сохранить баланс
        // 3 сохранить ("Food", "Clothes", "Entertainment", "Other")
        // 3.1 определить размер
        // 3.2 указать метку списка
        // 3.3 записать трату
        // 4 закрыть файл
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            balance = Double.parseDouble(scanner.nextLine());
            readMap(scanner, "FOOD", food);
            readMap(scanner, "CLOTHES", clothes);
            readMap(scanner, "ENTERTAINMENT", entertainment);
            readMap(scanner, "OTHER", other);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Purchases were loaded!");
    }

    private static void readMap(Scanner scanner, String type, Map<String, Double> map) {
        String marker = scanner.nextLine();
        String name = marker.split(":")[0];
        int size = Integer.parseInt(marker.split(":")[1]);
        for (int i = 0; i < size; i++) {
            String purchase = scanner.nextLine();
            double price = Double.parseDouble(scanner.nextLine());
            map.put(purchase, price);
            fullMap.put(purchase, price);
        }
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
                //кажется тут надо делать return
            } else {
//                currentMap.forEach((k, v) -> System.out.println(k + " $" + v));
                currentMap.forEach((k, v) -> System.out.println(String.format("%s $%.2f", k, v)));
            }
            showTotalSum(currentMap);
            System.out.println();
        }
    }

    private static void showTotalSum(Map<String, Double> map) {
        Double sum = map.values().stream().reduce(0.0, Double::sum);
        System.out.println(String.format("Total sum: $%.2f", sum));
    }

    private static void showBalance(Scanner scanner) {
        System.out.println(String.format("Balance: $%.2f", balance));
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
        System.out.println("5) Save");
        System.out.println("6) Load");
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
