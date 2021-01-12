package org.moara.ner.person;

import org.moara.ner.NamedEntityRecognizer;
import org.moara.ner.NamedEntityRecognizerManager;
import org.moara.ner.exception.RecognizerNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class PersonNamedEntityRecognizerManager implements NamedEntityRecognizerManager {

    Map<String, NamedEntityRecognizer> namedEntityRecognizerMap = new HashMap<>();

    public static NamedEntityRecognizerManager getInstance() {
        return Singleton.instance;
    }

    private static class Singleton {
        private static final NamedEntityRecognizerManager instance = new PersonNamedEntityRecognizerManager();
    }


    // thread lock
    private final Object createLock = new Object();

    @Override
    public NamedEntityRecognizer getNamedEntityRecognizer(String id) {
        NamedEntityRecognizer namedEntityRecognizer = namedEntityRecognizerMap.get(id);

        if (namedEntityRecognizer == null) {
            synchronized (createLock) {
                namedEntityRecognizer = namedEntityRecognizerMap.get(id);
                if (namedEntityRecognizer == null) {
                    createRecognizer(id);
                    namedEntityRecognizer = namedEntityRecognizerMap.get(id);
                }
            }
        }

        return namedEntityRecognizer;
    }


    private void createRecognizer(String id) {
        if (id.equals("reporter")) {
            String[] exceptionWords = {"엄마", "취재", "인턴", "촬영", "전문", "선임", "객원", "신문", "일보", "경제"};
            namedEntityRecognizerMap.put(id, new ReporterEntityRecognizer("기자", exceptionWords));
        } else {
            throw new RecognizerNotFoundException(id);
        }
    }
}
