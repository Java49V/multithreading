package consumer;

import messaging.MessageBox;

public class Receiver extends Thread {
	private MessageBox messageBox;

	public Receiver(MessageBox messageBox) {
		this.messageBox = messageBox;
	}
	@Override
	public void run() {
		String message = null;
		try {
			while(true) {//FIXME
			
				message = messageBox.get();
				messageProcessing(message);
			
		}
			} catch (InterruptedException e) {
				message = null; 
				while((message = messageBox.take()) != null ) {
					messageProcessing(message);
				}
			}
	}
	private void messageProcessing(String message) {
		System.out.printf("Thread %d has got message: %s\n", getId(), message);
	}
}