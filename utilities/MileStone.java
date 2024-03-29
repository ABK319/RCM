package utilities;

import java.io.Serializable;

public class MileStone implements Serializable {
	private Double time;
	private String name;
	public MileStone(Double time,String name) {
		this.time = time;
		this.name = name;
	}
	public Double getTime() {
		return this.time;
	}
	public String getName() {
		return this.name;
	}
	public boolean check(Double time) {
		if(time >= this.time) {
			return true;
		}else {
			return false;
		}
	}
	public String toString() {
		// Note we print in minutes
		return this.name + ": " + this.time*60.0;
	}
	public void setTime(Double time) {
		this.time = time;
	}
}
