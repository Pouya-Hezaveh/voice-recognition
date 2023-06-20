package com.github.hezavehir.models;

import java.io.File;

public class Test {
    private Test() {
        throw new IllegalStateException("Utility class");
    }

    public static void test() {
        File file = new File("./aud/Persian/numbers/1(yek).mp3");
    }
}
