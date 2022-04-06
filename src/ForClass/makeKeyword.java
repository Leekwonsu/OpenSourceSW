package ForClass;


import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class makeKeyword {
	
	public static void MakeKeyword(String filename) {
		
	//String filename="C://test/collection.xml"; //INPUT ����
	org.w3c.dom.Document doc = null;
	try {
	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	File file = new File(filename);
	doc=docBuilder.parse(file);
	org.w3c.dom.Element root=doc.getDocumentElement(); //docs �±�
	NodeList n=root.getChildNodes(); //doc�� childnodes ����Ʈ(doc.id)
	for (int i=0;i<n.getLength();i++) {
	org.w3c.dom.Element docid=(org.w3c.dom.Element) n.item(i);
	//i��° doc.id ��������
	NodeList nl=docid.getChildNodes();
	//i��° doc.id �� chilenodes ����Ʈ (title , body)
	org.w3c.dom.Element Body=(org.w3c.dom.Element) nl.item(1);
	//i��° doc.id �� 2��° element �������� (body)
	Text body=(Text)Body.getFirstChild();
	String input = body.getData();
	String output = "";
	
	KeywordExtractor ke = new KeywordExtractor();
	KeywordList kl = ke.extractKeyword(input, true);
	for( int j = 0; j < kl.size(); j++ ) {
	Keyword kwrd = kl.get(j);
	output+=kwrd.getString() + ":" + kwrd.getCnt() + "#";
	}
	body.setData(output);
	}
	//body�� text�� �����ͼ� kkm ����
	TransformerFactory tf = TransformerFactory.newInstance();
	Transformer t = tf.newTransformer();
	t.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
	DOMSource source = new DOMSource(doc);
	StreamResult result = new StreamResult(new FileOutputStream(new
	File("C:\\test\\index.xml")));
	t.transform(source, result);
	//���
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
	catch (SAXException e) {
	e.printStackTrace();
	}
	}
}