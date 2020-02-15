package L11_JMM_task2;

import L11_JMM_task2.ExecutionManager.ExecutionManager;
import L11_JMM_task2.Interfaces.IContext;
import L11_JMM_task2.UserCallableImpl.UserCallableTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private final static Logger log = LogManager.getLogger();
    private static ExecutorService service = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException {

        ExecutionManager em = new ExecutionManager(10);
        service.execute(em);

        List<Callable<Integer>> list = new LinkedList<>();
        for (int i = 0; i < 500; i++)
            list.add(new UserCallableTask(500));

        log.info("start");
        IContext c = em.execute(new UserCallableTask(1000),list);

        log.info("sleep");
        TimeUnit.SECONDS.sleep(5);

        log.info("interrupt");
        c.interrupt();




    }
}
