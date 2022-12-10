import java.util.*;
import java.util.Collections;
import java.util.Vector;
import java.util.stream.Collectors;

public class Main {

    public static Vector<String> processesOrder= new Vector<>(); //order of processes during execution
    public static Vector<Process> readyQueue = new Vector<>();// fih el 7agat elli el mafrod tetnafez b tarteb tanfezha
    public static Vector<Process> processVector = new Vector<>();/// kol el processes elli dkhalet
    public static Vector<Process> arrivingVector = new Vector<>();// tartib el arriving

    public static int idx=0;

    public static void checkAddtoQueue(int Time) {

        while (idx < arrivingVector.size())
        {
            if(Time>=arrivingVector.get(idx).getArrivalTime())
            {
                readyQueue.add(arrivingVector.get(idx));
                idx++;
            }
            else
                break;
        }
    }

    public static Process checkPriority()
    {
        Process min=null;
        int minPrio=10000000, minSer=1000000;

//    for (int i=0;i<queue.size();i++)
//    {
//       if (queue.get(i).priority<minPrio)
//       {
//            min= queue.get(i);
//            minPrio= queue.get(i).priority;
//            minSer= queue.get(i).burst_time;
//       }
//       else if (queue.get(i).priority==minPrio)
//        {
//            if (queue.get(i).burst_time<minSer)
//            {
//                min=queue.get(i);
//                minSer=queue.get(i).burst_time;
//            }
//        }
//    }
        int index = -1;
        for(int i =0 ; i< readyQueue.size(); i++)
        {
            if(readyQueue.get(i).getBurstTime() < minPrio)
            {
                minPrio = readyQueue.get(i).getBurstTime();
                index = i;
            }
        }
        min = readyQueue.get(index);
        return min;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void SJFScheduling (int context_switching)
    {
        //Collections.sort(arrivingVector , new Process());

        int finished=0, time=0;
        Process checkProcess = new Process();

        while(finished!=processVector.size())
        {

            checkAddtoQueue(time);

            if (readyQueue.size()==0)
                time++;

            while (readyQueue.size()!=0)
            {

                //int starvation=0;

                //To get The Highest Priority



                Process essam = checkPriority();

                //To access the execution time
                essam.checkExecutionTime(time);


                while (essam.getBurstTime()!=0)
                {
                    essam.setBurstTime(essam.getBurstTime()-1);
                    time++;
                    checkAddtoQueue(time);

                    //solving starvation problem
//                    starvation++;
//                    if (starvation==3)
//                    {
//                        essam.priority++;
//                        starvation=0;
//                    }


                    checkProcess=checkPriority();

                    // switching process to get The lowest & plus the context switch
                    if (essam!=checkProcess)
                    {
                        //starvation=0;
                        time += context_switching;
                        checkAddtoQueue(time);

                        checkProcess.checkExecutionTime(time);


                        essam = checkProcess;

                    }
                }
                essam.setEndTime(time);
                readyQueue.remove(essam);
                finished++;
            }

        }

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void RRScheduling (int context_switching,int quantum)
    {
        //Collections.sort(arrivingVector , new Process());\

        int finished=0, time=0;
        Process checkProcess = new Process();

        while(finished!=readyQueue.size())
        {

            checkAddtoQueue(time);

            if (readyQueue.size()==0)
                time++;

            while (readyQueue.size()!=0)
            {
                Process essam =readyQueue.get(0);
                essam.checkExecutionTime();
                if(essam.getBurstTime() <= quantum)
                {
                    time += (essam.getBurstTime() + context_switching);
                    essam.setBurstTime(0);
                    checkAddtoQueue(time);
                    readyQueue.remove(0);
                    essam.setEndTime(time);
                    finished++;
                }
                else
                {
                    time += (quantum + context_switching);
                    essam.setBurstTime(essam.getBurstTime()-quantum);
                    checkAddtoQueue(time);
                    readyQueue.remove(0);
                    readyQueue.add(essam);
                }

            }

        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void PPScheduling ()
    {
        //Collections.sort(arrivingVector , new Process());

        Process executionProcess = new Process();
        boolean arrivingIsEmpty=false;

        for(int i=0;i!=-1;i++)
        {
            System.out.print("\n\nloop "+i+":    \n");
            if(readyQueue.size()==0 && arrivingVector.size()==0)
            {
                i=-1;
                break;
            }

            if(arrivingVector.size()>0)
            {
                while (arrivingVector.size()>0 && i==arrivingVector.get(0).getArrivalTime())
                {
                    readyQueue.add(arrivingVector.get(0));
                    arrivingVector.remove(0);
                }

            }
            if(arrivingVector.size()==0)
            {
                arrivingIsEmpty=true;
            }
            //------------printing ready queue before sorting----------------------------
            System.out.print("\n befor sorting: \n");

            for(int y=0;y<readyQueue.size();y++)
            {
                readyQueue.get(y).printProcess();
            }
            System.out.print("\n");
            //----------------------------------------
//            stableArrivingSort(readyQueue);
//            stablePrioritySort(readyQueue);
            Collections.sort(readyQueue);
            Collections.sort(readyQueue,new Process()) ;

            //---------------printing ready queue after sorting-------------------------
            System.out.print("\n after sorting: \n");

            for(int y=0;y<readyQueue.size();y++)
            {
                readyQueue.get(y).printProcess();
            }
            System.out.print("\n");
            //----------------------------------------
            if(readyQueue.size()!=0){ executionProcess=readyQueue.get(0);}

            //this if else is to add to process execution order to vector
            if(processesOrder.size()==0 )
            {
                processesOrder.add(executionProcess.getProcessName());
            }
            else if(processesOrder.get(processesOrder.size()-1)!=executionProcess.getProcessName())
            {
                processesOrder.add(executionProcess.getProcessName());
            }
            //loop to decrease priority of waiting processes
            for(int j=1; j<readyQueue.size();j++)
            {
                if(readyQueue.get(j)!=executionProcess && readyQueue.get(j).getPriority()>0 && arrivingIsEmpty==false)
                {
                    readyQueue.get(j).setPriority(readyQueue.get(j).getPriority()-1);
                }
            }
            //ending the process and removing it from ready queue
            if(executionProcess.getBurstTime()==1)
            {
                executionProcess.setBurstTime(executionProcess.getBurstTime() - 1);
                executionProcess.setEndTime(i+1);
                readyQueue.remove(0);
            }
            else
            {
                executionProcess.setBurstTime(executionProcess.getBurstTime() - 1);
            }

//            System.out.print("executing process is:  \n");
//            executionProcess.printProcess();
//            //System.out.print("");
//
//            System.out.print("\narrivingVector:  \n");
//            for(int x=0;x<arrivingVector.size();x++)
//            {
//                arrivingVector.get(x).printProcess();
//            }
//            System.out.print("readyQueue:  \n");
//            for(int x=0;x<readyQueue.size();x++)
//            {
//                readyQueue.get(x).printProcess();
//            }
//            executionProcess=new Process();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int n, quantum , context_switching, Time=0,Finished=0;

        int numberOfProcesses, arrivalTime, burstTime, priority;
        String processName;

        int choice=0;

        System.out.println("enter number of processes");
        numberOfProcesses = input.nextInt();

        for (int i = 0; i < numberOfProcesses; i++)
        {
            System.out.println("enter process name");
            processName = input.next();

            System.out.println("enter process arrival time");
            arrivalTime = input.nextInt();


            System.out.println("enter process burst time");
            burstTime = input.nextInt();

            System.out.println("enter process priority");
            priority = input.nextInt();

            Process p1 = new Process(processName, arrivalTime, burstTime, priority);

            processVector.add(p1);
            arrivingVector.add(p1); // sorted by arrival time
        }

        Collections.sort(arrivingVector , new Process());

        System.out.println("choose one of the following:-\n");
        System.out.println("1-SJFScheduling\n");
        System.out.println("2-RRScheduling\n");
        System.out.println("3-PPScheduling\n");
        System.out.println("4-AGScheduling\n");
        choice=input.nextInt();

        if(choice==1)
        {
            System.out.println("enter the context switching\n");
            context_switching=input.nextInt();
            SJFScheduling(context_switching);
        }
        else if(choice==2)
        {
            System.out.println("enter the context switching\n");
            context_switching=input.nextInt();
            System.out.println("enter the quantum value\n");
            quantum=input.nextInt();
            RRScheduling(context_switching,quantum);

        }
        else if(choice==3)
        {
            PPScheduling();
        }
        else if(choice==4)
        {

        }
        else
        {
            System.out.println("Wrong input.\n");
        }

        System.out.println("The Execution Order: ");
        for (int i=0;i<processesOrder.size();i++)
        {
            System.out.println(processesOrder.get(i));
        }

        double sumWaitingTime = 0;
        double sumTurnAroundTime = 0;
        for(int i =0 ; i<readyQueue.size() ; i++)
        {
            System.out.println("Name: " + processVector.get(i).getProcessName());
            System.out.println("Burst: " + processVector.get(i).getBurstTime());
            System.out.println("Waiting time: " + processVector.get(i).getWaitingTime());
            System.out.println("end_time: " + processVector.get(i).getEndTime());


            sumWaitingTime += processVector.get(i).getWaitingTime();


            sumTurnAroundTime += processVector.get(i).getTurnAround();
        }

        System.out.println("Average Waiting Time: ");
        System.out.println(sumWaitingTime/processVector.size());
        System.out.println("Average Turn Around Time: ");
        System.out.println(sumTurnAroundTime / processVector.size());

    }

}
