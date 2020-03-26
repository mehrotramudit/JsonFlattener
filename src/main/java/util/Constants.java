package util;

/**
 * Collected constants of Java Flattener library
 * All members of this class are immutable.
 * @Author: Mudit Mehrotra
 */
public final class Constants {
  public static final String COLON = ":";
  public static final String LEFT_CURLY_BRACKET = "{";
  public static final String RIGHT_CURLY_BRACKET = "}";
  public static final String DOT = ".";
  public static final String TAB = "\t";
  public static final String NEWLINE = "\n";
  public static final String COMMA = ",";
  public static final String QUOTES = "\"";

  /**
   * The caller references the constants using Constants.DOT and so on.
   * Thus, the caller should be prevented from constructing objects of
   * this class, by declaring this private constructor.
   */
  private Constants() {
      //this prevents even the native class from calling this constructor as well
      throw new AssertionError();
  }
}
