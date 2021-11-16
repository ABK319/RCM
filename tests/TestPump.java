package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Set;

import org.junit.jupiter.api.Test;


import utilities.ResultHash;

class TestPump {

	String protocols[] = { "PKG", "A", "B", "C", "D", "E", "F","G" };
	PackProtocolResults res = new PackProtocolResults();
	
	

	public static boolean compareHash(ResultHash a, ResultHash b) {
		Set<String> keys = b.map.keySet();
		
		
		if(a.map.keySet().size()!=keys.size()) 
		{
			return false; 
		}

		for (String key : keys) {
			if (!a.map.keySet().contains(key)) {
				System.out.println(key);
				return false;
				
			}

			else if (a.map.get(key).compareTo(b.map.get(key)) != 0) {
			System.out.println(key + " A " +  a.map.get(key)+ " B "+ b.map.get(key));
			return false;
			}

		}

		return true;
	}

	public static boolean testProtocol(String protocol, HashMap<String, ArrayList<ResultHash>> baseResults,
			HashMap<String, ArrayList<ResultHash>> testResults) {
		ArrayList<ResultHash> baseHashList = baseResults.get(protocol);
		ArrayList<ResultHash> testHashList = testResults.get(protocol);
		

		for (int i = 0; i < baseHashList.size(); i++) {

			if (compareHash(baseHashList.get(i), testHashList.get(i)) == false) {
				return false;
			}
		}

		return true;

	}
	
	
	@Test
	void testProtocolPKG() {
		
		assertTrue(testProtocol(protocols[0], res.baseResults, res.testResults),"Protocol PKG Failed");

	}

	@Test
	void testProtocolA() {

		assertTrue(testProtocol(protocols[1], res.baseResults, res.testResults),"Protocol A Failed");

	}

	@Test
	void testProtocolB() {

		assertTrue(testProtocol(protocols[2], res.baseResults, res.testResults),"Protocol B Failed");

	}

	@Test
	void testProtocolC() {

		assertTrue(testProtocol(protocols[3], res.baseResults, res.testResults),"Protocol C Failed");

	}

	@Test
	void testProtocolD() {

		assertTrue(testProtocol(protocols[4], res.baseResults, res.testResults),"Protocol D Failed");

	}

	@Test
	void testProtocolE() {

		assertTrue(testProtocol(protocols[5], res.baseResults, res.testResults),"Protocol E Failed");

	}

	@Test
	void testProtocolF() {

		assertTrue(testProtocol(protocols[6], res.baseResults, res.testResults),"Protocol F Failed");

	}

	@Test
	void testProtocolG() {

		assertTrue(testProtocol(protocols[7], res.baseResults, res.testResults),"Protocol G Failed");

	}
	
	public static void main (String [] args) 
	{
		ResultHash a = new ResultHash(null); 
		ResultHash b = new ResultHash(null); 
		

		HashMap<String,Double> j = new HashMap<>(); 
		
		HashMap<String,Double> k = new HashMap<>(); 
		
		j.put("A",1.0);
		j.put("B",2.0);
		j.put("C",3.0);
		
		k.put("A",1.0);
		k.put("B",2.0);
		k.put("C",3.0);
		
		a.map = j; 
		b.map = k; 
		
		System.out.println(compareHash(a,b));
	}

}
