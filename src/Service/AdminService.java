package Service;

import database.AdminDatabase;
import model.Admin;

public class AdminService {
    private AdminDatabase adminDatabase;
    public AdminService(AdminDatabase adminDatabase){
        this.adminDatabase =adminDatabase;

    }

    public boolean  insertAdmin(Admin admin){
        if(validAdmin(admin.getEmail())){
            try {
                adminDatabase.insertAdmin(admin);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    private boolean validAdmin(String email){
        for(Admin ad : adminDatabase.fetchAllAdmin()){
            if(ad.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }

    public boolean login(String email, String password){

        return adminDatabase.login(email,password);
    }
}
