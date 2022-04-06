package ForClass;

import java.io.IOException;

public class kuir {
	public static void main(String[]args) throws ClassNotFoundException, IOException {
	
		makeCollection.MakeCollection("C://test");
		makeKeyword.MakeKeyword("C://test/collection.xml");
		indexer.Indexer("C://test/index.xml");
		searcher.Searcher("C://test/index.post", "초밥에는 식초, 밥, 회가 들어간다.");
	}
}
