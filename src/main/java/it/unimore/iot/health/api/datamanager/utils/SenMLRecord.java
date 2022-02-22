package it.unimore.iot.health.api.datamanager.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
+---------------+------+---------+
|         SenML | JSON | Type    |
+---------------+------+---------+
|     Base Name | bn   | String  |
|     Base Time | bt   | Number  |
|     Base Unit | bu   | String  |
|    Base Value | bv   | Number  |
|       Version | bver | Number  |
|          Name | n    | String  |
|          Unit | u    | String  |
|         Value | v    | Number  |
|  String Value | vs   | String  |
| Boolean Value | vb   | Boolean |
|    Data Value | vd   | String  |
|     Value Sum | s    | Number  |
|          Time | t    | Number  |
|   Update Time | ut   | Number  |
+---------------+------+---------+
*/

/**
 * {@code SenMLRecord} class provides standardized structure for telemetry data
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SenMLRecord {

	// Attributes
	private String bn;
	private Number bt;
	private String bu;
	private Number bv, bver;
	private String n, u;
	private Double v;
	private String vs;
	private Boolean vb;
	private String vd;
	private Number s;
	private Number t;
	private Number ut;


	// Default Constructor
	public SenMLRecord() {
	}


	// Getters and Setters
	public String getBn() {
		return bn;
	}

	public void setBn(String bn) {
		this.bn = bn;
	}

	public Number getBt() {
		return bt;
	}

	public void setBt(Number bt) {
		this.bt = bt;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public Number getBv() {
		return bv;
	}

	public void setBv(Number bv) {
		this.bv = bv;
	}

	public Number getBver() {
		return bver;
	}

	public void setBver(Number bver) {
		this.bver = bver;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public Double getV() {
		return v;
	}

	public void setV(Double v) {
		this.v = v;
	}

	public String getVs() {
		return vs;
	}

	public void setVs(String vs) {
		this.vs = vs;
	}

	public Boolean getVb() {
		return vb;
	}

	public void setVb(Boolean vb) {
		this.vb = vb;
	}

	public String getVd() {
		return vd;
	}

	public void setVd(String vd) {
		this.vd = vd;
	}

	public Number getS() {
		return s;
	}

	public void setS(Number s) {
		this.s = s;
	}

	public Number getT() {
		return t;
	}

	public void setT(Number t) {
		this.t = t;
	}

	public Number getUt() {
		return ut;
	}

	public void setUt(Number ut) {
		this.ut = ut;
	}


	// Override toString() to print
	@Override
	public String toString() {
		return "SenML [ " + (bn != null ? "bn=" + bn + "  " : "") + (bt != null ? "bt=" + bt + "  " : "")
				+ (bu != null ? "bu=" + bu + "  " : "") + (bv != null ? "bv=" + bv + "  " : "")
				+ (bver != null ? "bver=" + bver + "  " : "") + (n != null ? "n=" + n + "  " : "")
				+ (u != null ? "u=" + u + "  " : "") + (v != null ? "v=" + v + "  " : "")
				+ (vs != null ? "vs=" + vs + "  " : "") + (vb != null ? "vb=" + vb + "  " : "")
				+ (vd != null ? "vd=" + vd + "  " : "") + (s != null ? "s=" + s + "  " : "")
				+ (t != null ? "t=" + t + "  " : "") + (ut != null ? "ut=" + ut + "  " : "") + "]";
	}

}