package L11_JMM_task2.ExecutionManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;

public class ExecutionManager implements Runnable {

    private final static Logger log = LogManager.getLogger();

    private Deque<Callable<Integer>> listCallable = new ArrayDeque<>();
    private List<Future> listFuture = new LinkedList<>();

    // структура хранения статистических данныъ
    private static final Context context = new Context();
    private ExecutorService service = null;

    public ExecutionManager(int size){
        service = Executors.newFixedThreadPool(size);
        log.info("Conctructor init, size pool: " + size);
    }

    /*Метод execute принимает массив тасков, это задания которые ExecutionManager
    должен выполнять параллельно (в вашей реализации пусть будет в своем пуле потоков).
     После завершения всех тасков должен выполниться callback (ровно 1 раз). */
    public Context execute(Callable<Integer> finishTask, Collection<? extends Callable<Integer>> task)  {
        task.forEach( e ->  listCallable.offerLast(e));
        listCallable.offerLast(finishTask);
        log.info("listCallable.size(): " + listCallable.size());
        return context;
    }

    @Override
    public void run() {

        while (!context.isIterrupt()) {

            // добавлять в обработку пока есть в очереди
            for (int i = 0; i < 50 && !listCallable.isEmpty(); i++){
                listFuture.add(service.submit(listCallable.pollFirst()));
                context.incCountTask(1);
            }

            log.info("finishTask: " +listFuture.stream().filter(e -> e.isDone()).count() +
                    " executeTask: "+listFuture.stream().filter(e -> !e.isDone()).count()+
                    " queue task: " +listCallable.size());

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                log.warn(e);
            }
        }
        // отенить выполнение тасков
        if (context.isIterrupt())
            interraptTask();

        service.shutdownNow();
    }

    // отменить выполнение
    private void interraptTask() {
        listFuture.forEach((f) -> {
            if (!f.isDone()){
               if ( f.cancel(true))  // При успешной отмене выполнения задачи метод возвращает значение true
                   context.incInterruptedTaskCount();
               else
                   context.incFailedTaskCount();

            } else if (f.isDone())  // метод возвращает true, если выполнение задачи завершено, прервано или если в процессе ее выполнения возникло исключение
                context.intCompletedTaskCount();
            else if (f.isCancelled()) // метод возвращает true, если задача была отменена до ее нормального завершения
                context.incInterruptedTaskCount();

        });
        log.info("context: " + context);
    }
}

