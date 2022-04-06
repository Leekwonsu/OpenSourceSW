package ForClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class searcher {
	public static String idfile="C://test/collection.xml";
	public static String[] title;
	public static void Searcher(String filename,String query) throws IOException, ClassNotFoundException {
		
	
		org.w3c.dom.Document doc = null;
		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		File file = new File(idfile);
		doc=docBuilder.parse(file);
		org.w3c.dom.Element root=doc.getDocumentElement(); 
		NodeList n=root.getChildNodes(); 
		title=new String[n.getLength()];
		
		for (int i=0;i<n.getLength();i++) {
			org.w3c.dom.Element docid=(org.w3c.dom.Element) n.item(i);
			NodeList nl=docid.getChildNodes();
			org.w3c.dom.Element Body=(org.w3c.dom.Element) nl.item(0);		
			Text t=(Text)Body.getFirstChild();
			title[i] = t.getData();
		}}catch (IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
		e.printStackTrace();
		}
		catch (SAXException e) {
		e.printStackTrace();
		}
		
		
		FileInputStream fileStream = new FileInputStream(filename);
		ObjectInputStream objectIntputStream = new ObjectInputStream(fileStream);
		
		Object object = objectIntputStream.readObject();
		objectIntputStream.close();
		
		HashMap hashmap = (HashMap)object;
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		Map<Integer, Float> out = new LinkedHashMap<>();
		
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(query, true);
		for( int j = 0; j < kl.size(); j++ ) {
			Keyword kwrd = kl.get(j);
			map.put(kwrd.getString(),kwrd.getCnt());
		}
		
		float point;
		for (int i=0;i<indexer.Count;i++) {
			point = 0;
			for (Entry<String, Integer> entrySet : map.entrySet()) {
				if(hashmap.containsKey(entrySet.getKey())) {
					String[] input=((String)hashmap.get(entrySet.getKey())).split(" ");
					for (int j=1;j<input.length;j+=2) {
						if (Integer.valueOf(input[j])==i) {
							point += (entrySet.getValue()*Float.valueOf(input[j+1]));
						}
					}
				}
			}
			out.put(i, point);
		}
		Map<Integer, Float> result = sortMapByValue(out);
		Iterator<Entry<Integer,Float>> it = result.entrySet().iterator();
		int c=0;
		while(it.hasNext()) {
			Entry<Integer, Float> entrySet = (Entry<Integer, Float>) it.next();
			if (entrySet.getValue()!=0)
			System.out.printf("%d : %s\n",c+1,title[entrySet.getKey()]);
			c++;
			if (c==3) break;
		}

	}
	
	public static LinkedHashMap<Integer, Float> sortMapByValue(Map<Integer, Float> map) {
	    List<Map.Entry<Integer, Float>> entries = new LinkedList<>(map.entrySet());
	    Collections.sort(entries, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

	    LinkedHashMap<Integer, Float> result = new LinkedHashMap<>();
	    for (Entry<Integer, Float> entry : entries) {
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}
}
	

