package ForClass;

import java.io.IOException;

public class kuir {
	public static void main(String[]args) throws ClassNotFoundException, IOException {
	
		makeCollection.MakeCollection("C://test");
		makeKeyword.MakeKeyword("C://test/collection.xml");
		indexer.Indexer("C://test/index.xml");
		searcher.Searcher("C://test/index.post", "�ʹ信�� ����, ��, ȸ�� ����.");
	}
}
