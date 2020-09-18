package org.yido.regular;

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

    @Test

    public void urlCheck() {

        String regex = "^((https?:\\/\\/)|(www\\.))([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";

        Pattern p = Pattern.compile(regex);



        p.matcher("https://goodidea.tistory.com:8888/qr/aaa/ddd.html?abc=def&ddd=fgf#sharp").matches();

        System.out.println(p.matcher("http://dextto.tistory.com").matches());

        System.out.println(p.matcher("http://blog.daum.net/dexter").matches());

        System.out.println(p.matcher("http://www.daum.net:80/index.cfm").matches());

        System.out.println(p.matcher("http://xxx:password@www.daum.net").matches());

        System.out.println(p.matcher("http://localhost/index.php?ab=1&c=2").matches());

        System.out.println(p.matcher("http://localhost:8080").matches());

        System.out.println(p.matcher("http://dextto.tistory.com/admin/entry/post/?id=150&returnURL=/entry/JAVA-Regular-Expression-%EC%A0%95%EA%B7%9C-%ED%91%9C%ED%98%84%EC%8B%9D-%EC%98%88%EC%A0%9C").matches());
        System.out.println(p.matcher("ww.web.site").matches());
        System.out.println(p.matcher("www.web.site").matches());
    }
}
