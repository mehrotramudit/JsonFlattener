import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import static util.Constants.*;

/**
 * Test suite for Json Flattener
 * @Author: Mudit Mehrotra
 */
public class JsonFlattenerTest {
  @Test
  public void testJsonFlattenerForNullJson() {
    String json = null;
    String actualFlattenedJson = null;
    try {
      actualFlattenedJson = new JsonFlattener().flatten(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      Assert.fail();
    }
    Assert.assertEquals(LEFT_CURLY_BRACKET + NEWLINE + RIGHT_CURLY_BRACKET, actualFlattenedJson);
  }

  @Test
  public void testJsonFlattenerForEmptyJson() {
    String json = StringUtils.EMPTY;
    String actualFlattenedJson = null;
    try {
      actualFlattenedJson = new JsonFlattener().flatten(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      Assert.fail();
    }
    Assert.assertEquals(LEFT_CURLY_BRACKET + NEWLINE + RIGHT_CURLY_BRACKET, actualFlattenedJson);
  }

  @Test
  public void testJsonFlattenerForSimpleJson() {
    String json = "{\"a\":1,\"b\":true,\"c\":false,\"d\":\"test\"}";
    String actualFlattenedJson = null;
    String expectedFlattenedJson = "{\n" +
        "\t\"a\":1,\n" +
        "\t\"b\":true,\n" +
        "\t\"c\":false,\n" +
        "\t\"d\":\"test\"\n" +
        "}";
    try {
      actualFlattenedJson = new JsonFlattener().flatten(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      Assert.fail();
    }
    Assert.assertEquals(expectedFlattenedJson, actualFlattenedJson);
  }

  @Test
  public void testJsonFlattenerForNestedJson() {
    String json = "{\"a\":1,\"b\":true,\"c\":false,\"d\":{\"e\":123,\"f\":\"MongoDB\"},\"e\":\"test\"}";
    String actualFlattenedJson = null;
    String expectedFlattenedJson = "{\n" +
        "\t\"a\":1,\n" +
        "\t\"b\":true,\n" +
        "\t\"c\":false,\n" +
        "\t\"d.e\":123,\n" +
        "\t\"d.f\":\"MongoDB\",\n" +
        "\t\"e\":\"test\"\n" +
        "}";
    try {
      actualFlattenedJson = new JsonFlattener().flatten(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      Assert.fail();
    }
    Assert.assertEquals(expectedFlattenedJson, actualFlattenedJson);
  }

  @Test
  public void testJsonFlattenerForDeeplyNestedJson() {
    String json = "{\"a\":{\"b\":{\"c1\":{\"d\":{\"e1\":\"test\",\"e2\":true,\"e3\":123,\"e4\":1.0E+2}},\"c2\":{\"f1\":-10,\"f2\":0.5},\"c3\":{\"g1\":\"apple\"}}},\"h\":{\"h1\":\"abc\",\"h2\":null},\"i\":{\"i1\":\"123-456\"},\"j\":{\"j1\":0.0,\"j2\":\"red\",\"j3\":\"\"}}";
    String actualFlattenedJson = null;
    String expectedFlattenedJson = "{\n" +
        "\t\"a.b.c1.d.e1\":\"test\",\n" +
        "\t\"a.b.c1.d.e2\":true,\n" +
        "\t\"a.b.c1.d.e3\":123,\n" +
        "\t\"a.b.c1.d.e4\":100.0,\n" +
        "\t\"a.b.c2.f1\":-10,\n" +
        "\t\"a.b.c2.f2\":0.5,\n" +
        "\t\"a.b.c3.g1\":\"apple\",\n" +
        "\t\"h.h1\":\"abc\",\n" +
        "\t\"h.h2\":null,\n" +
        "\t\"i.i1\":\"123-456\",\n" +
        "\t\"j.j1\":0.0,\n" +
        "\t\"j.j2\":\"red\",\n" +
        "\t\"j.j3\":\"\"\n" +
        "}";
    try {
      actualFlattenedJson = new JsonFlattener().flatten(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      Assert.fail();
    }
    Assert.assertEquals(expectedFlattenedJson, actualFlattenedJson);
  }

  @Test
  public void testJsonFlattenerForJsonWithArray() {
    String json = "{\"a\":123,\"b\":\"test\",\"c\":{\"d\":[{\"e\":true,\"f\":\"red\"},{\"g\":true,\"h\":\"red\"}],\"i\":\"black\"}}";
    String actualFlattenedJson = null;
    String expectedFlattenedJson = "{\n" +
        "\t\"a\":123,\n" +
        "\t\"b\":\"test\",\n" +
        "\t\"c.i\":\"black\"\n" +
        "}";
    try {
      actualFlattenedJson = new JsonFlattener().flatten(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      Assert.fail();
    }
    Assert.assertEquals(expectedFlattenedJson, actualFlattenedJson);
  }

  @Test(expected = JsonProcessingException.class)
  public void testJsonFlattenerForInvalidJson() throws JsonProcessingException {
    String json = "{\"a\":123,\"b\":\"test\",\"c\":}";
    new JsonFlattener().flatten(json);
  }
}
