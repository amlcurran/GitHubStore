package uk.co.amlcurran.githubstore;

public class Time {
    private final long millis;

    private Time(long millis) {
        this.millis = millis;
    }

    public long millis() {
        return millis;
    }

    public static Time fromMillis(long millis) {
        return new Time(millis);
    }
}
