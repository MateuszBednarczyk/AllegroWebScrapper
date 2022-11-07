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
    private static final String URL = "https://allegro.pl/listing?string=firanki&p=";
    private final WebClient webClient = setupWebClient();
    private Document document;
    private final List<Article> articles = new ArrayList<>();
    private int actualPage = 1;

    public void scrapDocument() throws IOException {
        document = parseDocument(getHtmlPage());
    }

    public void process() throws IOException {
        String[] unformattedPagesCounterInformation = document.getElementsByClass("_1h7wt mh36_8 mvrt_8 _6d89c_3i0GV _6d89c_XEsAE").text().split(" ");
        int pages = Integer.parseInt(unformattedPagesCounterInformation[0]);
        for(int i = 1; i <= pages; i++) {
            actualPage = i;
            scrapDocument();
            document.getElementsByTag("article").forEach(element -> {
                String title = element.getElementsByClass("_w7z6o _uj8z7 meqh_en mpof_z0 mqu1_16 m6ax_n4 _6a66d_LX75-  ").text();
                String price = element.getElementsByClass("mpof_92 myre_zn").text();
                Article article = new Article(title, price);
                articles.add(article);
            });
        }

        System.out.println("Looked at " + pages + " pages" + " for " + articles.size() + " articles");
    }

    public List<Article> getResults(){
        return articles;
    }

    private static Document parseDocument(HtmlPage htmlPage) {
        return Jsoup.parse(htmlPage.asXml());
    }

    private HtmlPage getHtmlPage() throws IOException {
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
}
