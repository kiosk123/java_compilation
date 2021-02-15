package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import util.XMLUtil;
import client.HttpClient;


public class Main {
	public static void main(String[] args) {

		File file = null;
		Map<String, String> params = new HashMap<String, String>();

		try {
			file = new File("../HttpClient/xml/test.xml").getCanonicalFile();
			HttpClient httpClient = new HttpClient("http://192.168.0.117:8080/inbound",5000,10000);
			params.put("Content-Type","text/xml;charset=EUC-KR");			
			httpClient.setRequestMethod("POST");					
			httpClient.setRequestProperties(params);
			
			String xmlString = XMLUtil.convertXMLFileToString(file);			
			httpClient.writeBody(xmlString.getBytes());
			
			int responseCode = httpClient.getResponseCode();
			System.out.println("RESPONSE_CODE : "+responseCode);
			
			//서버에서 전송받은 결과 출력
			System.out.println("------------------------- result -----------------------------");
			String responseBody = httpClient.getResponseBody();
			//System.out.println(responseBody);
			
			//XML 파싱하여 결과 출력
			//XMLUtil.parseXMLStringByDOM(responseBody);
			XMLUtil.parseXMLStringByXpath(responseBody);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}			
	}
}
