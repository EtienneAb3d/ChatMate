package com.cubAIx.ChatMate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Task {
	Config config = null;
	File file = null;
	String userContent = null;
	String assistantContent = null;

	public Task(Config aConfig,String aUserContent) {
		config = aConfig;
		userContent = aUserContent;
	}
	
	public Task(Config aConfig,File aFile) {
		config = aConfig;
		file = aFile;
		try {
			BufferedReader aBR = new BufferedReader(new InputStreamReader(new FileInputStream(file.getCanonicalPath()), "utf-8"));
			String aLine;
			StringBuffer aSB = new StringBuffer(); 
			while((aLine = aBR.readLine()) != null) {
				aSB.append(aLine).append("\n");
			}
			aBR.close();
			userContent = aSB.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void saveResult() {
		try {
			String aPath = file.getCanonicalPath();
			int aPos = aPath.lastIndexOf(".");
			if(aPos <= 0) {
				aPos = aPath.length();
			}
			aPath = aPath.substring(0, aPos)+config.suffix+aPath.substring(aPos);
			BufferedWriter aBW = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(aPath)
					,"UTF8"));
			aBW.write(assistantContent);
			aBW.flush();
			aBW.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			}
		
	}
	
	public void process() throws Exception {
		URL obj = new URL(config.url);
		HttpURLConnection aCon = (HttpURLConnection) obj.openConnection();
		aCon.setRequestMethod("POST");
		aCon.setRequestProperty("Authorization", "Bearer " + config.key);
		aCon.setRequestProperty("Content-Type", "application/json");

		// The request body
		JSONObject aBody = new JSONObject();
		aBody.put("model", config.model);
		JSONArray aMsgs = new JSONArray();
		aBody.put("messages", aMsgs);
		JSONObject aMsg = new JSONObject();
		aMsgs.add(aMsg);
		aMsg.put("role", "system");
		aMsg.put("content", config.system);
		aMsg = new JSONObject();
		aMsgs.add(aMsg);
		aMsg.put("role", "user");
		aMsg.put("content", userContent);
		
		aCon.setDoOutput(true);
		OutputStreamWriter aOSW = new OutputStreamWriter(aCon.getOutputStream());
		aOSW.write(aBody.toJSONString());
		aOSW.flush();
		aOSW.close();

		// Response from ChatGPT
		BufferedReader aBR = new BufferedReader(new InputStreamReader(aCon.getInputStream()));
		String aLine = null;
		StringBuffer aSB = new StringBuffer();
		while ((aLine = aBR.readLine()) != null) {
			aSB.append(aLine);
		}
		aBR.close();

		// Extract the message.
		JSONParser parser = new JSONParser();
		JSONObject aJSA = (JSONObject)parser.parse(aSB.toString());
		assistantContent = ((String)
				((JSONObject)
						((JSONObject)
								((JSONArray)aJSA.get("choices")).get(0))
						.get("message"))
				.get("content"));
				
		System.out.println(assistantContent);
	}

	public static void main(String[] args) {
	}

}
