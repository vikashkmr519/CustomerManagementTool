package database;

import model.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminDatabase {

    List<Admin> adminDatabase = new ArrayList<>();

    public AdminDatabase(){
        adminDatabase = new ArrayList<>();
        Admin a1 = new Admin("admin1@c.com","465234782");
        Admin a2 = new Admin("admin2@c.com","43746q487q");
        adminDatabase.add(a1);
        adminDatabase.add(a2);
    }

    public void insertAdmin(Admin admin) throws Exception {

        if(admin.getEmail().isEmpty() || admin.getPassword().isEmpty()){
            throw new Exception("invalid emaail or password (Could be null value)");
        }
        adminDatabase.add(admin);
    }

    public Admin fetchAdminByEmail(String email) throws Exception {
        for(Admin ad : adminDatabase){
            if(ad.getEmail().equals(email)){
                return ad;
            }
        }

        throw new Exception("No Such Admin present");
    }

    public List<Admin> fetchAllAdmin(){
        return adminDatabase;
    }

    public boolean login(String email , String password){
        for(Admin ad: fetchAllAdmin()){
            if(ad.getEmail().equals(email) && ad.getPassword().equals(password)){
                return true;
            }
        }

        return false;
    }
}
