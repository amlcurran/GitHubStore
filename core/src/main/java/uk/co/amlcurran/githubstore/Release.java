package uk.co.amlcurran.githubstore;

public class Release {
    private String tag_name;
    private String body;

    public Release(String tagName, String body) {
        this.tag_name = tagName;
        this.body = body;
    }

    public String getTagName() {
        return tag_name;
    }

    public String getBody() {
        return body;
    }
}
