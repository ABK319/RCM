package modelcomponents;



public class PiezoPassiveCa extends Pump{
	private final Region cell; 
	private final Region medium;
	private final PiezoGoldman goldman;
	private double flux;
	private double fcalm;
	
	public PiezoPassiveCa(Region cell, Region medium, PiezoGoldman goldman) {
		this.cell = cell;
		this.medium = medium;
		this.goldman = goldman;
		this.setFlux(0.0);
		this.setFcalm(0.0); 
	}
	public void computeFlux(double em, double temperature,double I_18) {
		double fcalmX = -(this.getFcalm()/I_18); 
		
		double numerator = this.medium.Caf.getConcentration()-this.cell.Caf.getConcentration()*Math.exp(2*this.goldman.getGoldmanFactor());
		double denominator = 1-Math.exp(2*this.goldman.getGoldmanFactor());
		
		this.setFlux(fcalmX*2*this.goldman.getGoldmanFactor()*(numerator/denominator));
	}
	public Double getFcalm() {
		return fcalm;
	}
	public void setFcalm(Double fcalm) {
		this.fcalm = fcalm;
	}
	public Double getFlux() {
		return flux;
	}
	public void setFlux(Double flux) {
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

