package L11_JMM_task1;

import L11_JMM_task1.UserCallableImpl.UserCallableTask;
import L11_JMM_task1.Utils.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private final static Logger log = LogManager.getLogger();
    private final static int countThread = 5;
    private static ExecutorService service = Executors.newFixedThreadPool(countThread);

    public static void main(String[] args) throws InterruptedException {

        List<Thread> listTask = new ArrayList<>();
        Task<Callable<Integer>> taskExec = new Task(new UserCallableTask(1000));
        for (int i = 0; i < countThread; i++)
            listTask.add(
                    new Thread( () -> {
                        try {
                            log.info("Результат расчетов: " + taskExec.get());
                        } catch (Exception e){
                            log.info("исключение при выполнении метода get() " + e);
                        }
            }));

        listTask.forEach((e) -> service.submit(e));

        TimeUnit.SECONDS.sleep(5);
        service.shutdown();
    }
}
