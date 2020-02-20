package L11_JMM_task1.UserCallableImpl;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class UserCallableTask implements Callable {
    private long time;
    private Random rnd = new Random();

    public UserCallableTask(long t){
        time = t;
    }

    @Override
    public Object call() throws Exception {

        TimeUnit.MILLISECONDS.sleep( time);
        if (rnd.nextInt(10) < 1)
            throw new Exception("что то пошло не так...");
        return 2 + 2;

    }
}
