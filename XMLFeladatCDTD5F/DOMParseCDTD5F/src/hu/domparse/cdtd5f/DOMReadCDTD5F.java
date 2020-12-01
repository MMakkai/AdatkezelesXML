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
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); //Referenci�t hozunk l�tre a documentBuilderFactoryra
			
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); // documentBuilderFactory-val l�trehozunk egy documentBuilder p�ld�nyt
			
			Document document = documentBuilder.parse(new File("../XMLCDTD5F.xml")); // documentBuilder beolvassa az XML f�jlt, �s elt�roljuk egy dokument objektumba
			
			document.getDocumentElement().normalize(); //normaliz�lja a strukt�r�t(pl : t�bb sorban sz�tt�rdelt stringek egy Nodeba ker�lnek)
			
			Element rootElement = document.getDocumentElement(); // Visszaadja a dokumentum gy�k�relem�t (videojatekaruhaz)
			
			System.out.println("Gy�k�r elem: " + rootElement.getNodeName()); // Kiiratja a gy�k�relem nev�t.
			
			NodeList childNodes = rootElement.getChildNodes(); // Lek�rdezi a gy�k�relem �sszes gyerekelem�t.
			
			for(int i=0; i<childNodes.getLength(); i++) { // V�gigiter�lunk a gy�k�relem �sszes gyerekelem�n.
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
								for(int j=0; j<childNodes.getLength(); j++) // �tiratja az j�t�kok idj�r�l a j�t�kok nev�re olyan helyeken, ahol csak az id van megadva.
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
												if(attr3.equals("nev") && attr1.equals(attr2)) { // Itt cser�l�dik ki az ID a j�t�k nev�re.
													System.out.println(" " + actualElement.getNodeName() + ": " + actualElement2.getTextContent());
												}
											}
										}
									}
								}
							}
							else { // Referencia n�lk�li ki�rat�sn�l kiirja az elemek �rt�k�t.
								System.out.println(" " + actualElement.getNodeName() + ": " + actualElement.getTextContent());	
							}
						}
						actualNode = actualNode.getNextSibling(); // Tov�bbl�p�nk a k�vetkez� elemre.
					}
					System.out.println(); 
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) { //Kiv�telkezel�s.
			e.printStackTrace();
		}
	}
}