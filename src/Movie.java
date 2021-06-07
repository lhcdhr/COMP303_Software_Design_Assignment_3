/**
 * Flyweight Design pattern
 * Constructor cannot be directly accessed by client in order to prevent duplicates.
 * Movie objects with the same title will only be created once.
 *
 * @author Haochen Liu
 * @version 1.0
 */
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a single movie, with at least a title, language, and publishing studio. Each movie is identified by its
 * path on the file system.
 */
public class Movie implements Sequenceable<Movie> {
	
	private final File PATH;
	
	private final String TITLE;
	private final Language LANGUAGE;
	private final String STUDIO;
	
	private Movie prequel;
	private Movie sequel;
	
	private Map<String, String> aTags = new HashMap<>();

	/**
	 * Flyweight.
	 * Creates a movie from the file path. Callers must also provide required metadata about the movie.
	 * All created movie will be stored in the library.
	 * This constructor cannot be directly accessed by the client.
	 *
	 * @param pPath
	 *            location of the movie on the file system.
	 * @param pTitle
	 *            official title of the movie in its original language
	 * @param pLanguage
	 *            language of the movie
	 * @param pStudio
	 *            studio which originally published the movie
	 * @pre pPath!=null && pTitle!=null && pLanguage!=null && pStudio!=null
	 * @throws IllegalArgumentException
	 *             if the path doesn't point to a file (e.g., it denotes a directory)
	 */
	private Movie(File pPath, String pTitle, Language pLanguage, String pStudio) {
		assert pPath != null && pTitle != null && pLanguage != null && pStudio != null;
		if (pPath.exists() && !pPath.isFile()) {
			throw new IllegalArgumentException("The path should point to a file.");
		}
		this.PATH = pPath; // ok because File is immutable.
		this.TITLE = pTitle;
		this.LANGUAGE = pLanguage;
		this.STUDIO = pStudio;
		Library.getInstance().addMovie(this);
	}

	/**
	 * Flyweight.
	 * When Library already stores a Movie with such title,
	 * then it will return this Movie.
	 * Otherwise it will be created using the constructor.
	 * No duplicate Movies will be in this application.
	 *
	 * @param mPath the path of this movie file
	 * @param mTitle the title of of this movie
	 * @param mLanguage the language of this movie
	 * @param mStudio the studio that originally published this movie
	 * @pre mPath!=null && mTitle!=null && mLanguage!=null && mStudio!=null
	 * @return the newly-created Movie object, or the existing Movie object with the same title
	 */
	public static Movie generateMovie(String mPath, String mTitle,Language mLanguage, String mStudio){
		assert mPath != null && mTitle != null && mLanguage != null && mStudio != null;
		//return the exisitng one if the title is used
		if(Library.getInstance().hasMovie(mTitle)){
			return Library.getInstance().getMovie(mTitle);
		}
		//create a new one otherwise
		File temp = new File(mPath);
		return new Movie(temp, mTitle,mLanguage,mStudio);
	}
	
	@Override
	public void watch() {
		// Just a stub.
		// We don't expect you to implement a full media player!
		System.out.println("Now playing " + TITLE);
	}
	
	@Override
	public boolean isValid() {
		return PATH.isFile() && PATH.canRead();
	}
	
	@Override
	public String getTitle() {
		return TITLE;
	}
	
	@Override
	public Language getLanguage() {
		return LANGUAGE;
	}
	
	@Override
	public String getStudio() {
		return STUDIO;
	}
	
	@Override
	public String setInfo(String pKey, String pValue) {
		assert pKey != null && !pKey.isBlank();
		if (pValue == null) {
			return aTags.remove(pKey);
		}
		else {
			return aTags.put(pKey, pValue);
		}
	}
	
	@Override
	public boolean hasInfo(String pKey) {
		assert pKey != null && !pKey.isBlank();
		return aTags.containsKey(pKey);
	}
	
	@Override
	public String getInfo(String pKey) {
		assert hasInfo(pKey);
		return aTags.get(pKey);
	}
	
	@Override
	public boolean hasPrevious() {
		return prequel != null;
	}
	
	@Override
	public boolean hasNext() {
		return sequel != null;
	}
	
	@Override
	public Movie getPrevious() {
		return prequel;
	}
	
	@Override
	public Movie getNext() {
		return sequel;
	}
	
	/**
	 * Sets the previous Movie in the series, and updates the prequel and sequel information of all related movies
	 * involved.
	 *
	 * @param pMovie
	 *            the Movie object to set as previous
	 * @pre pMovie != null
	 */
	public void setPrevious(Movie pMovie) {
		assert pMovie != null;
		if (prequel != null) {
			prequel.sequel = null;
		}
		if (pMovie.sequel != null) {
			pMovie.sequel.prequel = null;
		}
		prequel = pMovie;
		pMovie.sequel = this;
	}

	/**
	 * The overridden equals method
	 * @param o the object to compare
	 * @return whether the two objects are equal
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Movie movie = (Movie) o;
		return PATH.equals(movie.PATH)
				&& TITLE.equals(movie.TITLE)
				&& LANGUAGE == movie.LANGUAGE
				&& STUDIO.equals(movie.STUDIO)
				&& Objects.equals(prequel, movie.prequel)
				&& Objects.equals(sequel, movie.sequel)
				&& aTags.equals(movie.aTags);
	}
	/**
	 * Override the hashCode method of Object.
	 * @return hashcode
	 */
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + PATH.hashCode();
		hash = 31*hash + TITLE.hashCode();
		hash = 31*hash + LANGUAGE.hashCode();
		hash = 31*hash + STUDIO.hashCode();
		hash = 31*hash + ( prequel == null ? 0: prequel.hashCode() );
		hash = 31*hash + ( sequel == null ? 0: sequel.hashCode() );
		hash = 31*hash + aTags.hashCode();
		return hash;
	}
}
