package L11_JMM_task2.ExecutionManager;


import L11_JMM_task2.Interfaces.IContext;

public class Context implements IContext {

    private int completedTaskCount = 0;
    private int failedTaskCount = 0;
    private int interruptedTaskCount = 0;
    private boolean interrupt = false;
    private int countTask = 0;


    @Override
    public int getCompletedTaskCount() {
        return completedTaskCount;
    }

    @Override
    public int getFailedTaskCount() {
        return failedTaskCount;
    }

    @Override
    public int getInterruptedTaskCount() {
        return interruptedTaskCount;
    }

    @Override
    public void interrupt() {
        interrupt = true;
    }

    @Override
    public boolean isFinished() {
        return countTask == completedTaskCount + interruptedTaskCount;
    }

    public boolean isIterrupt() {
        return interrupt;
    }

    @Override
    public String toString (){
        return String.format("Context: completedTaskCount %d, interruptedTaskCount %d, failedTaskCount %d ", completedTaskCount,interruptedTaskCount,failedTaskCount);
    }

    public void intCompletedTaskCount() {
        completedTaskCount++;
    }

    public void incInterruptedTaskCount() {
        interruptedTaskCount++;
    }

    public void incFailedTaskCount() {
        failedTaskCount++;
    }

    public void incCountTask(int size) {
        countTask+=size;
    }

}
