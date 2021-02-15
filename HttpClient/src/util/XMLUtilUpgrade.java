package util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

// TODO 엘레강스하게 값을 뽑아내는 걸 생각해보자
public class XMLUtilUpgrade {
		
		// xml파일을 읽어들인다음 문자열로 바꾼다
		public static String convertXMLFileToString(File file)throws IOException{
			
			String xmlString = null;
			try{
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				Document doc = documentBuilder.parse(file);
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(doc), new StreamResult(writer));
				xmlString = writer.getBuffer().toString();
			}catch (Exception e) {
				throw new IOException(e);
			}		
			return xmlString;		
		}
		
		// XML 형식의 문자를 읽어서 파싱하여 출력한다. DOM
		public static void parseXMLStringByDOM(String xml)throws IOException{

			try{
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				InputSource inputSource = new InputSource();
				inputSource.setCharacterStream(new StringReader(xml));
				Document document = documentBuilder.parse(inputSource); 
				
				//xml 출력
			
			}catch (Exception e) {
				throw new IOException(e);
			}		
		}
		

		// XML 형식의 문자를 읽어서 파싱하여 출력한다. XPATH
		public static void parseXMLStringByXpath(String xml)throws IOException{

			try{
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				InputSource inputSource = new InputSource();
				inputSource.setCharacterStream(new StringReader(xml));
				Document document = documentBuilder.parse(inputSource); 
			
				//xml 출력
				
			}catch (Exception e) {
				throw new IOException(e);
			}		
		}
}
