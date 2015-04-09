import java.util.Random;

/**
 * author:  Adrian Kuta
 * indeks:  204423
 * date:    2015-04-08
 * email:   redione193 @ gmail.com
 */
public class Employee extends Thread {

    private Random random;
    private String name;

    public Employee() {
        name = "";
        random = new Random();
    }

    public Employee(String name) {
        this.name = name;
        random = new Random();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                sleep(getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Task task = tryGetTask();
            if (task != null) {
                String result = solveTask(task);
                addResult(result);
                if (Utils.TRYB == Utils.GADATLIWY)
                    System.out.println(name + " Solved " + result);
            }
        }
    }

    private synchronized Task tryGetTask() {
        if (Utils.taskList.size() != 0)
            return Utils.taskList.remove(0);
        return null;
    }

    private String solveTask(Task task) {
        switch (task.operator) {
            case '+':
                return task.arg1 + "+" + task.arg2 + "=" + (task.arg1 + task.arg2);
            case '-':
                return task.arg1 + "-" + task.arg2 + "=" + (task.arg1 - task.arg2);
            case '/':
                if (task.arg2 != 0)
                    return task.arg1 + "/" + task.arg2 + "=" + ((float)task.arg1 / (float)task.arg2);
                else
                    return task.arg1 + "+" + task.arg2 + "= dividing by 0!";
            case '*':
                return task.arg1 + "*" + task.arg2 + "=" + (task.arg1 * task.arg2);
            default:
                return task.arg1 + task.operator + task.arg2 + "=" + "Wrong operator!";
        }
    }

    private synchronized void addResult(String result) {
        Utils.solvedTasks.add(result);
    }

    private long getSleepTime() {
        long bound = Utils.GET_TASK_MAX_INTERVAL - Utils.GET_TASK_MIN_INTERVAL;
        return Utils.GET_TASK_MIN_INTERVAL + (long) (random.nextDouble() * bound);
    }
}
