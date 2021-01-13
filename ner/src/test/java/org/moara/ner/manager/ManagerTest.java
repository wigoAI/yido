package org.moara.ner.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.moara.ner.exception.RecognizerNotFoundException;
import org.moara.ner.NamedEntityRecognizerManager;

public class ManagerTest {

    @Test
    public void testNotFoundException() {
        Assertions.assertThrows(RecognizerNotFoundException.class, () ->
            NamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("NOT_EXIST_RECOGNIZER"));
    }


}
