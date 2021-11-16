package modelcomponents;

public class SetUpModel { //class designed to set up model with pumps and initial concentrations

	protected Region cell;
	protected Region medium;

	protected NaPump napump;
	protected JacobsStewart JS;
	protected Cotransport cotransport;
	protected CarrierMediated carriermediated;
	protected Goldman goldman;
	protected PiezoGoldman piezoGoldman;
	protected A23187 a23;
	protected WaterFlux water;
	protected PassiveCa passiveca;
	protected PiezoPassiveCa piezoPassiveca;
	protected CaPumpMg2 capump;
	protected Piezo piezo;

	protected double bufferConc;

	protected double temp = 0.0;
	protected double tempCelsius;
	protected double em = 0.0;
	protected double I_18;

	protected double totalFluxNa;
	protected double totalFluxK;
	protected double totalFluxA;
	protected double totalFluxH;
	protected double totalFluxCa;
	protected double totalFlux;

	public SetUpModel() {
		cell = new Region();
		medium = new Region();

		setNapump(new NaPump(cell, medium));
		capump = new CaPumpMg2(cell, medium, getNapump());
		JS = new JacobsStewart(cell, medium);
		cotransport = new Cotransport(cell, medium);
		carriermediated = new CarrierMediated(cell, medium);
		goldman = new Goldman(cell, medium);
		piezoGoldman = new PiezoGoldman(cell, medium);
		a23 = new A23187(cell, medium);
		water = new WaterFlux(cell, medium);
		passiveca = new PassiveCa(cell, medium, goldman);
		piezoPassiveca = new PiezoPassiveCa(cell, medium, piezoGoldman);

		setInitialFluxes();
		setInitialMediumConcentrations();
		setInitialCellConcentrations();
	}

	private void setInitialMediumConcentrations() { //starting medium concentrations
		this.medium.Na.setConcentration(145.0);
		this.medium.K.setConcentration(5.0);
		this.medium.A.setConcentration(145.0);
		this.medium.H.setConcentration(0.0);
		this.bufferConc = 10.0;
		this.medium.Hb.setConcentration(0.0);
	}

	private void setInitialCellConcentrations() { //starting cell concentrations
		this.cell.Na.setConcentration(10.0);
		this.cell.K.setConcentration(140.0);
		this.cell.A.setConcentration(95.0);
		this.cell.H.setConcentration(0.0);
		this.cell.Hb.setConcentration(0.0);
		this.cell.X.setConcentration(0.0);
		this.cell.Mgt.setConcentration(0.0);
		this.cell.XHbm.setConcentration(0.0);
		this.cell.Cat.setConcentration(0.0);
	}

	private void setInitialFluxes() { //starting fluxes
		this.totalFluxNa = 0.0;
		this.totalFluxK = 0.0;
		this.totalFluxA = 0.0;
		this.totalFluxH = 0.0;
		this.totalFluxCa = 0.0;
		getNapump().setFluxRev(0.0015);
	}

	public void totalCaFlux() { //10.e
		this.totalFluxCa = this.a23.getFluxCa() + this.passiveca.getFlux() + this.piezoPassiveca.getFlux()
				+ this.capump.getFluxCa();
	}

	public void totalFlux() { 
		Double goldFlux = this.goldman.getFluxH() + this.goldman.getFluxNa() + this.goldman.getFluxK()
				- this.goldman.getFluxA();
		Double pGoldFlux = this.piezoGoldman.getFluxH() + this.piezoGoldman.getFluxNa() + this.piezoGoldman.getFluxK()
				- this.piezoGoldman.getFluxA();
		this.totalFlux = this.getNapump().getTotalFlux() + goldFlux + pGoldFlux
				+ this.capump.getCaH() * this.capump.getFluxCa() + 2.0 * this.passiveca.getFlux()
				+ 2.0 * this.piezoPassiveca.getFlux();
	}

	void totalIonFluxes() { //10

		// Na flux
		this.totalFluxNa = this.getNapump().getFluxNet() + this.carriermediated.getFluxNa() + this.goldman.getFluxNa()
				+ this.piezoGoldman.getFluxNa() + this.cotransport.getFluxNa();

		// K flux
		this.totalFluxK = this.getNapump().getFluxK() + this.carriermediated.getFluxK() + this.goldman.getFluxK()
				+ this.piezoGoldman.getFluxK() + this.cotransport.getFluxK();

		// Anion flux
		this.totalFluxA = this.goldman.getFluxA() + this.piezoGoldman.getFluxA() + this.cotransport.getFluxA()
				+ this.JS.getFluxA() + this.carriermediated.getFluxNa() + this.carriermediated.getFluxK();

		// Net proton flux, includes H-flux through Ca pump
		this.totalFluxH = this.JS.getFluxH() + this.goldman.getFluxH() + this.piezoGoldman.getFluxH()
				- 2 * this.a23.getFluxMg() - 2 * this.a23.getFluxCa() + this.capump.getFluxH();

	}

	

	

	public double getMediumNaConcentration() {
		return this.medium.Na.getConcentration();
	}

	public double getMediumKConcentration() {
		return this.medium.K.getConcentration();
	}

	public NaPump getNapump() {
		return napump;
	}

	public void setNapump(NaPump napump) {
		this.napump = napump;
	}

	public CaPumpMg2 getCaPump() {
		return capump;
	}

	public void setCapump(CaPumpMg2 capump) {
		this.capump = capump;
	}

	public Cotransport getCotransport() {
		return cotransport;
	}

	public void setCotransport(Cotransport cotransport) {
		this.cotransport = cotransport;
	}

	public JacobsStewart getJS() {
		return JS;
	}

	public void setJS(JacobsStewart jS) {
		JS = jS;
	}

	public Goldman getGoldman() {
		return goldman;
	}

	public void setGoldman(Goldman goldman) {
		this.goldman = goldman;
	}

	public CarrierMediated getCarriermediated() {
		return carriermediated;
	}

	public void setCarriermediated(CarrierMediated carriermediated) {
		this.carriermediated = carriermediated;
	}

}
