import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BirdFeederFacade {
	private int birdSong[] = {3,4,5}; // approved BirdSongs
	private	int _birds = 0; // number of unique birds that arrive at the feeder
	private	int _optical =0; //lumens-  amount of sunlight 
	private	int _remote = 0; // remote control used or not
	private	ArrayList<Integer> _tweets; // bird tweets
	private	ArrayList<Integer> feederData; // Data from the file
	private BirdFeeder feeder;
		
		BirdFeederFacade(){
			feeder = new BirdFeeder();
			if (getFeederData()) {
				initSongList();
			
				
				// Process one line of data ( birds, birdSongs, sunLevel, remoteUsed)	
				// send line data to the feeder class to be processed
				// Activate the Bird Feeder
				for(int i=0; i < feederData.size(); i++) {
					  _birds = feederData.get(i); i++;
					  if(_birds == 0) {
						  _tweets=null;
						  i++;
					  } else {
						  _tweets = new ArrayList<Integer>();
						  for (int j=0; j< _birds; j++) {
							  _tweets.add(feederData.get(i));
							  i++;
						  }
					  }
					  _optical = feederData.get(i); 
					  i++; // optical sensor
					  _remote = feederData.get(i);
					  feeder.getFeederLineData(_birds, _tweets, _optical, _remote);
					  feeder.activateFeeder();
				} 
			}
		}
		private void initSongList() {
			for (int i=0; i< birdSong.length; i++) 
			feeder.songList.add(birdSong[i]);
		}

		
		private boolean getFeederData() {
			//read all user and bird data from file 
			boolean fileFound = true;
			File myObj = new File ("C:/Temp/FeederData.txt");
			Scanner fileIn = null;
			try {
				fileIn = new Scanner(myObj);
				 feederData = new ArrayList<Integer>() ;
				 while(fileIn.hasNextInt()) {
						feederData.add(fileIn.nextInt());
					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(" The file C:/Temp/FeederData.txt has does not exist - Create please and try again!");
				fileFound = false;
				//e.printStackTrace();
			}

		   if (fileIn != null) {
			fileIn.close(); // close file
			System.out.println(feederData);
			}
		   return fileFound;
		} 
		
}
