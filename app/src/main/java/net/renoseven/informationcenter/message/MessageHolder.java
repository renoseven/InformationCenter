package net.renoseven.informationcenter.message;

import java.io.Serializable;
import java.util.Date;

/**
 * Serializable MessageHolder Object
 * Created by RenoSeven on 2016/9/8.
 */
public class MessageHolder implements Serializable, Cloneable {

    private MessageType msgType;
    private long timeStamp;
    private String charset;
    private String sender;
    private String senderName;
    private String receiver;
    private String receiverName;
    private String subject;
    private String text;

    public MessageHolder(MessageType msgType) {
        this.msgType = msgType;
    }

    public MessageType getMsgType() {
        return msgType;
    }

    public void setMsgType(MessageType msgType) {
        this.msgType = msgType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "[" + msgType.toString() + "] " + new Date(timeStamp).toString() + " Subject: " + subject + " From: " + senderName + "(" + sender + ")" + " To: " + receiverName + "(" + receiver + ") " + text;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
