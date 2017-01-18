package com.samples;

import java.util.Timer;
import java.util.TimerTask;

public class TimerJob {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimerTask task = new TimerTask() {
			  @Override
			  public void run() {
			    //do some processing
				  System.out.println(" Hello print");
			  }
			};

			Timer timer = new Timer();
			timer.schedule(task, 0l, 1000l);
	}

}
