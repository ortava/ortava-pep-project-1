package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account registerAccount(Account account) {
        if(!account.getUsername().isEmpty()
        && account.getPassword().length() >= 4
        && accountDAO.getAccountByUserName(account.getUsername()) == null) {
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }
    }

    public Account login(Account account) {
        Account existing = accountDAO.getAccountByUserName(account.getUsername());
        if(existing != null
        && account.getUsername().equals(existing.getUsername())
        && account.getPassword().equals(existing.getPassword())) {
            return existing;
        } else {
            return null;
        }
    }
}
