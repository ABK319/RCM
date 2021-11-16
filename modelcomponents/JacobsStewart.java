package modelcomponents;



public class JacobsStewart extends Pump{
	private Region cell;
	private Region medium;
	private double permeability;
	private double defaultPermeability;
	private double fluxA;
	private double fluxH;
	
	public JacobsStewart(Region cell,Region medium) {
		this.cell = cell;
		this.medium = medium;
		this.setDefaultPermeability(2.5e8);
		
	}
	public void computeFlux(double em, double temperature,double I_18) {
		
		double permX = -(this.getPermeability()/I_18);
		
		double fluxVar = permX*
				(this.cell.A.getConcentration()*this.cell.H.getConcentration() - 
						(this.medium.A.getConcentration()*this.medium.H.getConcentration()));  //16.c
		
		this.fluxA = fluxVar; 
		this.fluxH = fluxVar;
	}
	public double getFluxA() {
		return this.fluxA;
	}
	public double getFluxH() {
		return this.fluxH;
	}
	public Double getPermeability() {
		return permeability;
	}
	public Double getDefaultPermeability() {
		return defaultPermeability;
	}
	public void setDefaultPermeability(double permeability) {
		this.defaultPermeability = permeability;
		this.setPermeability(permeability);
	}
	public void setPermeability(double permeability) {
		this.permeability = permeability;
	}
	@Override
	public double getFluxNa() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getFluxK() {
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
	
}
