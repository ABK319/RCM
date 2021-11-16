package modelcomponents;




// Stores the state of the Piezo
// and the values with which to change the permeabilities
// note that the actual changes are made by the model class
// Note also that time here is in minutes
public class Piezo  {
//	private double startTime = 2.0/60.0; // hours
	private double startTime = 0.0;
	private double duration = (0.4 / 60.0) / 60.0; // (1.0/120.0)/60.0; // hours
	private double recovery = 1.0 / 60.0; // hours
	private Integer cycles = 7;
	private double pkg = 0.0;
	private double pnag = 0.0;
	private double pcag = 70.0;
	private double pag = 50.0;
	private double pmca = 0.0; // check this
	private double piezoFraction = 0.9;
	private double piezoJS = 1.0; // I.e. multiplicative, so by default, don't change
	private boolean restoreMedium = false;

	// Defaults for restoring medium
	private double restoreHepesNa = 10.0;
	private double restorepH = 7.4;
	private double restoreNa = 145.5048;
	private double restoreK = 5.0;
	private double restoreMg = 0.2;
	private double restoreCa = 1.0;

	private double oldPKG;
	private double oldPNaG;
	private double oldPCaG;
	private double oldPAG;
	private double oldPMCA;

	public double getRestoreHepesNa() {
		return restoreHepesNa;
	}

	public void setRestoreHepesNa(double restoreHepesNa) {
		this.restoreHepesNa = restoreHepesNa;
	}

	public double getRestorepH() {
		return restorepH;
	}

	public void setRestorepH(double restorepH) {
		this.restorepH = restorepH;
	}

	public double getRestoreNa() {
		return restoreNa;
	}

	public void setRestoreNa(double restoreNa) {
		this.restoreNa = restoreNa;
	}

	public double getRestoreK() {
		return restoreK;
	}

	public void setRestoreK(double restoreK) {
		this.restoreK = restoreK;
	}

	public double getRestoreMg() {
		return restoreMg;
	}

	public void setRestoreMg(double restoreMg) {
		this.restoreMg = restoreMg;
	}

	public double getRestoreCa() {
		return restoreCa;
	}

	public void setRestoreCa(double restoreCa) {
		this.restoreCa = restoreCa;
	}

	public void setRestoreMedium(boolean res) {
		this.restoreMedium = res;
	}

	public boolean getRestoreMedium() {
		return restoreMedium;
	}

	public double getPiezoJS() {
		return piezoJS;
	}

	public void setPiezoJS(double piezoJS) {
		this.piezoJS = piezoJS;
	}

	public double getPkg() {
		return pkg;
	}

	public void setPkg(double pkg) {
		this.pkg = pkg;
	}

	public double getPnag() {
		return pnag;
	}

	public void setPnag(double pnag) {
		this.pnag = pnag;
	}

	public double getPcag() {
		return pcag;
	}

	public void setPcag(double pcag) {
		this.pcag = pcag;
	}

	public double getPag() {
		return pag;
	}

	public void setPag(double pag) {
		this.pag = pag;
	}

	public double getPmca() {
		return pmca;
	}

	public void setPmca(double pmca) {
		this.pmca = pmca;
	}

	public Integer getCycles() {
		return cycles;
	}

	public void setCycles(Integer cycles) {
		this.cycles = cycles;
	}

	public double getRecovery() {
		return recovery;
	}

	public void setRecovery(double recovery) {
		this.recovery = recovery;
	}

	public double getStartTime() {
		return startTime;
	}

	public double getDuration() {
		return duration;
	}

	private double oldIF;
	private double iF = 1e-5;

	public double getiF() {
		return iF;
	}

	public void setiF(double iF) {
		this.iF = iF;
	}

	public double getOldPMCA() {
		return oldPMCA;
	}

	public void setOldPMCA(double oldPMCA) {
		this.oldPMCA = oldPMCA;
	}

	public double getOldPAG() {
		return oldPAG;
	}

	public void setOldPAG(double oldPAG) {
		this.oldPAG = oldPAG;
	}

	public double getOldPCaG() {
		return oldPCaG;
	}

	public void setOldPCaG(double oldPCaG) {
		this.oldPCaG = oldPCaG;
	}

	private Integer oldCycles;

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setOldIF(double i) {
		this.oldIF = i;
	}

	public double getOldIF() {
		return this.oldIF;
	}

	public void setOldPKG(double i) {
		this.oldPKG = i;
	}

	public double getOldPKG() {
		return this.oldPKG;
	}

	public void setOldPNaG(double i) {
		this.oldPNaG = i;
	}

	public double getOldPNaG() {
		return this.oldPNaG;
	}

	public void setOldCycles(Integer i) {
		this.oldCycles = i;
	}

	public Integer getOldCycles() {
		return this.oldCycles;
	}

	public double getPiezoFraction() {
		return piezoFraction;
	}

	public void setPiezoFraction(double piezoFraction) {
		this.piezoFraction = piezoFraction;
	}
}
