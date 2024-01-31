package com.cubAIx.ChatMate;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class ExploderSRT extends Exploder {

	@Override
	public Vector<String> cutParas(String aContent) {
		Vector<String> aParas = new Vector<String>();
		List<String> aPs = (List<String>)Arrays.asList(aContent.split("\n\n"));
		aParas.addAll(aPs);
		return aParas;
	}

	@Override
	public String mergeParas(Vector<String> aParas) {
		StringBuffer aSB = new StringBuffer();
		for(String aP : aParas) {
			aSB.append(aP).append("\n\n");
		}
		return aSB.toString();
	}
	
}
