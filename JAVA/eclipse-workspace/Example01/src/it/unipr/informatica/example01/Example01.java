package it.unipr.informatica.example01;

public class Example01 {
	private Object mutex = new Object();
	private boolean waitInProgess = false;
	
	public void go() {
		waitInProgess = false;
		
		Thread notifier = new Thread(this::doNotify);
		
		Thread waiter = new Thread(this::doWait);
		
		notifier.start();
		
		waiter.start();
	}
	
	private void doWait() {
			System.out.println("Waiter started");
			
			synchronized (mutex) {
				waitInProgess = true;
				
				mutex.notifyAll();
				
				try {
					mutex.wait();
				} catch (Throwable e) {
					// blank
				}
			}
			
			System.out.println("Waiter terminated");
	}
	
	private void doNotify() {
		System.out.println("Notifier started");
		
		synchronized (mutex) {
			try {
				while (!waitInProgess)
					mutex.wait();
				
				Thread.sleep(5000);
				
				mutex.notifyAll();
			} catch (Throwable throwable){
				// blank
			}
		}
		
		System.out.println("Notifier terminated");
	}
	
	public static void main(String[] args) {
		new Example01().go();
	}

}
