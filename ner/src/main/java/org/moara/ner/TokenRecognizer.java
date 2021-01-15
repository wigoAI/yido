package org.moara.ner;

import org.moara.splitter.utils.Area;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenRecognizer extends PersonNamedEntityRecognizer {


    /**
     * 토큰 인식기 생성자
     *
     * @param targetWord     개체를 가리키는 단어
     * @param exceptionWords 인식된 개체명 중 예외 개체명
     */
    public TokenRecognizer(String[] targetWord, String[] exceptionWords, String entityType, Area entityLength) {
        super(targetWord, exceptionWords, new String[]{"·", "?", "/"}, entityType, entityLength);
    }

    @Override
    protected String textPreprocessing(String text) {
        return text;
    }

    @Override
    protected Set<NamedEntity> recognizeEntities(String text) {
        Set<NamedEntity> personTokens = new HashSet<>();

        for (String targetWord : targetWords) {

            String tokenBoundary = "[가-힣 " + multipleSymbolRegx + "]*";
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
                    NamedEntityImpl token = new NamedEntityImpl(text.substring(matcher.start(), matcher.end()), "TOKEN", matcher.start(), matcher.end());
                    personTokens.add(token);
                }
            }
        }

        return personTokens;
    }
}