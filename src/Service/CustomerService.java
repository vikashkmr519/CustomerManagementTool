package Service;

import database.CustomerDatabase;
import model.Customer;

import java.util.EmptyStackException;
import java.util.List;

public class CustomerService {

    private CustomerDatabase customerDatabase ;

    public CustomerService(){
        this.customerDatabase = new CustomerDatabase();
    }

    public List<Customer> getAllCustomers() throws Exception {
        if(customerDatabase.getAllCustomers().size()==0){
            throw new Exception("No Customers registered yet");
        }

        return this.customerDatabase.getAllCustomers();
    }

    public boolean registerCustomer(Customer customer) throws Exception {
        if(customer.getEmail()==null || customer.getEmail()==""){
            throw new Exception("Customer cann not  be inserted as email cannot be null");
        }

        try {
            customerDatabase.insertCustomer(customer);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public Customer getCustomerByEmail(String email)throws  Exception{
        if(email==null || email.isEmpty()){
            throw new Exception("email cannot be empty or null");
        }

        Customer cust = this.customerDatabase.getCustomerByEmail(email);
        if(cust == null){
            throw new Exception("Customer with email "+email+" does not exist");
        }

        return cust;
    }

    public boolean validateCredentials(String email, String password) throws Exception {
        if(email == null || email.isEmpty()){
            throw new Exception("email cannot be empty or null");
        }

        return this.customerDatabase.login(email,password);
    }
}
