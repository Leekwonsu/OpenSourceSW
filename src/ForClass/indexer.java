package ForClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class indexer {
	public static int Count;
	public static void Indexer(String filename) {
		org.w3c.dom.Document doc = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			File file = new File(filename);
			doc=docBuilder.parse(file);
			org.w3c.dom.Element root=doc.getDocumentElement(); //docs 태그
			NodeList n=root.getChildNodes(); //doc의 childnodes 리스트(doc.id)
			Count=n.getLength();
			String[] Bodies=new String[Count];
			
			for (int i=0;i<Count;i++) {
				org.w3c.dom.Element docid=(org.w3c.dom.Element) n.item(i);
				String id=docid.getAttribute("id");
				NodeList nl=docid.getChildNodes();
				org.w3c.dom.Element Body=(org.w3c.dom.Element) nl.item(1);
				Text body=(Text)Body.getFirstChild();
				Bodies[i] = body.getData();
			}
			
			HashMap<String, Integer> map1 = new HashMap<String,Integer>();
			HashMap<String, String> map2 = new HashMap<String,String>();
			
			for (int i=0;i<Count;i++) {
				String[] array=Bodies[i].split("#");
				for (int j=0;j<array.length;j++) {
					String[] array2=array[j].split(":");
					if(map1.containsKey(array2[0]+":")) {
						map1.put(array2[0]+":", map1.get(array2[0]+":") + 1);							
					} else {
						map1.put(array2[0]+":", 1);
						map2.put(array2[0], "");
					}	
				}
			}
			for (int i=0;i<Count;i++) {
			
				String[] array3=Bodies[i].split("#");
				for (int j=0;j<array3.length;j++) {
					String[] array4=array3[j].split(":");
					if (map1.containsKey(array4[0]+":")) {
						double l=Math.round(Double.valueOf(array4[1])*Math.log(Count/map1.get(array4[0]+":"))*100)/100.0;
						map2.put(array4[0], map2.get(array4[0])+" "+Integer.toString(i)+" "+Double.toString(l));
					}
				}
			}
			
			FileOutputStream fileStream=new FileOutputStream("C://test/index.post");
			ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileStream);
			objectOutputStream.writeObject(map2);
			objectOutputStream.close();

	
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
	}
}
