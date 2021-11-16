package modelcomponents;

public class NaPump extends Pump {

	private double P1; // V_max for sodium pump
	private double defaultP1;
	private double P2; // V_max reverse
	Region cell;
	Region medium;

	private double fluxFwd;
	private double fluxRev;
	private double fluxNet;
	private double fluxK;
	private double totalFlux;
	private double PT;

	private final double NaConstantOne;
	private final double NaConstantTwo;
	private final double NaConstantThree;
	private final double NaConstantFour;

	private double mgNaP;
	private double phNaP;

	private double fNaPNaFFirst;
	private double fNaPNaFSecond;
	private double fNaPNaRFirst;
	private double fNaPNaRSecond;

	private double Q10;

	private double mgNaPk;
	private double mgNaPik;

	private double phD;
	private double I_78;

	NaPump(Region cell, Region medium) {
		this.cell = cell;
		this.medium = medium;

		this.fluxFwd = -2.61;
		this.fluxRev = 0.0015;
		this.setFluxNet();
		this.setFluxK();
		this.setTotalFlux(this.getFluxNet() + this.getFluxK());

		this.NaConstantOne = 0.2;
		this.NaConstantTwo = 18.0;
		this.NaConstantThree = 0.1;
		this.NaConstantFour = 8.3;

		this.Q10 = 4.0;
		this.mgNaPk = 0.05;
		this.mgNaPik = 4.0;
		this.phD = 7.216; // pH dependence of sodium pump
		this.I_78 = 0.4;
	}

	private void computePT(double temperature) // 11
	{
		this.PT = Math.exp(((37.0 - temperature) / 10.0) * Math.log(this.Q10));
	}

	private void FNaPNaFBrackets() { // 11.a

		double denominatorFirst = this.cell.Na.getConcentration() //broken down into brackets sections
				+ (this.NaConstantOne * (1 + (this.cell.K.getConcentration() / this.NaConstantFour)));
		double fractionFirst = this.cell.Na.getConcentration() / denominatorFirst;
		this.fNaPNaFFirst = Math.pow(fractionFirst, 3.0); // 11.a (first bracket)

		double denominatorSecond = this.medium.K.getConcentration()
				+ (this.NaConstantThree * (1 + this.medium.Na.getConcentration() / this.NaConstantTwo));
		double fractionSecond = this.medium.K.getConcentration() / denominatorSecond;
		this.fNaPNaFSecond = Math.pow(fractionSecond, 2.0); // 11.a (second bracket)

	}

	private void FNaPNaRBrackets() { // 11.b

		double denominatorFirst = this.cell.K.getConcentration()
				+ (this.NaConstantFour * (1 + this.cell.Na.getConcentration() / this.NaConstantOne));
		double fractionFirst = this.cell.K.getConcentration() / denominatorFirst;
		this.fNaPNaRFirst = Math.pow(fractionFirst, 2.0); // 11.b (first bracket)

		double denominatorSecond = (this.medium.Na.getConcentration()
				+ (this.NaConstantTwo * (1 + this.medium.K.getConcentration() / this.NaConstantThree)));
		double fractionSecond = this.medium.Na.getConcentration() / denominatorSecond;
		this.fNaPNaRSecond = Math.pow(fractionSecond, 3.0); // 11.b (second bracket)

	}

	public void computePermeabilities(double temperature) {
		this.FNaPNaFBrackets();
		this.FNaPNaRBrackets();
		computePT(temperature);
		this.computemgNaP();
		this.computephNaP();
		this.setDefaultP1(Math.abs(this.fluxFwd / (this.mgNaP * this.phNaP * this.fNaPNaFFirst * this.fNaPNaFSecond)));
		this.setP2(Math.abs(this.fluxRev / ((this.mgNaP * this.phNaP) * (this.fNaPNaRFirst * this.fNaPNaRSecond))));
	}

	private void computemgNaP() {
		this.mgNaP = (this.cell.Mgf.getConcentration() / (this.mgNaPk + this.cell.Mgf.getConcentration()))
				* (this.mgNaPik / (this.mgNaPik + this.cell.Mgf.getConcentration()));
	}

	private void computephNaP() {
		this.phNaP = Math.exp(-Math.pow(((this.cell.getpH() - this.phD) / this.I_78), 2.0));
	}

	@Override
	public void computeFlux(double em, double temperature, double I_18) {

		this.FNaPNaFBrackets();
		this.FNaPNaRBrackets();
		computePT(I_18);
		this.computemgNaP();
		this.computephNaP();
		this.fluxFwd = -(this.getP1() / this.PT) * this.mgNaP * this.phNaP * this.fNaPNaFFirst * this.fNaPNaFSecond;
		this.fluxRev = (this.getP2() / this.PT) * this.mgNaP * this.phNaP * this.fNaPNaRFirst * this.fNaPNaRSecond;
		this.setFluxNet();
		this.setFluxK();
		this.setTotalFlux(this.getFluxNet() + this.getFluxK());

	}

	// getter methods

	public double getDefaultP1() {
		return defaultP1;
	}

	public double getP1() {
		return P1;
	}

	public double getP2() {
		return P2;
	}

	public double getTotalFlux() {
		return totalFlux;
	}

	public double getFluxFwd() {
		return this.fluxFwd;
	}

	public double getPT() {
		return this.PT;
	}

	public double getFluxRev() {
		return this.fluxRev;
	}

	public double getFluxNet() {
		return fluxNet;
	}

	// overridden from super
	public double getFluxK() {
		return fluxK;
		}

	// setter methods

	public void setQ10(double Q10) {
		this.Q10 = Q10;
	}

	public void setFluxFwd(double f) {
		this.fluxFwd = f;
	}

	public void setFluxRev(double f) {
		this.fluxRev = f;
	}

	public void setFluxNet() { // 11.c

		this.fluxNet = this.fluxFwd + this.fluxRev;
	}

	public void setFluxK() { // 11.d

		this.fluxK = -this.getFluxNet() / 1.5;
	}

	private void setDefaultP1(double P1) {
		this.defaultP1 = P1;
		this.setP1(P1);
	}

	public void setP1(double p1) {
		P1 = p1;
	}

	public void setP2(double p2) {
		P2 = p2;
	}

	public void setTotalFlux(double totalFlux) {
		this.totalFlux = totalFlux;
	}

	@Override
	public double getFluxNa() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFluxA() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFluxMg() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFluxCa() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFluxH() {
		// TODO Auto-generated method stub
		return 0;
	}

}