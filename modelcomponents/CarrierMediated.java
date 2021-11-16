package modelcomponents;

public class CarrierMediated extends Pump {
	private Region cell;
	private Region medium;
	private double fluxNa;
	private double fluxK;
	private double permeabilityNa;
	private double defaultPermeabilityNa;
	private double permeabilityK;
	private double defaultPermeabilityK;

	public CarrierMediated(Region cell, Region medium) {
		this.cell = cell;
		this.medium = medium;

	}

	public double permeabilityNaValue() { //used to compute kNa value
		permeabilityNa = Math.abs(this.getFluxNa() / (this.cell.Na.getConcentration() * this.cell.A.getConcentration()
				- this.medium.Na.getConcentration() * this.medium.A.getConcentration()));
		return permeabilityNa;
	}

	public double permeabilityKValue() { //used to compute kKa value
		permeabilityK = Math.abs(this.getFluxK() / (this.cell.K.getConcentration() * this.cell.A.getConcentration()
				- this.medium.K.getConcentration() * this.medium.A.getConcentration()));
		return permeabilityK;
	}

	public void computePermeabilities() {
		this.setPermeabilityNa(permeabilityNaValue());
		this.setPermeabilityK(permeabilityKValue());
	}

	@Override
	public void computeFlux(double em, double temperature, double I_18) {
		double kNa = -(this.getPermeabilityNa() / I_18);
		double kKa = -(this.getPermeabilityK() / I_18); 
		//computes equations 16.a, 16.b and 16.c
		this.setFluxNa(kNa * (this.cell.Na.getConcentration() * this.cell.A.getConcentration()
				- this.medium.Na.getConcentration() * this.medium.A.getConcentration()));
		this.setFluxK(kKa * (this.cell.K.getConcentration() * this.cell.A.getConcentration()
				- this.medium.K.getConcentration() * this.medium.A.getConcentration())); 
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

	public void setDefaultPermeabilityK(double defaultK) {
		this.defaultPermeabilityK = defaultK;
		this.setPermeabilityK(defaultK);
	}

	public void setDefaultPermeabilityNa(double defaultNa) {
		this.defaultPermeabilityNa = defaultNa;
		this.setPermeabilityNa(defaultNa);
	}

	public double getDefaultPermabilityNa() {
		return this.defaultPermeabilityNa;
	}

	public double getDefaultPermeabilityK() {
		return this.defaultPermeabilityK;
	}

	public double getPermeabilityNa() {
		return permeabilityNa;
	}

	public void setPermeabilityNa(double permeabilityNa) {
		this.permeabilityNa = permeabilityNa;
	}

	public double getPermeabilityK() {
		return permeabilityK;
	}

	public void setPermeabilityK(double permeabilityK) {
		this.permeabilityK = permeabilityK;
	}

	@Override
	public double getFluxA() {
		
		return 0;
	}

	@Override
	public double getFluxMg() {
		
		return 0;
	}

	@Override
	public double getFluxCa() {
		
		return 0;
	}

	@Override
	public double getFluxH() {
		
		return 0;
	}

}
