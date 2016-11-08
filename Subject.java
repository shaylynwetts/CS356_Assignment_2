/*
 *  Name  : Shaylyn Wetts
 *  Class : CS 356 Object Oriented Design and Programming
 *  
 *  Date  : 11/08/2016
 *  
 *  Assignment 2
 *      Subject class for observer design pattern implementation.
 */

package cs356_Assignment_2;

import java.util.ArrayList;
import java.util.List;

import cs356_Assignment_2.Observer;

public abstract class Subject {
    private List<Observer> observers = new ArrayList<Observer>();
    
    public void attach(Observer observer) {
        observers.add(observer);
    }
    
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers() {
        for (Observer observe : observers) {
            observe.update(this);
        }
    }
            
}
