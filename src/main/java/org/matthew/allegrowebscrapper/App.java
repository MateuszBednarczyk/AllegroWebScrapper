package org.matthew.allegrowebscrapper;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        Scrapper scrapper = new Scrapper();
        scrapper.scrapDocument(1);
        scrapper.process(1);
        scrapper.getArticles().forEach(System.out::println);
    }

}
