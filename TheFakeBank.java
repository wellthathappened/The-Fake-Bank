/*  
    Name:             Ian Lewis
    Course:           CNT 4714 Summer 2017
    Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
    Date:             June 17, 2017
*/

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class TheFakeBank {

    public static void main(String[] args) 
    {
        ExecutorService TheFakeBank = Executors.newFixedThreadPool(10);
        
        Account fakeAccount = new Account();
        
        System.out.println("Deposit Threads         Withdrawal Threads          Balance        \n" +
                           "---------------         -----------------           ---------------");
        
        /*
            AccountAction takes in a thread to be run, the type of action to be
            taken (1 == Deposit || 0 == Withdrawal) and the account to be used.
        */
        
        TheFakeBank.execute(new AccountAction( 1, 1, fakeAccount));
        TheFakeBank.execute(new AccountAction( 2, 0, fakeAccount));
        TheFakeBank.execute(new AccountAction( 3, 1, fakeAccount));
        TheFakeBank.execute(new AccountAction( 4, 0, fakeAccount));
        TheFakeBank.execute(new AccountAction( 5, 1, fakeAccount));
        TheFakeBank.execute(new AccountAction( 6, 0, fakeAccount));
        TheFakeBank.execute(new AccountAction( 7, 1, fakeAccount));
        TheFakeBank.execute(new AccountAction( 8, 0, fakeAccount));
        TheFakeBank.execute(new AccountAction( 9, 0, fakeAccount));
        TheFakeBank.execute(new AccountAction(10, 0, fakeAccount));
    }
}
