package regular;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTest {

    @Test
    public void findBracketTest() {
        Pattern pattern = Pattern.compile("[\\(*\\{*\\[*][^\\)\\]\\}]*[\\)\\]\\}]");
        Matcher matcher = pattern.matcher("안녕하세요(ㅋㅋㅋ)저는조승현(일수도)입니다.");

        while(matcher.find()){
            System.out.println(matcher.start());
            System.out.println(matcher.end());
        }


    }
}
