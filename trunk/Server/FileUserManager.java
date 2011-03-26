import java.util.*;
import java.net.*;

import multitype.FrontEndUpdate;

public class FileUserManager {

	Map< Integer, OutputProcessor> outprocs; //userid 
	Map< Integer, MarkupProcessor> markupprocs; //fileid -> MarkupProcessor
	Map< Integer, String> filemap; //fileid -> filename
	Map< Integer, String> usermap; //userid -> username
	
	//fileid -> list of userids that have it open
	Map< Integer, Vector<Integer> > fileusermap; 
	
	int nextUID;
	
	public FileUserManager() {
		outprocs = new HashMap<Integer, OutputProcessor>();
		markupprocs = new HashMap<Integer, MarkupProcessor>();
		filemap = new HashMap<Integer,String>();
		usermap = new HashMap<Integer,String>();
		
		nextUID = 0;
		
	}
	
	/**
	 * Adds a new shared file
	 * @param fileid FileID for the file to be added (generated by host client).
	 * @param filename Filename to associate with this file.
	 */
	public void addFile(int fileid, String filename) {
		//TODO
		//spawn a new MarkupProcessor and add to Vector
	 	MarkupProcessor thisMarkupProc = new MarkupProcessor(this);
		markupprocs.put(fileid, thisMarkupProc);
		new Thread(thisMarkupProc).start();
		
	}
	
	/**
	 * Adds a new user
	 * @param uid UserID for the client to be added (from the FEU with username)
	 * @param username Username to associate with this username
	 */
	public void addUser(int uid, String username) {
		//TODO
		//associate username with userid
		usermap.put(uid,username);
	}
	
	/**
	 * Sends a Markup FEU to all the clients with that file open.
	 * @param feu The FEU to send.
	 */
	public void sendFEU(FrontEndUpdate feu) {
		//TODO
		//examine the fileid and only send to the output procs associated with that userid
		
		//For Build 1, just send to all clients
		for(OutputProcessor op : outprocs.values()) {
			op.addFEU(feu);
		}
	}

	/**
	 * Sends a FEU to a specific client 
	 * @param clientID userid of target client
	 * @param feu FEU to send
	 */
	public void sendFEUToClient(int clientID, FrontEndUpdate feu) {
		//TODO
		//examine the fileid and only send to the output procs associated with that userid
		
		//For Build 1, just send to all clients
		//for(OutputProcessor op : outprocs.values()) {
		//	op.addFEU(feu);
		//}
	}
	
	
	/**
	 * Adds a new client to the system
	 * Spawns a new output processor and responds to the client with its UserID
	 * @param s Socket to communicate with the client on
	 */
	public void addClient(Socket s) {
		//TODO
		//spawn an OutputProcessor and associate it with userid
	 	OutputProcessor thisOutputProc = new OutputProcessor(s);
		outprocs.put(nextUID, thisOutputProc);
		new Thread(thisOutputProc).start();
		
		//create a ConnnectionSucceeded Notification FEU
		//send it back to client immediately
		FrontEndUpdate feu = FrontEndUpdate.createNotificationFEU(
				FrontEndUpdate.NotificationType.Connection_Succeed, -1, nextUID, null);
		thisOutputProc.addFEU(feu);
		
		//generate next userid
		nextUID++;
	}

}
