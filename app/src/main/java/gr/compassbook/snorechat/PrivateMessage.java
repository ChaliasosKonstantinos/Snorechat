package gr.compassbook.snorechat;

/**
 * Created by Konstantinos on 9/1/2016.
 */
public class PrivateMessage {

    String sender, receiver, message;

    public PrivateMessage(String sender, String receiver, String message) {
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
    }

    public PrivateMessage(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
