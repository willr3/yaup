package perf.yaup.json;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class JsonTest {

    @Test
    public void quotesInValue(){
        Json json = new Json();
        json.set("key","\"quoted\" value");
        try {
            new JSONObject(json.toString());
        }catch(Exception e){
            Assert.fail("failed to parse with JSONObject");
        }
    }


    @Test
    public void equals(){
        Json a = new Json();
        a.add("key","foo");
        a.add("value","bar");
        a.add("child",new Json());
        a.getJson("child").add(new Json());
        a.getJson("child").add(new Json());

        a.getJson("child").getJson(0).add(new Json());
        a.getJson("child").getJson(0).getJson(0).add("key","a");
        a.getJson("child").getJson(0).getJson(0).add("value","Alpha");
        a.getJson("child").getJson(0).add(new Json());
        a.getJson("child").getJson(0).getJson(1).add("key","b");
        a.getJson("child").getJson(0).getJson(1).add("value","Bravo");
        a.getJson("child").getJson(1).add(new Json());
        a.getJson("child").getJson(1).getJson(0).add("key","y");
        a.getJson("child").getJson(1).getJson(0).add("value","Yankee");
        a.getJson("child").getJson(1).add(new Json());
        a.getJson("child").getJson(1).getJson(1).add("key","z");
        a.getJson("child").getJson(1).getJson(1).add("value","Zulu");


        Json b = new Json();
        b.add("child",new Json());
        b.getJson("child").add(new Json());
        b.getJson("child").add(new Json());
        b.add("value","bar");
        b.add("key","foo");

        b.getJson("child").getJson(0).add(new Json());
        b.getJson("child").getJson(0).getJson(0).add("value","Alpha");
        b.getJson("child").getJson(0).getJson(0).add("key","a");
        b.getJson("child").getJson(0).add(new Json());
        b.getJson("child").getJson(0).getJson(1).add("value","Bravo");
        b.getJson("child").getJson(0).getJson(1).add("key","b");


        b.getJson("child").getJson(1).add(new Json());
        b.getJson("child").getJson(1).getJson(0).add("value","Yankee");
        b.getJson("child").getJson(1).getJson(0).add("key","y");

        b.getJson("child").getJson(1).add(new Json());
        b.getJson("child").getJson(1).getJson(1).add("value","Zulu");
        b.getJson("child").getJson(1).getJson(1).add("key","z");


        Assert.assertTrue("a json should be equal to itself",a.equals(a));
        Assert.assertTrue("a should equal b",a.equals(b));
        Assert.assertTrue("b should equal a",b.equals(a));

    }

    @Test
    public void fromString(){
        Json expected = Json.fromString("[{\"comment\":\"comment1\"},{\"comment\":\"comment2\"},{\"key\":\"0Level1\"},{\"value\":\"hasValue\",\"key\":\"0Level1\",\"child\":[[{\"value\":\"normal\",\"key\":\"1\"},{\"value\":\"quoted[{]}\\\\\\\"Value\",\"key\":\"1\",\"child\":[[{\"value\":\"one\",\"key\":\"1.1\"},{\"value\":\"Alpha\",\"key\":\"1.1.a\"},{\"value\":\"Bravo\",\"key\":\"1.1.b\"}],[{\"value\":\"two\",\"key\":\"1.2\"},{\"value\":\"Yankee\",\"key\":\"1.2.y\"}],[{\"value\":\"Zulu\",\"key\":\"1.2.z\"}]]}]]},{\"comment\":\"inlineComment\",\"key\":\"0Level2\",\"child\":[[{\"key\":\"1\",\"child\":[[{\"key\":\"first\"},{\"key\":\"second\"},{\"key\":\"quoted\\\\\\\" :,[{]\"},{\"key\":\"other\"},[[{\"key\":\"subOne\"},{\"key\":\"subTwo\"}]],[[{\"value\":\"subValue\",\"key\":\"subKey\"}]],{\"key\":\"zed\"}]]},{\"value\":\"bar\",\"key\":\"2\"}]]}]\n");
        System.out.println(expected.toString(2));
    }

    @Test
    public void indent(){
        Json json = new Json();
        json.set("a",new Json());
        json.getJson("a").set("aa",new Json());
        json.getJson("a").getJson("aa").set("value","foo");
        json.set("b",new Json());
        json.getJson("b").add("foo");
        json.getJson("b").add("bar");


    }
}