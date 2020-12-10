package hu.domparse.cdtd5f;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DOMModifyCDTD5F {

	public static void main(String argv[]) {

	       try {
	    	   //Elérési útvonala az XML filenak
	        String filepath = "XMLCDTD5F.xml";
	        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	        Document doc = docBuilder.parse(filepath);

	        

	        // Megszerzi a videojatek elemet a neve alapján
	        Node videojatek = doc.getElementsByTagName("videojatek").item(0);

	        // Hozzáad egy új adatot a videojatek gyerekhez
	        Element ertekeles = doc.createElement("ertekeles");
	        ertekeles.appendChild(doc.createTextNode("5/5"));
	        videojatek.appendChild(ertekeles);

	        // Ciklusban végig megy a videojatek gyereken
	        NodeList list = videojatek.getChildNodes();

	        for (int i = 0; i < list.getLength(); i++) {
	            
	                   Node node = list.item(i);

	           // Megszerzi az ar elemet és frissiti új értékkel.
	           if ("ar".equals(node.getNodeName())) {
	            node.setTextContent("21000");
	           }
	        }

	        // Módosítás egy új xml fileban történik, amit XMLCDTD5F_edit.xml-nek hivnak.
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(new File("XMLCDTD5F_edit.xml"));
	        transformer.transform(source, result);

	        System.out.println("Done");

	       } catch (ParserConfigurationException pce) {
	        pce.printStackTrace();
	       } catch (TransformerException tfe) {
	        tfe.printStackTrace();
	       } catch (IOException ioe) {
	        ioe.printStackTrace();
	       } catch (SAXException sae) {
	        sae.printStackTrace();
	       }
	}
}
