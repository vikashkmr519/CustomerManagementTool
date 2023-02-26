package database;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDatabase {

    List<Customer> customers = new ArrayList<>();

    public CustomerDatabase(){
        customers.add(new Customer("a@a.com","aaa","pune","87362343","afad"));
        customers.add(new Customer("b@a.com","baa","Maharastra","364563","afad"));
        customers.add(new Customer("c@a.com","caa","delhi","562736428","afad"));
    }

    public List<Customer> getAllCustomers(){
        return this.customers;
    }

    public boolean insertCustomer(Customer customer)throws Exception{



        for(Customer cust : customers){
            if(cust.getEmail().equals(customer.getEmail())){
                throw new Exception("Customer cann not  be inserted as email cannot be null");
            }
        }

        customers.add(customer);
        return true;
    }

    public Customer getCustomerByEmail(String email){
        Customer customer = null;

        for(Customer cust : customers){
            if(cust.getEmail().equals(email)){
                customer = cust;
                break;
            }
        }

        return customer;
    }

    public boolean login(String email, String password){
        for(Customer c: customers){
            if(c.getEmail().equals(email) && c.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
