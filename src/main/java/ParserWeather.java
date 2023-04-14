import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class ParserWeather {

    private static final String url = "https://pogoda.spb.ru/";
    private static final String tableXPath = ".//table[@class='wt']";
    private static final String weatherColumnsXPath = "tr[class=wth]";
    private static final String weatherDataXPath = "tr[valign=top]";

    private static Document getPade() {
        Document page = null;
        try {
            page = Jsoup.parse(new URL(url), 3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (page == null) throw new IllegalArgumentException();
        return page;
    }


    public static void getAllHavingForecasts() {
        Document page = getPade();
        Elements table = page.selectXpath(tableXPath);
        Elements weatherColumns = table.select(weatherColumnsXPath);
        Elements weatherData = table.select(weatherDataXPath);
        boolean firstEntry = true;
        for (Element column : weatherColumns) {
            System.out.println(column.text() + "\n");
            if (firstEntry) {
                printData(3, weatherData);
                firstEntry = false;
            } else {
                printData(4, weatherData);
            }
            System.out.println("\n");
        }
    }

    private static void printData(int iterations, Elements weatherData) {
        for (int i = 0; i < iterations; i++) {
            System.out.println(weatherData.get(i).text());
        }
    }


    public static void main(String[] args) {
        getAllHavingForecasts();
    }
}
