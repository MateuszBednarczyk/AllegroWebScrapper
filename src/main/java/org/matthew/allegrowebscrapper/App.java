package org.matthew.allegrowebscrapper;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        Scrapper scrapper = new Scrapper();
        scrapper.scrapDocument();
        scrapper.process();
        scrapper.getResults().forEach(System.out::println);
    }

}
