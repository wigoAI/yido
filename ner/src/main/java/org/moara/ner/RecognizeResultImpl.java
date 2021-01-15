package org.moara.ner;

public class RecognizeResultImpl implements RecognizeResult {

    private final NamedEntity[] entities;

    public RecognizeResultImpl(NamedEntity[] entities) {
        this.entities = entities;
    }

    @Override
    public NamedEntity[] getEntities() {
        return entities;
    }

    @Override
    public int size() {
        return entities.length;
    }
}
