import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {

    private WebDriver driver;

    public Basket(WebDriver driver) {
        this.driver = driver;
    }

    By itemInBasketButton = By.xpath("//button[@class='button-fill product-actions__add-to-cart']");
    By plusBasketButton = By.xpath("//button[@class='counter__button counter__button_plus']");
    By minusBasketButton = By.xpath("//button[@class='counter__button counter__button_minus']");
    By basketButton = By.xpath("//span[text()='Перейти в корзину']");
    By catalogButton = By.xpath("//li/a[text()='Все украшения']");
    By newCatalogButton = By.xpath("//a[@href='/catalog/new']");
    By cartCountButton = By.xpath("//a[@aria-label='Корзина']");


    By plus2 = By.xpath("//input[@name='quantity']");
    By max = By.xpath("//div[@class='counter']");
    By cartCount = By.xpath("//div[@class='text-on-icon page-header__cart']/span");


    public Basket clickToItemButton() {
        String firstItem = this.findFirstItem();
//        System.out.println(firstItem);
        driver.findElement(By.xpath("//a[text()=" + "'" + firstItem + "']")).click();
        return this;
    }

    public Basket clickToAnotherItemButton() {
        String anotherItem = this.findAnotherItem();
        driver.findElement(By.xpath("//a[text()=" + "'" + anotherItem + "']")).click();
        return this;
    }

    public Basket clickToItemInBasketButton() {
        driver.findElement(itemInBasketButton).click();
        return this;
    }

    public Basket clickToPlusBasketButton() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", driver.findElement(plusBasketButton));
        return this;
    }

    public String getBasketNumber() {
        return driver.findElement(plus2).getAttribute("value");
    }

    public Basket clickToMinusBasketButton() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", driver.findElement(minusBasketButton));
        return this;
    }

    public Integer getDataMax() {
        return Integer.valueOf(driver.findElement(max).getAttribute("data-max"));
    }

    public Basket clickToBasketButton() {
        driver.findElement(basketButton).click();
        return this;
    }

    public Basket clickToCatalogButton() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", driver.findElement(catalogButton));
        return this;
    }

    public String getCartCount() {
        return driver.findElement(cartCount).getAttribute("textContent");
    }

    public Basket clickToCart() {
        driver.findElement(cartCountButton).click();
        return this;
    }

    public Basket clickToCartFromNew() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", driver.findElement(newCatalogButton));
        return this;
    }


    //SQL
    public static String findFirstItem() {
        DBWorker worker = new DBWorker();
        String name;
        List<String> list = new ArrayList<>();
        String query = "SELECT name from item_sku " +
                "JOIN storage_stock ON storage_stock.sku_id = item_sku.id " +
                "where balance - reserve >0" +
                " group by item_sku.id";
        try {
            Statement statement = worker.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                name = resultSet.getString("name");
                list.add(name);
//                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        System.out.println(list.get(1));
//        ////worker.getSession().disconnect();
        return list.get(1);
    }

    public static String findAnotherItem() {
        DBWorker worker = new DBWorker();
        String name;
        List<String> list = new ArrayList<>();
        String query = "SELECT name from item_sku " +
                "JOIN storage_stock ON storage_stock.sku_id = item_sku.id " +
                "where balance - reserve >0";
        try {
            Statement statement = worker.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                name = resultSet.getString("name");
                list.add(name);
//                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        System.out.println(list);
        ////worker.getSession().disconnect();
        return list.get(2);
    }

    public Integer getBalance() {
        DBWorker worker = new DBWorker();
        String name;
        Map<String, Integer> hashMap = new HashMap<>();
        String query = "SELECT item_sku.name, balance, reserve, sum(balance) - sum(reserve) as sum  from storage_stock " +
                "JOIN item_sku ON storage_stock.sku_id = item_sku.id " +
                "where balance - reserve >0" +
                " group by item_sku.id";
        try {
            Statement statement = worker.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                name = resultSet.getString("name");
                int summa = resultSet.getInt("sum");
                hashMap.put(name, summa);
//                System.out.println(name);
//                System.out.println(itog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String firstItem = this.findFirstItem();
        Integer i = hashMap.get(firstItem);
//        System.out.println(hashMap);
        ////worker.getSession().disconnect();
        return i;
    }

    //Тесты запросов к базе SQL
    public static void main(String[] args) {
//        DBWorker worker = new DBWorker();
//        String name;
//        List<String> list = new ArrayList<>();
//        String query = "SELECT name from item_sku " +
//                "JOIN storage_stock ON storage_stock.sku_id = item_sku.id " +
//                "where balance - reserve >0" +
//                " group by item_sku.id";
//        try {
//            Statement statement = worker.getCon().createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                name = resultSet.getString("name");
//                list.add(name);
////                System.out.println(name);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(list);
//        ////worker.getSession().disconnect();


//        DBWorker worker = new DBWorker();
//        String name;
//        Integer balance, reserve, itog;
//
//        List<String> list = new ArrayList<>();
//        Map<String, Integer> hashMap = new HashMap<String, Integer>();
//        String query = "SELECT name, balance, reserve  from item_sku " +
//                "JOIN storage_stock ON storage_stock.sku_id = item_sku.id " +
//                "where balance - reserve >0" +
//                " group by item_sku.id";
//        try {
//            Statement statement = worker.getCon().createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                name = resultSet.getString("name");
//                balance = resultSet.getInt("balance");
//                reserve = resultSet.getInt("reserve");
//
//                itog = balance- reserve;
////                list.add(name);
//
//                hashMap.put(name, itog);
////                System.out.println(name);
////                System.out.println(itog);
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        String firstItem = Basket.findFirstItem();
//        Integer i = hashMap.get(firstItem);
//        System.out.println(hashMap);
//        System.out.println(i);
        ////worker.getSession().disconnect();


        DBWorker worker = new DBWorker();
        String name;
        Integer balance, reserve, itog;
        Map<String, Integer> hashMap = new HashMap<>();
        String query = "SELECT item_sku.name, balance, reserve, sum(balance) - sum(reserve) as sum  from storage_stock "
                +
                "JOIN item_sku ON storage_stock.sku_id = item_sku.id " +
                "where balance - reserve >0" +
                " group by item_sku.id";
        try {
            Statement statement = worker.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                name = resultSet.getString("name");
                balance = resultSet.getInt("balance");
                reserve = resultSet.getInt("reserve");
                int summa = resultSet.getInt("sum");
                itog = balance - reserve;
                hashMap.put(name, summa);

//                System.out.println(summa);
//                System.out.println(itog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(hashMap);
        String firstItem = Basket.findFirstItem();
        Integer i = hashMap.get("Золотистые пусеты с кристаллами");
        System.out.println(i);
    }
}
