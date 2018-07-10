/*  
    Name:             Ian Lewis
    Course:           CNT 4714 Summer 2017
    Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
    Date:             June 17, 2017
*/

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountAction implements Runnable
{
    private final int threadNum, actionType;
    private final Account account;
    
    public AccountAction(int num, int action, Account accountUsed)
    {
        threadNum   = num;
        actionType  = action;
        account     = accountUsed;
    }
    
    // We ovveride run so that we can make it loop infinitely.
    @Override
    public void run()
    {
        // Infinite loop clause
        while(threadNum == threadNum)
        {
            // This is the maximum dollar value that can be used to make an amt.
            int max = 50 + (150 * actionType);
            
            try
            {
                // We send our thread number, amount and action to the airlock.
                account.airlock(threadNum, randomAmt(max), actionType);
                
                /*
                    We want more withdrawals than deposits. So we seed our
                    random amount generator with a number of miliseconds to help
                    add vareity to how the threads run so they don't run in a
                    pattern.
                */
                if(actionType == 1)
                    Thread.sleep(randomAmt(200));
                
                else if(actionType == 0)
                    Thread.sleep(randomAmt(50));
            }
            
            catch (InterruptedException ex)
            {
                Logger.getLogger(AccountAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Basic random number generator. Ensures only even numbers.
    private int randomAmt(int max)
    {
        Random rand = new Random();
        int amt = 1;
        
        while(amt % 2 != 0)
            amt = rand.nextInt(max) + 1;
        
        return amt;
    }
}
