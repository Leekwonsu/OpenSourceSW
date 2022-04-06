package ForClass;

import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class makeCollection {
	
	public static int htmlCount = 0;
	
	public static void MakeCollection(String filename) {
		
		//String filename="C://test"; //html문서를 담고 있는 폴더
		File file = new File(filename);
		ArrayList<File> htmlFiles = new ArrayList<File>();
		if (file.isDirectory()) {
			File[] items = file.listFiles();
			for (File item : items) {
				if (item.getName().endsWith(".html")) {
					htmlFiles.add(item.getAbsoluteFile());
					htmlCount++;
					}
				}
			}
		
		//SearchHtml 함수로 폴더 내 html문서들을 htmlFiles에 저장
		Document doc = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc2 = docBuilder.newDocument();
			org.w3c.dom.Element rootElement = doc2.createElement("docs");
			doc2.appendChild(rootElement);
			//출력을 위한 Document doc2를 생성하고 docs 태그 부착
			
			for (int i=0;i<htmlCount;i++) {
			doc = Jsoup.parse(htmlFiles.get(i),"UTF-8");
			Elements title=doc.select("title");
			Elements body=doc.select("p");
			org.w3c.dom.Element subElement1 = doc2.createElement("doc");
			rootElement.appendChild(subElement1);
			subElement1.setAttribute("id", Integer.toString(i));
			org.w3c.dom.Element subElement2 = doc2.createElement("title");
			subElement2.appendChild(doc2.createTextNode(title.text()));
			subElement1.appendChild(subElement2);
			org.w3c.dom.Element subElement3 = doc2.createElement("body");
			subElement1.appendChild(subElement3);
			for (Element b : body)
			subElement3.appendChild(doc2.createTextNode(b.text()));
			}
			//탐색된 html문서의 개수만큼 doc,title,body를 생성
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			DOMSource source = new DOMSource(doc2);
			StreamResult result = new StreamResult(new FileOutputStream(new
			File("C:\\test\\collection.xml")));
			t.transform(source, result);
			} catch (IOException e) {
			e.printStackTrace();
			}
			catch (ParserConfigurationException e) {
			e.printStackTrace();
			}
			catch (TransformerConfigurationException e) {
			e.printStackTrace();
			}
			catch (TransformerException e) {
			e.printStackTrace();
			}
		}
}

	
