package modelcomponents;



public class CaPumpMg2 extends Pump{
	private final Region cell;
	
	private final NaPump NaPumpU;
	private double hiK;
	private double caPMgK;
	private double caPMgKi;
	private double fCaPM;
	private double defaultFCaPM;
	private double powerConstant;
	private double caConstant;
	private double fluxCa;
	private double fluxH;
	private int caH;
	
	public CaPumpMg2(Region cell, Region medium, NaPump NaPumpU) {
		this.cell = cell;
		this.NaPumpU = NaPumpU;
		this.setHiK(4e-7);
		this.setCaPMgK(0.1);
		this.caPMgKi = 7.0;
		this.setDefaultFCaPM(12.0);
		this.setPowerConstant(4.0);
		this.caConstant = 2e-4;
		this.setFluxCa(-0.03);
		
	}
	public void computeFlux(double em, double temperature,double I_18) { //12
		
		//next 5 are intermediate variables for equations 12.a and 12.b 
		
		double caPMg = (this.cell.Mgf.getConcentration()/(this.getCaPMgK()+this.cell.Mgf.getConcentration()))*(this.caPMgKi/(this.caPMgKi+this.cell.Mgf.getConcentration()));
		
		double caPHiK=(this.getHiK()/(this.getHiK()+this.cell.H.getConcentration()));
		
		double kCaP=-(this.getFCaPM()/this.NaPumpU.getPT())*caPMg*caPHiK; 
		
		double fluxNumerator = Math.pow(this.cell.Caf.getConcentration(),this.getPowerConstant()); //12.a (numerator)
		
		double fluxDenominator = (Math.pow(this.caConstant,this.getPowerConstant()) + Math.pow(this.cell.Caf.getConcentration(),this.getPowerConstant())); //12.a (denominator)
		
		this.setFluxCa(kCaP*fluxNumerator/fluxDenominator); //12.a
		
		switch(this.getCaH()) //12.b
		{
		case  1: this.setFluxH(-this.getFluxCa());
		
		case  2: this.setFluxH(0.0);
		
		case 0:  this.setFluxH(-2.0*this.getFluxCa()); 
		}
		
}
	
	public double getFCaPM() {
		return fCaPM;
	}
	public double getDefaultFCaPM() {
		return defaultFCaPM;
	}
	public void setDefaultFCaPM(double fCaPM) {
		this.defaultFCaPM = fCaPM;
		this.setFCaPM(fCaPM);
	}
	public void setFCaPM(double fCaPM) {
		this.fCaPM = fCaPM;
	}
	public void setCaConstant(double caConstant) {
		this.caConstant = caConstant;
	}
	public double getPowerConstant() {
		return powerConstant;
	}
	public void setPowerConstant(double powerConstant) {
		this.powerConstant = powerConstant;
	}
	public int getCaH() {
		return caH;
	}
	public void setCaH(Integer caH) {
		this.caH = caH;
	}
	public double getHiK() {
		return hiK;
	}
	public void setHiK(double hiK) {
		this.hiK = hiK;
	}
	public double getCaPMgK() {
		return caPMgK;
	}
	public void setCaPMgK(double caPMgK) {
		this.caPMgK = caPMgK;
	}
	public double getFluxH() {
		return fluxH;
	}
	public void setFluxH(double fluxH) {
		this.fluxH = fluxH;
	}
	 
	public double getFluxCa() {
		return fluxCa;
	}
	public void setFluxCa(double fluxCa) {
		this.fluxCa = fluxCa;
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
}

