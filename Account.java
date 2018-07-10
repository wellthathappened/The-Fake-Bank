/*  
    Name:             Ian Lewis
    Course:           CNT 4714 Summer 2017
    Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
    Date:             June 17, 2017
*/

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Account
{
    private int bal;
    private final Lock lock;
    private final Condition withdrawalSignal;
    
    public Account()
    {
        bal              = 0;
        lock             = new ReentrantLock();
        withdrawalSignal = lock.newCondition();
    }
    
    /*
        The airlock function helps act as a generic stopper for any incoming
        threads that are trying to execute at the same time as another thread.
    
        This is where a deposit or a withdrawal is chosen based on the action
        type.
    */
    public void airlock(int threadNum, int amt, int actionType) throws InterruptedException
    {
        lock.lock();
        
        if(actionType == 1)
            deposit(threadNum, amt);
        
        else if(actionType == 0)
            withdraw(threadNum, amt);
        
        lock.unlock();
    }
    
    // Deposit the amount in the fake bank account
    public void deposit(int threadNum, int amt)
    {
        System.out.print("Thread D" + threadNum + " deposits $" + amt);
        
        bal += amt;
        System.out.println(buildBuffer(threadNum, amt, 1) + "Balance is $" + amt);
        
        // Signal that any withheld withdrawals can now run.
        withdrawalSignal.signal();
    }
    
    // Withdraw the amount from the fake bank account
    public void withdraw(int threadNum, int amt) throws InterruptedException
    {
        System.out.print("\t\t\tThread W" + threadNum + " withdraws $" + amt);
        
        if(amt <= bal)
        {
            bal -= amt;
            System.out.println(buildBuffer(threadNum, amt, 0) + "Balance is $" + amt);
        }
        
        /*
            If the amount to be withdrawn is not available in the account,
            send the signal that no more withdrawals can be sent to the queue.
        */ 
        else if(amt > bal)
        {
            System.out.println(" Withdrawal - Blocked - Insufficient Funds");
            withdrawalSignal.await();
        }
    }
    
    /*
        Because I cannot, for the life of me, get the Balance to print out
        in an even column.
    
        This builds a buffer to evenly space out every line so that all balance
        print outs are neat and organized.
    */
    public String buildBuffer(int threadNum, int amt, int actionType)
    {
        String buffer = "";
        
        if(threadNum <= 9)
            buffer = " ";
        
        if(amt <= 99)
            buffer += " ";
        
        if(amt <= 9)
            buffer += " ";
        
        if(actionType == 0)
            buffer += "   ";
        
        else if(actionType == 1)
            buffer += "\t\t\t    ";
        
        return buffer;
    }
}
