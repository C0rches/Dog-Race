package isp.lab10.racedemo;

public class Timer extends Thread{

    long time=0;

    public void run() {
        while(time<Global.endTime)
        time = time + 1;
        try{
        Thread.sleep(10);}
     catch (InterruptedException e) {
        e.printStackTrace();
    }
    }
}
