package org.moara.ner.person;

import org.moara.ner.NamedEntity;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonTokenRecognizer extends PersonNamedEntityRecognizer {


    /**
     * 사람 개체 토큰 인식기 생성자
     *
     * @param targetWord     객체를 가리키는 단어 (e.g: 직업 -> 기자, 리포터, 앵커) 해당 단어를 기준으로 사람 이름을 인식한다.
     * @param exceptionWords 인식된 개체명 중 예외 개체명
     */
    public PersonTokenRecognizer(String[] targetWord, String[] exceptionWords) {
        super(targetWord, exceptionWords, new String[]{"·", "?", "/"}, "TOKEN");
    }

    @Override
    protected String textPreprocessing(String text) {
        return text;
    }

    @Override
    protected Set<NamedEntity> recognizeEntities(String text) {
        Set<NamedEntity> personTokens = new HashSet<>();

        for (String targetWord : targetWords) {

            String tokenBoundary = "[^가-힣 " + multipleSymbolRegx + "]*";
            String tokenRegx = tokenBoundary + targetWord + tokenBoundary;
            Pattern pattern = Pattern.compile(tokenRegx);
            Matcher matcher = pattern.matcher(text);

            matcherFindLoop:
            while (matcher.find()) {
                for (String exceptionWord : exceptionWords) {
                    if (matcher.group().contains(exceptionWord)) {
                        continue matcherFindLoop;
                    }
                }


                if (matcher.group().length() > targetWord.length()) {
                    PersonEntity personToken = new PersonEntity(matcher.group(), "TOKEN", matcher.start(), matcher.end());
                    personTokens.add(personToken);
                }
            }
        }

        return personTokens;
    }
}