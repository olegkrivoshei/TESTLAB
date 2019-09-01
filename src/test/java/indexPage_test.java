import helper.Helper;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class indexPage_test {
    protected String url = "http://prestashop-automation.qatestlab.com.ua/ru/";

    @Test
    public void checkTitleTest() throws IOException {
        System.out.println(Helper.currentDir);
        Helper.checkLog();

        System.setProperty("webdriver.chrome.driver", Helper.currentDir);
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        if (driver.getTitle() != null && driver.getTitle().contains("prestashop-automation")) {
            System.out.println("Web index page was opened successfully.");
            Helper.logger.info("Web page http://prestashop-automation.qatestlab.com.ua/ru/ was opened successfully.");

        } else {
            System.out.println("Web index page could not open.");
            Helper.logger.info("Web page http://prestashop-automation.qatestlab.com.ua/ru/ could not open.");
        }
        Helper.addToAllLogs();
        driver.quit();
        Helper.fh.close();

    }

    @Test
    public void checkUsdEurUahTest() throws IOException {
        Helper.checkLog();
        String page = "http://prestashop-automation.qatestlab.com.ua/ru/?SubmitCurrency=1&id_currency=1";
        String valuta = "₴";
        reviewCurrency(valuta, page);

        page = "http://prestashop-automation.qatestlab.com.ua/ru/?SubmitCurrency=1&id_currency=3";
        valuta = "$";
        reviewCurrency(valuta, page);
        page = "http://prestashop-automation.qatestlab.com.ua/ru/?SubmitCurrency=1&id_currency=2";
        valuta = "€";
        reviewCurrency(valuta, page);
        Helper.addToAllLogs();
    }

    public static void reviewCurrency(String Currency, String Page) {
        System.setProperty("webdriver.chrome.driver", Helper.currentDir);
        String Xpath = "//*[contains(@class,'expand-more _gray-darker hidden-sm-down')]";
        WebDriver driver = new ChromeDriver();
        driver.get(Page);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        String p = driver.findElement(By.xpath(Xpath)).getText().toString();
        if (p.contains(Currency)) {
            if (driver.findElement(By.xpath("//*[contains(@class,'price')]")).getText().toString().contains(Currency)) {
                System.out.println(Currency + " setup well");
                Helper.logger.info("Currency " + Currency + " setup well on page " + Page);
            } else {
                System.out.println(Currency + " setup  not good");
                Helper.logger.info("Currency " + Currency + "  setup not good on page " + Page);
            }
        } else {
            System.out.println("Currency " + Currency + " does not match page");
            Helper.logger.info("Currency " + Currency + "  setup not good on page " + Page);
        }
        driver.quit();
        Helper.fh.close();

    }

    @Test
    public void checkUsdPage() throws IOException {
        WebDriver driver = new ChromeDriver();
        Helper.checkLog();
        try {
            System.out.println(Helper.currentDir);
            System.setProperty("webdriver.chrome.driver", Helper.currentDir);
            driver.get(url);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            List<WebElement> listofItems = driver.findElements(By.xpath("//*[contains(@class,'currency-selector dropdown js-dropdown')]/ul[contains(@class,'dropdown-menu hidden-sm-down')]/li/a[contains(@class,'dropdown-item')]"));

            driver.findElement(By.xpath("//*[contains(@class,'expand-more _gray-darker hidden-sm-down')]")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.get(listofItems.get(2).getAttribute("href"));
            System.out.println("Page with USD was opened");
            Helper.logger.info("Page with USD was opened from index tab");
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Page with USD was not opened ");
            Helper.logger.info("Page with USD was not opened from index tab");
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        }
    }

    @Test
    public void searchingDress() throws IOException {
        WebDriver driver = new ChromeDriver();
        Helper.checkLog();
        String searchingWord = "dress";

        try {
            System.out.println(Helper.currentDir);
            System.setProperty("webdriver.chrome.driver", Helper.currentDir);
            driver.get(url);

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            WebElement element = driver.findElement(By.className("ui-autocomplete-input"));

            element.sendKeys(searchingWord);
            element.submit();
            System.out.println("\"" + searchingWord + "\" has loaded successfully");
            Helper.logger.info("\"" + searchingWord + "\" has loaded and found" + "on page " + url + " successfully");
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();

        } catch (Exception e) {
            System.out.println("Page with USD was not opened ");
            Helper.logger.info("Page with USD was not opened from index tab");
            System.out.println("\"" + searchingWord + "\" has not loaded");
            Helper.logger.info("\"" + searchingWord + "\" has not loaded" + "on page " + url);
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        }
    }

    @Test
    public void getCountOfCommodity() throws IOException {
        WebDriver driver = new ChromeDriver();
        Helper.checkLog();
        String searchingWord = "dress";

        try {
            System.out.println(Helper.currentDir);
            System.setProperty("webdriver.chrome.driver", Helper.currentDir);
            driver.get(url);

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            WebElement element = driver.findElement(By.className("ui-autocomplete-input"));
            element.sendKeys(searchingWord);
            element.submit();

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            Integer expectedCountOfCommodity = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'col-md-6 hidden-sm-down total-products')]/p")).getText().replaceAll("\\D+", ""));
            Integer realCountOfCommodity = driver.findElements(By.className("thumbnail-container")).size();
            if (expectedCountOfCommodity == realCountOfCommodity) {
                System.out.println("Count of Commodity is equal");
                Helper.logger.info("Count of Commodity is equal on page SEARCH");
            } else {
                System.out.println("Count of Commodity is not equal");
                Helper.logger.info("Count of Commodity is not equal on page SEARCH");
            }
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        } catch (Exception e) {
            System.out.println("Commodity was not found");
            Helper.logger.info("Commodity was not found on page SEARCH");
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        }
    }

    @Test
    public void getResultUSD() throws IOException {
        WebDriver driver = new ChromeDriver();
        Helper.checkLog();
        String searchingWord = "dress";

        try {
            System.out.println(Helper.currentDir);
            System.setProperty("webdriver.chrome.driver", Helper.currentDir);
            driver.get(url);

            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            List<WebElement> listofItems = driver.findElements(By.xpath("//*[contains(@class,'currency-selector dropdown js-dropdown')]/ul[contains(@class,'dropdown-menu hidden-sm-down')]/li/a[contains(@class,'dropdown-item')]"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            driver.findElement(By.xpath("//*[contains(@class,'expand-more _gray-darker hidden-sm-down')]")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.get(listofItems.get(2).getAttribute("href"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);


            WebElement element = driver.findElement(By.className("ui-autocomplete-input"));
            element.sendKeys(searchingWord);
            element.submit();

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            List<WebElement> listofCommodity = driver.findElements(By.className("price"));
            Integer counter = 0;
            for (int i = 0; i < listofCommodity.size(); i++) {
                if (listofCommodity.get(i).getText().contains("$")) {
                    counter++;
                } else {
                    System.out.println("Page don`t have any Commodity ");
                    Helper.logger.info("Page don`t have any Commodity with search \"" + searchingWord + "\"");
                }
                if (counter == listofCommodity.size()) {
                    System.out.println("Count of dollars results is equal");
                    Helper.logger.info("Count of dollars results is equal with search \"" + searchingWord + "\"");
                }
            }
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        } catch (Exception e) {
            System.out.println("smth. was wrong");
            Helper.logger.info("Count of dollars results is wrong");
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        }

    }

    @Test
    public void getLowPrice() throws IOException {
        WebDriver driver = new ChromeDriver();
        Helper.checkLog();
        String searchingWord = "dress";

        try {
            System.out.println(Helper.currentDir);
            System.setProperty("webdriver.chrome.driver", Helper.currentDir);
            driver.get(url);

            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            List<WebElement> listofItems = driver.findElements(By.xpath("//*[contains(@class,'currency-selector dropdown js-dropdown')]/ul[contains(@class,'dropdown-menu hidden-sm-down')]/li/a[contains(@class,'dropdown-item')]"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            driver.findElement(By.xpath("//*[contains(@class,'expand-more _gray-darker hidden-sm-down')]")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.get(listofItems.get(2).getAttribute("href"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);


            WebElement element = driver.findElement(By.className("ui-autocomplete-input"));
            element.sendKeys(searchingWord);
            element.submit();

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            driver.findElement(By.className("select-title")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.findElements(By.xpath("//*[contains(@class,'dropdown-menu')]/a")).get(4).click();
            System.out.println("sorted from high price to low");
            Helper.logger.info("sorted from high price to low");


            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        } catch (Exception e) {
            System.out.println("smth. was wrong");
            Helper.logger.info("sorted from high price to low was not successfully");
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        }

    }

    public boolean isElementPresent(String sxpath, WebElement sdriver) {
        try {
            sdriver.findElement(By.className(sxpath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void getTrueSorted() throws IOException {
        WebDriver driver = new ChromeDriver();
        Helper.checkLog();
        String searchingWord = "dress";

        try {
            System.out.println(Helper.currentDir);
            System.setProperty("webdriver.chrome.driver", Helper.currentDir);
            driver.get(url);

            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            List<WebElement> listofItems = driver.findElements(By.xpath("//*[contains(@class,'currency-selector dropdown js-dropdown')]/ul[contains(@class,'dropdown-menu hidden-sm-down')]/li/a[contains(@class,'dropdown-item')]"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            driver.findElement(By.xpath("//*[contains(@class,'expand-more _gray-darker hidden-sm-down')]")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.get(listofItems.get(2).getAttribute("href"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);


            WebElement element = driver.findElement(By.className("ui-autocomplete-input"));
            element.sendKeys(searchingWord);
            element.submit();

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            driver.findElement(By.className("select-title")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.findElements(By.xpath("//*[contains(@class,'dropdown-menu')]/a")).get(4).click();

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);


            driver.manage().window().setSize(new Dimension(500, 754));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

            List<WebElement> prices = driver.findElements(By.xpath("//article/div/div[contains(@class,'product-description')]/div[contains(@class,product-price-and-shipping)]/span[contains(@itemprop,'price')] "));

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

            List<WebElement> oldPrice = driver.findElements(By.xpath("//article/div/div[contains(@class,'product-description')]/div[contains(@class,product-price-and-shipping)]"));


            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            Integer max = 0;


            for (int i = 0; i < oldPrice.size(); i++) {


                if (isElementPresent("regular-price", oldPrice.get(i))) {
                    System.out.println(Double.parseDouble(oldPrice.get(i).findElement(By.className("regular-price")).getText().replaceAll("[^,0-9]+", "").replaceAll(",", ".")));
                    max++;
                } else {
                    System.out.println(Double.parseDouble(prices.get(i).getText().replaceAll("[^,0-9]+", "").replaceAll(",", ".")));
                    max++;
                }

            }

            if (oldPrice.size() == max) {
                System.out.println("Sorted was fine");
                Helper.logger.info("Sorted was fine");
            }

            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        } catch (Exception e) {
            System.out.println("Sorted was not fine");
            Helper.logger.info("Sorted was  not fine");
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        }

    }

    @Test
    public void getDiscountAndPrices() throws IOException {
        WebDriver driver = new ChromeDriver();
        Helper.checkLog();
        String searchingWord = "dress";

        try {
            System.out.println(Helper.currentDir);
            System.setProperty("webdriver.chrome.driver", Helper.currentDir);
            driver.get(url);

            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            List<WebElement> listofItems = driver.findElements(By.xpath("//*[contains(@class,'currency-selector dropdown js-dropdown')]/ul[contains(@class,'dropdown-menu hidden-sm-down')]/li/a[contains(@class,'dropdown-item')]"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            driver.findElement(By.xpath("//*[contains(@class,'expand-more _gray-darker hidden-sm-down')]")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.get(listofItems.get(2).getAttribute("href"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);


            WebElement element = driver.findElement(By.className("ui-autocomplete-input"));
            element.sendKeys(searchingWord);
            element.submit();

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            driver.findElement(By.className("select-title")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.findElements(By.xpath("//*[contains(@class,'dropdown-menu')]/a")).get(4).click();

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);


            driver.manage().window().setSize(new Dimension(500, 754));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);


            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);


            List<WebElement> fullBox = driver.findElements(By.className("thumbnail-container"));


            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);


            for (int i = 0; i < fullBox.size(); i++) {


                if (isElementPresent("regular-price", fullBox.get(i)) && isElementPresent("discount-percentage", fullBox.get(i)) && isElementPresent("price", fullBox.get(i))) {
                    Helper.logger.info("On page Search with word " + searchingWord + " was found discount = " + fullBox.get(i).findElement(By.className("discount-percentage")).getText() + " and 2 prices: " + fullBox.get(i).findElement(By.className("price")).getText() + " and " + fullBox.get(i).findElement(By.className("regular-price")).getText());
                    System.out.println("it works!");
                } else {
                    Helper.logger.info("On page Search with word " + searchingWord + " was found price");
                }
            }

            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        } catch (Exception e) {
            System.out.println("Something was wrong");
            Helper.logger.info("Sorted was  not fine");
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        }

    }
    @Test
    public void getDiscountTrue() throws IOException {
        WebDriver driver = new ChromeDriver();
        Helper.checkLog();
        String searchingWord = "dress";

        try {
            System.out.println(Helper.currentDir);
            System.setProperty("webdriver.chrome.driver", Helper.currentDir);
            driver.get(url);

            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            List<WebElement> listofItems = driver.findElements(By.xpath("//*[contains(@class,'currency-selector dropdown js-dropdown')]/ul[contains(@class,'dropdown-menu hidden-sm-down')]/li/a[contains(@class,'dropdown-item')]"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            driver.findElement(By.xpath("//*[contains(@class,'expand-more _gray-darker hidden-sm-down')]")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.get(listofItems.get(2).getAttribute("href"));

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);


            WebElement element = driver.findElement(By.className("ui-autocomplete-input"));
            element.sendKeys(searchingWord);
            element.submit();

            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

            driver.findElement(By.className("select-title")).click();
            driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.findElements(By.xpath("//*[contains(@class,'dropdown-menu')]/a")).get(4).click();

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);


            driver.manage().window().setSize(new Dimension(500, 754));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);


            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);


            List<WebElement> fullBox = driver.findElements(By.className("thumbnail-container"));


            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);


            for (int i = 0; i < fullBox.size(); i++) {


                if (isElementPresent("regular-price", fullBox.get(i)) && isElementPresent("discount-percentage", fullBox.get(i)) && isElementPresent("price", fullBox.get(i))) {

                  Integer discount= Integer.valueOf(fullBox.get(i).findElement(By.className("discount-percentage")).getText().replaceAll("[^,0-9]+", "").replaceAll(",", "."));
                  Double regularPrice= Double.valueOf(fullBox.get(i).findElement(By.className("regular-price")).getText().replaceAll("[^,0-9]+", "").replaceAll(",", "."));
                  Double currentPrice= Double.valueOf(fullBox.get(i).findElement(By.className("price")).getText().replaceAll("[^,0-9]+", "").replaceAll(",", "."));

                  int result = 100-(int)Math.ceil((currentPrice*100)/regularPrice);
                    if(result==discount){
                        System.out.println("it works");
                        Helper.logger.info("Discount is write");
                        result=0;
                    }
                }



            }


            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        } catch (Exception e) {
            System.out.println("Something was wrong");
            Helper.logger.info("Discount was  not fine");
            Helper.addToAllLogs();
            driver.quit();
            Helper.fh.close();
        }

    }
}


