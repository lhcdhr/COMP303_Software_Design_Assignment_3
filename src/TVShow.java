/**
 * Flyweight Design pattern
 * Constructor cannot be directly accessed by client in order to prevent duplicates.
 * Library objects with the same title will only be created once.
 *
 * @author Haochen Liu
 * @version 1.0
 */
import java.io.File;
import java.util.*;

/**
 * Represents a single TV show, with at least a title, language, and publishing studio. Each TVShow aggregates episodes.
 */
public class TVShow implements Watchable, Bingeable<Episode> {
	
	private final String TITLE;
	private final Language LANGUAGE;
	private final String STUDIO;
	private Map<String, String> aInfo;
	
	private List<Episode> aEpisodes = new ArrayList<>();
	private int aNextToWatch;
	
	/**
	 * Creates a TVShow with required metadata about the show.
	 * All created TVShows will be stored in the library.
	 * If a TVShow with the same title is already stored in the library, then it will be overwritten, no duplicates.
	 * This constructor cannot be directly accessed by the client.
	 *
	 * @param pTitle
	 *            official title of the TVShow
	 * @param pLanguage
	 *            language of the movie, in full text (e.g., "English")
	 * @param pStudio
	 *            studio which originally published the movie
	 * @pre pTitle!=null && pLanguage!=null && pStudio!=null
	 */
	private TVShow(String pTitle, Language pLanguage, String pStudio) {
		assert pTitle != null && pLanguage != null && pStudio != null;
		TITLE = pTitle;
		LANGUAGE = pLanguage;
		STUDIO = pStudio;
		aNextToWatch = 0;
		aInfo = new HashMap<>();
		Library.getInstance().addTVShow(this);
	}

	/**
	 * Flyweight.
	 * When Library already stores a TVShow with such title,
	 * then it will return this TVShow.
	 * Otherwise it will be created using the constructor.
	 * No duplicate Movies will be in this application.
	 *
	 * @param tTitle the title of of this TVShow
	 * @param tLanguage the language of this TVShow
	 * @param tStudio the studio that originally published this TVShow
	 * @pre tTitle!=null && tLanguage!=null && tStudio!=null
	 * @return the newly-created TVShow object, or the existing TVShow object with the same title
	 */
	public static TVShow generateTVShow(String tTitle, Language tLanguage, String tStudio){
		assert tTitle != null && tLanguage != null && tStudio != null;
		//return the new one if the title is used
		if(Library.hasTVShow(tTitle)){
			return Library.getTVShow(tTitle);
		}
		//create a new one otherwise
		return new TVShow(tTitle,tLanguage,tStudio);
	}
	
	@Override
	public void watch() {
		for (Episode episode : aEpisodes) {
			if (episode.isValid()) {
				episode.watch();
			}
		}
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
	public String getInfo(String pKey) {
		return aInfo.get(pKey);
	}
	
	@Override
	public boolean hasInfo(String pKey) {
		return aInfo.containsKey(pKey);
	}
	
	@Override
	public String setInfo(String pKey, String pValue) {
		if (pValue == null) {
			return aInfo.remove(pKey);
		}
		else {
			return aInfo.put(pKey, pValue);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the TV show contains at least one valid episode
	 */
	@Override
	public boolean isValid() {
		for (Episode episode : aEpisodes) {
			if (episode.isValid()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Creates a new Episode for this TV show, and adds it the end of the episode list.
	 * 
	 * @param pPath
	 *            the path of the file that contains the video of the episode
	 * @param pTitle
	 *            the title of the episode
	 * @pre pPath != null && pTitle != null;
	 */
	public void createAndAddEpisode(File pPath, String pTitle) {
		assert pPath != null && pTitle != null;
		int nb = aEpisodes.size();
		Episode episode = new Episode(pPath, this, pTitle, nb);
		aEpisodes.add(episode);
	}
	
	/**
	 * Returns the Episode of a given number. Episode numbers are 1-based: the first episode is 1, not 0.
	 *
	 * @param pNumber
	 *            the number whose Episode is to be returned
	 * @return the Episode of the given number
	 * @pre there is an episode for the given number
	 */
	public Episode getEpisode(int pNumber) {
		assert aEpisodes.size() >= pNumber;
		return aEpisodes.get(pNumber - 1);
	}
	
	@Override
	public int getTotalCount() {
		return aEpisodes.size();
	}
	
	@Override
	public int getRemainingCount() {
		return aEpisodes.size() - aNextToWatch;
	}
	
	@Override
	public Episode next() {
		assert getRemainingCount() > 0;
		Episode nextEpisode = aEpisodes.get(aNextToWatch);
		aNextToWatch++;
		if (aNextToWatch >= aEpisodes.size()) {
			aNextToWatch = 0;
		}
		return nextEpisode;
	}
	
	@Override
	public void reset() {
		aNextToWatch = 0;
	}
	
	@Override
	public Iterator<Episode> iterator() {
		return aEpisodes.iterator();
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
		TVShow episodes = (TVShow) o;
		return TITLE.equals(episodes.TITLE)
				&& LANGUAGE == episodes.LANGUAGE
				&& STUDIO.equals(episodes.STUDIO)
				&& aInfo.equals(episodes.aInfo)
				&& aEpisodes.equals(episodes.aEpisodes);
	}
	/**
	 * Override the hashCode method of Object.
	 * @return hashcode
	 */
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + TITLE.hashCode();
		hash = 31*hash + LANGUAGE.hashCode();
		hash = 31*hash + STUDIO.hashCode();
		hash = 31*hash + aInfo.hashCode();
		hash = 31*hash + aNextToWatch;
		hash = 31*hash + aEpisodes.hashCode();
		return hash;
	}
}