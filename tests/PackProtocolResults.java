package tests;

import java.util.ArrayList;


import java.util.HashMap;

import modelcomponents.RBCModel;
import oldclasses.RBC_modelOld;
import utilities.DSSettings;
import utilities.ExampleProtocols;
import utilities.ExperimentalSettings;
import utilities.ResultHash;

public class PackProtocolResults {
	
	HashMap<String,ExperimentalSettings> settings; 
	HashMap<String,ArrayList<ResultHash>> baseResults;
	HashMap<String,ArrayList<ResultHash>> testResults;
	
	
	RBC_modelOld baseModel; 
	RBCModel testModel; 
	
	
	

	public PackProtocolResults() 
	{
		this.settings = new HashMap<String, ExperimentalSettings>(); 
		this.baseResults = new HashMap<String, ArrayList<ResultHash>>(); 
		this.testResults = new HashMap<String, ArrayList<ResultHash>>(); 
		this.baseModel =  new RBC_modelOld();
		this.testModel =  new RBCModel();
		
		this.populateSettings();
		this.populateResult(); 
		
	}
	
	public void populateResult() 
	{
		for(String key : this.settings.keySet()) 
		{
		  baseResults.put(key,computebaseResult(settings.get(key)));
		  testResults.put(key,computeNewResult(settings.get(key)));
		}
		
		
	}
	
	
	public void populateSettings() {
		
		ExperimentalSettings experimentalSettingsPKG = ExampleProtocols.getPKG();
		this.settings.put("PKG",experimentalSettingsPKG);
		
		ExperimentalSettings experimentalSettingsA = ExampleProtocols.getA();
		this.settings.put("A",experimentalSettingsA);
		
		ExperimentalSettings experimentalSettingsB = ExampleProtocols.getB();
		this.settings.put("B",experimentalSettingsB);
		
		ExperimentalSettings experimentalSettingsC = ExampleProtocols.getC();
		this.settings.put("C",experimentalSettingsC);
		
		ExperimentalSettings experimentalSettingsD = ExampleProtocols.getD();
		this.settings.put("D",experimentalSettingsD);
		
		ExperimentalSettings experimentalSettingsE = ExampleProtocols.getE();
		this.settings.put("E",experimentalSettingsE);

		ExperimentalSettings experimentalSettingsF = ExampleProtocols.getF();
		this.settings.put("F",experimentalSettingsF);
		
		ExperimentalSettings experimentalSettingsG = ExampleProtocols.getG();
		this.settings.put("G",experimentalSettingsG);
		
	}
	
	public ArrayList<ResultHash> computebaseResult(ExperimentalSettings experimentalSettings) {
		
		
		ArrayList<String> usedoptionsRS = new ArrayList<String>();
		ArrayList<String> usedoptions = new ArrayList<String>();
		//
		baseModel.setup(experimentalSettings.getRSOptions(), usedoptionsRS);
		for(DSSettings d: experimentalSettings.getDSStages()) {
			baseModel.setupDS(d.getOptions(), usedoptions);
			baseModel.runall(null);
		}
		
		ArrayList<ResultHash> result = this.baseModel.getResults();
		return result; 
		
	}
	
	public ArrayList<ResultHash> computeNewResult(ExperimentalSettings experimentalSettings) {
		
		
		ArrayList<String> usedoptionsRS = new ArrayList<String>();
		ArrayList<String> usedoptions = new ArrayList<String>();
		//
		testModel.setup(experimentalSettings.getRSOptions(), usedoptionsRS);
		for(DSSettings d: experimentalSettings.getDSStages()) {
			testModel.setupDS(d.getOptions(), usedoptions);
			testModel.runall(null);
		}
		
		ArrayList<ResultHash> result = this.testModel.getResults();
		return result; 
		
	}

}
