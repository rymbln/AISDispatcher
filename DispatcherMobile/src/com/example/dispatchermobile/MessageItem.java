package com.example.dispatchermobile;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 18.11.13
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class MessageItem {
    public String Id;
    public String MessageText;
    public String Author;
    public String Datetime;
    public String TypeMessage; // Driver - Dispetcher

    public MessageItem(String id, String messageText, String author, String datetime, String typeMessage)
    {
        this.Id = id;
        this.MessageText = messageText;
        this.Author = author;
        this.Datetime = datetime;
        this.TypeMessage = typeMessage;

    }
}
