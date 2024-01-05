package com.cubAIx.ChatMate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Config {
	public static String ChatMateUserDir = System.getProperty("user.home")+File.separatorChar+"ChatMate";
	static {
		if(!new File(ChatMateUserDir).exists()) {
			new File(ChatMateUserDir).mkdirs();
		}
	}
	
	public String name = "default";
	public String suffix = "-NEW";
	public String url = "https://api.openai.com/v1/chat/completions";
	public String model = "gpt-3.5-turbo";
	public String key = "XX-XXX";
	
	public String system = "You are a helpful assistant";
	
	
	public void save() {
		try {
			String aPath = ChatMateUserDir+File.separatorChar+name+".config";
			BufferedWriter aBW = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(aPath)
					,"UTF8"));
			aBW.write("suffix="+suffix+"\n");
			aBW.write("model="+model+"\n");
			aBW.write("key="+key+"\n");
			aBW.write("system="+system.replaceAll("\n", "<br/>")+"\n");
			aBW.flush();
			aBW.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public boolean load() {
		try {
			String aPath = ChatMateUserDir+File.separatorChar+name+".config";
			if(!new File(aPath).exists()) {
				//??
				return false;
			}
			BufferedReader aBR = new BufferedReader(new InputStreamReader(new FileInputStream(aPath), "utf-8"));
			String aLine;
			StringBuffer aSB = new StringBuffer(); 
			while((aLine = aBR.readLine()) != null) {
				int aPos = aLine.indexOf("=");
				if(aPos <= 0) {
					//??
					continue;
				}
				String aK = aLine.substring(0, aPos);
				if("suffix".equals(aK)) {
					suffix = aLine.substring(aPos+1);
				}
				if("model".equals(aK)) {
					model = aLine.substring(aPos+1);
				}
				if("key".equals(aK)) {
					key = aLine.substring(aPos+1);
				}
				if("system".equals(aK)) {
					system = aLine.substring(aPos+1).replaceAll("<br/>", "\n");
				}
			}
			aBR.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return true;
	}
}
