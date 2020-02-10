package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Test2 {

	public static void main(String[] args) {
		File file = new File("C:\\Shashank\\input.txt");
		try {
			Scanner sc = new Scanner(file);
			List<SimpleEntry<String, Integer>> sessions = new ArrayList<SimpleEntry<String, Integer>>();
			List<SimpleEntry<String, Integer>> finalsessions = new ArrayList<SimpleEntry<String, Integer>>();
			while (sc.hasNextLine()) {
				String sessionDetail = sc.nextLine();
				String[] split = sessionDetail.split(" ");
				int time = 0;
				switch(split[split.length - 1]) {
					case "60min": time = 60; break;
					case "45min": time = 45; break;
					case "30min": time = 30; break;
					default: time= 5; break;
				}
				sessions.add(new SimpleEntry<String, Integer>(sessionDetail, new Integer(time)));
			}
			Random randomGenerator = new Random();
			int randomSelectSession = randomGenerator.nextInt(sessions.size());
			
			conference(sessions, finalsessions, 180, randomSelectSession);
			finalsessions.add(new SimpleEntry<String, Integer>("Lunch", 60));
			conference(sessions, finalsessions, 240, randomSelectSession);
			
			finalsessions.add(new SimpleEntry<String, Integer>("Netwrorking Event", 1));
			
			LocalTime time = LocalTime.of(9, 0);
			long prevOffset = 0;
			for(int k=0; k< finalsessions.size(); k++) {
				time = time.plusMinutes(prevOffset);
				System.out.println(time +" "+finalsessions.get(k).getKey());
				prevOffset = finalsessions.get(k).getValue().longValue();
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}

	}
	
	public static void conference(List<SimpleEntry<String, Integer>> sessions, List<SimpleEntry<String, Integer>> finalsessions, int sessionDurationAMPM, int startIndex) {
		if(sessionDurationAMPM ==0){
			return;
		}
		if(startIndex < sessions.size()-1) {
			int sessionTime = sessions.get(startIndex).getValue().intValue();
			if(sessionTime != 5 && ((sessionDurationAMPM - sessionTime >= 30 ) || (sessionDurationAMPM - sessionTime == 0))){
				sessionDurationAMPM = sessionDurationAMPM - sessionTime;
				finalsessions.add(sessions.get(startIndex));
				sessions.remove(startIndex);
			}
			startIndex +=1;
			conference(sessions, finalsessions, sessionDurationAMPM, startIndex);
		} else {
			conference(sessions, finalsessions, sessionDurationAMPM, 0);
		}
	}

}
