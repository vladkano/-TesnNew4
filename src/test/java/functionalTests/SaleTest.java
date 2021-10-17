package functionalTests;

import baseForTests.TestBase;
import filters.Filters;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import sections.Sale;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaleTest extends TestBase {

    @BeforeEach
    public void setUp() {
        mainSetUp();
        driver.get(getUrl + "catalog/sale/");
        sale = new Sale(driver);
        filters = new Filters(driver);
    }

    //Кол-во наименование в базе и на странице, выборочная проверка по наименованию
    @Test
    public void namesOfSale() {
        String countHeader = filters.getCountHeader();
        Integer numberOnly = Integer.valueOf(countHeader.replaceAll("[^0-9]", ""));
        //sql:
        List<String> sqlList = sale.getNames();
        int sqlSize = sqlList.size();
        //site:
        List<WebElement> elements = driver.findElements(numberOfItem);
        for (WebElement text : elements) {
            String s = text.getText();
            siteList.add(s);
        }
        //сравниваем размеры и содержание списков
        assertEquals(sqlSize, numberOnly);
        assertEquals(sqlList.subList(0, 47), siteList.subList(0, 47));
    }

    //Проверка по наименованию дизайнера. База и на сайте
    @Test
    public void designersOfSale() {
        List<String> sqlList = sale.getDesigners();
        List<WebElement> elements = driver.findElements(designerName);
        for (WebElement text : elements) {
            String s = text.getText();
            siteList.add(s);
        }
        //сравниваем содержание списков
        assertEquals(sqlList.subList(0, 47), siteList.subList(0, 47));
    }

    //Проверка по цене со скидкой. База и на сайте
    @Test
    public void finalPriceOfSale() {
        List<Integer> sqlList = sale.getFinalPrice();
        List<WebElement> elements = driver.findElements(price);
        for (WebElement text : elements) {
            String s = text.getText();
            String replace = s.replace(" ", "");
            String result = replace.replaceAll("[^A-Za-z0-9]", "");
            Integer price = parseInt(result);
            priceList.add(price);
        }
        //сравниваем содержание списков
        assertEquals(sqlList.subList(0, 47), priceList.subList(0, 47));
    }

    //Проверка по цене без скидки. База и на сайте
    @Test
    public void priceOfSale() {
        List<Integer> sqlList = sale.getOldPrice();
        //site:
        List<WebElement> elements = driver.findElements(By.xpath("//span[@class='price-block__price price-block__price_old']"));
        for (WebElement text : elements) {
            String s = text.getText();
            String replace = s.replace(" ", "");
            String result = replace.replaceAll("[^A-Za-z0-9]", "");
            Integer price = parseInt(result);
            priceList.add(price);
        }
        //сравниваем содержание списков
        assertEquals(sqlList.subList(0, 47), priceList.subList(0, 47));
    }

    //Проверка отображения размера скидки.
    @Test
    public void amountOfDiscount() {
        //sql:
        List<Integer> sqlList = sale.getSale();
        //site:
        List<WebElement> elements = driver.findElements(By.xpath("//span[@class='price-block__discount']"));
        for (WebElement text : elements) {
            String s = text.getText();
            String replace = s.replace(" ", "");
            String result = replace.replaceAll("[^A-Za-z0-9]", "");
            Integer sale = parseInt(result);
            priceList.add(sale);
        }
        //сравниваем содержание списков
        assertEquals(sqlList.subList(0, 47), priceList.subList(0, 47));
    }

    @AfterEach
    public void tearDownEach() {
        driver.quit();
    }
}
