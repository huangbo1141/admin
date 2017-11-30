package com.hgc.admin.model;

import com.hgc.admin.database.model.*;

public class StationModel {
	private Station station;
	private Line line;
	private Dan dan;
	
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public Line getLine() {
		return line;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	public Dan getDan() {
		return dan;
	}
	public void setDan(Dan dan) {
		this.dan = dan;
	}
	
	
}
