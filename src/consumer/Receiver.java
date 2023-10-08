package consumer;

import messaging.MessageBox;

public class Receiver extends Thread {
	private MessageBox messageBox;

	public Receiver(MessageBox messageBox) {
//		setDaemon(true); //FIXME
		this.messageBox = messageBox;
	}
	@Override
	public void run() {
	    while (true) {
	        try {
	            String message = messageBox.get();
	            int messageNumber = Integer.parseInt(message.substring(7)); // Extract the message number
	            if ((getId() % 2 == 0 && messageNumber % 2 != 0) || (getId() % 2 != 0 && messageNumber % 2 == 0)) {
	                System.out.printf("Thread %d has got message: %s\n", getId(), message);
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
