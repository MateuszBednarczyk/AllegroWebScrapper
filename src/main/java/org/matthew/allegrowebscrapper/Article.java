package org.matthew.allegrowebscrapper;

public class Article {
    String name;
    Double price;
    String link;
    String page;

    public Article(String name, Double price, String link, String page) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.page = page;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", link='" + link + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
