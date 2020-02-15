package L11_JMM_task2.Interfaces;

public interface IContext {

    //Метод getCompletedTaskCount() возвращает количество тасков, которые на текущий момент успешно выполнились.
    int getCompletedTaskCount();

    //Метод getFailedTaskCount() возвращает количество тасков, при выполнении которых произошел Exception.
    int getFailedTaskCount();

    //Метод interrupt() отменяет выполнения тасков, которые еще не начали выполняться.
    int getInterruptedTaskCount();

    //Метод getInterruptedTaskCount() возвращает количество тасков, которые не были выполены из-за отмены (вызовом предыдущего метода).
    void interrupt();

    //Метод isFinished() вернет true, если все таски были выполнены или отменены, false в противном случае.
    boolean isFinished();

    String toString();
}

