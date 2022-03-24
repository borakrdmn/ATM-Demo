import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static boolean exit = true;
    static boolean exit2 = true;
    static boolean exit3 = true;
    static String path = "C:\\Users\\user\\Desktop\\Kod\\Java\\atmDemo\\src\\users.txt";
    static File file = new File(path);
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();
    static ArrayList<Person> users = new ArrayList<Person>();
    static boolean isLogged = false;

    public static void main(String[] args) throws IOException {
        menu();
    }

    static void menu() throws IOException {
        do {
            System.out.println("1-Login" + "\n" + "2-Register" + "\n" + "3-Exit" + "\n" + "Enter action: ");
            String action = sc.nextLine();
            switch (action) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    exit();
                    break;
            }

        }
        while (exit);
    }

    static void login() throws IOException {
        do {
            System.out.println("1-User Login" + "\n" + "2-Admin Login" + "\n" + "3-Back to main menu" + "\n" + "Enter action: ");
            String action = sc.nextLine();
            switch (action) {
                case "1":
                    userLoginMenu();
                    break;
                case "2":
                    adminLoginMenu();
                    break;
                case "3":
                    exit2();
                    break;
            }

        }
        while (exit2);

    }

    static void readInfo() throws IOException {
        users.clear();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String s = "";
        while ((s = reader.readLine()) != null) {
            String data[] = new String[3];
            data = s.split(",");
            String id = data[0];
            String firstName = data[1];
            String lastName = data[2];
            String tcNo = data[3];
            String password = data[4];
            String accNum = data[5];
            String money = data[6];
            Person person = new Person(Integer.valueOf(id), firstName, lastName, tcNo, password, accNum, Integer.valueOf(money));
            users.add(person);
        }
    }

    static void register() throws FileNotFoundException {
        System.out.println("Enter your first name: ");
        String firstName = enterFirstName();
        System.out.println("Enter your last name: ");
        String lastName = enterLastName();
        System.out.println("Enter your tcNo: ");
        String tcNo = enterTcNo();
        System.out.println("Enter your password: ");
        String password = enterPassword();
        String accNum = String.valueOf(enterAccountNumber());
        int money = 0;
        Person person = new Person(enterID(), firstName, lastName, tcNo, password, accNum, money);
        addPerson(person);
    }

    static void addPerson(Person person) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
        pw.append(person.id + "," + person.firstName + "," + person.lastName + "," + person.tcNo + "," + person.password + "," + person.accNumber + "," + person.money + "\n");
        pw.close();
    }

    static void exit() {
        exit = false;
    }

    static void exit2() {
        exit2 = false;
    }

    static void exit3() {
        exit3 = false;
    }

    static void userLoginMenu() throws IOException {
        do {
            users.clear();
            readInfo();
            System.out.println("Enter tcNo: ");
            String inputTcNo = sc.nextLine();
            if(String.valueOf(inputTcNo).equals("*exit*")){
                exit3();
                break;
            }
            System.out.println("Enter password: ");
            String inputPassword = sc.nextLine();
            for (Person user : users) {
                if (String.valueOf(user.tcNo).equals(String.valueOf(inputTcNo)) && String.valueOf(user.password).equals(String.valueOf(inputPassword))) {
                    System.out.println("Logged in.");
                    isLogged = true;
                    loggedUserMenu(user);
                }
                else {
                    System.out.println("Wrong information.Try again.");
                    exit3();
                }
            }
        }
        while (exit3);
    }

    static void loggedUserMenu(Person user) throws IOException {
        do {
            System.out.println("Balance: "+user.money+"\n"+"1-Deposit" + "\n" + "2-Withdraw" + "\n" + "3-Pay" + "\n" +"4-Transfer"+"\n+"+"5-Logout" + "\n" + "Enter action: ");
            String action = sc.nextLine();
            switch (action) {
                case "1":
                    deposit(user);
                    break;
                case "2":
                    withdraw(user);
                    break;
                case "3":
                    pay(user);
                    break;
                case "4":
                    transfer(user);
                    break;
                case "5":
                    isLogged = false;
                    exit3();
                    menu();
            }
        }
        while (exit3);

    }
    static void findID(int id) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "rw");
        String delete;
        String task="";
        byte []tasking;
        while ((delete = file.readLine()) != null) {
            if (delete.startsWith(String.valueOf(id))) {
                continue;
            }
            task+=delete+"\n";
        }
        System.out.println(task);
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(task);
        file.close();
        writer.close();
    }

    static void deposit(Person user) throws IOException {
        System.out.println("Enter deposit amount: ");
        int amount = sc.nextInt();
        user.money+=amount;
        findID(user.id);
        addPerson(user);
    }

    static void withdraw(Person user) throws IOException {
        System.out.println("Enter withdraw amount: ");
        int amount = sc.nextInt();
        user.money-=amount;
        findID(user.id);
        addPerson(user);
    }
    static void transfer(Person user){

    }

    static void pay(Person user) {

    }

    static void adminLoginMenu() {

    }

    static String enterFirstName() {
        return sc.nextLine();
    }

    static String enterLastName() {
        return sc.nextLine();
    }

    static String enterTcNo() {
        return sc.nextLine();
    }

    static int enterID() {
        return random.nextInt(100);
    }

    static int enterAccountNumber() {
        return random.nextInt(9999999);
    }

    static String enterPassword() {
        return sc.nextLine();
    }
}
