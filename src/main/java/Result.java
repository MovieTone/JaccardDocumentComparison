/**
 * Contains the result (file title and similarity)
 */

public class Result {

	private String title;
	private double similarity;

	public Result(String title, double similarity2) {
		setTitle(title);
		setSimilarity(similarity2);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

}
