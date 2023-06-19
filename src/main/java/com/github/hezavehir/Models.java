package com.github.hezavehir;

import java.io.File;

public class Models{

    static void test(){
        File file = new File("./aud/Persian/numbers/1(yek).mp3");
        System.out.println(file.exists());
    }
}