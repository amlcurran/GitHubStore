package uk.co.amlcurran.githubstore;

public interface Toaster {
    void noApksAvailable();

    @Deprecated
    /**
     * @deprecated this should be covered as a feature
     */
    void multipleApksAvailable();

    void toast(String toast);

    void incorrectSearchStructure();
}
