package org.matthew.allegrowebscrapper;

public class Article {
    String name;
    String price;

    public Article(String name, String price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
