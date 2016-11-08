/*
 *  Name  : Shaylyn Wetts
 *  Class : CS 356 Object Oriented Design and Programming
 *  
 *  Date  : 11/08/2016
 *  
 *  Assignment 2
 *      GUI for Admin Control Panel.  Includes functionality for adding users, adding groups,
 *      checking total users, checking total groups, checking total messages, checking total
 *      percent of positive messages, opening a user view GUI, and viewing all users and groups
 *      in a tree.
 */

package cs356_Assignment_2;

// Include files
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
class AdminControlPanelGUI extends JFrame {

    // Global variables for GUI buttons
    private JButton addUser;
    private JButton addGroup;
    private JButton openUserView;
    private JButton showUserTotal;
    private JButton showGroupTotal;
    private JButton showMessageTotal;
    private JButton showPosPercentage;

    // Global variables for GUI layout
    private JPanel basePanel;
    private JScrollPane treeViewScrollPane;

    // Global variables for GUI tree
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root");
    DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
    JTree treeView = new JTree(treeModel);
    
    // Global variable for database
    private Database data = Database.getInstance();

    // Instantiates the AdminControlPanelGUI class and starts the initialization
    // of the GUI
    public AdminControlPanelGUI() {
        initGUI();
    }
    
    // Initializes the settings for the GUI tree
    private void initTree() {
        treeView.setEditable(false);
        treeView.setSelectionRow(0);
    }

    // Initializes the settings for the GUI buttons as well as includes functionality
    // for when buttons are clicked
    private void initButton() {
        addUser = new JButton();
        addGroup = new JButton();
        openUserView = new JButton();
        showUserTotal = new JButton();
        showGroupTotal = new JButton();
        showMessageTotal = new JButton();
        showPosPercentage = new JButton();

        // Adds a new user.  Users cannot have the same user name as an existing user
        // or an existing group.  Users cannot have children nodes on the GUI tree.
        // Includes the new user in the database as well as the GUI tree
        addUser.setText("Add User");
        addUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String text = JOptionPane.showInputDialog("Add a new user");
                String item = null;

                if (text != null) {
                    item = text.trim();
                } else {
                    return;
                }
                
                if (!item.isEmpty()) {
                    if (data.users.contains(item)) {
                        JOptionPane.showMessageDialog(null, "User already exists.");
                    } else if (data.groups.contains(item)) {
                        JOptionPane.showMessageDialog(null, "User already exists \nas a group.");
                    } else {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeView.getLastSelectedPathComponent();
                        if (selectedNode != null) {
                            if (selectedNode.getAllowsChildren() == false) {
                                JOptionPane.showMessageDialog(null, "Users cannot have children nodes.\n Please select a group.");
                            } else {
                                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("USER: " + item);
                                newNode.setAllowsChildren(false);
                                
                                treeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
                                TreeNode[] nodeArray = treeModel.getPathToRoot(newNode);
                                TreePath treePath = new TreePath(nodeArray);
                                treeView.scrollPathToVisible(treePath);
                                treeView.setSelectionPath(treePath);
                                treeView.startEditingAtPath(treePath);
                                
                                data.setUser(item);
                            }
                        }
                    }
                }
            }
        });

        // Adds a new group.  Groups cannot have the same name as an existing user
        // or an existing group.  Groups can have children nodes on the GUI tree.
        // Includes the new group in the database as well as the GUI tree
        addGroup.setText("Add Group");
        addGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String text = JOptionPane.showInputDialog("Add a new group");
                String item = null;

                if (text != null) {
                    item = text.trim();
                } else {
                    return;
                }
                
                if (!item.isEmpty()) {
                    if (data.groups.contains(item)) {
                        JOptionPane.showMessageDialog(null, "Group already exists.");
                    } else if (data.users.contains(item)) {
                        JOptionPane.showMessageDialog(null, "Group already exists \nas a user.");
                    } else {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeView.getLastSelectedPathComponent();
                        if (selectedNode != null) {
                            if (selectedNode.getAllowsChildren() == false) {
                                JOptionPane.showMessageDialog(null, "Users cannot have children nodes.\n Please select a group.");
                            } else {
                                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("GROUP: " + item);
                                
                                treeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
                                TreeNode[] nodeArray = treeModel.getPathToRoot(newNode);
                                TreePath treePath = new TreePath(nodeArray);
                                treeView.scrollPathToVisible(treePath);
                                treeView.setSelectionPath(treePath);
                                treeView.startEditingAtPath(treePath);
                                
                                data.setGroup(item);
                            }
                        }
                    }
                }
            }
        });

        // Opens an instance of the UserControlPanelGUI for the specified user
        openUserView.setText("Open User View");
        openUserView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String text = JOptionPane.showInputDialog("Choose a user view to open");
                String item = null;

                if (text != null) {
                    item = text.trim();
                } else {
                    return;
                }

                if (data.users.contains(item)) {
                    new UserControlPanelGUI(item, data).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "That user does not exist.");
                }

            }
        });

        // Opens a message dialogue box with the total number of current users
        showUserTotal.setText("Show User Total");
        showUserTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numUsers = data.getTotalUsers();
                JOptionPane.showMessageDialog(null, "Total Users: \n" + numUsers);
            }
        });

        // Opens a message dialogue box with the total number of current groups
        showGroupTotal.setText("Show Group Total");
        showGroupTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numGroups = data.getTotalGroups();
                JOptionPane.showMessageDialog(null, "Total Groups: \n" + numGroups);
            }
        });

        // Opens a message dialogue box with the total number of current messages
        showMessageTotal.setText("Show Msg Total");
        showMessageTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numMessages = data.getTotalMessages();
                JOptionPane.showMessageDialog(null, "Total Messages: \n" + numMessages);
            }
        });

        // Opens a message dialogue box with the percent of positive current messages
        showPosPercentage.setText("Show Positive %");
        showPosPercentage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double numPosMessages = data.getTotalPosMessages();
                DecimalFormat formatter = new DecimalFormat("#0.00");
                JOptionPane.showMessageDialog(null,
                        "Total Percent of \nPositive Messages: \n" + formatter.format(numPosMessages) + "%");
            }
        });
    }

    // Initializes the Admin Control GUI, using a composite design.  Calls the initialization
    // methods for the GUI tree and GUI buttons
    private void initGUI() {

        initTree();
        initButton();

        basePanel = new JPanel();
        treeViewScrollPane = new JScrollPane(treeView);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        treeViewScrollPane.setViewportView(treeView);

        GroupLayout panelLayout = new GroupLayout(basePanel);
        basePanel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup().addContainerGap()
                        .addComponent(treeViewScrollPane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(addUser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent(addGroup, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent(openUserView, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(showUserTotal, GroupLayout.PREFERRED_SIZE, 141,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(showGroupTotal, GroupLayout.PREFERRED_SIZE, 141,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(showMessageTotal, GroupLayout.PREFERRED_SIZE, 141,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(showPosPercentage, GroupLayout.PREFERRED_SIZE, 141,
                                                GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap()));

        panelLayout.setVerticalGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelLayout
                .createSequentialGroup().addContainerGap()
                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(addUser, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(addGroup, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(openUserView, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(showUserTotal, GroupLayout.PREFERRED_SIZE, 80,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(showGroupTotal, GroupLayout.PREFERRED_SIZE, 80,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(showMessageTotal, GroupLayout.PREFERRED_SIZE, 80,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(showPosPercentage, GroupLayout.PREFERRED_SIZE, 80,
                                                GroupLayout.PREFERRED_SIZE)))
                        .addComponent(treeViewScrollPane))
                .addContainerGap()));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(basePanel,
                GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(basePanel,
                GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }

}
