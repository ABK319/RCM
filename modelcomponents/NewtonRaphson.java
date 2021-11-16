package modelcomponents;

import modelcomponents.RBCModel.ligeq1;
import modelcomponents.RBCModel.ligeq3;
import utilities.NWRunnerU;

public class NewtonRaphson {

	static double newtonRaphson(NWRunnerU r, Double initial, Double step, Double stop, Integer maxIts,
			Integer initialIts, int itCounter, boolean verbose) { //method used inside RBCModel class for optimization
		Double x3 = initial;
		int noIts = initialIts;
		Boolean finished = false;
		while (!finished) {
			Double x1 = x3 - step;
			Double y1 = r.run(x1);
			if (verbose) {
				System.out.println("x1: " + x1 + ", y1: " + y1);
			}
			Double x2 = x3 + step;
			Double y2 = r.run(x2);
			Double S = (y2 - y1) / (x2 - x1);
			x3 = x1 - (y1 / S);
			if (verbose) {
				System.out.println("It: " + noIts + ", x3: " + x3 + ", S: " + S + ", Y/S: " + y1 / S);
			}
			Double y3;
			if (r instanceof ligeq3 || r instanceof ligeq1) {
				y3 = y2;
			} else {
				y3 = r.run(x3);
			}
			if (verbose) {
				System.out.println("y3: " + y3);
				System.out.println();
			}
			noIts++;
			if (noIts > maxIts) {
				finished = true;
			}
			if (Math.abs(y3) < stop) {
				finished = true;
			}
			itCounter += 1;
		}
		return x3;
	}

}
