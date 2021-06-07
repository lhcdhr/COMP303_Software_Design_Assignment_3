/**
 * Implements the equals method(at the bottom).
 *
 * @author Haochen Liu
 * @version 1.0
 */
import java.util.*;

/**
 * Represents a sequence of watchables to watch in FIFO order.
 */
public class WatchList implements Bingeable<Watchable>{
	
	private final List<Watchable> aList = new LinkedList<>();
	private String aName;
	private int aNext;
	
	/**
	 * Creates a new empty watchlist.
	 * 
	 * @param pName
	 *            the name of the list
	 * @pre pName!=null;
	 */
	public WatchList(String pName) {
		assert pName != null;
		aName = pName;
		aNext = 0;
	}
	
	public String getName() {
		return aName;
	}
	
	/**
	 * Changes the name of this watchlist.
	 * 
	 * @param pName
	 *            the new name
	 * @pre pName!=null;
	 */
	public void setName(String pName) {
		assert pName != null;
		aName = pName;
	}
	
	/**
	 * Adds a watchable at the end of this watchlist.
	 * 
	 * @param pWatchable
	 *            the watchable to add
	 * @pre pWatchable!=null;
	 */
	public void addWatchable(Watchable pWatchable) {
		assert pWatchable != null;
		aList.add(pWatchable);
	}
	
	/**
	 * Retrieves and removes the next watchable to watch from this watchlist. Watchables are retrieved in FIFO order.
	 */
	public Watchable removeNext() {
		return aList.remove(0);
	}
	
	/**
	 * @return the total number of valid watchable elements
	 */
	public int getValidCount() {
		int count = 0;
		for (Watchable item : aList) {
			if (item.isValid()) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public int getTotalCount() {
		return aList.size();
	}
	
	@Override
	public int getRemainingCount() {
		return aList.size() - aNext;
	}
	
	@Override
	public Watchable next() {
		assert getRemainingCount() > 0;
		Watchable next = aList.get(aNext);
		aNext++;
		if (aNext >= aList.size()) {
			aNext = 0;
		}
		return next;
	}
	
	@Override
	public void reset() {
		aNext = 0;
	}
	
	@Override
	public Iterator<Watchable> iterator() {
		return Collections.unmodifiableList(aList).iterator();
	}


	/**
	 * Determine whether two Watchlist objects are equal.
	 * Regardless of their names, if both of them contains
	 * the same Watchable objects in the same order,
	 * then they are equal.
	 * If they both contain no Watchable objects,
	 * then they are equal.
	 *
	 * @param o the object to compare with
	 *
	 * @return true if the watchable objects and their order are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		WatchList that = (WatchList) o;

		if(this.getTotalCount()!=that.getTotalCount()){
			return false;
		}
		//If two watchlists both contain 0 Watchable objects, then they are duplicates.
		if(this.getTotalCount()==0 && that.getTotalCount()==0){
			return true;
		}
		//Check order and other details.
		for(int i=0;i<this.getTotalCount();i++){
			Watchable w1 = this.aList.get(i);
			Watchable w2 = that.aList.get(i);

			if(!w1.equals(w2)){
				return false;
			}
		}
		return true;
	}

	/**
	 * Override the hashCode method of Object.
	 * @return hashcode
	 */
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + aList.hashCode();
		hash = 31*hash + aName.hashCode();
		hash = 31*hash + aNext;
		return hash;
	}
}
