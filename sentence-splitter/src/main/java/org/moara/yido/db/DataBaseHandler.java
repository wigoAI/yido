package org.moara.yido.db;

import java.util.stream.Stream;

public interface DataBaseHandler {
    void close();
    void connect();
    Stream<String> doQuery(String query);
}
