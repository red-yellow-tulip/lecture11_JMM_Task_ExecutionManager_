package L11_JMM_task1.UserCallableImpl;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class UserCallableTask implements Callable {
    private long time;

    public UserCallableTask(long t){
        time = t;
    }

    @Override
    public Object call() throws Exception {

        TimeUnit.MILLISECONDS.sleep( time);
        return 2 + 2;

    }
}
