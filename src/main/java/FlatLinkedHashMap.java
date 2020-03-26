import java.util.LinkedHashMap;
import java.util.Map;
import static util.Constants.*;

/**
 * This class extends the functionality of a LinkedHashMap to pretty print the k-v pairs in the map
 * @Author: Mudit Mehrotra
 */
public class FlatLinkedHashMap<K, V> extends LinkedHashMap<K,V> {
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    int elementCount = this.size();
    int counter = 0;
    builder.append(LEFT_CURLY_BRACKET + NEWLINE);
    for(Map.Entry entry : entrySet()) {
      builder.append(TAB + QUOTES + entry.getKey() + QUOTES);
      builder.append(COLON);
      if(entry.getValue() instanceof String)
        builder.append(QUOTES + entry.getValue() + QUOTES);
      else
        builder.append(entry.getValue());
      if(++counter < elementCount)
        builder.append(COMMA + NEWLINE);
    }
    if (elementCount > 0)
      builder.append(NEWLINE + RIGHT_CURLY_BRACKET);
    else
      builder.append(RIGHT_CURLY_BRACKET);
    return builder.toString();
  }
}
