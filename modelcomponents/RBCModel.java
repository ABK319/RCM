package modelcomponents;

import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import utilities.MileStone;
import utilities.NWRunnerU;
import utilities.ResultHash;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class RBCModel {

	private String[] PublishOrder = { "V/V", "Vw", "CVF", "Hct", "MCHC", "Density", "Em", "pHi", "pHo", "rH", "rA",
			"QNa", "QK", "QA", "QCa", "QMg", "CH/nM", "CNa", "CK", "CA", "CCa2+", "CMg2+", "CHb", "fHb", "nHb",
			"fHb*CHb", "nHb*CHb", "CX-", "nX", "nX*CX-", "COs", "MOs", "MNa", "MK", "MA", "MH", "MB", "MCat", "MCa2+",
			"MMgt", "MMg2+", "MEDGTA", "Msucrose", "Mgluconate", "Mglucamine", "FNaP", "FKP", "FCaP", "FHCaP",
			"FKGgardos", "FCaG", "FNaG", "FKG", "FAG", "FHG", "FAJS", "FHJS", "FClCo", "FKCo", "FNaCo", "FA23Ca",
			"FA23Mg", "FNa", "FK", "FA", "FH", "FCa", "FW", "EA", "EH", "EK", "ENa", "FzK", "FzNa", "FzA", "FzCa",
			"EN test" };

	private ArrayList<ResultHash> resultList = new ArrayList<ResultHash>();
	SetUpModel model = new SetUpModel();
	private ResultHash finalPiezoResult;

	private double samplingTime = 0.0;
	private double durationExperiment = 0.0;
	private double T6 = 0.0;
	private double dedgh = 0.0;
	private double ligchoice = 0.0;
	private double edghk1 = 0.0;
	private double edghk2 = 0.0;
	private double edgcak = 0.0;
	private double edgmgk = 0.0;
	private double edg4 = 0.0;
	private double edg3 = 0.0;
	private double edg2 = 0.0;
	private double edgmg = 0.0;
	private double edgca = 0.0;
	private double edgto = 0.0;
	private double edgneg = 0.0;
	private double edghnew = 0.0;
	private double edghold = 0.0;
	private double VV = 0.0;
	private double Em = 0.0;
	private double rA = 0.0;
	private double rH = 0.0;
	private double fHb = 0.0;
	private double nHb = 0.0;
	private double fraction = 0.0;
	private double PT = 0.0;
	private double clGExchange = 0.0;
	private double mgb = 0.0;
	private double mgb0;
	private double cabb = 0.0;
	private double finalPiezoCCa = 0.0;
	private double finalPiezoFK = 0.0;
	private double finalPiezoFNa = 0.0;
	private double finalPiezoFA = 0.0;
	private double finalPiezoFCa = 0.0;
	private double pka = 0.0;
	private double pkhepes = 0.0;
	private double benz2;
	private double benz2k = 0.0;
	private double temp = 0.0; // placeholder for pump
	private double em = 0.0; // placeholder for pump
	private double netChargeHb = 0.0;
	private double mchc = 0.0;
	private double density = 0.0;
	private double finalPiezoHct = 0.0;
	private double ff = 0.0;
	private double hematocritFraction = 0.0;
	private double nX = 0.0;
	private double VwT0 = 0.0;
	private double mPhT0 = 0.0;
	private double hbContent;

	private double virialCoefficientB = 0.0645;; // verial coefficients of osmotic coefficient of haemo (fhb)
	private double virialCoefficientC = 0.0258;
	private double initialHematocrit = 0.0;
	private double kB = 2.813822508658947e-08;
	private double diff = 0.00001;
	private double fG = 0.1;
	private double naToK = 1.5;
	private double tempCelsius = 37.0;
	double Q10Passive = 2.0;
	private double integrationIntervalFactor = 0.01; // Integration factor

	private double ptcSegment;
	private double pit0;
	private double parseTemp;
	private double deltaTime; // Initial value in case it is not computed
	private double deltaH;
	private double deltaNa;
	private double deltaK;
	private double deltaA;
	private double deltaWater;
	private double deltaMg;
	private double deltaCa;
	private double Vw;
	private double waterVolume;
	private double defaultFraction;
	private double cbenz2;
	private double b1ca;
	private double b1cak;
	private double alpha;
	private double atp;
	private double dpgp;
	private double vlysis;
	private double oldJSPermeability;
	private double pI;
	private double ligHb;

	private String BufferType = "HEPES";

	private int totalCycleCount = 0;
	private int itCounter;
	private int stage = 0;
	private int cycleCount = 0;
	private int cyclesPerPrint = 777;
	private int nIts = 0;
	private int dp; // number of decimal places in the output

	private boolean lifespan = false;
	private boolean isCancelled = false;
	private boolean computeDeltaTime = true;
	private boolean verbose = true;
	private boolean doPublish = true;

	public RBCModel() {

		setPtcSegment(-10.0);
		setPit0(7.2);
		setHbContent(34.0);
		setWaterVolume(0.75);
		setVw(0.0);
		setMgb0(0.0);
		setBenz2(0.0);
		setCbenz2(0.0);
		setB1ca(0.0);
		setB1cak(0.0);
		setAlpha(0.0);
		setAtp(1.2);
		setDpgp(0.0);
		setVlysis(1.45);

	}

	// ***********RUNNER FUNCTIONS ***************//

	private void startPiezo() {

		model.piezo.setOldCycles(this.cyclesPerPrint);
		this.cyclesPerPrint = model.piezo.getCycles();
		this.cycleCount = this.cyclesPerPrint - 1; // forces an output now

		model.piezoGoldman.setPermeabilityK(model.piezo.getPkg());
		model.piezoGoldman.setPermeabilityNa(model.piezo.getPnag());
		model.piezoGoldman.setPermeabilityA(model.piezo.getPag());

		model.piezoPassiveca.setFcalm(model.piezo.getPcag());

		/*
		 * Change in following line to save the old PMCA as whatever the current value
		 * is, for replacing after transit period
		 */
		model.piezo.setOldPMCA(model.capump.getFCaPM());

		double fac = (100.0 - model.piezo.getPmca()) / 100.0;
		model.capump.setFCaPM(model.capump.getDefaultFCaPM() * fac);

		model.piezo.setOldIF(this.integrationIntervalFactor);
		this.integrationIntervalFactor = model.piezo.getiF();

		double jsfactor = model.piezo.getPiezoJS();
		oldJSPermeability = model.JS.getPermeability();
		model.JS.setPermeability(model.JS.getDefaultPermeability() * jsfactor);

		this.setFraction(model.piezo.getPiezoFraction());

		if (this.getInitialHematocrit() != this.getFraction()) {
			this.setInitialHematocrit(this.getFraction());
			this.setHematocritFraction(this.getInitialHematocrit() / (1.0 - this.getInitialHematocrit()));
		}
		System.err.println("Fraction @ Piezo: " + this.getFraction());
	}

	private void stopPiezo() {

		// Save the final fluxes
		saveFinalPiezoFluxes();

		this.cycleCount = this.cyclesPerPrint - 1; // forces an output now
		model.piezoGoldman.setPermeabilityK(0.0);
		model.piezoGoldman.setPermeabilityNa(0.0);
		model.piezoGoldman.setPermeabilityA(0.0);

		this.finalPiezoHct = this.getFraction() * 100.0;
		this.finalPiezoCCa = model.cell.Cat.getConcentration();
		System.err.println(this.finalPiezoHct);

		this.finalPiezoResult = makeResultHash();

		model.piezoPassiveca.setFcalm(0.0);
		model.capump.setFCaPM(model.piezo.getOldPMCA());

		model.JS.setPermeability(oldJSPermeability);

		this.setFraction(this.getDefaultFraction());
		if (this.getInitialHematocrit() != this.getFraction()) {
			this.setInitialHematocrit(this.getFraction());
			this.setHematocritFraction(this.getInitialHematocrit() / (1.0 - this.getInitialHematocrit()));
		}

		if (model.piezo.getRestoreMedium()) {
			System.err.println("RESTORING");
			this.restoreMedium();
			this.publish();
		}

	}

	private void endPiezo() {
		this.cyclesPerPrint = model.piezo.getOldCycles();
		this.cycleCount = this.cyclesPerPrint - 1; // forces an output now
		this.integrationIntervalFactor = model.piezo.getOldIF();
	}

	public void output(String ad, JTextArea ta) {
		if (this.verbose) {
			if (ta == null) {
				System.out.println(ad);
			} else {
				ta.append(ad + '\n');
			}
		}
	}

	public void runall(JTextArea ta) { //run the model

		this.setIsCancelled(false);

		this.output("RUNNING DS STAGE " + this.stage, ta);
		this.output("Current Sampling time: " + 60.0 * this.samplingTime, ta);
		this.output("Running until: " + this.durationExperiment, ta);

		this.cycleCount = 0;
		this.nIts = 0;
		this.output("Publishing at t=" + 60.0 * this.samplingTime, ta);

		this.publish();

		ArrayList<MileStone> mileStones = new ArrayList<MileStone>();
		// Note milestones are always in *hours*
		if (model.piezo != null) {
			// We have a piezo stage
			if (model.piezo.getStartTime() > 0.0) {
				// Add the initial output points
				for (int i = 0; i < 3; i++) { // Only need to loop 3 times as we always publish at the start
					double outputTime = (0.5 + i * 0.5) / 60.0 + this.samplingTime;
					mileStones.add(new MileStone(outputTime, "PUBLISH"));
				}
			}

			double pStop = (model.piezo.getStartTime() + this.samplingTime) + model.piezo.getDuration();
			double pEnd = pStop + model.piezo.getRecovery();
			mileStones.add(new MileStone(model.piezo.getStartTime() + this.samplingTime, "PIEZO START"));
			mileStones.add(new MileStone(pStop, "PIEZO STOP"));

			double endTime = this.durationExperiment / 60.0;
			if (pEnd >= endTime) {
				pEnd = endTime - 1e-6;
			}
			mileStones.add(new MileStone(pEnd, "PIEZO END"));

		}
		// Add the END milestone always
		mileStones.add(new MileStone(this.durationExperiment / 60.0, "END"));

		// Check the ordering
		for (int i = 0; i < mileStones.size(); i++) {
			System.err.println(mileStones.get(i).getName() + " " + mileStones.get(i).getTime());
		}
		if (mileStones.size() > 1) {
			for (int i = 1; i < mileStones.size(); i++) {
				if (mileStones.get(i).getTime() <= mileStones.get(i - 1).getTime()) {
					System.err.println("MILESTONES NOT IN ORDER");
					return;
				}
			}
		}

		if (this.verbose) {
			System.out.println("Milestones OK!");
		}
		int mileStonePos = 0;
		String mileStoneOperation = null;

		while (this.samplingTime * 60 <= this.durationExperiment) {
			if (mileStoneOperation != null) {
				this.output(mileStoneOperation, ta);
				if (mileStoneOperation.equals("END")) {
					break;
				}
				if (mileStoneOperation.equals("PUBLISH")) {
					this.cycleCount = this.cyclesPerPrint - 1; // Force a publish
				}
				if (mileStoneOperation.equals("PIEZO START")) {
					startPiezo();
				}
				if (mileStoneOperation.equals("PIEZO STOP")) {
					stopPiezo();
				}
				if (mileStoneOperation.equals("PIEZO END")) {
					endPiezo();
				}
			}

			model.getNapump().computeFlux(temp, em, this.tempCelsius);
			// Temperature dependence of the passive fluxes, uses Q10L
			this.PT = Math.exp(((37.0 - this.tempCelsius) / 10.0) * Math.log(this.Q10Passive));
			model.carriermediated.computeFlux(temp, em, this.PT);
			model.cotransport.computeFlux(temp, em, this.PT);
			model.JS.computeFlux(temp, em, this.PT);

			this.Em = NewtonRaphson.newtonRaphson(new computeAllFluxes(), this.Em, 0.001, 0.0001, 100, getItCounter(),
					0, false);

			model.totalIonFluxes();
			model.water.computeFlux(this.fHb, this.getCbenz2(), model.bufferConc, this.getEdgto(), this.PT);

			MileStone nextMileStone = mileStones.get(mileStonePos);
			if (this.integrationInterval(nextMileStone)) { // cycleCount is increased here
				mileStoneOperation = nextMileStone.getName();
				mileStonePos += 1;
				this.cycleCount = this.cyclesPerPrint; // Force an output
			} else {
				mileStoneOperation = null;
			}
			this.computeDeltas();

			this.updateContents();

			model.cell.Cat.setConcentration(model.cell.Cat.getAmount() / this.getVw());
			this.setCbenz2(this.getBenz2() / this.getVw());

			this.chBeTc();

			this.rA = model.medium.A.getConcentration() / model.cell.A.getConcentration(); // 28d
			this.rH = model.cell.H.getConcentration() / model.medium.H.getConcentration(); // 28c
			if (this.getVw() > this.getVlysis()) {
				// Cells have been lysed
				break;
			}

			if (this.getClGExchange() > 0 && this.getT6() > 0) {
				if (this.samplingTime * 60 > this.getT6()) {
					model.medium.Gluconate
							.setConcentration(model.medium.Gluconate.getConcentration() - this.getClGExchange());
					model.medium.A.setConcentration(model.medium.A.getConcentration() + this.getClGExchange());
					this.setClGExchange(0.0);
				}
			}

			if (this.cycleCount == this.cyclesPerPrint) {
				this.output("Publishing at t=" + 60.0 * this.samplingTime, ta);
				this.publish();
				this.cycleCount = 0;
			}

			this.totalCycleCount += 1;

			if (getIsCancelled()) {
				this.output("CANCELLED", ta);
				break;
			}
		}
		this.output("Publishing at t=" + 60.0 * this.samplingTime, ta);
		this.publish();
		this.output("Finished!", ta);

	}

	private class computeAllFluxes implements NWRunnerU {
		public double run(double local_em) {
			model.getGoldman().computeFlux(local_em, tempCelsius, PT);
			model.piezoGoldman.computeFlux(local_em, tempCelsius, PT);
			model.a23.computeFlux(local_em, tempCelsius, PT);
			model.passiveca.computeFlux(local_em, tempCelsius, PT);
			model.piezoPassiveca.computeFlux(local_em, tempCelsius, PT);
			model.getCaPump().computeFlux(local_em, tempCelsius, em);
			model.totalCaFlux();
			model.totalFlux();
			return model.totalFlux;

		}
	}

	// **********SET UP *************//

	public void setup(HashMap<String, String> rsoptions, ArrayList<String> usedoptions) {
		if (this.stage == 0) {

			String temp = rsoptions.get("NaCl");
			if (temp != null) {
				model.cell.Na.setConcentration(Double.parseDouble(temp));
				model.cell.Na.setAmount(Double.parseDouble(temp) * this.getVw());
				usedoptions.add("NaCl");
			}

			temp = rsoptions.get("KCl");
			if (temp != null) {
				model.cell.K.setConcentration(Double.parseDouble(temp));
				model.cell.K.setAmount(Double.parseDouble(temp) * this.getVw());
				usedoptions.add("KCl");
			}

			System.out.println("Setting up the RS");
			OptionsParsers.naPumpScreenRS(rsoptions, usedoptions, this);
			OptionsParsers.cellWaterScreenRS(rsoptions, usedoptions, this);
			OptionsParsers.cellanionProtonScreenRS(rsoptions, usedoptions, this);
			OptionsParsers.chargeAndPiScreenRS(rsoptions, usedoptions, this);

			this.cyclesPerPrint = 777;
			this.setVw(this.getWaterVolume());
			this.setFraction(1e-4);
			this.setDefaultFraction(1e-4);
			model.medium.setpH(7.4);
			this.setmPhT0(model.medium.getpH());
			this.VwT0 = 1.0 - this.getHbContent() / 136.0;
			this.samplingTime = 0.0;

			this.setMgDefaults();
			this.setCaDefaults();

			OptionsParsers.mgBufferScreenRS(rsoptions, usedoptions, this);
			OptionsParsers.caBufferScreenRS(rsoptions, usedoptions, this);

			this.computeRS();

			System.out.println("Used RS options");
			for (String option : usedoptions) {
				System.out.println(option);
			}

			System.out.println();
			System.out.println("Unused RS options");
			for (String option : rsoptions.keySet()) {
				if (!usedoptions.contains(option)) {
					System.out.println(option);
					JOptionPane.showMessageDialog(null, "Didn't recognise " + option + " for RS.");
				}
			}

		}
	}

	public void setupDS(HashMap<String, String> options, ArrayList<String> usedoptions) { //set up dynamic state
		
		OptionsParsers.setScreenTimeFactorOptions(options, usedoptions, this);
		OptionsParsers.setCellFractionOptions(options, usedoptions, this);
		OptionsParsers.setTransportChangesOptions(options, usedoptions, this);
		OptionsParsers.setTempPermeabilityOptions(options, usedoptions, this);
		OptionsParsers.setPiezoOptions(options, usedoptions, this);
		if (this.verbose) {
			System.out.println();
			System.out.println("Used DS options");
			for (String option : usedoptions) {
				System.out.println(option);
			}

			System.out.println();
			System.out.println("Unused DS options");
			for (String option : options.keySet()) {
				if (!usedoptions.contains(option)) {
					System.out.println(option);
					JOptionPane.showMessageDialog(null, "Didn't recognise " + option + " for DS - tell Simon!");
				}
			}
		}
		this.stage += 1;
		System.out.println("Setup DS, stage = " + this.stage);
		// this.publish();
		for (String option : options.keySet()) {
			if (!usedoptions.contains(option)) {
				System.out.println("Not used: " + option);
			}
		}
	}

	

	// ******************************** Calculations **********************//

	private void updateContents() { //change values of cell and medium at appropriate time 

		double VwOld = this.getVw();

		this.setVw(this.getVw() + this.deltaWater);

		model.cell.Hb.setConcentration(model.cell.Hb.getAmount() / this.getVw()); // 24f

		double hbSquare = Math.pow(model.cell.Hb.getConcentration(), 2);
		this.fHb = 1.0 + this.virialCoefficientB * model.cell.Hb.getConcentration()
				+ this.virialCoefficientC * hbSquare; // 25

		model.cell.Na.setAmount(model.cell.Na.getAmount() + this.deltaNa); // 20g
		model.cell.K.setAmount(model.cell.K.getAmount() + this.deltaK); // 20h
		model.cell.A.setAmount(model.cell.A.getAmount() + this.deltaA); // 20i
		this.netChargeHb = this.netChargeHb + this.deltaH; // 21a

		this.nHb = this.netChargeHb / model.cell.Hb.getAmount();
		model.cell.Mgt.setAmount(model.cell.Mgt.getAmount() + this.deltaMg); // 20j
		model.cell.Cat.setAmount(model.cell.Cat.getAmount() + this.deltaCa); // 20k

		// Cell pH and cell proton concentration
		model.cell.setpH(this.pI + this.nHb / this.getPtcSegment()); // 21c
		model.cell.H.setConcentration(Math.pow(10, (-model.cell.getpH()))); // 21d
		this.nHb = this.getPtcSegment() * (model.cell.getpH() - this.pI); // 21c
		this.VV = (1 - this.VwT0) + this.getVw(); // 26a
		this.mchc = this.getHbContent() / this.VV; // 23d
		this.density = (this.getHbContent() / 100 + this.getVw()) / this.VV; // 23e
		this.setFraction(this.getInitialHematocrit() * this.VV); // 23f

		// Cell concentrations and external concentrations
		model.cell.Na.setConcentration(model.cell.Na.getAmount() / this.getVw()); // 24a
		model.cell.K.setConcentration(model.cell.K.getAmount() / this.getVw()); // 24b
		model.cell.A.setConcentration(model.cell.A.getAmount() / this.getVw()); // 24c

		// compute mgf
		model.cell.Mgf.setConcentration(
				NewtonRaphson.newtonRaphson(new Eqmg(), 0.02, 0.0001, 0.00001, 100, getItCounter(), 0, false)); // 9.f?

		updateMedium(VwOld);

		this.canr();
	}

	private void updateMedium(double x) //change the values of the medium at appropriate times

	{
		double minBufferConcentration = 1e-10;

		// 27 a-j
		double intermediate = 1 + (this.getVw() - x) * this.getHematocritFraction();
		model.medium.Na.setConcentration(
				model.medium.Na.getConcentration() * intermediate - this.deltaNa * this.getHematocritFraction());
		model.medium.K.setConcentration(
				model.medium.K.getConcentration() * intermediate - this.deltaK * this.getHematocritFraction());
		model.medium.A.setConcentration(
				model.medium.A.getConcentration() * intermediate - this.deltaA * this.getHematocritFraction());
		model.medium.Gluconate.setConcentration(model.medium.Gluconate.getConcentration() * intermediate);
		model.medium.Glucamine.setConcentration(model.medium.Glucamine.getConcentration() * intermediate);
		model.medium.Sucrose.setConcentration(model.medium.Sucrose.getConcentration() * intermediate);
		model.bufferConc = model.bufferConc * intermediate;
		model.medium.Hb.setConcentration(model.medium.Hb.getConcentration() * intermediate);

		// Medium proton, Ca2+, Mg2+, free and bound buffer and ligand concentrations
		model.medium.Mgt.setConcentration(
				model.medium.Mgt.getConcentration() * intermediate - this.deltaMg * this.getHematocritFraction());
		model.medium.Cat.setConcentration(
				model.medium.Cat.getConcentration() * intermediate - this.deltaCa * this.getHematocritFraction());
		this.setEdgto(this.getEdgto() * intermediate);
		if (this.getEdgto() == 0) {
			model.medium.Mgf.setConcentration(model.medium.Mgt.getConcentration());
			model.medium.Caf.setConcentration(model.medium.Cat.getConcentration());
		}
		if (this.getLigchoice() != 0) {
			this.edgta();
		} else {
			model.medium.Hb.setConcentration(
					model.medium.Hb.getConcentration() * intermediate - this.deltaH * this.getHematocritFraction());
			model.medium.H.setConcentration(this.kB
					* (model.medium.Hb.getConcentration() / (model.bufferConc - model.medium.Hb.getConcentration()))); // 28a
			if (model.medium.H.getConcentration() < minBufferConcentration) {
				model.medium.H.setConcentration(minBufferConcentration);
			}
			model.medium.setpH(-Math.log(model.medium.H.getConcentration()) / Math.log(10.0));
			if (Double.isNaN((model.medium.getpH()))) {
				System.out.println("Warning: pH = NaN, Buffer conc = " + model.medium.H.getConcentration());
			}
		}
	}

	private void computeDeltas() { // 20 a..k
		this.deltaNa = model.totalFluxNa * this.deltaTime;
		this.deltaK = model.totalFluxK * this.deltaTime;
		this.deltaA = model.totalFluxA * this.deltaTime;
		this.deltaH = model.totalFluxH * this.deltaTime;
		this.deltaWater = model.water.getFlux() * this.deltaTime;
		this.deltaMg = model.a23.getFluxMg() * this.deltaTime;
		this.deltaCa = model.totalFluxCa * this.deltaTime;
	}

	private boolean integrationInterval(MileStone nextMileStone) {
		boolean mileStoneFound = false;
		// 8010 Integration interval
		double I23 = 10.0 + 10.0 * Math.abs(model.a23.getFluxMg() + model.totalFluxCa)
				+ Math.abs(model.goldman.getFluxH() + model.piezoGoldman.getFluxH()) + Math.abs(this.dedgh)
				+ Math.abs(model.totalFluxNa) + Math.abs(model.totalFluxK) + Math.abs(model.totalFluxA)
				+ Math.abs(model.totalFluxH) + Math.abs(model.water.getFlux() * 100.0);
		if (this.computeDeltaTime) {
			this.deltaTime = this.integrationIntervalFactor / I23;
		} else {
			// deltaTime is a constant, but check if it's much bigger than calculated value
			// and issue a warning if it is
			double temp = this.integrationIntervalFactor / I23;
			if (temp < this.deltaTime) {
				System.out.println("WARNING: fixed deltaTime is greater than computed value");
			}
		}
		if (nextMileStone.check(this.samplingTime + this.deltaTime)) {
			// We've passed the milestone
			this.deltaTime = nextMileStone.getTime() - this.samplingTime;
			mileStoneFound = true;
		}
//		System.out.println("DT: " + this.deltaTime);
		this.samplingTime = this.samplingTime + this.deltaTime;
//		System.out.println("ST: " + this.samplingTime);
		this.cycleCount = this.cycleCount + 1;
		this.nIts = this.nIts + 1;
		return mileStoneFound;
	}

	private void restoreMedium()  { //return orignal medium values

		if (this.lifespan) {
			model.bufferConc = model.piezo.getRestoreHepesNa();
			model.medium.setpH(model.piezo.getRestorepH());

			model.medium.H.setConcentration(Math.pow(10, -model.medium.getpH())); // 21d
			model.medium.Hb.setConcentration(model.bufferConc
					* (model.medium.H.getConcentration() / (this.kB + model.medium.H.getConcentration())));

			if (model.piezo.getRestoreNa() > 0) {
				model.medium.Na.setConcentration(model.piezo.getRestoreNa());
			}

			if (model.piezo.getRestoreK() > 0) {
				model.medium.K.setConcentration(model.piezo.getRestoreK());
			}

			model.medium.Caf.setConcentration(model.piezo.getRestoreCa());
			model.medium.Cat.setConcentration(model.piezo.getRestoreCa());

			model.medium.Mgf.setConcentration(model.piezo.getRestoreMg());
			model.medium.Mgt.setConcentration(model.piezo.getRestoreMg());
		} else {

			HashMap<String, String> tempOptions = new HashMap<String, String>();
			tempOptions.put("MMg", "" + model.piezo.getRestoreMg());
			tempOptions.put("MCa", "" + model.piezo.getRestoreCa());
			tempOptions.put("pHo", "" + model.piezo.getRestorepH());
			tempOptions.put("MB", "" + model.piezo.getRestoreHepesNa());

			if (model.piezo.getRestoreNa() > 0) {
				double deltaNa = model.piezo.getRestoreNa() - model.getMediumNaConcentration();
				tempOptions.put("NaCl add/remove", "" + deltaNa);
			}
			if (model.piezo.getRestoreK() > 0) {
				double deltaK = model.piezo.getRestoreK() - model.getMediumKConcentration();
				tempOptions.put("KCl add/remove", "" + deltaK);

			}

			OptionsParsers.setCellFractionOptions(tempOptions, new ArrayList<String>(), this);
		}
	}

	private void saveFinalPiezoFluxes() {
		this.finalPiezoFK = model.piezoGoldman.getFluxK();
		this.finalPiezoFNa = model.piezoGoldman.getFluxNa();
		this.finalPiezoFA = model.piezoGoldman.getFluxA();
		this.finalPiezoFCa = model.capump.getFluxCa();
	}

	public void computeEdgValues(double hk1, double hk2, double cak, double mgk) {
		this.edghk1 = Math.pow(10, (hk1));
		this.edghk2 = Math.pow(10, (hk2));
		this.edgcak = Math.pow(10, (cak));
		this.edgmgk = Math.pow(10, (mgk));
	}

	void oldEdgTa() {
		// The following computes initial "dedgh"=proton release to medium on chelation
		double fh = 1 + model.medium.H.getConcentration() / this.edghk1
				+ Math.pow(model.medium.H.getConcentration(), 2) / (this.edghk1 * this.edghk2);
		double edg4old = this.getEdgto() / (1000 * fh);
		double edg3old = edg4old * model.medium.H.getConcentration() / this.edghk1;
		double edg2old = edg4old * Math.pow(model.medium.H.getConcentration(), 2) / (this.edghk1 * this.edghk2);
		this.edghold = edg3old + 2 * edg2old;
		this.edghold = 1000 * this.edghold;
		this.edgtainitial();
		this.edgta();

		this.edgneg = 2 * this.edg2 + 3 * this.edg3 + 4 * this.edg4 + 2 * (this.edgca + this.edgmg);

		if (this.getBufferType() == "c" || this.getBufferType() == "C") {
			model.medium.A.setConcentration(model.medium.Na.getConcentration() + model.medium.Hb.getConcentration()
					+ model.medium.Glucamine.getConcentration() + 2 * model.medium.Mgf.getConcentration()
					+ 2 * model.medium.Caf.getConcentration() - this.edgneg
					- model.medium.Gluconate.getConcentration());
		} else {
			model.medium.Na.setConcentration(
					model.medium.A.getConcentration() + this.edgneg + model.medium.Gluconate.getConcentration()
							+ (model.bufferConc - model.medium.Hb.getConcentration())
							- model.medium.Glucamine.getConcentration() - model.medium.K.getConcentration()
							- 2 * model.medium.Mgf.getConcentration() - 2 * model.medium.Caf.getConcentration());
		}
	}

	private void edgtainitial() {
		// Convert ligand, Ca and Mg concentrations to Molar units for ligroots sub
		this.setEdgto(this.getEdgto() / 1000.0);
		model.medium.Caf.setConcentration(model.medium.Caf.getConcentration() / 1000.0);
		model.medium.Mgf.setConcentration(model.medium.Mgf.getConcentration() / 1000.0);
		model.medium.Cat.setConcentration(model.medium.Cat.getConcentration() / 1000.0);
		model.medium.Mgt.setConcentration(model.medium.Mgt.getConcentration() / 1000.0);
		model.bufferConc = model.bufferConc / 1000.0;
		this.deltaH = this.deltaH / 1000.0;
		this.dedgh = this.dedgh / 1000.0;

		double fff = 1 + model.medium.H.getConcentration() / this.edghk1
				+ Math.pow(model.medium.H.getConcentration(), 2) / (this.edghk1 * this.edghk2)
				+ model.medium.Caf.getConcentration() / this.edgcak + model.medium.Mgf.getConcentration() / this.edgmgk;
		// This parameter was made a class parameter to avoid
		// extra variables being passed to newton_raphson.
		// It's only used by ligeq1
		this.ligHb = model.medium.H.getConcentration()
				* (model.bufferConc / (model.medium.H.getConcentration() + this.kB)
						+ this.getEdgto() / (fff * this.edghk1)
						+ model.medium.H.getConcentration() * this.getEdgto() / (fff * this.edghk1 * this.edghk2));
		int nn = 0;
		boolean finished = false;
		while (!finished) {
			int bbb = 0;
			double buff = model.medium.H.getConcentration();
			double hhold = buff;
			this.setItCounter(0);
			double diff1 = 0.0001 * model.medium.H.getConcentration();
			double X_3 = NewtonRaphson.newtonRaphson(new ligeq1(), buff, diff1, this.diff, 100, getItCounter(), bbb,
					false);
			bbb += this.getItCounter();
			if (X_3 < 0) {
				X_3 = hhold;
			}
			model.medium.H.setConcentration(X_3);

			buff = model.medium.Caf.getConcentration();
			double cafold = buff;
			diff1 = 0.0001 * cafold;
			this.setItCounter(0);
			X_3 = NewtonRaphson.newtonRaphson(new ligeq2(), buff, diff1, this.diff, 100, getItCounter(), bbb, false);
			bbb += this.getItCounter();
			if (X_3 < 0) {
				X_3 = cafold / 2.0;
			}
			model.medium.Caf.setConcentration(X_3);

			buff = model.medium.Mgf.getConcentration();
			double mgfold = buff;
			diff1 = 0.0001 * mgfold;
			this.setItCounter(0);
			X_3 = NewtonRaphson.newtonRaphson(new ligeq3(), buff, diff1, this.diff, 100, getItCounter(), bbb, false);
			bbb += this.getItCounter();
			if (X_3 < 0) {
				X_3 = mgfold / 2.0;
			}
			model.medium.Mgf.setConcentration(X_3);
			nn += 1;
			if (nn > 100) {
				finished = true;
			}
			if ((Math.abs(model.medium.H.getConcentration() - hhold) <= this.diff * hhold)
					&& (Math.abs(cafold - model.medium.Caf.getConcentration()) <= this.diff * cafold)
					&& (Math.abs(mgfold - model.medium.Mgf.getConcentration()) <= this.diff * mgfold)) {
				finished = true;
			}
		}

		model.medium.setpH(-Math.log(model.medium.H.getConcentration()) / Math.log(10.0));
		model.bufferConc = model.bufferConc * 1000.0;
		this.deltaH = this.deltaH * 1000.0;
		model.medium.Hb.setConcentration(
				model.medium.H.getConcentration() * model.bufferConc / (model.medium.H.getConcentration() + this.kB));
		this.edg4 = this.getEdgto() / this.ff;
		this.edg3 = this.edg4 * model.medium.H.getConcentration() / this.edghk1;
		this.edg2 = this.edg4 * Math.pow(model.medium.H.getConcentration(), 2) / (this.edghk1 * this.edghk2);
		this.edgca = this.edg4 * model.medium.Caf.getConcentration() / this.edgcak;
		this.edgmg = this.edg4 * model.medium.Mgf.getConcentration() / this.edgmgk;
		this.edgneg = 2 * this.edg2 + 3 * this.edg3 + 4 * this.edg4 + 2 * (this.edgca + this.edgmg);
		this.edghnew = this.edg3 + 2 * this.edg2;

		// Convert ligand, Ca and Mg concentrations back to mM units
		this.setEdgto(this.getEdgto() * 1000);
		model.medium.Caf.setConcentration(model.medium.Caf.getConcentration() * 1000);
		model.medium.Mgf.setConcentration(model.medium.Mgf.getConcentration() * 1000);
		model.medium.Cat.setConcentration(model.medium.Cat.getConcentration() * 1000);
		model.medium.Mgt.setConcentration(model.medium.Mgt.getConcentration() * 1000);
		this.edg4 = this.edg4 * 1000;
		this.edg3 = this.edg3 * 1000;
		this.edg2 = this.edg2 * 1000;
		this.edgca = this.edgca * 1000;
		this.edgmg = this.edgmg * 1000;
		this.edgneg = this.edgneg * 1000;
		this.edghnew = this.edghnew * 1000;
		// computes d[H]o due to chelation.
		this.dedgh = this.edghnew - this.edghold;

	}

	private void edgta() {
		this.edghold = this.edg3 + 2 * this.edg2;
		this.edgtainitial();
	}

	void chelator() {
		model.medium.Na.setConcentration(model.medium.Na.getConcentration() + 2 * this.getEdgto());
	}

	void phadjust() { // 2
		model.medium.H.setConcentration(Math.pow(10.0, (-model.medium.getpH())));
		// Protonized buffer concentration;
		if (this.getBufferType() == "HEPES") {
			this.pkhepes = 7.83 - 0.014 * this.tempCelsius;
			this.kB = Math.pow(10.0, (-this.pkhepes));
			model.medium.Hb.setConcentration(model.bufferConc
					* (model.medium.H.getConcentration() / (this.kB + model.medium.H.getConcentration())));
		} else {
			this.kB = Math.pow(10.0, (-this.getPka()));
			model.medium.Hb.setConcentration(model.bufferConc
					* (model.medium.H.getConcentration() / (this.kB + model.medium.H.getConcentration())));
		}

		if (this.getBufferType() == "C") {
			if (model.medium.getpH() >= this.getmPhT0()) {
				model.medium.Na.setConcentration(model.medium.A.getConcentration() + this.edgneg
						+ model.medium.Gluconate.getConcentration() - model.medium.Hb.getConcentration()
						- model.medium.Glucamine.getConcentration() - model.medium.K.getConcentration()
						- 2 * model.medium.Mgf.getConcentration() - 2 * model.medium.Caf.getConcentration());
			} else {
				model.medium.A.setConcentration(model.medium.Na.getConcentration() + model.medium.K.getConcentration()
						+ model.medium.Glucamine.getConcentration() + model.medium.Hb.getConcentration()
						- model.medium.Gluconate.getConcentration() - this.edgneg
						+ 2 * model.medium.Mgf.getConcentration() + 2 * model.medium.Caf.getConcentration());
			}
		} else {
			if (model.medium.getpH() >= this.getmPhT0()) {
				model.medium.Na.setConcentration(
						model.medium.A.getConcentration() + this.edgneg + model.medium.Gluconate.getConcentration()
								+ (model.bufferConc - model.medium.Hb.getConcentration())
								- model.medium.Glucamine.getConcentration() - model.medium.K.getConcentration()
								- 2 * model.medium.Mgf.getConcentration() - 2 * model.medium.Caf.getConcentration());
			} else {
				model.medium.A.setConcentration(model.medium.Na.getConcentration() + model.medium.K.getConcentration()
						+ model.medium.Glucamine.getConcentration()
						- (model.bufferConc - model.medium.Hb.getConcentration())
						- model.medium.Gluconate.getConcentration() - this.edgneg
						+ 2 * model.medium.Mgf.getConcentration() + 2 * model.medium.Caf.getConcentration());
			}
		}
	}

	public void computeRS() {
		double mgtCatFac = 2 * (model.medium.Mgt.getConcentration() + model.medium.Cat.getConcentration());
		model.medium.A.setConcentration(model.medium.A.getConcentration() + mgtCatFac);
		this.computehtRS();

		this.setInitialHematocrit(this.getFraction());
		this.setHematocritFraction(this.getInitialHematocrit() / (1 - this.getInitialHematocrit()));

		this.mediumConcentrationsRS();
		this.cellPhEtc();

		this.naKAAmountsMgCaConcRS();

		// Computes Q_X
		this.secureIsonoticityRS();

		// Computes n_X (Non-protonizable charge on X (nX))
		this.nX = (model.cell.A.getAmount() + 2 * this.getBenz2()
				- (model.cell.Na.getAmount() + model.cell.K.getAmount() + 2 * model.cell.Mgt.getAmount()
						+ 2 * model.cell.Cat.getAmount() + this.nHb * model.cell.Hb.getAmount()))
				/ model.cell.X.getAmount();

		// Net charge on Hb
		this.netChargeHb = this.nHb * model.cell.Hb.getAmount();

		this.fluxesRS();

		this.cycleCount = 0;
		this.nIts = 0;
		this.durationExperiment = 0.0;

		// Set stage to zero everytime the RS is computed - stage = 1 means we're about
		// to start DS 1
		this.stage = 0;
	}

	private void fluxesRS() {
		// Flux-rates and RS fluxes
		model.getNapump().computePermeabilities(tempCelsius);

		model.carriermediated.setFluxNa(-(1 - this.fG) * model.getNapump().getFluxNet());
		model.carriermediated.setFluxK(-model.carriermediated.getFluxNa() / this.naToK);
		model.carriermediated.computePermeabilities();

		// A fluxes through Na:Cl and K:Cl
		double fal = model.carriermediated.getFluxNa() + model.carriermediated.getFluxK();
		// G-flux of A required to balance fal
		model.goldman.setFluxA(-fal);
		// G-rates and RS fluxes
		model.goldman.setFluxNa(-this.fG * model.getNapump().getFluxNet());
		model.goldman.setFluxK(-model.goldman.getFluxNa() / this.naToK);
		this.PT = 1.0;
		model.goldman.computePermeabilities(this.Em, this.tempCelsius);

		// Zero-factor for cotransport
		model.cotransport.computeZeroFactor();
		// model.cotransportmediatedfluxes()
		model.cotransport.computeFlux(temp, temp, this.PT);
		model.totalIonFluxes();

		// fHb is osmotic coefficient of haemoglobin

		this.chBeTc();
	}

	private void chBeTc() {
		model.cell.Hb.setConcentration(model.cell.Hb.getAmount() / this.getVw());
		model.cell.Mgt.setConcentration(model.cell.Mgt.getAmount() / this.getVw());
		model.cell.X.setConcentration(model.cell.X.getAmount() / this.getVw());
		model.cell.XHbm.setAmount(this.netChargeHb + this.nX * model.cell.X.getAmount() - 2 * this.getBenz2());
		model.cell.XHbm.setConcentration(model.cell.XHbm.getAmount() / this.getVw());
		model.cell.COs.setConcentration(this.fHb * model.cell.Hb.getAmount() / this.getVw());
		// Concentration of charge on Hb
		model.cell.Hbpm.setConcentration(this.nHb * model.cell.Hb.getAmount() / this.getVw());

		model.medium.Os.setConcentration(model.medium.Na.getConcentration() + model.medium.K.getConcentration()
				+ model.medium.A.getConcentration() + model.bufferConc + model.medium.Gluconate.getConcentration()
				+ model.medium.Glucamine.getConcentration() + model.medium.Sucrose.getConcentration()
				+ (model.medium.Mgf.getConcentration() + model.medium.Caf.getConcentration() + this.getEdgto())); // 5

		setCellOsAmount();
		setCellOsConcentration();

	}

	private void setCellOsAmount() {
		model.cell.Os.setAmount(model.cell.Na.getAmount() + model.cell.K.getAmount() + model.cell.A.getAmount()
				+ this.fHb * model.cell.Hb.getAmount() + model.cell.X.getAmount() + model.cell.Mgt.getAmount()
				+ (model.cell.Mgf.getConcentration() + model.cell.Caf.getConcentration()) * this.getVw()
				+ this.getBenz2()); // 22b
	}

	private void setCellOsConcentration() {
		model.cell.Os.setConcentration(
				model.cell.Na.getConcentration() + model.cell.K.getConcentration() + model.cell.A.getConcentration()
						+ this.fHb * model.cell.Hb.getConcentration() + model.cell.X.getConcentration()
						+ model.cell.Mgf.getConcentration() + model.cell.Caf.getConcentration() + this.getCbenz2()); // 22
	}

	private void secureIsonoticityRS() {
		// Secures initial isotonicity and electroneutrality; it computes the
		// QX and nX required for initial osmotic and charge balance. Since the Mg and
		// Ca buffers are within X, only the unbound forms of Mg and Ca participate in
		// osmotic equilibria within the cell.
		double sumM = model.medium.Na.getConcentration() + model.medium.K.getConcentration()
				+ model.medium.A.getConcentration() + model.bufferConc + model.medium.Gluconate.getConcentration()
				+ model.medium.Glucamine.getConcentration() + model.medium.Sucrose.getConcentration()
				+ model.medium.Mgt.getConcentration() + model.medium.Cat.getConcentration();
		double sumQ = model.cell.Na.getAmount() + model.cell.K.getAmount() + model.cell.A.getAmount()
				+ (model.cell.Mgf.getConcentration() + model.cell.Caf.getConcentration()) * this.getVw()
				+ this.fHb * model.cell.Hb.getAmount() + this.getBenz2();
		model.cell.X.setAmount(this.getVw() * sumM - sumQ);

	}

	private void naKAAmountsMgCaConcRS() {
		// Cell amounts of Na,K,and A, and concentrations of Mg and Ca
		model.cell.Na.setAmount(model.cell.Na.getConcentration() * this.getVw());
		model.cell.K.setAmount(model.cell.K.getConcentration() * this.getVw());
		model.cell.A.setAmount(model.cell.A.getConcentration() * this.getVw());
		model.cell.Mgt.setConcentration(model.cell.Mgt.getAmount() / this.getVw());
		model.cell.Cat.setConcentration(model.cell.Cat.getAmount() / this.getVw());
	}

	private void cellPhEtc() {

		this.rA = model.medium.A.getConcentration() / model.cell.A.getConcentration();
		this.rH = this.rA;

		model.cell.H.setConcentration(this.rH * model.medium.H.getConcentration());
		model.cell.setpH(-Math.log10(model.cell.H.getConcentration()));

		this.Em = -(8.615600000000004e-02) * (273 + this.tempCelsius) * Math.log(this.rA);

		// Osmotic coeficient of Hb
		this.fHb = 1 + this.virialCoefficientB * model.cell.Hb.getAmount() / this.getVw() // 7
				+ this.virialCoefficientC * Math.pow(model.cell.Hb.getAmount() / this.getVw(), 2.0);
		// physiological pI at 37oC;
		this.pI = this.getPit0() - (0.016 * 37);
		// net charge on Hb (Eq/mole)
		this.nHb = this.getPtcSegment() * (model.cell.getpH() - this.pI);
	}

	private void mediumConcentrationsRS() { //sets medium concentration at reference state 
		this.setBufferType("HEPES");
		model.medium.H.setConcentration(Math.pow(10.0, -model.medium.getpH()));
		this.pkhepes = 7.83 - 0.014 * this.tempCelsius;
		this.kB = Math.pow(10.0, -this.pkhepes);
		model.medium.Hb.setConcentration(
				model.bufferConc * (model.medium.H.getConcentration() / (this.kB + model.medium.H.getConcentration()))); // 2
		// Medium Na,K,or A concentration
		if (model.medium.getpH() >= this.getmPhT0()) {
			model.medium.Na.setConcentration(
					model.medium.A.getConcentration() + this.edgneg + model.medium.Gluconate.getConcentration()
							+ (model.bufferConc - model.medium.Hb.getConcentration())
							- model.medium.Glucamine.getConcentration() - model.medium.K.getConcentration()
							- 2 * model.medium.Mgf.getConcentration() - 2 * model.medium.Caf.getConcentration());
		} else {
			model.medium.K.setConcentration(
					model.medium.A.getConcentration() + this.edgneg + model.medium.Gluconate.getConcentration()
							+ (model.bufferConc - model.medium.Hb.getConcentration())
							- model.medium.Glucamine.getConcentration() - model.medium.Na.getConcentration()
							- 2 * model.medium.Mgf.getConcentration() - 2 * model.medium.Caf.getConcentration());
		}
	}

	public void computehtRS() {
		this.VV = (1 - this.VwT0) + this.getVw();
		this.mchc = this.getHbContent() / this.VV;
		this.density = (this.getHbContent() / 100.0 + this.getVw()) / this.VV;
	}

	public void setMgDefaults() {
		model.cell.Mgt.setAmount(2.5);
		model.medium.Mgt.setConcentration(0.2);
		this.setAtp(1.2);
		this.setDpgp(15.0);
		this.VV = (1.0 - this.VwT0) + this.getVw();
		double conc = NewtonRaphson.newtonRaphson(new Eqmg(), 0.02, 0.0001, 0.00001, 100, getItCounter(), 0, false);
		model.cell.Mgf.setConcentration(conc);

	}

	public void setCaDefaults() {
		model.cell.Cat.setAmount(0.000580);
		model.cell.Cat.setConcentration(model.cell.Cat.getAmount() / this.getVw());
		model.medium.Cat.setConcentration(1.0);
		this.setAlpha(0.30);
		this.setB1ca(0.026);
		this.setB1cak(0.014);
		this.setBenz2(0.0);
		this.benz2k = 5e-5;
		model.cell.Caf.setConcentration(1.12e-4);

		this.setEdgto(0.0);
		model.medium.Mgf.setConcentration(model.medium.Mgt.getConcentration());
		model.medium.Caf.setConcentration(model.medium.Cat.getConcentration());

	}

	void canr() {
		model.cell.Caf.setConcentration(1000.0 * model.cell.Caf.getConcentration());
		model.cell.Cat.setAmount(1000.0 * model.cell.Cat.getAmount());
		this.setB1ca(this.getB1ca() * 1000.0);
		this.setB1cak(this.getB1cak() * 1000.0);
		this.setBenz2(this.getBenz2() * 1000.0);
		this.benz2k = this.benz2k * 1000.0;
		double conc = NewtonRaphson.newtonRaphson(new Eqca(), model.cell.Caf.getConcentration(), 0.000001, 0.000001,
				100, getItCounter(), 0, false);
		model.cell.Caf.setConcentration(conc);

		model.cell.Caf.setConcentration(model.cell.Caf.getConcentration() / 1000.0);
		model.cell.Cat.setAmount(model.cell.Cat.getAmount() / 1000.0);
		this.setB1ca(this.getB1ca() / 1000.0);
		this.setB1cak(this.getB1cak() / 1000.0);
		this.setBenz2(this.getBenz2() / 1000.0);
		this.benz2k = this.benz2k / 1000.0;
	}

//******************************** NW Utility Classes **********************// 

	public class ligeq1 implements NWRunnerU {
		public double run(double X_3) {
			ff = 1 + X_3 / edghk1 + Math.pow(X_3, 2) / (edghk1 * edghk2) + model.medium.Caf.getConcentration() / edgcak
					+ model.medium.Mgf.getConcentration() / edgmgk;
			return X_3
					* (model.bufferConc / (X_3 + kB) + getEdgto() / (ff * edghk1)
							+ X_3 * getEdgto() / (ff * edghk1 * edghk2))
					- (ligHb - getHematocritFraction() * deltaH - dedgh);
		}
	}

	private class ligeq2 implements NWRunnerU {
		public double run(double X_3) {
			ff = 1 + model.medium.H.getConcentration() / edghk1
					+ Math.pow(model.medium.H.getConcentration(), 2) / (edghk1 * edghk2) + X_3 / edgcak
					+ model.medium.Mgf.getConcentration() / edgmgk;
			return model.medium.Cat.getConcentration() - X_3 * (1 + getEdgto() / (ff * edgcak));
		}
	}

	public class ligeq3 implements NWRunnerU {
		public double run(double X_3) {
			ff = 1 + model.medium.H.getConcentration() / edghk1
					+ Math.pow(model.medium.H.getConcentration(), 2) / (edghk1 * edghk2)
					+ model.medium.Caf.getConcentration() / edgcak + X_3 / edgmgk;
			return model.medium.Mgt.getConcentration() - X_3 * (1 + getEdgto() / (ff * edgmgk));
		}
	}

	private class Eqca implements NWRunnerU { // 9.c
		public double run(double local_x6) {
			cabb = local_x6 * (Math.pow(getAlpha(), -1.0)) + ((getB1ca() / VV) * local_x6 / (getB1cak() + local_x6))
					+ ((getBenz2() / VV) * local_x6 / (benz2k + local_x6));
			double y = model.cell.Cat.getAmount() - cabb;
			return y;
		}
	}

	public class Eqmg implements NWRunnerU { // 9.g
		public double run(double local_mgf) {
			mgb = getMgb0() + ((getAtp() / VV) * local_mgf / (0.08 + local_mgf))
					+ ((getDpgp() / VV) * local_mgf / (3.6 + local_mgf));
			double y = model.cell.Mgt.getAmount() - local_mgf * (getVw() / (getVw() + getHbContent() / 136.0)) - mgb;
			return y;
		}
	}

	public ResultHash makeResultHash() { //used to output final results 
		ResultHash newResult = new ResultHash(this.samplingTime * 60.0); // convert to minutes for publishing
		newResult.setItem("Vw", this.getVw());
		newResult.setItem("V/V", this.VV);
		newResult.setItem("CVF", this.getFraction());
		newResult.setItem("MCHC", this.mchc);
		newResult.setItem("Density", this.density);
		newResult.setItem("pHi", model.cell.getpH());
		newResult.setItem("pHo", model.medium.getpH());
		newResult.setItem("Hct", this.getFraction() * 100.0);
		newResult.setItem("Em", this.Em);
		newResult.setItem("QNa", model.cell.Na.getAmount());
		newResult.setItem("QK", model.cell.K.getAmount());
		newResult.setItem("QA", model.cell.A.getAmount());
		newResult.setItem("QCa", model.cell.Cat.getAmount());
		newResult.setItem("QMg", model.cell.Mgt.getAmount());
		newResult.setItem("CNa", model.cell.Na.getConcentration());
		newResult.setItem("CK", model.cell.K.getConcentration());
		newResult.setItem("CA", model.cell.A.getConcentration());
		newResult.setItem("CH/nM", 1e9 * Math.pow(10.0, -model.cell.getpH()));
		newResult.setItem("CCa2+", model.cell.Caf.getConcentration());
		newResult.setItem("CMg2+", model.cell.Mgf.getConcentration());
		newResult.setItem("CHb", model.cell.Hb.getConcentration());
		newResult.setItem("CX-", model.cell.X.getConcentration());
		newResult.setItem("nX", this.nX);
		newResult.setItem("nX*CX-", this.nX * model.cell.X.getConcentration());
		newResult.setItem("COs", model.cell.Os.getConcentration());
		newResult.setItem("MOs", model.medium.Os.getConcentration());
		newResult.setItem("rA", this.rA);
		newResult.setItem("rH", this.rH);
		newResult.setItem("fHb", this.fHb);
		newResult.setItem("nHb", this.nHb);
		newResult.setItem("MNa", model.medium.Na.getConcentration());
		newResult.setItem("MK", model.medium.K.getConcentration());
		newResult.setItem("MA", model.medium.A.getConcentration());
		newResult.setItem("MH", 1e9 * Math.pow(10.0, -model.medium.getpH()));
		newResult.setItem("MB", model.bufferConc);
		newResult.setItem("MCat", model.medium.Cat.getConcentration());
		newResult.setItem("MCa2+", model.medium.Caf.getConcentration());
		newResult.setItem("MMgt", model.medium.Mgt.getConcentration());
		newResult.setItem("MMg2+", model.medium.Mgf.getConcentration());
		newResult.setItem("FNaP", model.getNapump().getFluxNet());
		newResult.setItem("FCaP", model.capump.getFluxCa());
		newResult.setItem("FHCaP", -2.0 * model.capump.getFluxCa());
		newResult.setItem("FKP", model.getNapump().getFluxK());
		newResult.setItem("FNa", model.totalFluxNa);
		newResult.setItem("FK", model.totalFluxK);
		newResult.setItem("FA", model.totalFluxA);
		newResult.setItem("FH", model.totalFluxH);
		newResult.setItem("FCa", model.totalFluxCa);
		newResult.setItem("FW", model.water.getFlux());
		newResult.setItem("FNaG", model.goldman.getFluxNa());
		newResult.setItem("FKG", model.goldman.getFluxK());
		newResult.setItem("FAG", model.goldman.getFluxA());
		newResult.setItem("FHG", model.goldman.getFluxH());
		newResult.setItem("FCaG", model.passiveca.getFlux());
		newResult.setItem("FAJS", model.JS.getFluxA());
		newResult.setItem("FHJS", model.JS.getFluxH());
		newResult.setItem("FA23Ca", model.a23.getFluxCa());
		newResult.setItem("FA23Mg", model.a23.getFluxMg());
		double V_14 = -model.goldman.getRtoverf()
				* Math.log(model.medium.A.getConcentration() / model.cell.A.getConcentration());
		newResult.setItem("EA", V_14);
		double V_13 = model.goldman.getRtoverf()
				* Math.log(model.medium.H.getConcentration() / model.cell.H.getConcentration());
		newResult.setItem("EH", V_13);
		double V_15 = model.goldman.getRtoverf()
				* Math.log(model.medium.K.getConcentration() / model.cell.K.getConcentration());
		newResult.setItem("EK", V_15);
		double V_16 = model.goldman.getRtoverf()
				* Math.log(model.medium.Na.getConcentration() / model.cell.Na.getConcentration());
		newResult.setItem("ENa", V_16);
		newResult.setItem("FKGgardos", model.goldman.computeFKGardos(PT));
		newResult.setItem("FzK", model.piezoGoldman.getFluxK());
		newResult.setItem("FzNa", model.piezoGoldman.getFluxNa());
		newResult.setItem("FzA", model.piezoGoldman.getFluxA());
		newResult.setItem("FzCa", model.piezoPassiveca.getFlux());

		newResult.setItem("MEDGTA", this.getEdgto());
		newResult.setItem("FClCo", model.cotransport.getFluxA());
		newResult.setItem("FKCo", model.cotransport.getFluxK());
		newResult.setItem("FNaCo", model.cotransport.getFluxNa());

		newResult.setItem("fHb*CHb", this.fHb * model.cell.Hb.getConcentration());
		newResult.setItem("nHb*CHb", this.nHb * model.cell.Hb.getConcentration());
		newResult.setItem("Msucrose", model.medium.Sucrose.getConcentration());
		newResult.setItem("Mgluconate", model.medium.Gluconate.getConcentration());
		newResult.setItem("Mglucamine", model.medium.Glucamine.getConcentration());

		newResult.setItem("TransitHct", this.finalPiezoHct);

		newResult.setItem("FzKGTransit", this.finalPiezoFK);
		newResult.setItem("FzNaGTransit", this.finalPiezoFNa);
		newResult.setItem("FzAGTransit", this.finalPiezoFA);
		newResult.setItem("FzCaGTransit", this.finalPiezoFCa);

		double enFluxTest = model.totalFluxNa + model.totalFluxK + model.totalFluxH - model.totalFluxA
				+ 2.0 * (model.totalFluxCa + model.a23.getFluxMg());
		newResult.setItem("EN test", enFluxTest);

		return newResult;
	}

	public void publish() {
		if (!this.doPublish) {
			return;
		}

		System.out.println("Publishing at time: " + this.samplingTime);
		ResultHash newResult = makeResultHash();

		this.resultList.add(newResult);
	}

	public void clearResults() {
		/*
		 * Method used to wipe the arraylist to avoid clogging up memory with enormous
		 * quantities of dirt.
		 */
		this.resultList = new ArrayList<ResultHash>();
	}

	public void writeCsv(String name, ArrayList<ResultHash> resultList) {
		FileWriter filewriter = null;
		try {
			filewriter = new FileWriter(name);
			String headString = "Time";
			for (int i = 0; i < this.PublishOrder.length; i++) {
				headString += ',' + this.PublishOrder[i];
			}
			headString += '\n';
			filewriter.append(headString);
			String resultString;
			String numFormat = "%7." + this.dp + "f";
			for (ResultHash r : resultList) {
				resultString = String.format(numFormat, r.getTime());
				for (int i = 0; i < this.PublishOrder.length; i++) {
					resultString += ',' + String.format(numFormat, r.getItem(this.PublishOrder[i]));
				}
				resultString += '\n';
				filewriter.append(resultString);
			}
		} catch (Exception e) {

		} finally {
			try {
				filewriter.flush();
				filewriter.close();
			} catch (IOException e) {

			}
		}
	}

	public void writeCsv(String name) {
		writeCsv(name, this.resultList);
	}

	/*
	 * Getters and setters from here
	 */

	public int getTotalCycleCount() {
		return totalCycleCount;
	}

	public ArrayList<ResultHash> getResults() {
		return this.resultList;
	}

	public ResultHash getLastResult() {
		return this.resultList.get(this.resultList.size() - 1);
	}

	public String[] getPublishOrder() {
		return this.PublishOrder;
	}

	public double getDefaultFraction() {
		return this.defaultFraction;
	}

	public double getSamplingTime() {
		return this.samplingTime;
	}

	public ResultHash getFinalPiezoResult() {
		return finalPiezoResult;
	}

	public double getFinalPiezoHct() {
		return this.finalPiezoHct;
	}

	public double getFinalPiezoCCa() {
		return this.finalPiezoCCa;
	}

	public int getStage() {
		return this.stage;
	}

	public double getDurationExperiment() {
		return durationExperiment;
	}

	public void setDurationExperiment(double durationExperiment) {
		this.durationExperiment = durationExperiment;
	}

	public int getDp() {
		return dp;
	}

	public void setDp(int dp) {
		this.dp = dp;
	}

	public double getIntegrationIntervalFactor() {
		return integrationIntervalFactor;
	}

	public void setIntegrationIntervalFactor(double integrationIntervalFactor) {
		this.integrationIntervalFactor = integrationIntervalFactor;
	}

	public boolean iscomputeDeltaTime() {
		return computeDeltaTime;
	}

	public void setComputeDeltaTime(boolean computeDeltaTime) {
		this.computeDeltaTime = computeDeltaTime;
	}

	public double getDeltaTime() {
		return deltaTime;
	}

	public void setDeltaTime(double deltaTime) {
		this.deltaTime = deltaTime;
	}

	public Integer getCyclesPerPrint() {
		return cyclesPerPrint;
	}

	public void setCyclesPerPrint(Integer cyclesPerPrint) {
		this.cyclesPerPrint = cyclesPerPrint;
	}

	public double getHbContent() {
		return hbContent;
	}

	public void setHbContent(double hbContent) {
		this.hbContent = hbContent;
	}

	public double getWaterVolume() {
		return waterVolume;
	}

	public double getTempCelsius() {
		return tempCelsius;
	}

	public void setTempCelsius(double tempCelsius) {
		this.tempCelsius = tempCelsius;
	}

	public void setWaterVolume(double waterVolume) {
		this.waterVolume = waterVolume;
	}

	public double getVlysis() {
		return vlysis;
	}

	public void setVlysis(double vlysis) {
		this.vlysis = vlysis;
	}

	public double getPtcSegment() {
		return ptcSegment;
	}

	public void setPtcSegment(double ptcSegment) {
		this.ptcSegment = ptcSegment;
	}

	public double getPit0() {
		return pit0;
	}

	public void setPit0(double pit0) {
		this.pit0 = pit0;
	}

	public double getMgb0() {
		return mgb0;
	}

	public void setMgb0(double mgb0) {
		this.mgb0 = mgb0;
	}

	public double getAtp() {
		return atp;
	}

	public void setAtp(double atp) {
		this.atp = atp;
	}

	public double getDpgp() {
		return dpgp;
	}

	public void setDpgp(double dpgp) {
		this.dpgp = dpgp;
	}

	public double getB1ca() {
		return b1ca;
	}

	public void setB1ca(double b1ca) {
		this.b1ca = b1ca;
	}

	public double getB1cak() {
		return b1cak;
	}

	public void setB1cak(double b1cak) {
		this.b1cak = b1cak;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getBenz2() {
		return benz2;
	}

	public void setBenz2(double benz2) {
		this.benz2 = benz2;
	}

	public double getCbenz2() {
		return cbenz2;
	}

	public void setCbenz2(double cbenz2) {
		this.cbenz2 = cbenz2;
	}

	public double getVw() {
		return Vw;
	}

	public void setVw(double vw) {
		Vw = vw;
	}

	public int getItCounter() {
		return itCounter;
	}

	public void setItCounter(int itCounter) {
		this.itCounter = itCounter;
	}

	public void setIsCancelled(boolean state) {
		this.isCancelled = state;
	}

	public boolean getIsCancelled() {
		return this.isCancelled;
	}

	public void setPublishOrder(String[] PublishOrder) {
		this.PublishOrder = PublishOrder;
	}

	public void setResults(ArrayList<ResultHash> resultList) {
		this.resultList = resultList;
	}

	public void setLifespan(boolean state) {
		this.lifespan = state;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public void setPublish(boolean publish) {
		this.doPublish = publish; // Used to stop publishing events
	}

	public String getBufferType() {

		return this.BufferType;
	}

	public void setpI(double pI) {
		this.pI = pI;

	}

	public double getpI() {
		return this.pI;

	}

	public double getPkhepes() {
		return this.pkhepes;

	}

	public void setPkhepes(double pkhepes) {
		this.pkhepes = pkhepes;

	}

	public double getKB() {

		return this.kB;
	}

	public void setKB(double kB) {

		this.kB = kB;
	}

	public double getPka() {
		return pka;
	}

	public void setPka(double pka) {
		this.pka = pka;
	}

	public double getEdgto() {
		return edgto;
	}

	public void setEdgto(double edgto) {
		this.edgto = edgto;
	}

	public double getLigchoice() {
		return ligchoice;
	}

	public void setLigchoice(double ligchoice) {
		this.ligchoice = ligchoice;
	}

	public double getParseTemp() {
		return parseTemp;
	}

	public void setParseTemp(double parseTemp) {
		this.parseTemp = parseTemp;
	}

	public void setBufferType(String bufferType) {
		BufferType = bufferType;
	}

	public double getInitialHematocrit() {
		return initialHematocrit;
	}

	public void setInitialHematocrit(double initialHematocrit) {
		this.initialHematocrit = initialHematocrit;
	}

	public double getHematocritFraction() {
		return hematocritFraction;
	}

	public void setHematocritFraction(double hematocritFraction) {
		this.hematocritFraction = hematocritFraction;
	}

	public double getFraction() {
		return fraction;
	}

	public void setFraction(double fraction) {
		this.fraction = fraction;
	}

	public void setDefaultFraction(double defaultFraction) {
		this.defaultFraction = defaultFraction;
	}

	public double getmPhT0() {
		return mPhT0;
	}

	public void setmPhT0(double mPhT0) {
		this.mPhT0 = mPhT0;
	}

	public double getClGExchange() {
		return clGExchange;
	}

	public void setClGExchange(double clGExchange) {
		this.clGExchange = clGExchange;
	}

	public double getT6() {
		return T6;
	}

	public void setT6(double t6) {
		T6 = t6;
	}

}
