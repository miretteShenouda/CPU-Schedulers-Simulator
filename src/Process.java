import java.util.Comparator;

public class Process implements Comparable<Process>, Comparator<Process> {
    private int arrivalTime, burstTime,priority, endTime, constBurstTime, waitingTime,turnAround;
    private String processName;
    public Process() {
        this.processName = "";
        this.priority = 0;
        this.arrivalTime = 0;
        this.burstTime = 0;
        this.endTime=0;
    }
    public Process(String processName, int arrivalTime, int burstTime,int priority)
    {
        this.processName=processName;
        this.arrivalTime=arrivalTime;
        this.burstTime=burstTime;
        this.constBurstTime=burstTime;
        this.priority=priority;
        this.endTime=0;
    }

    public int getConstBurstTime() {
        return constBurstTime;
    }

    public int getEndTime() {
        return endTime;
    }
    public void setEndTime(int endTime)
    {
        this.endTime=endTime;
    }
    public void setArrivalTime(int arrivalTime){this.arrivalTime=arrivalTime;}
    public void setBurstTime(int burstTime){this.burstTime=burstTime;}
    public void setPriority(int priority) {this.priority = priority;}
    public void setProcessName(String processName) {this.processName=processName;}
    public int getArrivalTime() { return arrivalTime;}
    public int getBurstTime() {return burstTime;}
    public int getPriority() {return priority;}
    public String getProcessName() { return processName;}

    public void printProcess()
    {
        System.out.print( "{" +
                "processID = " + this.processName +
                ", priority = " + this.priority +
                ", arrivingTime = " + this.arrivalTime +
                ", burstTime = " + this.burstTime + ", endTime = " + this.endTime +
                "}\n");
    }

    public int getWaitingTime()
    {
        this.waitingTime=endTime-arrivalTime-constBurstTime;
        return waitingTime;
    }
    public int getTurnAround()
    {
        this.turnAround=endTime-arrivalTime;
        return turnAround;
    }

    @Override
    public int compareTo(Process p) {
        return this.getPriority()-p.getPriority();
    }

    @Override
    public int compare(Process p1, Process p2) {
        return p1.getArrivalTime() - p2.getArrivalTime();}

    public void checkExecutionTime(int Time)
    {
        Main.processesOrder.add(this.processName);
    }

    public void checkExecutionTime()
    {
        Main.processesOrder.add(this.processName);
    }


}
