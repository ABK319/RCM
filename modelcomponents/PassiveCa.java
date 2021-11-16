package modelcomponents;

public class PassiveCa extends Pump {
	private final Region cell;
	private final Region medium;
	private final Goldman goldman;
	private double flux;
	private double caLK;
	private double caLiK;
	private double fCaLm;

	public PassiveCa(Region cell, Region medium, Goldman goldman) {
		this.cell = cell;
		this.medium = medium;
		this.goldman = goldman;
		this.setFlux(0.03);
		this.caLK = 0.8;
		this.caLiK = 0.0002;
		this.setFcalm(0.050);
	}

	public void computeFlux(double em, double temperature, double I_18) {
		double calReg = (this.caLiK / (this.caLiK + this.cell.Caf.getConcentration()))
				* (this.medium.Caf.getConcentration() / (this.caLK + this.medium.Caf.getConcentration())); // 15
		this.setFlux(-(this.getFcalm() / I_18) * calReg * 2 * this.goldman.getGoldmanFactor()
				* ((this.medium.Caf.getConcentration()
						- this.cell.Caf.getConcentration() * Math.exp(2 * this.goldman.getGoldmanFactor()))
						/ (1 - Math.exp(2 * this.goldman.getGoldmanFactor()))));
	}

	public double getFcalm() {
		return fCaLm;
	}

	public void setFcalm(double fcalm) {
		this.fCaLm = fcalm;
	}

	public double getFlux() {
		return flux;
	}

	public void setFlux(double flux) {
		this.flux = flux;
	}

	@Override
	public double getFluxNa() {
		
		return 0;
	}

	@Override
	public double getFluxK() {
		
		return 0;
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
