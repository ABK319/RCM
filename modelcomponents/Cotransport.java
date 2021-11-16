package modelcomponents;



public class Cotransport extends Pump  {
	private double permeability = 0.000000001;
	private double fluxA;
	private double fluxNa;
	private double fluxK;
	private Region cell;
	private Region medium;
	private double zeroFactor;

	public Cotransport(Region cell, Region medium) {
		this.cell = cell;
		this.medium = medium;
	}
	
	public void computeZeroFactor() {
		double zeroNumerator = this.cell.Na.getConcentration()*this.cell.K.getConcentration()*Math.pow(this.cell.A.getConcentration(),2.0); //17.b (numerator)
		double zeroDenominator = this.medium.Na.getConcentration()*this.medium.K.getConcentration()*Math.pow(this.medium.A.getConcentration(), 2.0); //17.b (denominator)
		this.zeroFactor = zeroNumerator/zeroDenominator; //17.b
				
	}
	public void computeFlux(double em, double temperature,double I_18) {
		
		double permX = -(this.getPermeability()/I_18); //17.a (-kCo) 
		
		double fluxVar = permX*
				(Math.pow(this.cell.A.getConcentration(),2.0)*this.cell.Na.getConcentration()*this.cell.K.getConcentration() - 
						this.zeroFactor*(Math.pow(this.medium.A.getConcentration(), 2.0)*this.medium.Na.getConcentration()*this.medium.K.getConcentration())); //17.a
		
		this.fluxA = 2*fluxVar; //17.d
		this.fluxNa = fluxVar;  //17.c
		this.fluxK = fluxVar;	//17.c
	}
	public double getFluxA() {
		return this.fluxA;
	}
	public double getFluxNa() {
		return this.fluxNa;
	}
	public double getFluxK() {
		return this.fluxK;
	}

	public double getPermeability() {
		return permeability;
	}

	public void setPermeability(double permeability) {
		this.permeability = permeability;
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
