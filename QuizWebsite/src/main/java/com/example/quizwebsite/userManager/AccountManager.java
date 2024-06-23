//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.quizwebsite.userManager;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private Map<String, String> accounts = new HashMap();

    public AccountManager() {
        this.accounts.put("Patrick", "1234");
        this.accounts.put("Molly", "FloPup");
    }

    public boolean accountExists(String username) {
        return this.accounts.containsKey(username);
    }

    public boolean validatePassword(String username, String password) {
        return this.accounts.containsKey(username) && ((String)this.accounts.get(username)).equals(password);
    }

    public boolean createAccount(String username, String password) {
        if (this.accountExists(username)) {
            return false;
        } else {
            this.accounts.put(username, password);
            return true;
        }
    }
}
