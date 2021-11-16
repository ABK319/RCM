package modelcomponents;

import java.util.ArrayList;
import java.util.HashMap;



public class OptionsParsers { 
	
	public static void setScreenTimeFactorOptions(HashMap<String, String> options, ArrayList<String> usedoptions,
			RBCModel model) {
		String temp = options.get("Time");
		if (temp != null) {
			model.setDurationExperiment(model.getDurationExperiment() + Double.parseDouble(temp));
			usedoptions.add("Time");
		}

		temp = options.get("Accuracy");
		if (temp != null) {
			model.setDp(Integer.parseInt(temp));
			usedoptions.add("Accuracy");
		}

		temp = options.get("FrequencyFactor");
		if (temp != null) {
			model.setIntegrationIntervalFactor(Double.parseDouble(temp));
			usedoptions.add("FrequencyFactor");
		}

		temp = options.get("Regular dt");
		if (temp != null) {
			if (temp.equals("no")) {
				model.setComputeDeltaTime(false);
				usedoptions.add("Regular dt");
			} else if (temp.equals("yes")) {
				model.setComputeDeltaTime(true);
				usedoptions.add("Regular dt");
			} else {
				System.out.println("Invalud value for field compute_delta_time");
			}
		}
		temp = options.get("dt");
		if (temp != null) {
			model.setDeltaTime(Double.parseDouble(temp));
			usedoptions.add("dt");
		}
		temp = options.get("Cyclesperprint(epochs)");
		if (temp != null) {
			model.setCyclesPerPrint(Integer.parseInt(temp));
			usedoptions.add("Cyclesperprint(epochs)");
		}
	}

	public static void setTransportChangesOptions(HashMap<String, String> options, ArrayList<String> usedoptions,
			RBCModel model) {
		String temp = options.get("% inhibition of Na/K pump FNamax");
		if (temp != null) {
			Double inhibFac = (100.0 - Double.parseDouble(temp)) / 100.0;
			model.model.getNapump().setP1(model.model.getNapump().getDefaultP1() * inhibFac);
			usedoptions.add("% inhibition of Na/K pump FNamax");
		}

		temp = options.get("na-pump-reverse-flux-change");
		if (temp != null) {
			model.model.getNapump().setP2(Double.parseDouble(temp));
			usedoptions.add("na-pump-reverse-flux-change");
		}
		temp = options.get("naa-change");
		if (temp != null) {
			model.model.getCarriermediated().setPermeabilityNa(
					model.model.getCarriermediated().getDefaultPermabilityNa() * Double.parseDouble(temp) / 100.0);
			usedoptions.add("naa-change");
		}
		temp = options.get("ka-change");
		if (temp != null) {
			model.model.getCarriermediated().setPermeabilityK(
					model.model.getCarriermediated().getDefaultPermeabilityK() * Double.parseDouble(temp) / 100.0);
			usedoptions.add("ka-change");
		}
		// Not visible in the GUI at the moment....
		temp = options.get("cotransport-activation");
		if (temp != null) {
			Double co_f = Double.parseDouble(temp);
			model.model.getCotransport().setPermeability(0.0002 * co_f / 100.0);
			usedoptions.add("cotransport-activation");
		}
		temp = options.get("% inhibition/stimulation(-) of JS kAE1");
		if (temp != null) {
			Double jsfactor = Double.parseDouble(temp);
			jsfactor = (100.0 - jsfactor) / 100.0;
			model.model.getJS().setPermeability(model.model.getJS().getDefaultPermeability() * jsfactor);
			usedoptions.add("% inhibition/stimulation(-) of JS kAE1");
		}
		temp = options.get("% inhibition/stimulation(-) of FCaPmax");
		if (temp != null) {
			Double fc = (100.0 - Double.parseDouble(temp)) / 100.0;
			model.model.getCaPump().setFCaPM(model.model.getCaPump().getDefaultFCaPM() * fc);
			usedoptions.add("% inhibition/stimulation(-) of FCaPmax");
		}

		temp = options.get("% inhibition of PKGardosMax");
		if (temp != null) {
			Double gc = (100.0 - Double.parseDouble(temp)) / 100.0;
			model.model.getGoldman().setPkm(model.model.getGoldman().getDefaultPkm() * gc);
			usedoptions.add("% inhibition of PKGardosMax");
		}
	}

	public static void naPumpScreenRS(HashMap<String, String> rsoptions, ArrayList<String> usedoptions,
			RBCModel model) {
		String na_efflux_fwd = rsoptions.get("Na/K pump Na efflux");
		if (na_efflux_fwd != null) {
			model.model.getNapump().setFluxFwd(Double.parseDouble(na_efflux_fwd));
			usedoptions.add("Na/K pump Na efflux");
		}

		String na_efflux_rev = rsoptions.get("na-efflux-rev");
		if (na_efflux_rev != null) {
			model.model.getNapump().setFluxRev(Double.parseDouble(na_efflux_rev));
			usedoptions.add("na-efflux-rev");
		}

		// Other code to be added here...
		// New ones added for reduced RS in March 18
		String temp = rsoptions.get("CNa");
		if (temp != null) {
			model.model.cell.Na.setConcentration(Double.parseDouble(temp));
			usedoptions.add("CNa");
		}
		temp = rsoptions.get("CK");
		if (temp != null) {
			model.model.cell.K.setConcentration(Double.parseDouble(temp));
			usedoptions.add("CK");
		}

		temp = rsoptions.get("Q10 passive");
		if (temp != null) {
			model.Q10Passive = Double.parseDouble(temp);
			usedoptions.add("Q10 passive");
		}
		/*
		 * The following sets the active Q10 in the sodium pump which is also used by
		 * the Ca-Mg transporter
		 */
		temp = rsoptions.get("Q10 active");
		if (temp != null) {
			model.model.napump.setQ10(Double.parseDouble(temp));
			usedoptions.add("Q10 active");
		}
	}

	public static void cellWaterScreenRS(HashMap<String, String> rsoptions, ArrayList<String> usedoptions,
			RBCModel model) {
		String hb_content_str = rsoptions.get("MCHC");
		if (hb_content_str != null) {
			usedoptions.add("MCHC");
			model.setHbContent(Double.parseDouble(hb_content_str));
		}
		model.model.cell.Hb.setAmount(model.getHbContent() * 10.0 / 64.5);
		model.setWaterVolume(1.0 - model.getHbContent() / 136.0);
		model.setVlysis(1.45);
		if (model.getHbContent() == 34.0) {
			String temp = rsoptions.get("Vw");
			if (temp != null) {
				model.setWaterVolume(Double.parseDouble(temp));
				usedoptions.add("Vw");
			}
			temp = rsoptions.get("lytic-cell-water");
			if (temp != null) {
				model.setVlysis(Double.parseDouble(temp));
				usedoptions.add("lytic-cell-water");
			}
		}
	}

	public static void cellanionProtonScreenRS(HashMap<String, String> rsoptions, ArrayList<String> usedoptions,
			RBCModel model) {
		String temp = rsoptions.get("CA");
		if (temp != null) {
			model.model.cell.A.setConcentration(Double.parseDouble(temp));
			usedoptions.add("CA");
		}
	}

	public static void chargeAndPiScreenRS(HashMap<String, String> rsoptions, ArrayList<String> usedoptions,
			RBCModel model) {
		String temp = rsoptions.get("a");
		if (temp != null) {
			model.setPtcSegment(Double.parseDouble(temp));
			usedoptions.add("a");
		}
		temp = rsoptions.get("pi");
		if (temp != null) {
			model.setPit0(Double.parseDouble(temp));
			usedoptions.add("pi");
		}

		// New option added for reduced RS, March 18
		temp = rsoptions.get("Hb A or S");
		if (temp != null) {
			if (temp == "A") {
				model.setPtcSegment(-1.0);
				model.setPit0(7.2);
			} else if (temp == "S") {
				model.setPtcSegment(-8.0);
				model.setPit0(7.4);
			}
			usedoptions.add("Hb A or S");
		}
	}

	public static void mgBufferScreenRS(HashMap<String, String> rsoptions, ArrayList<String> usedoptions,
			RBCModel model) {
		String temp = rsoptions.get("mgot-medium");
		if (temp != null) {
			model.model.medium.Mgt.setConcentration(Double.parseDouble(temp));
			usedoptions.add("mgot-medium");
		} else {
			model.model.medium.Mgt.setConcentration(0.2);
		}

		temp = rsoptions.get("mgit");
		if (temp != null) {
			model.model.cell.Mgt.setAmount(Double.parseDouble(temp));
			usedoptions.add("mgit");
		} else {
			model.model.cell.Mgt.setAmount(2.5);
		}

		temp = rsoptions.get("hab");
		if (temp != null) {
			model.setMgb0(Double.parseDouble(temp));
			usedoptions.add("hab");
		} else {
			model.setMgb0(0.05);
		}

		temp = rsoptions.get("atpp");
		if (temp != null) {
			model.setAtp(Double.parseDouble(temp));
			usedoptions.add("atpp");
		} else {
			model.setAtp(1.2);
		}

		temp = rsoptions.get("23dpg");
		if (temp != null) {
			model.setDpgp(Double.parseDouble(temp));
			usedoptions.add("23dpg");
		} else {
			model.setDpgp(15.0);
		}
		Double conc = NewtonRaphson.newtonRaphson(model.new Eqmg(), 0.02, 0.0001, 0.00001, 100, model.getItCounter(),
				0, false);
		model.model.cell.Mgf.setConcentration(conc);

	}

	public static void caBufferScreenRS(HashMap<String, String> rsoptions, ArrayList<String> usedoptions,
			RBCModel model) {
		String temp = rsoptions.get("cato-medium");
		if (temp != null) {
			model.model.medium.Cat.setConcentration(Double.parseDouble(temp));
			usedoptions.add("cato-medium");
		} else {
			model.model.medium.Cat.setConcentration(1.0);
		}

		temp = rsoptions.get("add-ca-buffer");
		if (temp != null) {
			model.setB1ca(Double.parseDouble(temp));
			usedoptions.add("add-ca-buffer");
		} else {
			model.setB1ca(0.026);
		}

		temp = rsoptions.get("kd-of-ca-buffer");
		if (temp != null) {
			model.setB1cak(Double.parseDouble(temp));
			usedoptions.add("kd-of-ca-buffer");
		} else {
			model.setB1cak(0.014);
		}

		temp = rsoptions.get("alpha");
		if (temp != null) {
			model.setAlpha(Double.parseDouble(temp));
			usedoptions.add("alpha");
		} else {
			model.setAlpha(0.3);
		}

		temp = rsoptions.get("benz2loaded");
		if (temp != null) {
			model.setBenz2(Double.parseDouble(temp));
			usedoptions.add("benz2loaded");
		} else {
			model.setBenz2(0.0);
		}
		model.setCbenz2(model.getBenz2() / model.getVw());

		temp = rsoptions.get("PMCA FCaPmax");
		if (temp != null) {
			model.model.capump.setDefaultFCaPM(Double.parseDouble(temp));
			usedoptions.add("PMCA FCaPmax");
		}

		temp = rsoptions.get("k1/2");
		if (temp != null) {
			model.model.capump.setCaConstant(Double.parseDouble(temp));
			usedoptions.add("k1/2");
		} else {
			model.model.capump.setCaConstant(2e-4);
		}

		temp = rsoptions.get("hills");
		if (temp != null) {
			model.model.capump.setPowerConstant(Double.parseDouble(temp));
			usedoptions.add("hills");
		} else {
			model.model.capump.setPowerConstant(4.0);
		}

		temp = rsoptions.get("pump-electro");
		Integer capstoich;
		if (temp != null) {
			capstoich = Integer.parseInt(temp);
			model.model.capump.setCaH(2 - capstoich);
			usedoptions.add("pump-electro");
		} else {
//			capstoich = 2; // Default sets cah to 0 which is 2 protons per Ca
			// This is now set in the constructor of the Calcium pump
		}

		temp = rsoptions.get("h+ki");
		if (temp != null) {
			model.model.capump.setHiK(Double.parseDouble(temp));
			usedoptions.add("h+ki");
		} else {
			model.model.capump.setHiK(4e-7);
		}

		temp = rsoptions.get("Mg2+K1/2");
		if (temp != null) {
			model.model.capump.setCaPMgK(Double.parseDouble(temp));
			usedoptions.add("Mg2+K1/2");
		} else {
			model.model.capump.setCaPMgK(0.1);
		}

		temp = rsoptions.get("PCaG");
		if (temp != null) {
			model.model.passiveca.setFcalm(Double.parseDouble(temp));
			usedoptions.add("PCaG");
		}
//		else {
//			model.passiveca.setFcalm(0.05);
//		}

		temp = rsoptions.get("PKGardosMax");
		if (temp != null) {
			model.model.goldman.setDefaultPkm(Double.parseDouble(temp));
			usedoptions.add("PKGardosMax");
		}
//		else {
//			model.goldman.setPkm(30.0);
//		}

		temp = rsoptions.get("KCa Gardos channel");
		if (temp != null) {
			model.model.goldman.setPkcak(Double.parseDouble(temp));
			usedoptions.add("KCa Gardos channel");
		}
//		else {
//			model.goldman.setPkcak(1e-2);
//		}
		if (model.getBenz2() != 0) {
			model.model.cell.Caf.setConcentration(1e-8);
			model.canr();
		}

	}
	
	public static void setPiezoOptions(HashMap<String, String> options, ArrayList<String> usedoptions,RBCModel model) {
		String temp = options.get("Pz stage no or yes");
		if (temp == null) {
			model.model.piezo = null;
			return;
		}
		if (temp != null) {
			usedoptions.add("Pz stage no or yes");
			if (!temp.equals("yes")) {
				// piezo is off by default
				model.model.piezo = null;
			} else {
				model.model.piezo = new Piezo();
				temp = options.get("piezo_start");
				if (temp != null) {
					usedoptions.add("piezo_start");
					// Convert to hours and add to sampling time
					model.model.piezo.setStartTime(Double.parseDouble(temp) / 60.0);
				}
				temp = options.get("PzOpen state");
				if (temp != null) {
					usedoptions.add("PzOpen state");
//					double duration_ms = Double.parseDouble(temp);
					double duration_s = Double.parseDouble(temp);
//					double duration_s = duration_ms / 1000.0;
					double duration_m = duration_s / 60.0;
					double duration_h = duration_m / 60.0;
					model.model.piezo.setDuration(duration_h);
				}

				temp = options.get("piezo_recovery");
				if (temp != null) {
					usedoptions.add("piezo_recovery");
					model.model.piezo.setRecovery(Double.parseDouble(temp) / 60.0);
				}

				temp = options.get("Pzcyclesperprint");
				if (temp != null) {
					usedoptions.add("Pzcyclesperprint");
					model.model.piezo.setCycles(Integer.parseInt(temp));
				}

				temp = options.get("PzK");
				if (temp != null) {
					usedoptions.add("PzK");
					model.model.piezo.setPkg(Double.parseDouble(temp));
				}

				temp = options.get("PzNa");
				if (temp != null) {
					usedoptions.add("PzNa");
					model.model.piezo.setPnag(Double.parseDouble(temp));
				}

				temp = options.get("PzCa");
				if (temp != null) {
					usedoptions.add("PzCa");
					model.model.piezo.setPcag(Double.parseDouble(temp));
				}

				temp = options.get("PzA");
				if (temp != null) {
					usedoptions.add("PzA");
					model.model.piezo.setPag(Double.parseDouble(temp));
				}

				temp = options.get("Pz PMCA I/S");
				if (temp != null) {
					usedoptions.add("Pz PMCA I/S");
					model.model.piezo.setPmca(Double.parseDouble(temp));
				}

				temp = options.get("PzFrequencyFactor");
				if (temp != null) {
					usedoptions.add("PzFrequencyFactor");
					model.model.piezo.setiF(Double.parseDouble(temp));
				}

				temp = options.get("Pz transit CVF");
				if (temp != null) {
					usedoptions.add("Pz transit CVF");
					model.model.piezo.setPiezoFraction(Double.parseDouble(temp));
				}

				temp = options.get("Pz JS I/S");
				if (temp != null) {
					Double jsfactor = Double.parseDouble(temp);
					jsfactor = (100.0 - jsfactor) / 100.0;
					model.model.piezo.setPiezoJS(jsfactor);
					usedoptions.add("Pz JS I/S");
				}
				temp = options.get("Restore Medium (no/yes)");
				if (temp != null) {
					if (temp.equals("yes")) {
						model.model.piezo.setRestoreMedium(true);
					} else {
						model.model.piezo.setRestoreMedium(false);
					}
					usedoptions.add("Restore Medium (no/yes)");
				}
				temp = options.get("Restored Medium HEPES-Na concentration");
				if (temp != null) {
					model.model.piezo.setRestoreHepesNa(Double.parseDouble(temp));
					usedoptions.add("Restored Medium HEPES-Na concentration");
				}
				temp = options.get("Restored Medium pH");
				if (temp != null) {
					model.model.piezo.setRestorepH(Double.parseDouble(temp));
					usedoptions.add("Restored Medium pH");
				}
				temp = options.get("Restored Medium Na");
				if (temp != null) {
					model.model.piezo.setRestoreNa(Double.parseDouble(temp));
					usedoptions.add("Restored Medium Na");
				}
				temp = options.get("Restored Medium K");
				if (temp != null) {
					model.model.piezo.setRestoreK(Double.parseDouble(temp));
					usedoptions.add("Restored Medium K");
				}
				temp = options.get("Restored Medium Mg");
				if (temp != null) {
					model.model.piezo.setRestoreMg(Double.parseDouble(temp));
					usedoptions.add("Restored Medium Mg");
				}
				temp = options.get("Restored Medium Ca");
				if (temp != null) {
					model.model.piezo.setRestoreCa(Double.parseDouble(temp));
					usedoptions.add("Restored Medium Ca");
				}

			}
		}
	}
	
	static void setTempPermeabilityOptions(HashMap<String, String> options, ArrayList<String> usedoptions,RBCModel model) {
		double defaultTemp = model.getTempCelsius();
		String temp = options.get("Temperature");
		if (temp != null) {
			model.setTempCelsius( Double.parseDouble(temp)); 
			usedoptions.add("Temperature");
			double piold = model.getPit0() - (0.016 * defaultTemp);
			double pinew = model.getPit0() - (0.016 * model.getTempCelsius());
			model.setpI(pinew);
			double newphc = pinew - piold + model.model.cell.getpH();
			model.model.cell.setpH(newphc);
			model.model.cell.H.setConcentration(Math.pow(10, -model.model.cell.getpH()));
			if (model.getBufferType() == "HEPES") {
				model.setPkhepes(7.83 - 0.014 * model.getTempCelsius());  
				double a5old = model.getKB();
				double m4old = model.model.medium.H.getConcentration();
				model.setKB(Math.pow(10.0, -(model.getPkhepes())));
				model.model.medium.H.setConcentration(model.getKB() * m4old / a5old);

				model.model.medium.setpH(-Math.log(model.model.medium.H.getConcentration()) / Math.log(10.0));
				model.model.medium.Hb.setConcentration(model.model.bufferConc
						* (model.model.medium.H.getConcentration() / (model.getKB() + model.model.medium.H.getConcentration())));

			} else {
				model.setKB(Math.pow(10.0, -model.getPka()));

				model.model.medium.H.setConcentration(model.getKB() * 0.0 / 0.0);

				model.model.medium.setpH(-Math.log(model.model.medium.H.getConcentration()) / Math.log(10.0)); //28b
				model.model.medium.Hb.setConcentration(model.model.bufferConc
						* (model.model.medium.H.getConcentration() / (model.getKB() + model.model.medium.H.getConcentration())));
			}
		}

		temp = options.get("Pw");
		if (temp != null) {
			model.model.water.setPermeability(Double.parseDouble(temp));
			usedoptions.add("Pw");
		}

		temp = options.get("PK");
		if (temp != null) {
			model.model.goldman.setPermeabilityK(Double.parseDouble(temp));
			usedoptions.add("PK");
		}

		temp = options.get("pgkh");
		if (temp != null) {
			model.model.goldman.setPgkh(Double.parseDouble(temp));
			usedoptions.add("pgkh");
		}

		temp = options.get("PNa");
		if (temp != null) {
			model.model.goldman.setPermeabilityNa(Double.parseDouble(temp));
			usedoptions.add("PNa");
		}

		temp = options.get("PA");
		if (temp != null) {
			model.model.goldman.setPermeabilityA(Double.parseDouble(temp));
			usedoptions.add("PA");
		}

		temp = options.get("PH");
		if (temp != null) {
			model.model.goldman.setPermeabilityH(Double.parseDouble(temp));
			usedoptions.add("PH");
		}

		// New option Jan 2018
		temp = options.get("PCa");
		if (temp != null) {
			model.model.passiveca.setFcalm(Double.parseDouble(temp));
			usedoptions.add("PCa");
		}

		temp = options.get("PA23CaMg");
		if (temp != null) {
			model.model.a23.setPermeabilityMg(Double.parseDouble(temp));
			usedoptions.add("PA23CaMg");
			temp = options.get("a23cam");
			if (temp != null) {
				model.model.a23.setKmCa(Double.parseDouble(temp));
				usedoptions.add("a23cam");
			} else {
				model.model.a23.setKmCa(10.0);
			}
			temp = options.get("a23mgm");
			if (temp != null) {
				model.model.a23.setKmMg(Double.parseDouble(temp));
				usedoptions.add("a23mgm");
			} else {
				model.model.a23.setKmMg(10.0);
			}
			temp = options.get("a23cai");
			if (temp != null) {
				model.model.a23.setKiCa(Double.parseDouble(temp));
				usedoptions.add("a23cai");
			} else {
				model.model.a23.setKiCa(10.0);
			}
			temp = options.get("a23mgi");
			if (temp != null) {
				model.model.a23.setKiMg(Double.parseDouble(temp));
				usedoptions.add("a23mgi");
			} else {
				model.model.a23.setKiMg(10.0);
			}
		}

		model.model.a23.setPermeabilityCa(model.model.a23.getPermeabilityMg());

		temp = options.get("Hb Oxy or Deoxy");
		if (temp != null) {
			double oldPit = model.getPit0(); // Store old value
			usedoptions.add("Hb Oxy or Deoxy");
			if (temp.equals("Oxy")) {
				model.setPit0(7.2);
			} else if (temp.equals("Deoxy")) {
				model.setPit0(7.5);
			} else {
				model.setPit0(7.2);
			}
			model.model.cell.setpH(model.getPit0() - oldPit + model.model.cell.getpH());
			model.model.cell.H.setConcentration(Math.pow(10.0, -model.model.cell.getpH()));
			model.setpI(model.getPit0() - (0.016 * model.getTempCelsius()));
			temp = options.get("deoxy");
			if (temp == "Y") {
				model.setAtp(model.getAtp() / 2.0);
				model.setDpgp(model.getDpgp() / 1.7);
			}

		}

	}
	
	static void setCellFractionOptions(HashMap<String, String> options, ArrayList<String> usedoptions, RBCModel model) {
		String temp = options.get("CVF");
		if (temp != null) {
			model.setFraction(Double.parseDouble(temp));
			model.setDefaultFraction(model.getFraction());

			usedoptions.add("CVF");
//		}
			/*
			 * Note: this change to overcome the problems that build up over multiple
			 * consecutive dynamic states
			 */

			if (model.getInitialHematocrit() != model.getFraction()) {
				model.setInitialHematocrit(model.getFraction());
				model.setHematocritFraction(model.getInitialHematocrit() / (1.0 - model.getInitialHematocrit()));
			}
		}
		temp = options.get("buffer-name");
		if (temp != null) {
			int bufferNumber = Integer.parseInt(temp);
			switch (bufferNumber) {
			case 0:
				model.setBufferType("HEPES");
			case 1:
				model.setBufferType("A");
			case 2:
				model.setBufferType("C");
			}
			usedoptions.add("buffer-name");
			temp = options.get("pka");
			if (temp != null) {
				model.setPka(Double.parseDouble(temp));
				usedoptions.add("pka");
			}
		}

		temp = options.get("MB");
		if (temp != null) {
			model.model.bufferConc = Double.parseDouble(temp);
			usedoptions.add("MB");
		}

		model.setmPhT0(model.model.medium.getpH());
		temp = options.get("pHo");
		if (temp != null) {
			model.model.medium.setpH(Double.parseDouble(temp));
			usedoptions.add("pHo");
		}
		model.phadjust();

		temp = options.get("Na x Glucamine");
		if (temp != null) {
			model.setParseTemp(Double.parseDouble(temp));
			usedoptions.add("Na x Glucamine");
			model.model.medium.Glucamine.setConcentration(model.model.medium.Glucamine.getConcentration() + model.getParseTemp());
			model.model.medium.Na.setConcentration(model.model.medium.Na.getConcentration() - model.getParseTemp());
		}
		// Should this one be removed once done?
		temp = options.get("A x Gluconate");
		if (temp != null) {
			model.setClGExchange(Double.parseDouble(temp));
			usedoptions.add("A x Gluconate");
			model.model.medium.Gluconate.setConcentration(model.model.medium.Gluconate.getConcentration() + model.getClGExchange());
			model.model.medium.A.setConcentration(model.model.medium.A.getConcentration() - model.getClGExchange());
			if (model.getClGExchange() != 0) {
				String temp2 = options.get("clxdur");
				if (temp2 != null) {
					model.setT6(Double.parseDouble(temp2));
					usedoptions.add("clxdur");
				}
			}
		}

		temp = options.get("Replace KCl with NaCl");
		if (temp != null) {
			model.setParseTemp(Double.parseDouble(temp));
			model.model.medium.Na.setConcentration(model.model.medium.Na.getConcentration() + model.getParseTemp());
			model.model.medium.K.setConcentration(model.model.medium.K.getConcentration() - model.getParseTemp());
			usedoptions.add("Replace KCl with NaCl");
//			options.remove("NaxK");
		}

		temp = options.get("Replace NaCl with KCl");
		if (temp != null) {
			model.setParseTemp(Double.parseDouble(temp));
			model.model.medium.Na.setConcentration(model.model.medium.Na.getConcentration() - model.getParseTemp());
			model.model.medium.K.setConcentration(model.model.medium.K.getConcentration() + model.getParseTemp());
			usedoptions.add("Replace NaCl with KCl");
			// options.remove("KxNa");
		}

		temp = options.get("NaCl add/remove");
		if (temp != null) {
			model.setParseTemp(Double.parseDouble(temp));
			model.model.medium.Na.setConcentration(model.model.medium.Na.getConcentration() + model.getParseTemp());
			model.model.medium.A.setConcentration(model.model.medium.A.getConcentration() + model.getParseTemp());
			usedoptions.add("NaCl add/remove");
		}

		temp = options.get("KCl add/remove");
		if (temp != null) {
			model.setParseTemp(Double.parseDouble(temp));
			model.model.medium.K.setConcentration(model.model.medium.K.getConcentration() + model.getParseTemp());
			model.model.medium.A.setConcentration(model.model.medium.A.getConcentration() + model.getParseTemp());
			usedoptions.add("KCl add/remove");
		}

		temp = options.get("Sucrose add/remove");
		if (temp != null) {
			model.setParseTemp(Double.parseDouble(temp));
			model.model.medium.Sucrose.setConcentration(model.model.medium.Sucrose.getConcentration() + model.getParseTemp());
			usedoptions.add("Sucrose add/remove");
		}

		temp = options.get("MMg");
		if (temp != null) {
			double mgtold = model.model.medium.Mgt.getConcentration();
			model.model.medium.Mgt.setConcentration(Double.parseDouble(temp));
			usedoptions.add("MMg");
			if (model.model.medium.Mgt.getConcentration() != 0) {
				model.model.medium.A.setConcentration(
						model.model.medium.A.getConcentration() + 2.0 * (model.model.medium.Mgt.getConcentration() - mgtold));
			}
		}

		temp = options.get("MCa");
		if (temp != null) {
			double catold = model.model.medium.Cat.getConcentration();
			model.model.medium.Cat.setConcentration(Double.parseDouble(temp));
			usedoptions.add("MCa");
			if (model.model.medium.Cat.getConcentration() != 0) {
				model.model.medium.A.setConcentration(
						model.model.medium.A.getConcentration() + 2.0 * (model.model.medium.Cat.getConcentration() - catold));
			}
		}

		temp = options.get("EDGTA 0; G(1); D(2)");
		if (temp != null) {
			usedoptions.add("EDGTA 0; G(1); D(2)");
			model.setLigchoice(Double.parseDouble(temp));
		}

		temp = options.get("MEDGTA"); // chelator concentration
		if (temp != null) {
			usedoptions.add("MEDGTA");
			model.setEdgto(Double.parseDouble(temp));
		}

		if (model.getLigchoice() == 1) { // EGTA

			model.computeEdgValues(-9.22, -8.65, -10.34, -5.10);

			if (model.model.medium.Cat.getConcentration() < model.getEdgto()) {
				model.model.medium.Caf.setConcentration(model.model.medium.Caf.getConcentration() / 100000.0);

			} else if (model.model.medium.Cat.getConcentration() == model.getEdgto()) {
				model.model.medium.Caf.setConcentration(model.model.medium.Cat.getConcentration() / 100.0);

			} else if (model.model.medium.Cat.getConcentration() > model.getEdgto()) {
				model.model.medium.Caf.setConcentration(Math.abs(model.model.medium.Cat.getConcentration() - model.getEdgto()));

			}
			if (model.model.medium.Mgt.getConcentration() < (model.getEdgto() - model.model.medium.Cat.getConcentration())) {
				model.model.medium.Mgf.setConcentration(model.model.medium.Mgt.getConcentration() / 5.0);
			} else {
				model.model.medium.Mgf.setConcentration(model.model.medium.Mgt.getConcentration());
			}

		} else if (model.getLigchoice() == 2) { // EDTA
			model.computeEdgValues(-9.84, -5.92, -9.95, -8.46);

			// Initial cafo/mgfo values for iterative solution
			double camgratio = model.model.medium.Cat.getConcentration()
					/ (model.model.medium.Cat.getConcentration() + model.model.medium.Mgt.getConcentration());
			if (model.getEdgto() < (model.model.medium.Cat.getConcentration() + model.model.medium.Mgt.getConcentration())) {
				model.model.medium.Caf.setConcentration(model.model.medium.Cat.getConcentration() - model.getEdgto() * camgratio);
				model.model.medium.Mgf.setConcentration(model.model.medium.Mgt.getConcentration() - model.getEdgto() * (1.0 - camgratio));
			} else if (model.getEdgto() > (model.model.medium.Cat.getConcentration() + model.model.medium.Mgt.getConcentration())) {
				model.model.medium.Caf.setConcentration(model.model.medium.Cat.getConcentration() / 100000.0);
				model.model.medium.Mgf.setConcentration(model.model.medium.Mgt.getConcentration() / 1000.0);
			} else {
				model.model.medium.Caf.setConcentration(model.model.medium.Cat.getConcentration() / 1000.0);
				model.model.medium.Mgf.setConcentration(model.model.medium.Mgt.getConcentration() / 10.0);
			}

		} else if (model.getLigchoice() == 0) {
			model.setEdgto(0.0);
			model.model.medium.Mgf.setConcentration(model.model.medium.Mgt.getConcentration());
			model.model.medium.Caf.setConcentration(model.model.medium.Cat.getConcentration());
			return;
		}

		model.chelator();
		model.oldEdgTa();
	}

}
