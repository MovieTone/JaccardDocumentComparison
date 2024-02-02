import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

/**
 * Processor does most of the work
 */

public class Processor {

	private String text;
	private String title;

	public Processor(String text, String title) {
		this.text = text;
		this.title = title;
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public List<Result> getResults() {
		Tokenizer tokenizer = new Tokenizer(text);
		// splits text into words
		Set<String> words = tokenizer.getWords();
		MinHashProcessor minHashProc = new MinHashProcessor(words);
		// turns words to int shingles
		List<Integer> shingles = minHashProc.getShingles();
		FileContainer file = new FileContainer(title, shingles);
		List<Result> results = new LinkedList<Result>();

		// initializes DB
		ObjectContainer db = null;
		try {
			db = Db4o.openFile("shingles.data");
			
			ObjectSet shinglesComparing = db.queryByExample(new FileContainer(null, null));
			while (shinglesComparing.hasNext()) {
				FileContainer fileComp = (FileContainer) shinglesComparing.next();
				double similarity = MinHashProcessor.computeSimilarity(shingles, fileComp.getShingles());
				Result result = new Result(fileComp.getTitle(), similarity);
				results.add(result);
			}

			// persists shingles of the uploaded file in DB
			db.store(file);
			db.commit();
		} finally {
			db.close();
		}
		return results;
	}

}
