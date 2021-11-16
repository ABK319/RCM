/**
 * 
 */
package modelcomponents;

/**
 * @author Abhishek 
 *
 */
public abstract class Pump {
	
	/**
	 * Generic template class used to create more pumps
	 * abstract getter methods simply return 0 if unused
	 * @param em
	 * @param temperature
	 * @param I_18
	 */
	public  abstract void computeFlux(double em, double temperature,double I_18);
	
	public abstract double getFluxNa();
	public abstract double getFluxK();
	public abstract double getFluxA();
	public abstract double getFluxMg();
	public abstract double getFluxCa();
	public abstract double getFluxH();
	
	
}