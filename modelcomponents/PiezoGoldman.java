package modelcomponents;



public class PiezoGoldman extends Pump{
	private final Region cell;
	private final Region medium; 
	private double permeabilityNa ;
	private double permeabilityA ;
	private double permeabilityH ;
	private double permeabilityK ;

	private double fluxNa ;
	private double fluxA ;
	private double fluxH ;
	private double fluxK ;

	private double GoldmanFactor ;
	
	private double rtoverf;
	private double foverrt;
	
	private double gConst; 
	
	public PiezoGoldman(Region cell, Region medium) {
		this.cell = cell;
		this.medium = medium;
		
		this.setFluxNa(0.0);
		this.setFluxA(0.0);
		this.setFluxH(0.0);
		this.setFluxK(0.0);
		this.gConst = 8.6156e-2;
		this.GoldmanFactor = 0.0;
				
	}
	
	private void gfactors(double Em, double temperature) {
		this.rtoverf = ((gConst)*(273+temperature));
		this.foverrt = 1.0/((gConst)*(273+temperature));
		this.GoldmanFactor = Em*foverrt;
	}
	public double getRtoverf() {
		return this.rtoverf;
	}
	public double getFoverrt() {
		return this.foverrt;
	}

	public void computeFlux(double em, double temperature, double I_18) {
		this.gfactors(em,temperature);
		this.setFluxNa(this.fullgflux(this.cell.Na,this.medium.Na,this.getPermeabilityNa(),I_18));
		this.setFluxA(this.fullgflux(this.cell.A,this.medium.A,this.getPermeabilityA(),I_18));
		this.setFluxH(this.fullgflux(this.cell.H,this.medium.H,this.getPermeabilityH(),I_18));
		this.setFluxK(this.fullgflux(this.cell.K,this.medium.K,this.getPermeabilityK(),I_18));
	}
	private double gflux(Species cellSpecies, Species mediumSpecies) {
		return -cellSpecies.getZ()*this.GoldmanFactor*(mediumSpecies.getConcentration() - cellSpecies.getConcentration()*Math.exp(cellSpecies.getZ()*this.GoldmanFactor))/(1.0-Math.exp(cellSpecies.getZ()*this.GoldmanFactor));
	}
	
	private double fullgflux(Species cellSpecies,Species mediumSpecies,double permeability, double I_18) {
		return (permeability/I_18)*this.gflux(cellSpecies,mediumSpecies);
	}
	public double getGoldmanFactor() {
		return this.GoldmanFactor;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFluxCa() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	
}

