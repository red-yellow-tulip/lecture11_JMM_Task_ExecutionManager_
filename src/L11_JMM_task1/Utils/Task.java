package L11_JMM_task1.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

  /*
    Данный класс в конструкторе принимает экземпляр java.util.concurrent.Callable. Callable похож на Runnuble,
    но результатом его работы является объект (а не void).

    Ваша задача реализовать метод get() который возвращает результат работы Callable. Выполнение callable должен
     начинать тот поток, который первый вызвал метод get(). Если несколько потоков одновременно вызывают этот метод,
     то выполнение должно начаться только в одном потоке, а остальные должны ожидать конца выполнения (не нагружая процессор).
    Если при вызове get() результат уже просчитан, то он должен вернуться сразу, (даже без задержек на вход в
    синхронизированную область).
    Если при просчете результата произошел Exception, то всем потокам при вызове get(), надо кидать этот Exception,
    обернутый в ваш RuntimeException (подходящее название своему ексепшену придумайте сами).

    * */

public class Task<T>{

    private static final Logger log = LogManager.getLogger();

    private Callable<? extends T> callable = null;
    private static final Object lock = new Object();
    private volatile T result;
    private volatile Exception exception = null;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
        log.info ("Constructor потока id-" + Thread.currentThread().getId());
    }

    public T get() {
        try {
            //....даже без задержек на вход в  синхронизированную область....
            if (result != null)
                return result;

            //...должен  начинать тот поток, который первый вызвал метод get().
            if (result == null) {

                synchronized (lock) {
                    log.info("synchronized IN id-" + Thread.currentThread().getId() + "   " + result);

                    if (result == null && exception == null) {
                        try {
                            result = callable.call();
                            log.info("Расчет произвел поток id-" + Thread.currentThread().getId() + " result="+ result);
                        }catch (Exception e){
                            exception = e;
                            log.info("Неожиданное исключение - поток id-" + Thread.currentThread().getId() +" "+ exception.getMessage());
                        }

                    }//if

                    log.info("synchronized  OUT id-" + Thread.currentThread().getId() + "   " + result);
                }// synchronized (lock) {

            }

            // ждать пока не завершится расчет результата
            while (result == null) {

                //.... то всем потокам при вызове get(), надо кидать этот Exception,....
                if (exception != null)
                    throw new ArithmeticException(exception.getMessage());

                TimeUnit.MILLISECONDS.sleep(1000);
                log.info("Ожидает расчета результатов поток id-" + Thread.currentThread().getId() + "   " + result);
            }

        } catch (Exception e) {
            log.info("Ошибка расчета результатов поток id-" + Thread.currentThread().getId());
        }
        return result;
    }

}
