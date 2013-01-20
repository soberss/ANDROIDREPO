package parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpParser {
	public static String extractPatternFromString(String url, String pattern){
		 Pattern p = Pattern.compile(pattern);
	     Matcher m = p.matcher(url);
	     if (!m.find()) return null;	     
	     return m.toMatchResult().group(1);	
	}	
}
