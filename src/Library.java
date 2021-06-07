/**
 * Singleton Design pattern.
 * Constructor cannot be accessed by the client directly.
 * Only one Library object is going to be created.
 *
 * @author Haochen Liu
 * @version 1.0
 */
import java.util.*;

/**
 * Represents a movie library, with individual movie titles and watch lists.
 */
public class Library {

	private static HashMap<String,Movie> aMovies = new HashMap<String,Movie>();
	private static Set<WatchList> aWatchLists = new HashSet<>();
	private static Set<Episode> aEpisodes = new HashSet<>();
	private static HashMap<String,TVShow> aTVShows = new HashMap<String,TVShow>();

	private String name;
	private String EmailID;
	private static Library library = new Library();

	/**
	 * Constructor of Library.
	 * Generate the only Library object(singleton design pattern).
	 */
	private Library(){
		this.name = "unnamed";
	}

	/**
	 * Singleton.
	 *
	 * @return the only Library instance.
	 */
	public static Library getInstance(){
		if(library==null){
			library = new Library();
		}
		return library;
	}

	/**
	 * Set the name of the Library instance.
	 *
	 * @param nName the (new) name of the instance
	 */
	public void setName(String nName){
		library.name = nName;
	}

	/**
	 * Get the name of the Library instance.
	 *
	 * @return the current name of the Library instance
	 */
	public String getName(){
		return library.name;
	}

	/**
	 * Set the email ID of the Library instance.
	 *
	 * @param nEmailID the (new) email ID
	 */
	public void setEmailID(String nEmailID){
		library.EmailID = nEmailID;
	}

	/**
	 * Get the current email ID of the Library instance.
	 *
	 * @return the current email ID
	 */
	public String getEmailID(){
		return library.EmailID;
	}

	/**
	 * Check whether the Movie object with such title is already stored in Library.
	 *
	 * @param mTitle the title of Movie to check
	 * @return whether such Movie object exists in Library
	 */
	public boolean hasMovie(String mTitle){
		return library.aMovies.containsKey(mTitle);
	}

	/**
	 * Get the Movie object by its title.
	 *
	 * @param mTitle the title of Movie to get
	 * @return the Movie object in Library with such title
	 */
	public Movie getMovie(String mTitle){
		return library.aMovies.get(mTitle);
	}

	/**
	 * Check whether the TVShow object with such title is already stored in Library.
	 *
	 * @param tTitle the title of TVShow to check
	 * @return whether such TVShow object exists in Library
	 */
	public static boolean hasTVShow(String tTitle){
		return Library.aTVShows.containsKey(tTitle);
	}

	/**
	 * Get the TVShow object by its title.
	 *
	 * @param tTitle the title of TVShow to get
	 * @return the TVShow object in Library with such title
	 */
	public static TVShow getTVShow(String tTitle){
		return library.aTVShows.get(tTitle);
	}


	/**
	 * Adds a movie to the library. Duplicate movies aren't added twice.
	 * 
	 * @param pMovie
	 *            the movie to add
	 * @pre pMovie!=null
	 */
	public void addMovie(Movie pMovie) {
		assert pMovie != null;
		aMovies.put(pMovie.getTitle(),pMovie);
	}
	
	/**
	 * Adds a watchlist to the library. All movies from the list are also added as individual movies to the library.
	 * 
	 * @param pList
	 *            the watchlist to add
	 * @pre pList!=null;
	 */
	public void addWatchList(WatchList pList) {
		assert pList != null;
		aWatchLists.add(pList);
		for (Watchable movie : pList) {
			addMovie((Movie) movie);
		}
	}
	
	/**
	 * Adds a TVShow to the library. All Episodes from the list are also added as individual episodes to the library.
	 *
	 * @param pTVShow
	 *            the TVShow to add
	 * @pre pTVShow!=null;
	 */
	public void addTVShow(TVShow pTVShow) {
		assert pTVShow != null;
		aTVShows.put(pTVShow.getTitle(),pTVShow);
		for (Episode episode : pTVShow) {
			aEpisodes.add(episode);
		}
	}
	
	/**
	 * Method to generate a new watchlist based on some filtering mechanism
	 * 
	 * @param pName
	 *            the name of the watchlist to create
	 * @param pGenerationParameters
	 *            the generation parameters
	 * @pre pName!=null && pFilter!=null;
	 */
	public WatchList generateWatchList(String pName, WatchListGenerationInfo pGenerationParameters) {
		assert (pName != null) && (pGenerationParameters != null);
		List<Watchable> items = new ArrayList<>();
		for (TVShow show : aTVShows.values()) {
			if (pGenerationParameters.filter(show)) {
				for (Episode episode : show) {
					if (pGenerationParameters.filter(episode)) {
						items.add(episode);
					}
				}
			}
		}
		for (Movie movie : aMovies.values()) {
			if (pGenerationParameters.filter(movie)) {
				items.add(movie);
			}
		}
		Collections.sort(items, pGenerationParameters);
		WatchList watchlist = new WatchList(pName);
		for (Watchable item : items) {
			watchlist.addWatchable(item);
		}
		return watchlist;
	}
}
