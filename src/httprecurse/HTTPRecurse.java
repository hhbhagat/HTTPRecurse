package httprecurse;

import IO.FileIO;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HTTPRecurse {

    //SET THESE PLS
    private static final String startURL = "";
    private static final String outputPath = "";

    private static FileIO io = new FileIO();
    private static ArrayList<String> arrURL = new ArrayList();
    private static final String blacklist = "?C=";
    private static WebDriver init = new FirefoxDriver();
    private static WebDriver pdrv = new FirefoxDriver();
    private static WebDriver getter = new FirefoxDriver();

    public static void main(String[] args) {


        init.get(startURL);
        init.switchTo().defaultContent();
        for (WebElement el : init.findElements(By.tagName("a"))) {
            if ((!el.getAttribute("href").contains("?C="))
                    && (!el.getText().contains("Parent Directory"))) {
                System.out.println("recursing");
                pdrv.get(el.getAttribute("href"));
                try {
                    recurse(el.getAttribute("href"));
                } catch (Exception e) {
                }
            }
        }
        pdrv.quit();
        init.quit();
        getter.quit();
        io.writeArrayToFile(arrURL, outputPath);
        System.out.println("Size of everything is: ");
        try {
            GetSize.run(arrURL);
        } catch (Exception e) {
            System.out.println("Failed.");
        }
    }

    private static List<WebElement> recurse(String elem) throws Exception {
        getter.get(elem);
        for (WebElement el : getter.findElements(By.tagName("a"))) {
            try {
                if ((!el.getAttribute("href").contains("?C="))
                        && (!el.getText().contains("Parent Directory"))) {
                    System.out.println("getting " + el.getAttribute("href"));
                    getter.get(el.getAttribute("href"));
                    for (WebElement innerEL : getter.findElements(By.tagName("a"))) {
                        try {
                            String[] spl = innerEL.getAttribute("href").split(".");
                            if (!el.getAttribute("href").contains("?C=")) {
                                if (!el.getText().contains("Parent Directory")) {
                                    if (spl[(spl.length - 1)].length() != 3) {
                                        recurse(innerEL.getAttribute("href"));
                                    }
                                } else {
                                    arrURL.add(innerEL.getAttribute("href"));
                                    System.out.println(innerEL.getAttribute("href"));
                                }
                            }
                        } catch (Exception e) {
                            if (!innerEL.getAttribute("href").contains("?C=")) {
                                if (!(innerEL.getAttribute("href").charAt(innerEL.getAttribute("href").length() - 1) == '/')) {
                                    arrURL.add(innerEL.getAttribute("href"));
                                    System.out.println(innerEL.getAttribute("href"));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
        return null;
    }
}