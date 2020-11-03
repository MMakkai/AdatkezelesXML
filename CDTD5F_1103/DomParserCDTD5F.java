package domcdtd5f;

public class DomParserCDTD5F {
	
	try {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactor.newInstance();
		
		DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();
		
		Document doc = documentBuilder.parse(new File("student.xml"));
		
		doc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.GetElementByTagName("student");
		System.out.println("-----------------------------");
		
		for (int temp=0; temp < nList.getLength(); temp++) {
			Node nNode = childNodes.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println("Student roll no : " + eElement.getAttribute("rollno"));
				
				System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
				
				System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
				
				System.out.println("Nickname : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
				
				System.out.println("Age : " + eElement.getElementsByTagName("age").item(0).getTextContent());
			}
		}
			
	} catch (Exception e) {
		e.printStackTrace();
	}
}