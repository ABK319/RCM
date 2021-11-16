package tests;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


import utilities.DSSettings;
import utilities.ExampleProtocols;
import utilities.ExperimentalSettings;
import utilities.ResultHash;

public class FilteredProtocolResults {
	
	private HashMap<String,ArrayList<ResultHash>> results; 
	
	public FilteredProtocolResults() 
	{
		
	}
	
	void populateResults() 
	{
		
		String [] protocols = {"ResultsPkg","ResultsA","ResultsB","ResultsC","ResultsD","ResultsE","ResultsF"}; 
		
		
		for(int i = 0; i<protocols.length; i++) 
		{
			try
		    {
		        FileInputStream FIS = new FileInputStream(protocols[i]);
		        ObjectInputStream OIS = new ObjectInputStream(FIS);
		        ArrayList<ResultHash> protocolResult = new ArrayList();
		        protocolResult = (ArrayList) OIS.readObject();
		        
		        this.results.put(protocols[i],protocolResult); 

		        OIS.close();
		        FIS.close();
		        
		        
		    } 
		    
			catch (IOException ioe) 
		    {
		        ioe.printStackTrace();
		        return;
		    } 
		    
			catch (ClassNotFoundException c) 
		    {
		        System.out.println("Class not found");
		        c.printStackTrace();
		        return;
		    }
		}
		
		
		
		
	}
	
	public HashMap<String, ArrayList<ResultHash>> getResults() 
	{
		return this.results; 
	}
	
	
}

	
	
