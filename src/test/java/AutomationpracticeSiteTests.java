import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AutomationpracticeSiteTests {


    static WebDriver driver;

    @BeforeAll
    static void prepareBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://automationpractice.com/index.php");
    }

    @BeforeEach
    void clearCookies(){
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }

    // Test 1  -------------------------------------------------
    @Test
    void checkErrorMessageWhenEmailIsEmpty() {
        List<WebElement> elements = driver.findElements(By.className("login"));
        Assertions.assertEquals(1, elements.size());
        goToLogInSite();
        WebElement loginPageAuthentications = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", loginPageAuthentications.getText(),"I can't find Authentication");
        WebElement typePassword = driver.findElement(By.cssSelector("#passwd"));
        typePassword.click();
        typePassword.sendKeys("PeterGriffin1234");
        WebElement submitButton = driver.findElement(By.cssSelector("#SubmitLogin"));
        submitButton.click();
        WebElement errorMessage = driver.findElement(By.cssSelector(".alert-danger > ol "));
        Assertions.assertEquals("An email address required.", errorMessage.getText(),"There is no error message");


    }

    // Test 2  -------------------------------------------------
    @Test
    void checkErrorMessageWhenPasswordIsEmpty() {
        List<WebElement> elements = driver.findElements(By.cssSelector(".login"));
        Assertions.assertEquals(1, elements.size());
        goToLogInSite();
        WebElement typeLogin = driver.findElement(By.id("email"));
        typeLogin.click();
        typeLogin.sendKeys("PeterGriffin1234@gork.com");
        WebElement submitButton = driver.findElement(By.cssSelector("#SubmitLogin"));
        submitButton.click();
        WebElement errorMessage = driver.findElement(By.cssSelector(".alert-danger > ol "));
        Assertions.assertEquals("Password is required.", errorMessage.getText(),"There is no error message");
    }

    // Test 3
    @Test
    void checkLogoAndSearchBoxInMainSiteAndSignInPage() {
        List<WebElement> areYuHrSearchbox = driver.findElements(By.cssSelector("#searchbox"));
        Assertions.assertEquals(1, areYuHrSearchbox.size());
        List<WebElement> areYuHrLogo = driver.findElements(By.cssSelector("#header_logo"));
        Assertions.assertEquals(1, areYuHrLogo.size());

        if (areYuHrSearchbox.size() == 1 && areYuHrLogo.size() == 1) {

            Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login")));
            goToLogInSite();
            List<WebElement> areYuHrSearchbox1 = driver.findElements(By.cssSelector("#searchbox"));
            Assertions.assertEquals(1, areYuHrSearchbox1.size());
            List<WebElement> areYuHrLogo1 = driver.findElements(By.cssSelector("#header_logo"));
            Assertions.assertEquals(1, areYuHrLogo1.size());

        }


    }

    // Test 4  -------------------------------------------------
    @Test
    void checkContactUsSiteFromMainSite() {
        String checkMainSite = driver.getCurrentUrl();
        Assertions.assertEquals("http://automationpractice.com/index.php",checkMainSite);
        WebElement gotoContactUsSite = driver.findElement(By.cssSelector("#contact-link"));
        gotoContactUsSite.click();
        WebElement checkContactUs = driver.findElement(By.className("navigation_page"));
        Assertions.assertEquals("Contact", checkContactUs.getText(),"There is no any Contact");

    }

    // Test 5  -------------------------------------------------
    @Test
    void checkMainSiteFromSignInPage() {
        goToLogInSite();
        WebElement checkLogInSite = driver.findElement(By.className("navigation_page"));
        Assertions.assertEquals("Authentication", checkLogInSite.getText());
        WebElement gotoMainSite = driver.findElement(By.cssSelector("#header_logo > a"));
        gotoMainSite.click();
        String checkMainSiteUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://automationpractice.com/index.php", checkMainSiteUrl,"wrong URL");
    }
    // Test 6  -------------------------------------------------
    @Test
    void checkAddProductToCart() {
        WebElement product = driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[1]"));
        Actions moveMouse = new Actions(driver);
        moveMouse.moveToElement(product);
        WebElement addProduct = driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[1]/div/div[2]/div[2]/a[1]/span"));
        moveMouse.click(addProduct);
        WebElement isAddToCart = driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/h2/span[2]"));
        Assertions.assertEquals("There is 1 item in your cart.",isAddToCart.getText(),"don't find item");

    }

    // Test 7  -------------------------------------------------
    @Test
    void checkDeleteProductFromCart() {
        WebElement searchItemToAdd = driver.findElement(By.id("search_query_top"));
        searchItemToAdd.click();
        searchItemToAdd.sendKeys("Blouse");
        WebElement findItem = driver.findElement(By.className("button-search"));
        findItem.click();
        WebElement changeToList = driver.findElement(By.className("icon-th-list"));
        changeToList.click();
        WebElement addToCartButton = driver.findElement(By.cssSelector("a.button.ajax_add_to_cart_button.btn.btn-default > span"));
        addToCartButton.click();
        WebElement closeButton = driver.findElement(By.cssSelector(".cross"));
        closeButton.click();
        Actions moveMouse = new Actions(driver);
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.ajax_cart_product_txt.unvisible")));
        moveMouse.moveToElement(driver.findElement(By.cssSelector(".ajax_cart_product_txt.unvisible")));
        moveMouse.click(driver.findElement(By.className("ajax_cart_block_remove_link")));
        WebElement isCartEmpty = driver.findElement(By.cssSelector("span.ajax_cart_quantity.unvisible"));
        Assertions.assertTrue(isCartEmpty.isEnabled());

    }

    // Test 8  -------------------------------------------------
    @Test
    void logInTest() {
        goToLogInSite();
        login("test@softie.pl","1qaz!QAZ");
        Assertions.assertEquals("My account",driver.findElement(By.className("navigation_page")).getText(),"log in is impossible");
    }

    // Functions -----------------------------------------------
    // #########################################################
    // login ------------------------------------------------
    public void login(String email,String psswrd){
        WebElement findPasswordInput = driver.findElement(By.id("passwd"));
        WebElement findLoginInput = driver.findElement(By.id("email"));
        WebElement signIn = driver.findElement(By.id("SubmitLogin"));
        findPasswordInput.sendKeys(psswrd);
        findLoginInput.sendKeys(email);
        signIn.click();

    }

    // goToLoginSite ------------------------------------------------
    public static void goToLogInSite(){
        WebElement loginButton = driver.findElement(By.cssSelector(".login"));
        loginButton.click();
    }

}


