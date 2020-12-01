package hu.domparse.cdtd5f;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DOMReadCDTD5F
{
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); //Referenciát hozunk létre a documentBuilderFactoryra
			
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); // documentBuilderFactory-val létrehozunk egy documentBuilder példányt
			
			Document document = documentBuilder.parse(new File("../XMLCDTD5F.xml")); // documentBuilder beolvassa az XML fájlt, és eltároljuk egy dokument objektumba
			
			document.getDocumentElement().normalize(); //normalizálja a struktúrát(pl : több sorban széttördelt stringek egy Nodeba kerülnek)
			
			Element rootElement = document.getDocumentElement(); // Visszaadja a dokumentum gyökérelemét (videojatekaruhaz)
			
			System.out.println("Gyökér elem: " + rootElement.getNodeName()); // Kiiratja a gyökérelem nevét.
			
			NodeList childNodes = rootElement.getChildNodes(); // Lekérdezi a gyökérelem összes gyerekelemét.
			
			for(int i=0; i<childNodes.getLength(); i++) { // Végigiterálunk a gyökérelem összes gyerekelemén.
				Node node = childNodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)node;
					String id = element.getAttribute("id");
					System.out.println("id: " + id);
					
					Node actualNode = element.getFirstChild();
					while (actualNode != null) {
						if (actualNode.getNodeType() == Node.ELEMENT_NODE) {
							Element actualElement = (Element)actualNode;
							if(actualElement.getTextContent() == "") {
								for(int j=0; j<childNodes.getLength(); j++) // Átiratja az játékok idjéröl a játékok nevére olyan helyeken, ahol csak az id van megadva.
								{
									Node node2 = childNodes.item(j);
									if (node2.getNodeType() == Node.ELEMENT_NODE) {
										Element element2 = (Element)node2;
										NodeList childNodes2 = element2.getChildNodes();
										for(int k=0; k<childNodes2.getLength(); k++) {
											if(childNodes2.item(k).getNodeType() == Node.ELEMENT_NODE) {
												Element actualElement2 = (Element)childNodes2.item(k);
												String attr1 = element2.getAttributes().item(0).getNodeValue();
												String attr2 = actualElement.getAttributes().item(0).getNodeValue();
												String attr3 = actualElement2.getNodeName();
												if(attr3.equals("nev") && attr1.equals(attr2)) { // Itt cserélõdik ki az ID a játék nevére.
													System.out.println(" " + actualElement.getNodeName() + ": " + actualElement2.getTextContent());
												}
											}
										}
									}
								}
							}
							else { // Referencia nélküli kiíratásnál kiirja az elemek értékét.
								System.out.println(" " + actualElement.getNodeName() + ": " + actualElement.getTextContent());	
							}
						}
						actualNode = actualNode.getNextSibling(); // Továbblépünk a következõ elemre.
					}
					System.out.println(); 
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) { //Kivételkezelés.
			e.printStackTrace();
		}
	}
}