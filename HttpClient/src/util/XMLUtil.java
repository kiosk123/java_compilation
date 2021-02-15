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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtil {
	
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
	
	//XML 형식의 문자를 읽어서 파싱하여 출력한다. DOM
	public static void parseXMLStringByDOM(String xml)throws IOException{

		try{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xml));
			Document document = documentBuilder.parse(inputSource); 
			
			//xml 출력
			printNodeByDOM(document);
		}catch (Exception e) {
			throw new IOException(e);
		}		
	}
	
	//XML 형식의 문자를 읽어서 파싱하여 출력한다. XPATH
	public static void parseXMLStringByXpath(String xml)throws IOException{

		try{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xml));
			Document document = documentBuilder.parse(inputSource); 
		
			//xml 출력
			printNodeByXpath(document);
		}catch (Exception e) {
			throw new IOException(e);
		}		
	}
	
	//일단 하드코딩으로 한 것...
	private static void printNodeByDOM(Document document){
		
		document.getDocumentElement().normalize();
		
		//root
		System.out.println("root tag name : "+document.getDocumentElement().getNodeName());
		
		System.out.println("-----------------------------------------");
		
		//header
		NodeList nodeList = document.getElementsByTagName("header");
		Element headerElement = (Element)nodeList.item(0);
		
		System.out.println("tag name : "+headerElement.getTagName());
		
		//globalid
		Node node = headerElement.getElementsByTagName("globalid").item(0);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//interfaceid
		node = headerElement.getElementsByTagName("interfaceid").item(0);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//res_code
		node = headerElement.getElementsByTagName("res_code").item(0);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//res_message
		node = headerElement.getElementsByTagName("res_message").item(0);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		System.out.println("-----------------------------------------");
		
		//data
		nodeList = document.getElementsByTagName("data");
		Element dataElement= (Element)nodeList.item(0);
		System.out.println("tag name : "+dataElement.getTagName()+", attr name value : "+dataElement.getAttribute("name"));
		
		//item
		node = dataElement.getElementsByTagName("item").item(0);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//color
		node = dataElement.getElementsByTagName("color").item(0);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//length
		node = dataElement.getElementsByTagName("length").item(0);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
	}
	
	//일단 하드코딩으로 한 것...
	private static void printNodeByXpath(Document document)throws Exception{
		document.getDocumentElement().normalize();
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		//root
		Node node = (Node)xPath.evaluate("/root", document.getDocumentElement(), XPathConstants.NODE);		
		System.out.println("root tag name : " + node.getNodeName());
		
		System.out.println("-----------------------------------------");
		
		//header
		node = (Node)xPath.evaluate("/root/header", document.getDocumentElement(), XPathConstants.NODE);
		System.out.println("tag name : "+node.getNodeName());
		
		//globalid
		node = (Node)xPath.evaluate("/root/header/globalid", document.getDocumentElement(), XPathConstants.NODE);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//interfaceid
		node = (Node)xPath.evaluate("/root/header/interfaceid", document.getDocumentElement(), XPathConstants.NODE);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//res_code
		node = (Node)xPath.evaluate("/root/header/res_code", document.getDocumentElement(), XPathConstants.NODE);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//res_message
		node = (Node)xPath.evaluate("/root/header/res_message", document.getDocumentElement(), XPathConstants.NODE);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		System.out.println("-----------------------------------------");
		
		//data
		node = (Node)xPath.evaluate("/root/data[@name]", document.getDocumentElement(), XPathConstants.NODE);
		String attr = (String)xPath.evaluate("/root/data[@name]/@name", document.getDocumentElement(), XPathConstants.STRING);
		System.out.println("tag name : "+node.getNodeName()+", attr name value : "+attr);
		
		//item
		node = (Node)xPath.evaluate("/root/data[@name]/item", document.getDocumentElement(), XPathConstants.NODE);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//color
		node = (Node)xPath.evaluate("/root/data[@name]/color", document.getDocumentElement(), XPathConstants.NODE);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
		
		//length
		node = (Node)xPath.evaluate("/root/data[@name]/length", document.getDocumentElement(), XPathConstants.NODE);
		System.out.println("tag name : "+node.getNodeName()+", value : "+node.getTextContent());
	}
}
