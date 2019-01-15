package com.zgm.spider.base;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import net.sf.json.JSONObject;

/**
 * @author zhang
 * @date 2019年1月3日 上午9:42:16
 * 
 */
public class SpiderKugou {

	public static String filePath = "F:/music/";
	public static String mp3 = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=jQuery191027067069941080546_1546235744250&"
			+ "hash=HASH&album_id=0&_=TIME";

	public static String LINK = "https://www.kugou.com/yy/rank/home/PAGE-23784.html?from=rank";

	public static void main(String[] args) throws IOException {
		for (int i = 1; i <= 23; i++) {
			String url = LINK.replace("PAGE", i + "");
			
			getTitle(url);
		}
	}

	public static String getTitle(String url) throws IOException {
		String content = HttpGetConnect.connect(url, "utf-8");
		HtmlManage html = new HtmlManage();
		Document doc = html.manage(content);
		Element ele = doc.getElementsByClass("pc_temp_songlist").get(0);
		Elements eles = ele.getElementsByTag("li");
		for (int i = 0; i < eles.size(); i++) {
			Element item = eles.get(i);
			String title = item.attr("title").trim();
			String link = item.getElementsByTag("a").first().attr("href");

			download(link, title);
		}
		return null;
	}

	public static String download(String url, String name) throws IOException {
		String hash = "";
		String content = HttpGetConnect.connect(url, "utf-8");

		String regEx = "\"hash\":\"[0-9A-Z]+\"";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			hash = matcher.group();
			hash = hash.replace("\"hash\":\"", "");
			hash = hash.replace("\"", "");
		}

		String item = mp3.replace("HASH", hash);
		item = item.replace("TIME", System.currentTimeMillis() + "");

		String mp = HttpGetConnect.connect(item, "utf-8");

		mp = mp.substring(mp.indexOf("(") + 1, mp.length() - 3);

		JSONObject json = JSONObject.fromObject(mp);
		String playUrl = json.getJSONObject("data").getString("play_url");

		FileDownload.download(playUrl, filePath + name + ".mp3");
		return playUrl;
	}
}
