/*
 *  Name  : Shaylyn Wetts
 *  Class : CS 356 Object Oriented Design and Programming
 *  
 *  Date  : 11/08/2016
 *  
 *  Assignment 2
 *      Database interface for visitor design pattern implementation.
 */

package cs356_Assignment_2;

import java.util.ArrayList;

public interface IDatabase {

    public int getTotalUsers();
    public void setUser(String userID);
    public int getTotalGroups();
    public String getLastGroupItem();
    public void setGroup(String groupID);
    public int getTotalMessages();
    public ArrayList<String> getMessagesByUser(String userID);
    public void setMessage(String userID, String messageContent);
    public double getTotalPosMessages();
    
}
