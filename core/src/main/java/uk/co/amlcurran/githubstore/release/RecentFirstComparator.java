package uk.co.amlcurran.githubstore.release;

public class RecentFirstComparator implements java.util.Comparator<Release> {
    @Override
    public int compare(Release o1, Release o2) {
        long longResult = o1.getDate().millis() - o2.getDate().millis();
        if (longResult > 0) {
            return -1;
        } else if (longResult < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
