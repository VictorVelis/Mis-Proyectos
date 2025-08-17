package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Logs;

import java.time.Duration;

public class SauceDemoTests extends BaseTest {
    @Test(groups = {regression, smoke})
    public void usuarioInvalidotest() {
        rellenarFormularioLogin("locked_out_user", "secret_sauce");

        Logs.info("Verificando el mensaje de error");
        final var errorLabel = driver.findElement(By.cssSelector("h3[data-test='error']"));

        softAssert.assertTrue(errorLabel.isDisplayed());
        softAssert.assertEquals(errorLabel.getText(),
                "Epic sadface: Sorry, this user has been locked out.");
        softAssert.assertAll();
    }

    @Test(groups = {regression, smoke})
    public void usuarioValidoTest() {
        rellenarFormularioLogin("standard_user", "secret_sauce");

        var localizadorInventario = driver.findElement(By.id("page_wrapper"));

        Logs.info("Verificando que el inventario esta visible");
        Assert.assertTrue(localizadorInventario.isDisplayed());


    }

    @Test(groups = {regression})
    public void caracteristicasItemTest() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        rellenarFormularioLogin("standard_user", "secret_sauce");


        final var listaItem = driver.findElements(
                By.className("inventory_item_img"));

        Logs.info("Haciendo click en el primer elemento");

        listaItem.get(0).click();

        Logs.info("Esperando que cargue el detalle del producto");
        final var inventoryName = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.className("inventory_details_name")));

        Logs.info("Verificando las caracteristicas");

        softAssert.assertTrue(inventoryName.isDisplayed());
        softAssert.assertTrue(driver.findElement(By.className("inventory_details_price")).isDisplayed());
        softAssert.assertTrue(driver.findElement(By.className("inventory_details_desc")).isDisplayed());
        softAssert.assertTrue(driver.findElement(By.className("inventory_details_img")).isDisplayed());
        softAssert.assertTrue(driver.findElement(By.id("add-to-cart")).isDisplayed());
        softAssert.assertAll();


    }

    @Test(groups = {regression})
    public void select1Test() {
        rellenarFormularioLogin("standard_user", "secret_sauce");

        final var selectLocator = driver.findElement(By.className("product_sort_container"));

        //casteamos el Select

        final var select = new Select(selectLocator);

        Logs.info("Seleccionamos los items de Z-->A");
        select.selectByValue("za");

        final var listItemNombres = driver.findElements(By.className("inventory_item_name"));

        Logs.info("Verificando los articulos");

        softAssert.assertEquals(listItemNombres.get(0).getText(),
                "Test.allTheThings() T-Shirt (Red)");
        softAssert.assertEquals(listItemNombres.get(listItemNombres.size() - 1).getText(),
                "Sauce Labs Backpack");
        softAssert.assertAll();
    }

    @Test(groups = {regression})
    public void select2Test() {
        rellenarFormularioLogin("standard_user", "secret_sauce");

        final var selectLocator = driver.findElement(By.className("product_sort_container"));

        //casteamos el Select

        final var select = new Select(selectLocator);

        Logs.info("Seleccionamos los items de low to high price");
        select.selectByValue("lohi");

        Logs.info("Obteniendo la lista de precios");

        var listaPrecios = driver.findElements(By.className("inventory_item_price"));

        Logs.info("Obteniendo los valores de los precios del primer y ultimo item");

        var primerPrecio = Double.parseDouble(listaPrecios.get(0).getText().replace("$", ""));
        var ultimoPrecio = Double.parseDouble(listaPrecios.get(listaPrecios.size() - 1).getText().replace("$", ""));

        Logs.info("Verificando el precio de el primer y ultimo item");

        softAssert.assertEquals(primerPrecio, 7.99);
        softAssert.assertEquals(ultimoPrecio, 49.99);
        softAssert.assertAll();

    }

    @Test(groups = {regression})
    public void linkTest() {
        rellenarFormularioLogin("standard_user", "secret_sauce");

        final var facebookLabel = driver.findElement(By.xpath("//a[text()='Facebook']"));

        Logs.info("Verificando las caracteristicas del link");

        softAssert.assertTrue(facebookLabel.isDisplayed());
        softAssert.assertTrue(facebookLabel.isEnabled());
        softAssert.assertEquals(facebookLabel.getAttribute("href"), "https://www.facebook.com/saucelabs");
        softAssert.assertAll();
    }

    @Test(groups = {regression})
    public void link2Test() {
        rellenarFormularioLogin("standard_user", "secret_sauce");

        final var linkedinLabel = driver.findElement(By.xpath("//a[text()='LinkedIn']"));

        Logs.info("Verificando las caracteristicas del link");

        softAssert.assertTrue(linkedinLabel.isDisplayed());
        softAssert.assertTrue(linkedinLabel.isEnabled());
        softAssert.assertEquals(linkedinLabel.getAttribute("href"), "https://www.linkedin.com/company/sauce-labs/");
        softAssert.assertAll();

    }

    @Test(groups = {regression})
    public void link3Test() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        rellenarFormularioLogin("standard_user", "secret_sauce");

        Logs.info("Abriendo el Burguer Menu");
        final var menuBtn = driver.findElement(By.id("react-burger-menu-btn"));
        menuBtn.click();

        Logs.info("Esperamos que abra el menu");
        final var aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("about_sidebar_link")));


        Logs.info("Verificando las caracteristicas del link");

        softAssert.assertTrue(aboutLink.isDisplayed());
        softAssert.assertTrue(aboutLink.isEnabled());
        softAssert.assertEquals(aboutLink.getAttribute("href"), "https://saucelabs.com/");
        softAssert.assertAll();
    }

    @Test(groups = {regression})
    public void logoutTest() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        rellenarFormularioLogin("standard_user", "secret_sauce");

        Logs.info("Presionando el Burguer Menu");
        driver.findElement(By.id("react-burger-menu-btn")).click();

        Logs.info("Esperamos que cargue el menu");
        final var logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));

        Logs.info("Haciendo click en el boton de logout");
        logoutButton.click();

        Logs.info("Esperando que cargue la pagina de inicio");
        final var usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

        Logs.info("Verificamos que volvimos a la pagina de inicio");
        Assert.assertTrue(usernameInput.isDisplayed());

    }

    @Test(groups = {regression, smoke})
    public void cookie1Test() {
        rellenarFormularioLogin("standard_user", "secret_sauce");

        Logs.info("Obteniendo el set de las cookies");
        var cookieSet = driver.manage().getCookies();

        Logs.info("Verificando que solo hay 1 cookie");
        Assert.assertEquals(cookieSet.size(), 1);

        Logs.info("Borrando todas las cookies");
        driver.manage().deleteAllCookies();

        Logs.info("Obteniendo el set de las cookies nuevamente");
        cookieSet = driver.manage().getCookies();

        Logs.info("Verificando que el tamaño sea 0");
        Assert.assertTrue(cookieSet.isEmpty());


    }

    @Test(groups = {regression, smoke})
    public void cookie2Test() {
        rellenarFormularioLogin("standard_user", "secret_sauce");

        Logs.info("Obteniendo el valor de la cookie de login");
        final var cookieLogin = driver.manage().getCookieNamed("session-username");

        Logs.info("Verificando que su value sea standard_user");
        Assert.assertEquals(cookieLogin.getValue(), "standard_user");
    }

    @Test(groups = {regression, smoke})
    public void relativeLocator1Test() {
        rellenarFormularioLogin("standard_user", "secret_sauce");

        /*
            Obtengo el localizador de manera relativa, este tiene un tag button
            ademas, se encuentra debajo del texto Sauce Labs Bolt T-Shirt
         */
        final var locator = (By) RelativeLocator
                .with(By.className("inventory_item_price"))
                .below(By.xpath("//div[text()='Sauce Labs Bolt T-Shirt']"));

        // encuentro el precio : quito dolar --> cambio a double
        final var price = Double.parseDouble(
                driver.findElement(locator).getText().replace("$", "")
        );

        Logs.info("Verificando que el precio sea correcto");
        Assert.assertEquals(price, 15.99);
    }

    @Test(groups = {regression, smoke})
    public void relativeLocator2Test() {
        rellenarFormularioLogin("standard_user", "secret_sauce");

        /*
            Obtengo el localizador de manera relativa, este tiene un tag button
            ademas, se encuentra debajo del texto Sauce Labs Fleece Jacket
         */
        final var locator = (By) RelativeLocator
                .with(By.tagName("button"))
                .below(By.xpath("//div[text()='Sauce Labs Fleece Jacket']"));

        var carButton = driver.findElement(locator);

        Logs.info("Verificando que el texto sea Add to cart");
        Assert.assertEquals(
                carButton.getText(), "Add to cart"
        );

        Logs.info("Haciendo click en el boton Add to cart");
        carButton.click();

        Logs.info("Refrescando la referencia de boton");
        carButton = driver.findElement(locator);

        Logs.info("Verificando que su texto sea Remove");
        Assert.assertEquals(
                carButton.getText(), "Remove"
        );


    }

    private void rellenarFormularioLogin(String username, String password) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Logs.info("Navegando a la pagina");
        driver.get("https://www.saucedemo.com/");

        Logs.info("Esperando que cargue la pagina ptincipal");
        final var usernameInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("user-name")));

        Logs.info("Escribiendo el username");
        usernameInput.sendKeys(username);

        Logs.info("Escribiendo el password");
        driver.findElement(By.id("password")).sendKeys(password);

        Logs.info("Haciendo click en el boton de login");
        driver.findElement(By.id("login-button")).click();

       // Logs.info("Esperando que la pagina de shopping cargue");
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

    }
}

