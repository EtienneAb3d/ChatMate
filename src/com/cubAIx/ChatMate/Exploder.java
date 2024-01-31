package com.cubAIx.ChatMate;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Exploder {

	static public Exploder getExploder(String aPath,String aContent) {
		if(aPath != null && aPath.toLowerCase().endsWith(".srt")) {
			return new ExploderSRT();
		}
		//Basic detection is sufficient for this version
		if(aContent != null && aContent.indexOf(" --> ") > 0) {
			return new ExploderSRT();
		}
		return new Exploder();
	}
	
	public Vector<String> cutParts(String aContent,int aPartSize) {
		Vector<String> aParts = new Vector<String>();
		if(aPartSize <= 0) {
			//Not cut
			aParts.add(aContent);
			return aParts;
		}
		Vector<String> aParas = cutParas(aContent);
		if(aParas.size() < aPartSize * 1.5) {
			//Not sufficient
			aParts.add(aContent);
			return aParts;
		}
		int aNbParts = 1+aParas.size()/aPartSize;
		int aNbParas = 1+aParas.size()/aNbParts;
		Vector<String> aPs = new Vector<String>();
		for(String aPara : aParas) {
			aPs.add(aPara);
			if(aPs.size() >= aNbParas) {
				aParts.add(mergeParas(aPs));
				aPs = new Vector<String>();
			}
		}
		if(aPs.size() > 0) {
			aParts.add(mergeParas(aPs));
			aPs = new Vector<String>();
		}
		return aParts;
	}
	
	public Vector<String> cutParas(String aContent) {
		Vector<String> aParas = new Vector<String>();
		List<String> aPs = (List<String>)Arrays.asList(aContent.split("\n"));
		aParas.addAll(aPs);
		return aParas;
	}
	
	public String mergeParts(Vector<String> aParts) {
		Vector<String> aParas = new Vector<String>();
		for(String aPart : aParts) {
			aParas.addAll(cutParas(aPart));
		}
		return mergeParas(aParas);
	}
	
	public String mergeParas(Vector<String> aParas) {
		StringBuffer aSB = new StringBuffer();
		for(String aP : aParas) {
			aSB.append(aP).append("\n");
		}
		return aSB.toString();
	}
}
