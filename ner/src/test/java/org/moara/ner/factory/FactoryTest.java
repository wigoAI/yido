package org.moara.ner.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.moara.ner.NamedEntity;
import org.moara.ner.NamedEntityRecognizer;
import org.moara.ner.person.PersonEntity;
import org.moara.ner.person.PersonNamedEntityRecognizerFactory;

import java.util.Arrays;
import java.util.List;

public class FactoryTest {
    @Test
    public void testCreateEmailEntityRecognizer() {
        NamedEntityRecognizer emailEntityRecognizer = PersonNamedEntityRecognizerFactory.EMAIL.create();

        String text = " 아침에 작업을 시키지를 않으셨으면 전날부터 이렇게 문자나 이런 걸 보면 부르질 말았어야 했는데…여도현 기자 (yeo.dohyun@jtbc.co.kr) [영상취재: 김영묵,박용길,조용희 / 영상편집: 박인서]";

        NamedEntity[] namedEntities = emailEntityRecognizer.recognize(text);

        for (NamedEntity namedEntity : namedEntities) {
            System.out.println(namedEntity.getText());
        }
        List<PersonEntity> answerList = Arrays.asList(new PersonEntity("yeo.dohyun@jtbc.co.kr", "EMAIL", 62, 83));

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        Assertions.assertTrue(answerList.containsAll(Arrays.asList(namedEntities.clone())));

    }

}
