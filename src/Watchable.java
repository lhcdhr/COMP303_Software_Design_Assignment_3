/**
 * Represents a video object that can be watched
 */
interface Watchable {
	
	/**
	 * Plays the video to the user
	 * @pre isValid()
	 */
	public void watch();

	/**
	 * Indicates whether the Watchable element is ready to be played.
	 * 
	 * @return true if the file path points to an existing location that can be read and is not a directory
	 */
	public boolean isValid();

	public String getTitle();

	public Language getLanguage();

	public String getStudio();

	public String getInfo(String pKey);

	public boolean hasInfo(String pKey);

	public String setInfo(String pKey, String pValue);
	
}