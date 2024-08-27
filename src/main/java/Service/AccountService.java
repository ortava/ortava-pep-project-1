package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * @param account The new account to be registered (not including account_id).
     * @return If successful, the newly registered account (including account_id). Otherwise, return null.
     */
    public Account registerAccount(Account account) {
        if(!account.getUsername().isEmpty()
        && account.getPassword().length() >= 4
        && accountDAO.getAccountByUserName(account.getUsername()) == null) {
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }
    }

    /**
     * @param account The account that is attempting to login (not including account_id).
     * @return If successful, account that attempted to login (including account_id). Otherwise, return null.
     */
    public Account login(Account account) {
        Account existingAccount = accountDAO.getAccountByUserName(account.getUsername());
        if(existingAccount != null
        && account.getUsername().equals(existingAccount.getUsername())
        && account.getPassword().equals(existingAccount.getPassword())) {
            return existingAccount;
        } else {
            return null;
        }
    }
}
