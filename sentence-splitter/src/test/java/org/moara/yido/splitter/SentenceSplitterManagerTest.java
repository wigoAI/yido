package org.moara.yido.splitter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.moara.yido.SentenceSplitterManager;

public class SentenceSplitterManagerTest {

    @Test
    public void getManagerInstanceTest() {
        SentenceSplitterManager ssm1 = SentenceSplitterManager.getInstance();
        SentenceSplitterManager ssm2 = SentenceSplitterManager.getInstance();
        assertEquals(ssm1, ssm2);
    }

}
