import java.util.Comparator;
import java.util.Vector;

public class Process implements Comparable<Process>, Comparator<Process> {
    private int arrivalTime, burstTime,priority, endTime, constBurstTime, waitingTime,turnAround , baseQuantum , varQuantum;
    private String processName;
    Vector<Integer> baseHistory=new Vector<Integer>();
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

    public int getVarQuantum() {
        return varQuantum;
    }

    public void setVarQuantum(int varQuantum) {
        this.varQuantum = varQuantum;
    }

    public int getBaseQuantum() {
        return baseQuantum;
    }

    public void setBaseQuantum(int baseQuantum) {
        this.baseQuantum = baseQuantum;
        this.varQuantum = this.baseQuantum;
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
                ", burstTime = " + this.burstTime + ", endTime = " + this.endTime + ", BaseQuantum = " + this.baseQuantum +
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

    public void resetVarQuantum(){
        varQuantum = baseQuantum;
    }

    public void checkExecutionTime()
    {
        Main.processesOrder.add(this.processName);
    }

    public void printHistory(Vector <Integer> vec)
    {
        System.out.print("Base Quantum History: ");
        for (int i=0;i<vec.size();i++)
        {
            if (i>0 && vec.get(i) == vec.get(i-1))
            {
                continue;
            }
            System.out.print(vec.get(i) + " ");
        }
        System.out.println("");
    }
}
