package ui;

import Service.AdminService;
import Service.CustomerService;
import database.AdminDatabase;
import database.CustomerDatabase;
import model.Admin;
import model.Customer;

import java.sql.*;
import java.util.Scanner;

public class CustomerMenu {


    public static void adminMenu() {


        System.out.println("Select option\n" +
                "1. See All customers\n" +
                "2. Fetch by email\n" +
                "3. Logoff");
    }

    public static void customerMenu() {
        System.out.println("Select option\n" +
                "1. See  profile\n" +
                "2. Edit Profile\n" +
                "3. Logoff\n");
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //  Class.forName("com.mysql.cj.jdbc.Driver");
//
//        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//        String url = "jdbc:mysql://localhost/custemp";
//        Connection con = DriverManager.getConnection(url,"root","Sysofni@1021");
//        System.out.println(con);
//
//        Statement st = con.createStatement();
//
//        ResultSet rs = st.executeQuery("select * from customer");
//
//        while(rs.next()){
//            System.out.println(rs.getString("email"));
//        }
//
//        st.close();
//        con.close();


        CustomerService customerService = new CustomerService();
        AdminDatabase adminDatabase = new AdminDatabase();
        AdminService adminService = new AdminService(adminDatabase);
        Scanner sc = new Scanner(System.in);
        String email, password, city, phone, name;
        int choice;
        boolean flag1 = true;
        do {
            System.out.println("Choose one of the below" +
                    "1. Login\n" +
                    "2. Register Customer\n" +
                    "3. Exit\n"
            );

            int uInput = sc.nextInt();
            sc.nextLine();
            switch (uInput) {
                case 1:
                    login(sc, customerService, adminService);
                    break;
                case 2:
                    register(sc, customerService, adminService);
                    break;
                case 3:
                    flag1 = false;
                    break;
                default:
                    System.out.println("Please select correct option");
            }


        } while (flag1);
    }

    private static void login(Scanner sc, CustomerService customerService, AdminService adminService) {
        boolean loginFlag = true;
        do {
            System.out.println("What Role you want to login as , choose your option\n" +
                    "1. Admin\n" +
                    "2. Customer\n" +
                    "3. Exit\n");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    loginAdmin(sc, adminService, customerService);
                    break;
                case 2:
                    loginCustomer(sc, customerService);

                case 3:
                    System.out.println("You have been exited from this option");
                    loginFlag = false;
                    break;

                default:
                    System.out.println("Please choose valid option");
            }
        } while (loginFlag);
        

    }


    private static void loginAdmin(Scanner sc, AdminService adminService, CustomerService customerService) {
        System.out.println("Please enter email");
        String email = sc.nextLine();
        System.out.println("Please enter password");
        String password = sc.nextLine();

        if (adminService.login(email, password)) {
            System.out.println("Successfully LoggedIn");
            processAdminDashBoard(sc, adminService, customerService);
        } else {
            System.out.println("login Failed please try again");
        }
    }

    private static void processAdminDashBoard(Scanner sc, AdminService adminService, CustomerService customerService) {
        boolean flagadmin = true;
        do {
            adminMenu();
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    seeAllCustomers(customerService);
                    break;
                case 2:
                    System.out.println("Please provide the email for the customer");
                    String email = sc.nextLine();
                    fetchCustomerByEmail(customerService, email);
                    break;
                case 3:
                    System.out.println("Thank You");
                    flagadmin = false;
                    break;
                default:
                    System.out.println("Please choose correct option");
            }
        } while (flagadmin);
    }

    private static void loginCustomer(Scanner sc, CustomerService customerService) {
        System.out.println("Please enter email");
        String email = sc.nextLine();
        System.out.println("Please enter password");
        String password = sc.nextLine();
        try {
            if (customerService.validateCredentials(email, password)) {
                System.out.println("Welcome Customer");
                boolean flagCustomer = true;
                do {
                    customerMenu();
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1:
                            System.out.println(customerService.getCustomerByEmail(email));
                            break;
                        case 2:
                            editMyData(sc, customerService, email);
                            break;
                        case 3:
                            flagCustomer = false;
                            break;
                        default:
                            System.out.println("Please choose correct option");
                    }
                } while (flagCustomer);


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        private static void register (Scanner sc, CustomerService customerService, AdminService adminService){

            boolean registerFlag = true;
            do {
                System.out.println("Select who you want to be?\n" +
                        "1. Customer\n" +
                        "2. Admin\n" +
                        "3. Exit\n");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        registerCustomer(sc, customerService);
                        break;

                    case 2:

                        if (!registerAdmin(sc, adminService)) {
                            registerFlag = false;
                        }
                        ;
                        break;
                    default:
                        System.out.println("Please choose valid option");
                }

            } while (registerFlag);
        }

        private static void seeAllCustomers (CustomerService customerService){
            try {
                for (Customer cust : customerService.getAllCustomers()) {
                    System.out.println(cust);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private static void fetchCustomerByEmail (CustomerService customerService, String email){
            try {
                System.out.println(customerService.getCustomerByEmail(email));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private static void editMyData (Scanner sc, CustomerService customerService, String email){

            try {
                Customer cust = customerService.getCustomerByEmail(email);
                System.out.println("If you want to edit the field then write the updated value, otherwise null in , separated form\n" +
                        "email, custName, city, phone, password");
                String updateValue = sc.nextLine();
                String[] updatedArray = updateValue.split(",");
                cust.setCustName(updatedArray[1].equals("null") ? cust.getCustName() : updatedArray[1]);
                cust.setEmail(updatedArray[0].equals("null") ? cust.getEmail() : updatedArray[0]);
                cust.setCity(updatedArray[2].equals("null") ? cust.getCity() : updatedArray[2]);
                cust.setPhone(updatedArray[3].equals("null") ? cust.getPhone() : updatedArray[3]);
                cust.setPassword(updatedArray[4].equals("null") ? cust.getPassword() : updatedArray[4]);

                System.out.println("Data Updated Successfully");

            } catch (Exception ex) {

                System.out.println(ex.getMessage());

            }
        }

        private static void registerCustomer (Scanner sc, CustomerService customerService){
            System.out.println("Please provide your details\n");
            System.out.println("Please enter your email");
            String email = sc.nextLine();
            System.out.println("Please enter your Name");
            String custName = sc.nextLine();
            System.out.println("Please enter your city");
            String city = sc.nextLine();
            System.out.println("Please enter your phone");
            String phone = sc.nextLine();
            System.out.println("Please enter your password");
            String password = sc.nextLine();
            Customer customer = new Customer(email, custName, city, phone, password);
            try {
                if (customerService.registerCustomer(customer)) {
                    System.out.println("Admin registered Successfully");

                } else {
                    System.out.println("Admin Failer to register try again");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        private static boolean registerAdmin (Scanner sc, AdminService adminService){

            System.out.println("Please provide your details\n");
            System.out.println("Please enter your email");
            String email = sc.nextLine();
            System.out.println("Please enter your password");
            String password = sc.nextLine();
            Admin admin = new Admin(email, password);
            if (adminService.insertAdmin(admin)) {
                System.out.println("Admin registered Successfully");
                return true;
            } else {
                System.out.println("Admin Failer to register try again");
                return false;
            }


        }
    }

