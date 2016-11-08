/*
 *  Name  : Shaylyn Wetts
 *  Class : CS 356 Object Oriented Design and Programming
 *  
 *  Date  : 11/08/2016
 *  
 *  Assignment 2
 *      GUI for User Control Panel.  Includes functionality for adding users
 *      to the current user's following list, displaying that following list,
 *      an editable text box for writing out messages, a button for posting 
 *      the message in the text box, and a display for the messages from
 *      both the current user and the users being followed.
 */

package cs356_Assignment_2;

// Include files
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

@SuppressWarnings("serial")
public class UserControlPanelGUI extends JFrame implements Observer{

    // Global variables for GUI buttons
    private JButton postButton;
    private JButton followButton;
    
    // Global variables for GUI lists
    private DefaultListModel<String> newsFeed;
    private DefaultListModel<String> followers;
    private JList<String> newsFeedList;
    private JList<String> followersList;
    
    // Global variables for GUI layout
    private JScrollPane newsFeedScrollPane;
    private JScrollPane followersScrollPane;
    
    // Global variables for GUI text fields
    private JTextField inputTweetTextField;
    private JTextField followingTextField;
    private JTextField newsFeedTextField;
    private JTextField currentUserTextField;
    
    // Global variables
    private ArrayList<String> followersArrayList = new ArrayList<String>();
    private ArrayList<String> currentUserTweets = new ArrayList<String>();
    
    private String currentUserID;
    
    Subject subject;
    Database data;

    // Instantiates the UserControlPanelGUI class and starts the initialization
    // of the GUI, as well as sets the current user ID and the database variables
    public UserControlPanelGUI(String userID, Database newData) {
        currentUserID = userID;
        data = newData;
        initGUI();
    }
    
    // Updates the current user's news feed with messages from a user whom the
    // current user is following
    private void updateNewsFeedArray(String userID) {
        ArrayList<String> newFollow = new ArrayList<String>();
        newFollow = data.messages.get(userID);
        for (int i = 0; i < newFollow.size(); i++) {
            newsFeed.addElement(newFollow.get(i));
        }
    }
    
    // Update method for observer design pattern
    @Override
    public void update(Subject subject) {
        subject.notifyObservers();
    }

    // Method for formatting the time stamp on posted messages
    private String formatTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");
        Date currentDate = new Date();
        String timeStamp = format.format(currentDate);
        return timeStamp;
    }
    
    // Initializes the settings for the GUI buttons as well as includes functionality
    // for when buttons are clicked
    private void initButton() {
        postButton = new JButton();
        followButton = new JButton();
        
        // Posts the text currently in the editable text box on the GUI.  Includes
        // the poster's user name and a time stamp of when that message was posted.
        // Includes the message in the ArrayList of all messages
        postButton.setText("POST");
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newTweet = inputTweetTextField.getText();
                String timeStamp = formatTimeStamp();
                currentUserTweets.add(currentUserID + " [" + timeStamp + "] : " + newTweet);
                if (!data.messages.containsKey(currentUserID)) {
                    data.messages.put(currentUserID, currentUserTweets);
                } else {
                    data.messages.replace(currentUserID, currentUserTweets);
                }
                newsFeed.addElement(currentUserID + " [" + timeStamp + "] : " + newTweet);
            }
        });
        
        // Follows a new user as long as the user exists and the user is not the
        // current user.  Adds all posts made by the new followed user to the current
        // user's news feed display
        followButton.setText("Follow New User");
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String text = JOptionPane.showInputDialog("Add a user ID to follow");
                String item = null;

                if (text != null) {
                    item = text.trim();
                } else {
                    return;
                }
                
                if (data.users.contains(item) && !item.equals(currentUserID)) {
                    followers.addElement(item);
                    followersArrayList.add(item);
                    updateNewsFeedArray(item);
                } else if (item.equals(currentUserID)){
                    JOptionPane.showMessageDialog(null, "You already follow yourself.");
                } else {
                    JOptionPane.showMessageDialog(null, "This user does not exist.");
                }

            }
        });
    }
    
    // Initializes the GUI lists
    private void initList() {
        followers = new DefaultListModel<String>();
        newsFeed = new DefaultListModel<String>();
        newsFeedList = new JList<String>(newsFeed);
        followersList = new JList<String>(followers);
        
    }

    // Initializes the User Control GUI, using a composite design.  Calls the initialization
    // methods for the GUI buttons and the GUI lists
    private void initGUI() {

        initButton();
        initList();
        
        newsFeedScrollPane = new JScrollPane();
        followersScrollPane = new JScrollPane();

        inputTweetTextField = new JTextField();
        followingTextField = new JTextField();
        newsFeedTextField = new JTextField();
        currentUserTextField = new JTextField();

        inputTweetTextField.setText("");

        newsFeedScrollPane.setViewportView(newsFeedList);
        followersScrollPane.setViewportView(followersList);

        followingTextField.setEditable(false);
        followingTextField.setText("Following");

        newsFeedTextField.setEditable(false);
        newsFeedTextField.setText("News Feed");
        
        currentUserTextField.setEditable(false);
        currentUserTextField.setText("User: " + currentUserID);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(currentUserTextField)
                    .addComponent(followersScrollPane, GroupLayout.Alignment.TRAILING)
                    .addComponent(newsFeedScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputTweetTextField, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(postButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                    .addComponent(followingTextField)
                    .addComponent(followButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newsFeedTextField, GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
            layout.createSequentialGroup().addContainerGap()
                .addComponent(currentUserTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(followButton, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(followingTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(followersScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(inputTweetTextField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addComponent(postButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(newsFeedTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(newsFeedScrollPane, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }

}