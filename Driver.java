/*
 *  Name  : Shaylyn Wetts
 *  Class : CS 356 Object Oriented Design and Programming
 *  
 *  Date  : 11/08/2016
 *  
 *  Assignment 2
 *      Driver for the AdminControlPanelGUI
 */

package cs356_Assignment_2;

// Opens an instance of the AdminControlPanelGUI
public class Driver {

	public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminControlPanelGUI().setVisible(true);
            }
        });
	}

}
