package utilities;

public class HelpText {
	public static final String rsHelp = "<html><body style='width: 500px'>"
			+ "<h1>The Reference State (RS):</h1>"
			+ "<p>The RS is a steady-state representing the initial physiological condition of a RBC at the start of experiments. Changing default values automatically recalculates a new initial steady-state condition for the redefined cell. For instance, to approximate a young RBC one could replace the corresponding defaults by CNa 5, CK 145, Vw 0.82 and FNaP -3.2.</p><hr />" + 
			"<p><b>HbA or HbS:</b> HbA and HbS differ in the values of their isoelectric point and net charge per mol, a.  For HbA (default) pI(oC) = 7.2 and a = -10 Eq/(mol*∆(pH – pI) unit); for HbS, the corresponding values are 7.4 and -8.  The net charge on Hb at each pHi, n<sub>Hb</sub>, is computed from n<sub>Hb</sub> = a(pHi – pI), where a is the slope of the proton titration curve of Hb in Eq/mol, and pI is the isoelectric pH of Hb.</p><hr />" + 
			"<p><b>Na/K pump Na efflux:</b>	Changing the Na flux automatically resets the associated pump-mediated K influx and reverse Na/K fluxes following the stoichiometries and relative forward-reverse flux ratio encoded in the model.</p><hr />" + 
			"<p><b>CA:</b>	Initial cell Cl<sub>-</sub> + HCO<sub>3-</sub> concentration (mmol/Lcw). Ca and CCl are used indistinctly throughout.</p><hr />" + 
			"<p><b>MCHC:</b> Mean Cell Haemoglobin Concentration, a common haematological parameter in blood test assays, traditionally reported in gHb/dLoc; MCHC of <i>mean</i> model cell = 34 gHb/dL</p><hr />" + 
			"<p><b>PMCA Fmax:</b>	Maximal Ca<sup>2+</sup> extrusion flux through an ATP and Ca<sup>2+</sup>-saturated plasma membrane calcium pump</p><hr />" + 
			"<p><b>PKGardosMax:</b>	electrodiffusional K<sup>+</sup> permeability through Ca<sup>2+</sup>-saturated Gardos channels</p><hr />" + 
			"<p><b>KCaGardos channel:</b>	Half-maximal Ca<sup>2+</sup> dissociation constant (K1/2) for Gardos channel activation</p><hr />" + 
			"<p><b>Vw:</b>	Water content associated with 340 g Hb; the volume occupied by 340 g Hb tetramer with a molar weight of 1.36 g/ml is 0.25 L. The default 0.75 Lcw/Loc for Vw sets a value of 1 L/Loc for the initial volume of the default <i>mean</i> RBC.</p><hr />" + 
			"<p><b>Q10 active or passive:</b>	The Q10 factors determine the extent by which active and passive fluxes (F) are set to increase or decrease for each 10oC increase or decrease in temperature, T.</p><hr />" + 
			"</body></html>";
	public static final String dsHelp = "<html><body style='width: 500px'>"
			+"<h1>The Dynamic State (DS)</h1>" + 
			"<p>Displays five tabs grouping lists of parameters and variables with default values for constructing one or successive stages in simulated experimental protocols</p>" + 
			"<hr />" + 
			"<h2>Time & Data Output Frequency:</h2>" + 
			"<p><b>Time:</b>Sets the duration of each DSn stage </p>" + 
			"<p><b>FrequencyFactor:</b> Sets the duration of each iteration interval (∆t at time = t) inversely proportional to the absolute value of the sum of all the net fluxes across the cell membrane at time t.  Allows data output frequencies to appear proportional to the rate of change in the system at constant “cyclesperprint” values. </p>" + 
			"<p><b>Cyclesperprint:</b> sets the number of computational cycles between data output points.</p>" + 
			"<p><b>Accuracy:</b> sets the decimal precision on the output data  </p>" + 
			"<hr />" + 
			"<h2>Cell fraction and Medium Composition: </h2>" + 
			"<p>Medium concentrations of X are indicated by MX in mM units.  Isosmotic exchanges of X for Y are shown as X x Y.  Addition/removals allow changes in medium osmolarity. HEPES, Glucamine, gluconate, sucrose, Mg, EGTA and EDTA are treated as impermeant medium solutes. Sucrose represents any electroneutral impermeant small solute used to alter medium osmolarity. Gluconate and glucamine represent any impermeant monovalent ion used to replace A or Na in the medium.    </p>" + 
			"<p><b>MB:</b> Medium buffer concentration, HEPES by default</p>" + 
			"<p><b>EDGTA 0; 1; 2:</b>  Prompts for the addition of EGTA (G(1)) or EDTA (D(2)) to the cell suspension. </p>" + 
			"<p><b>MEDGTA:</b>  Prompts for the concentration of EGTA or EDTA, if added.  The default is 0, no addition.</p>    " + 
			"<hr />" + 
			"<h2>Temperature & Permeabilities:</h2>" + 
			"<p><b>Notation on the unit used for electrodiffusional ion permeabilities, 1/h or h<sup>-1</sup>:</b> 1/h is a widely used permeability unit in the RBC literature. For permeability comparisons between membranes from different cell types the most widely used unit is cm/s.  For RBCs, both units are related through P<sub>cm/s</sub>= P<sub>1/h</sub>*(V/A)/3600, where V and A correspond to the RBC volume and membrane area (in cm units) at the time the permeability measurement was taken.    " + 
			"<p><b>PHG:</b> PHG was modelled to enable simulations of the effects of protonophore additions.  The default value represents no protonophore present.  To simulate observed effects change PHG from 2e-1 to 2e10.   </p>  " + 
			"<p><b>PA23CaMg:</b> Ionophore A23187 mediates electroneutral X<sup>2+</sup>:2H<sup>+</sup> exchanges with well defined highly non-linear kinetics in human RBCs. The default value represents absence of ionophore.  To simulate the effects of ionophore concentrations capable of generating a Ca<sup>2+</sup> flux exceeding that of the PMCA Fmax at medium Ca<sup>2+</sup> concentrations around 0.2 mM use values around 2e18. </p>       " + 
			"<p><b>Hb pI(0oC) oxy(7.2), deoxy(7.5):</b> Hb is assumed to be in a oxygen-saturated condition by default (oxy).</p>"
			+ "<p><b>Deoxygenation of Hb</b> (deoxy) changes its pI(0oC) from 7.2 to 7.5.  The model automatically adjusts the actual pI change for the temperature of the experiment.  The pI shifts during oxy-deoxy transitions cause changes in the protonization condition of Hb with secondary changes in pHi and [Mg<sup>2+</sup>]<su	b>i</sub>, changes which the model accurately predicts.  </p>" + 
			"<hr />" + 
			"<h2>Transport inhibition (%; defaults = 0):</h2>" + 
			"<p>The default Fmax value for each transporter, Fm, is modified according to Fm*(100-X)/100 where X is the % inhibition entered at the prompt.  Fm stays modified in successive DS stages unless modified again. Entries in successive DSs always apply to the original default (0%).  To return to the original uninhibited Fm enter “0”. The same equation delivers stimulation if you enter negative numbers. It applies to JS and PMCA entries only. For an n-fold stimulation, enter “–n*100”, for instance “-200” for a two-fold stimulation.</p>" + 
			"<hr />" + 
			"</body></html>";
	public static final String piezoHelp = "<html><body style='width: 500px'>"
			+ "<h1>The PIEZO1 routine</h1>" + 
			"<p>All the membrane transporters represented in the red cell model are active all the time and participate to different extents in the dynamic responses to perturbations.  PIEZO1 is unique in that it is generally inactive by default, responding only to conditions eliciting cell deformation, for instance during capillary transits. The dedicated PIEZO tag allows exploration of the potential effects of PIEZO1 activation during capillary transits. Preliminary tests showed that because of the brief duration of each transit (less than 1s), the magnitude of the changes in model variables was extremely small even attributing extremely high permeabilities to PIEZO1 mediation.  This required a substantial increase in decimal accuracy and in the density of data acquisition. The tag offers a protocol design with defaults open to change by the user, as outlined next.</p>" + 
			"<ol><li>In a DS selected for PIEZO1 implementation click on the PIEZO1 tab</li>" + 
			"<li>Double click on the “Incorporate PIEZO stage: no” and enter “yes”</li>" + 
			"<li>This brings up a predesigned DS stage with the following defaults</li>" + 
			"<ol><li>Incorporate PIEZO stage: yes</li>" +
			"<li>Pz Restore Medium: yes</li>" + 
			"<li>Open state: 0.4 (s)</li>" + 
			"<li>Pz frequency factor: 1e-5</li>" + 
			"<li>Pz cycles per print: 111</li>" + 
			"<li>PzK: 0 (1/h)</li>" + 
			"<li>PzNa: 0 (1/h)</li>" + 
			"<li>PzA: 50 (1/h)</li>" + 
			"<li>PzCa: 70 (1/h)</li>" + 
			"</ol></ol>" + 
			"<p>Pz identifies PIEZO-related parameters and variables. The default five-minute duration of the PIEZO1 DS stage covers three consecutive periods: an initial two-minute baseline control, a PIEZO1 open state period, and a PIEZO1 closure period for the remainder time to five minutes to allow assessment of the short-term reversibility of the changes induced during the open state.  Pz accuracy determines the decimal precision of data output in the csv file.  Pz frequency factor and Pz cycles per print determine the density of data output over this period. The PzX values list the Goldmanian permeabilities assigned by default to K+, Na+, Cl-, and Ca2+ in the open state.  The precise ion selectivity, conductance and inactivation kinetics with which PIEZO1 channels operate in human RBCs is unknown at present. The default permeability values chosen render model outcomes in conformity with the scarce but solid experimental evidence available in RBCs (Dyrda et al., 2010; Danielczok et al., 2017).</p>" + 
			"</body></html>";
}
