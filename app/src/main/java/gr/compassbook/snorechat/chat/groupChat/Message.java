package gr.compassbook.snorechat.chat.groupChat;


public class Message {

    private String sender, message;
    private int counter;

    public Message() {
    }

    public Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public Message(String sender, String message, int counter) {
        this.sender = sender;
        this.message = message;
        this.counter = counter;
    }

    int getCounter() {
        return counter;
    }

    void setCounter(int counter) {
        this.counter = counter;
    }

    public String getSender() {
        return sender;
    }

    void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
