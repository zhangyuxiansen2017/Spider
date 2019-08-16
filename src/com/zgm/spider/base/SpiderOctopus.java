package com.zgm.spider.base;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author zhang
 * @date 2019年1月15日 下午5:12:09
 */
public class SpiderOctopus {

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 20; i++) {
            getTitle("https://cd.lianjia.com/ershoufang/pg" + i + "/");
        }
    }

    private static String getTitle(String url) throws IOException {
        String content = HttpGetConnect.connect(url, "utf-8");
        HtmlManage html = new HtmlManage();
        Document doc = html.manage(content);
        Element ele = doc.getElementsByClass("sellListContent").get(0);
        Elements eles = ele.getElementsByTag("li");
        for (int i = 0; i < eles.size(); i++) {
            Element item = eles.get(i);
            String title = item.getElementsByClass("title").get(0).getElementsByTag("a").text();
            //String link = item.getElementsByTag("a").first().attr("href");
            System.out.println(title);
        }
        return null;
    }
}
