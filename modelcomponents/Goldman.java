package modelcomponents;

//No info on equations implemented 
public class Goldman extends Pump {
	private final Region cell;
	private final Region medium;
	private double permeabilityNa;
	private double permeabilityA;
	private double permeabilityH;
	private double permeabilityK;

	private double fluxNa;
	private double fluxA;
	private double fluxH;
	private double fluxK;

	private double goldmanFactor;
	private double P11;

	private double pkm;
	private double defaultPkm;
	private double pKCaK;
	private double pgKH;

	private double rtoverf;
	private double foverrt;
	private double gConst;

	public Goldman(Region cell, Region medium) {
		this.cell = cell;
		this.medium = medium;

		this.setPermeabilityA(1.2);
		this.setPermeabilityH(2e-10);

		this.setFluxNa(0.0);
		this.setFluxA(0.0);
		this.setFluxH(0.0);
		this.setFluxK(0.0);

		this.goldmanFactor = 0.0;
		this.setDefaultPkm(30.0);
		this.setPkcak(1e-2);
		this.gConst = 8.6156e-2;
		this.pgKH = 0.0;
	}

	private void gfactors(double Em, double temperature) {
		this.rtoverf = ((gConst) * (273 + temperature));
		this.foverrt = 1.0 / ((gConst) * (273 + temperature));
		this.goldmanFactor = Em * foverrt;
	}

	public void computePermeabilities(double Em, double temperature) {
		this.gfactors(Em, temperature);
		this.setPermeabilityNa(Math.abs(this.getFluxNa() / this.gflux(this.cell.Na, this.medium.Na)));
		this.setPermeabilityK(Math.abs(this.getFluxK() / this.gflux(this.cell.K, this.medium.K)));
	}

	private void computeP11() {
		double intermediate = 1.0 / (1.0 + Math.pow(this.cell.H.getConcentration(), 4.0) / 2.5e-30);
		this.P11 = this.getPgkh() * intermediate;
	}

	private double computeP6() {
		double intermediate = (Math.pow(this.cell.Caf.getConcentration(), 4.0)
				/ (Math.pow(this.getPkcak(), 4.0) + Math.pow(this.cell.Caf.getConcentration(), 4.0)));

		return this.getPermeabilityK() + this.getPkm() * intermediate;
	}

	private double totalGPermeabilityK() {
		this.computeP11();
		return this.computeP6() + this.P11;
	}

	public double computeFKGardos(double I18) {
		return this.gflux(this.cell.K, this.medium.K) * (((this.computeP6() - this.getPermeabilityK())) / I18);
	}

	public void computeFlux(double em, double temperature, double I_18) {
		this.gfactors(em, temperature);
		this.setFluxNa(this.fullgflux(this.cell.Na, this.medium.Na, this.getPermeabilityNa(), I_18));
		this.setFluxA(this.fullgflux(this.cell.A, this.medium.A, this.getPermeabilityA(), I_18));
		this.setFluxH(this.fullgflux(this.cell.H, this.medium.H, this.getPermeabilityH(), I_18));
		this.setFluxK(this.fullgflux(this.cell.K, this.medium.K, this.totalGPermeabilityK(), I_18));
	}

	private double gflux(Species cellSpecies, Species mediumSpecies) {
		double goldmanSpecies = Math.exp(cellSpecies.getZ() * this.goldmanFactor);
		double goldmanSpeciesDiff = 1.0 - Math.exp(cellSpecies.getZ() * this.goldmanFactor);
		return -cellSpecies.getZ() * this.goldmanFactor
				* (mediumSpecies.getConcentration() - cellSpecies.getConcentration() * goldmanSpecies)
				/ goldmanSpeciesDiff;
	}

	private double fullgflux(Species cellSpecies, Species mediumSpecies, double permeability, double I18) {
		return (permeability / I18) * this.gflux(cellSpecies, mediumSpecies);
	}

	public double getGoldmanFactor() {
		return this.goldmanFactor;
	}

	public double getPkm() {
		return pkm;
	}

	public double getRtoverf() {
		return this.rtoverf;
	}

	public double getFoverrt() {
		return this.foverrt;
	}

	public double getDefaultPkm() {
		return this.defaultPkm;
	}

	public void setDefaultPkm(double pkm) {
		this.defaultPkm = pkm;
		this.setPkm(pkm);
	}

	public void setPkm(double pkm) {
		this.pkm = pkm;
	}

	public double getPkcak() {
		return pKCaK;
	}

	public void setPkcak(double pKCaK) {
		this.pKCaK = pKCaK;
	}

	public double getFluxA() {
		return fluxA;
	}

	public void setFluxA(double fluxA) {
		this.fluxA = fluxA;
	}

	public double getFluxNa() {
		return fluxNa;
	}

	public void setFluxNa(double fluxNa) {
		this.fluxNa = fluxNa;
	}

	public double getFluxK() {
		return fluxK;
	}

	public void setFluxK(double fluxK) {
		this.fluxK = fluxK;
	}

	public double getFluxH() {
		return fluxH;
	}

	public void setFluxH(double fluxH) {
		this.fluxH = fluxH;
	}

	public double getPermeabilityK() {
		return permeabilityK;
	}

	public void setPermeabilityK(double permeabilityK) {
		this.permeabilityK = permeabilityK;
	}

	public double getPgkh() {
		return pgKH;
	}

	public void setPgkh(double pgKH) {
		this.pgKH = pgKH;
	}

	public double getPermeabilityNa() {
		return permeabilityNa;
	}

	public void setPermeabilityNa(double permeabilityNa) {
		this.permeabilityNa = permeabilityNa;
	}

	public double getPermeabilityA() {
		return permeabilityA;
	}

	public void setPermeabilityA(double permeabilityA) {
		this.permeabilityA = permeabilityA;
	}

	public double getPermeabilityH() {
		return permeabilityH;
	}

	public void setPermeabilityH(double permeabilityH) {
		this.permeabilityH = permeabilityH;
	}

	@Override
	public double getFluxMg() {

		return 0;
	}

	@Override
	public double getFluxCa() {

		return 0;
	}

}
