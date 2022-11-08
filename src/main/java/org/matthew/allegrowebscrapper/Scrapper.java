package org.matthew.allegrowebscrapper;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scrapper {
    private static final String URL = "https://allegro.pl/listing?string=grzejnik&p=";
    private final WebClient webClient = setupWebClient();
    private Document document;
    private final List<Article> articles = new ArrayList<>();

    public void scrapDocument(int actualPage) throws IOException {
        document = parseDocument(getHtmlPage(actualPage));
    }

    public void process(int actualPage) throws IOException {
        String[] unformattedPagesCounterInformation = document.getElementsByClass("_1h7wt mh36_8 mvrt_8 _6d89c_3i0GV _6d89c_XEsAE").text().split(" ");
        int pages = Integer.parseInt(unformattedPagesCounterInformation[0]);
        for (int i = actualPage; i <= pages; i++) {
            System.out.println(i);
            document.getElementsByClass("mp4t_0 m3h2_0 mryx_0 munh_0 mg9e_0 mvrt_0 mj7a_0 mh36_0 _1405b_2ueS-");
            scrapDocument(i);
            document.getElementsByTag("article").forEach(element -> {
                String title = element.getElementsByClass("_w7z6o _uj8z7 meqh_en mpof_z0 mqu1_16 m6ax_n4 _6a66d_LX75-  ").text();
                String price = processPrice(element.getElementsByClass("mpof_92 myre_zn").text());
                String link = element.getElementsByClass("_w7z6o _uj8z7 meqh_en mpof_z0 mqu1_16 m6ax_n4 _6a66d_LX75-  ").attr("href");
                String page = element.getElementsByClass("_14uqc _1r8rh mzmg_6m m3h2_8 _cc6ig _6d89c_3ISNX").attr("value");
                Article article = new Article(title, price, link, page);
                articles.add(article);
            });
        }

        System.out.println("Looked at " + pages + " pages" + " for " + articles.size() + " articles");
    }

    private static Document parseDocument(HtmlPage htmlPage) {
        return Jsoup.parse(htmlPage.asXml());
    }

    public String processPrice(String price) {
        StringBuilder priceHolder = new StringBuilder("");
        for (char c : price.toCharArray()) {
            if (Character.isDigit(c)) {
                priceHolder.append(c);
            } else if (c == ',') {
                priceHolder.append('.');
            } else {
                System.out.println("Found useless char: " + c);
            }
        }
        return priceHolder.toString();
    }

    private HtmlPage getHtmlPage(int actualPage) throws IOException {
        return webClient.getPage(URL + actualPage);
    }

    private WebClient setupWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);

        return webClient;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
