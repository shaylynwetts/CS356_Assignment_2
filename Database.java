/*
 *  Name  : Shaylyn Wetts
 *  Class : CS 356 Object Oriented Design and Programming
 *  
 *  Date  : 11/08/2016
 *  
 *  Assignment 2
 *      Database class that holds all the users, groups, messages,
 *      and positive code words.  Includes methods for retrieving
 *      this information.
 */

package cs356_Assignment_2;

// Include files
import java.util.ArrayList;
import java.util.HashMap;
import cs356_Assignment_2.IDatabase;

public class Database implements IDatabase{

        // Global variables for storing data
        ArrayList<String> users = new ArrayList<String>();
        ArrayList<String> groups = new ArrayList<String>();
        ArrayList<String> currentIndividualMsgs = new ArrayList<String>();
        HashMap<String, ArrayList<String>> messages = new HashMap<String, ArrayList<String>>();
        ArrayList<String> posCodeWords = new ArrayList<String>();

        // Instance variable for singleton design pattern implementation
        private static Database instance;
        
        // Initializes the database and sets the positive code words
        private Database() {
            setPosCodeWords();
        }
        
        // Singleton design pattern implementation
        public static Database getInstance() {
            if (instance == null) {
                instance = new Database();
            }
            return instance;
        }

        // Returns the total current users
        public int getTotalUsers() {
            return users.size();
        }
        
        // Adds a user to the current users
        public void setUser(String userID) {
            users.add(userID);
        }
        
        // Returns the total current groups
        public int getTotalGroups() {
            return groups.size();
        }
        
        // Returns the most recent group added
        public String getLastGroupItem() {
            return groups.get(groups.size()-1);
        }
        
        // Adds a group to the current groups
        public void setGroup(String groupID) {
            groups.add(groupID);
        }
        
        // Gathers all the current messages into a single ArrayList
        private void currentArrayOfMsgs() {
            ArrayList<String> tempMessages = new ArrayList<String>();
            String[] currentUsers = new String[users.size()];
            currentIndividualMsgs.clear();
            
            for (int i = 0; i < currentUsers.length; i++) {
                currentUsers[i] = users.get(i);
            }
            
            for (int userMsgCount = 0; userMsgCount < messages.size(); userMsgCount++) {
                tempMessages = messages.get(currentUsers[userMsgCount]);
                for (int individualMsgCount = 0; individualMsgCount < tempMessages.size(); individualMsgCount++) {
                    currentIndividualMsgs.add(tempMessages.get(individualMsgCount));
                }
            }
        }
        
        // Returns the number of total current messages
        public int getTotalMessages() {
            currentArrayOfMsgs();
            return currentIndividualMsgs.size();
        }
        
        // Returns an ArrayList of messages posted by a single user
        public ArrayList<String> getMessagesByUser(String userID) {
            ArrayList<String> msgReturnArray = new ArrayList<String>();
            msgReturnArray = messages.get(userID);
            return msgReturnArray;
        }
        
        // Adds a new message to a current user's ArrayList of messages
        public void setMessage(String userID, String messageContent) {
            ArrayList<String> tempArrayList = new ArrayList<String>();
            tempArrayList = messages.get(userID);
            tempArrayList.add(messageContent);
            messages.replace(userID, tempArrayList);
        }
        
        // Returns the percentage of messages that include a word from
        // the preset positive code words
        public double getTotalPosMessages() {
            int totalPosMessages = 0;
            double totalPosMsgPercent = 0;
            int totalCurrentMsgs = 0;
            String temp;
            currentArrayOfMsgs();
            
            for (int userMsg = 0; userMsg < currentIndividualMsgs.size(); userMsg++) {
                temp = currentIndividualMsgs.get(userMsg);
                for (int posCodeWord = 0; posCodeWord < posCodeWords.size(); posCodeWord++) {
                    if (temp.contains(posCodeWords.get(posCodeWord))) {
                        totalPosMessages++;
                    }
                }
                totalCurrentMsgs++;
            }
            
            if (totalCurrentMsgs == 0) {
                return 0;
            }
            
            totalPosMsgPercent = (double)totalPosMessages / (double)totalCurrentMsgs;
            
            return totalPosMsgPercent;
        }
        
        // Sets the specific positive code words
        private void setPosCodeWords() {
            posCodeWords.add("good");
            posCodeWords.add("great");
            posCodeWords.add("excellent");
            posCodeWords.add("awesome");
            posCodeWords.add("amazing");
        }
}
