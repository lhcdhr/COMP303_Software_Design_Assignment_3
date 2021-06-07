/**
 *
 * @author Haochen Liu
 * @version 1.0
 */
public class Driver {
    public static void main(String[] args) {
        Library l1 = Library.getInstance();
        Library l2 = Library.getInstance();
        System.out.println("Class Library is designed using Singleton Design Pattern.");
        System.out.println("Only 1 Library object is created -- check whether l1 is the same as l2:"+l1.equals(l2));
        System.out.println();

        System.out.println("Class Movie and Class TVShow are designed using Flyweight Design Pattern.");
        System.out.println("Create Watchable objects m1, m2, m3, m4, m5, t1, t2, t3.");
        System.out.println("Note that m2 and m3 are actually the same(same title).");
        System.out.println("Note that t1 and t2 are actually the same(same title).");
        Watchable m1 = Movie.generateMovie("C:\\Users\\1.mp4","movie1", Language.ENGLISH, "mcgill");
        Watchable m2 = Movie.generateMovie("C:\\Users\\2.mp4","movie2",Language.ENGLISH,"ubc");
        Watchable m3 = Movie.generateMovie("C:\\Users\\3.mp4","movie2",Language.ENGLISH,"ubc");
        Watchable m4 = Movie.generateMovie("C:\\Users\\4.mp4","movie4",Language.FRENCH,"mcgill");
        Watchable m5 = Movie.generateMovie("C:\\Users\\5.mp4","movie5",Language.ANCIENT_GREEK,"uoft");
        Watchable t1 = TVShow.generateTVShow("Wow Show",Language.ENGLISH,"concordia");
        Watchable t2 = TVShow.generateTVShow("Wow Show",Language.CHINESE,"ubc");
        Watchable t3 = TVShow.generateTVShow("Yay Show",Language.CHINESE,"ubc");
        System.out.println();
        System.out.println("Check whether m2 and m3 are the same:"+m2.equals(m3));
        System.out.println("Check whether m4 and m3 are the same:"+m4.equals(m3));
        System.out.println("Check whether t1 and t2 are the same:"+t1.equals(t2));
        System.out.println("Check whether t1 and t3 are the same:"+t1.equals(t3));

        WatchList wl1 = new WatchList("watchlist1");
        WatchList wl2 = new WatchList("watchlist2");
        WatchList wl3 = new WatchList("watchlist3");

        System.out.println("wl1 is a wacthlist contains m1, m2, m4, m5, t1");
        wl1.addWatchable(m1);
        wl1.addWatchable(m2);
        wl1.addWatchable(m4);
        wl1.addWatchable(m5);
        wl1.addWatchable(t1);

        System.out.println("wl2 is a wacthlist contains m1, m3, m4, m5, t2");
        wl2.addWatchable(m1);
        wl2.addWatchable(m3);
        wl2.addWatchable(m4);
        wl2.addWatchable(m5);
        wl2.addWatchable(t2);

        System.out.println("wl3 is a wacthlist contains m2, m1, t1, m5, t2");
        wl3.addWatchable(m2);
        wl3.addWatchable(m1);
        wl3.addWatchable(t1);
        wl3.addWatchable(m5);
        wl3.addWatchable(t2);

        System.out.println("By the requirement, wl1 and wl2 are equal, while wl3 is not equal to wl1 or wl2.");

        System.out.println("Check whether wl1 is equal to wl2: "+wl1.equals(wl2));
        System.out.println("Check whether wl1 is equal to wl3: "+wl1.equals(wl3));
        System.out.println("Check whether wl2 is equal to wl3: "+wl2.equals(wl3));

    }
}
