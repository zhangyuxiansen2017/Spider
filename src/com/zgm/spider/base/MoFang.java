package com.zgm.spider.base;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author Mr. Zhang
 * @description
 * @date 2019/8/15 17:31
 * @website https://www.zhangguimin.cn
 */
public class MoFang {

    public static void main(String[] args) throws IOException {
        getHtml("http://m.mofangge.com/");
    }


    private static void getHtml(String url) throws IOException {
        Elements li = doDocument(url, "indexul");
        for (int i = 0; i < li.size(); i++) {
            for (int j = 0; j < 100; j++) {

                String href = li.get(i).getElementsByTag("a").first().attr("href");
                System.out.println("课程："+li.get(i).getElementsByTag("a").first().text());
                if (j > 0) {
                    href = href + "All/" + (j + 1);
                }
                Elements li1 = doDocument(href, "plist");
                if (li1.size() == 0) {
                    break;
                }
                for (int l = 0; l < 100; l++) {
                    String href2 = li1.get(0).getElementsByTag("a").first().attr("href");
                    System.out.println("分类："+li1.get(0).getElementsByTag("a").first().text());
                    if (l > 0) {
                        href = href + (l + 1);
                    }
                    Elements li2 = doDocument(href2, "qtlist");
                    if (li2.size() == 0) {
                        break;
                    }
                    for (int k = 0; k < li2.size(); k++) {
                        String href3 = li2.get(k).getElementsByTag("a").first().attr("href");
                        String content = HttpGetConnect.connect(href3, "utf-8");
                        HtmlManage html = new HtmlManage();
                        Document doc = html.manage(content);
                        Elements detilinfo = doc.getElementsByClass("detilinfo");
                        for (int g = 0; g < detilinfo.size() - 1; g++) {
                            if (g == 0) {
                                System.out.println("题目："+detilinfo.get(0).html());
                            }
                            if (g == 1) {
                                System.out.println("答案："+detilinfo.get(1).html());
                            }
                            if (g == 3) {
                                System.out.println("说明："+detilinfo.get(3).html());
                            }
                        }
                    }
                }
            }
        }
    }

    private static Elements doDocument(String url, String cl) throws IOException {
        String content = HttpGetConnect.connect(url, "utf-8");
        HtmlManage html = new HtmlManage();
        Document doc = html.manage(content);
        Element cla = doc.getElementsByClass(cl).get(0);
        Elements li = cla.getElementsByTag("li");
        return li;
    }
    public class Topic{
        private String subject;
        private String category;
        private String topic;
        private String answer;
        private String description;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
