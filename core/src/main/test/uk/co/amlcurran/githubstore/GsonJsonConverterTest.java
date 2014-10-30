package uk.co.amlcurran.githubstore;

import org.junit.Test;

import java.util.List;

import uk.co.amlcurran.githubstore.release.Release;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GsonJsonConverterTest {

    public static final String TAG_1 = "tag1";
    public static final String BODY_2 = "body2";

    @Test
    public void testJsonParsedCorrectly() {
        GsonJsonConverter converter = new GsonJsonConverter();
        List<Release> releases = converter.convertReleases("[ { 'tag_name' : '" + TAG_1 + "', 'body' : 'body1' }, { 'tag_name' : 'tag2', 'body' : '" + BODY_2 + "' } ]");
        assertThat(releases.get(0).getTagName(), is(TAG_1));
        assertThat(releases.get(1).getBody(), is(BODY_2));
    }


}