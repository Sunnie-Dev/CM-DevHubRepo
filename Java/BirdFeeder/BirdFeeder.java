import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/* @ param : Author  Casey Munga - The Red Howler - March 3-2022
 * @ param : Design Patterns. Controller, Facade , aggregate and Composition 
 * @ param  : File Layout int 1 = number of birds that visit the feeder with unique birdSongs
 *       					int 2 = unique tweet frequency or BirdSongs from Birds
 *                          int 3 = lumens or light value
 *                          int 4 = state ( automatic or user remote) -1 = Automatic 0,1 = Human Intervention
 *                          
 *@ param : BirdSong range / SongList int {3,4,5}  all others are unwanted Birds
		*/

public class BirdFeeder{
	private int lumens; // amount of light at present
	private Doors door = new Doors(); // door on feeder to be activated
	private AudioSensor audioSensor; // sensor listens to bird tweets
	private OpticalSensor opticalSensor; // sensor that detects sunlight in lumens
	private RemoteControl remote; // used by user to manually open the door
	
	public ArrayList<Integer> songList; // valid range of songs/ frequencies for songBirds
    private ArrayList<Integer> tweets; // from file
    private ArrayList<Integer> feederData; // all data from file to process
    private int remoteAction; // from file
    private int opticalSensorAction; //from file
    private int birds; // from file
    private static boolean servoState;
    private static boolean doorState;
    
    public BirdFeeder() { // every Bird feeder will come with control and sensors activated
    	 opticalSensor = new OpticalSensor();
         remote = new RemoteControl();
         audioSensor= new AudioSensor();          
         songList = new ArrayList<Integer>();
         feederData = new ArrayList<Integer>();
    }
    
     public void getFeederLineData(int _birds, ArrayList<Integer> _tweets, int _optical, int _remote) { // gets an event line from the file
    	 // each line in the file signifies an event birds come or go from the feeder
    	 this.tweets=_tweets;
    	 this.opticalSensorAction = _optical;
    	 this.remoteAction = _remote;
    	 this.birds =_birds;
     }
     
  
     public void activateFeeder() {  // runs the operations of the feeder
    	 final int USER_CLOSE = 0;
    	 final int AUTOMATIC = -1;
    	 final int USER_OPEN = 1;
    	 final int DUSK = 25;
    	 
    	 // Servo is the mechanism to open the door. It must be activated
         // No birds - check lumens levels by processing light levels
         if (birds == 0 || opticalSensorAction <= DUSK) {
        	 opticalSensor.processLight(this.opticalSensorAction);
         } else {

        	 if (birds != 0 && this.remoteAction == USER_CLOSE) { // We have birds but the user overrides and presses the remote control button
        		 remote.buttonPress();
        	 }
        	if (birds != 0  && this.remoteAction == AUTOMATIC) { // There are birds lets check and no manual overrides then checks songlist for validity
        		audioSensor.processTweets();
        	}
        	
        	if (birds != 0 && this.remoteAction == USER_OPEN) {
        		remote.buttonPress();
        	}
         } 
     }
 

private class Doors{
	//private boolean doorIsOpen;
	boolean doorIsOpen;

	
	private Doors() {
		doorIsOpen = doorState; // normally false but is true for the use case to be set in driver
	}
	
	private void open() {
		/*checks to see of the door is closed. If it is then open it. If its already opened we can 
		another message that the door is already opened Its a good case for the remote control
		or if reading from a file. */
		if (doorIsOpen == false) {
			System.out.println("Opening Feeder Door.");
			doorIsOpen = true;
			doorState= doorIsOpen;
		}else
			System.out.println("Feeder Door opened.");
		 
	}
	
	private void close() {
		/*checks to see of the door is open. If it is then close it. If its already closed we can 
		another message that the door is already opened Its a good case for the remote control
		or if reading from a file. */
		if (doorIsOpen == true) {
			System.out.println("Closing Feeder Door.");
			doorIsOpen= false;
			doorState=doorIsOpen;
		}else
		 System.out.println("Feeder Door closed.");
	}
	
	private boolean isOpen() {
		doorState=doorIsOpen;
		return doorIsOpen; // do action and acknowledge
	}
}

private class  Servo{
	// Servo is a mechanical device to trigger the doors to open and close
	// Its states can be activated and deactivated. In the real life case the servo may work
	// but the doors may not. But if the Servo is not working triggering it with the remote control
	// or other devices will not work. We can keep a log of this as diagnostic data
	
	//private boolean activateServo;
	private Doors door;
	
	private Servo() {
		servoState = false; // initial state servo is deactivated
	    door = new Doors(); //
		
	}
	
	private void activate() {
		if(servoState == false) {
			System.out.println("Servo is activated."); // mechanism that triggers door
			servoState = true;
			door.open();
		}else System.out.println("Servo already activated.");
	}
	
	private void deactivate() {
		if(servoState == true) {
			System.out.println("Servo is deactivated.");
	        servoState = false;
	        door.close();
		}else System.out.println("Servo already deactivated.");
	}
	
	private boolean isActivated() {
		return servoState;
	}
}

 private class AudioSensor {
	 //automatic audio sensor to trigger the servo to activate the doors
	 private Servo servo;
	 //private BirdFeeder birdFeeder;
	 private boolean isSongBird;
	 
	 private AudioSensor(){
		 isSongBird = false;
		 servo=new Servo();
		 
	 }
	 
	private void processTweets() {
		//AI to detect the birds that arrive at the feeder
		//Songlist hold the list of SongBirds frequency/tweets
		if (songList.containsAll(tweets)) {   //checks to see birds are in the list
			System.out.println("Song Birds Arrive.");
			isSongBird = true;
			servo.activate();
		} else {
			System.out.println("Unwanted Birds Arrive.");
			servo.deactivate();
		}			
	}
 }
 
 private class OpticalSensor{
	 //Automatic light sensor to open at sunlight and close at sunset
	 final int SUNLIGHT = 40; // sunlight is 40 or higher
	 final int SUNSET = 25; // sunset is 25 or lower
	 int lumens;
	 private Servo servo;

	 private OpticalSensor () {
		 servo = new Servo();
		 lumens=0;
	 }
	 private void processLight(int light) {
		 lumens = light;
		 if (lumens >= SUNLIGHT ) {
			 System.out.println("There is sunlight.");
		     servo.activate();}
		 
		 if (lumens <= SUNSET) {
			 System.out.println("It is nightime.");
			 servo.deactivate();
		 }
	 }
}
 
private class RemoteControl{
	//User presses remote control button to open and close feeding door
	private Servo servo;
	boolean buttonPress;
	
	private RemoteControl() {
		buttonPress = false;
		servo = new Servo();
	}
	private void buttonPress() {
		System.out.println("User presses Remote Contol Button.");
		if(servo.isActivated()== true) { // means door is open
			servo.deactivate();
		}else
			servo.activate();
	}
 }	
}