import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Splits text into tokens (words)
 */

public class Tokenizer {

	private String text;

	public Tokenizer(String text) {
		this.text = text;
	}

	public Set<String> getWords() {
		Set<String> words = new TreeSet<String>(Arrays.asList(text.split("[^\\p{L}0-9]+")));
		return words;
	}

}
