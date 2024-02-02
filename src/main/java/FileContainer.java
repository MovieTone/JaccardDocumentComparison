import java.util.List;

/**
 * Contains a title and shingles that a file is split into
 */

public class FileContainer {

	private String title;
	private List<Integer> shingles;

	public FileContainer(String title, List<Integer> shingles) {
		this.title = title;
		this.shingles = shingles;
	}

	public List<Integer> getShingles() {
		return shingles;
	}

	public String getTitle() {
		return title;
	}

}
