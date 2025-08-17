package automation;

import com.github.javafaker.Faker;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Logs;

import java.time.Duration;

public class DemoQATests extends BaseTest {
    @Test
    public void keyboard1Test() {
        Logs.info("Navegando a la pagina");
        driver.get("https://demoqa.com/text-box");

        final var faker = new Faker();
        final var fullName = faker.name().fullName();
        Logs.info("fullname: %s", fullName);

        final var usernameInput = driver.findElement(By.id("userName"));

        Logs.info("Hago click para dar focus, presionando SHIFT y escribiendo en mayusculas");
        new Actions(driver)
                .click(usernameInput) //hago click para dar focus
                .keyDown(Keys.SHIFT)// presiono SHIFT
                .sendKeys(fullName)// escribo el fullname
                .keyUp(Keys.SHIFT)// dejo de presionar SHIFT
                .perform(); // realizo las acciones

        Logs.info("Verifico que el input esta en mayusculas");
        Assert.assertEquals(usernameInput.getAttribute("value"), fullName.toUpperCase());
    }

    @Test
    public void keyboard2Test() {
        Logs.info("Navegando a la pagina");
        driver.get("https://demoqa.com/text-box");

        final var faker = new Faker();
        final var address = faker.address().fullAddress();
        Logs.info("fullname: %s", address);

        final var currentAddressInput = driver.findElement(By.id("currentAddress"));

        Logs.info("Doy focus, escrbo, selecciono y copio el contenido");
        new Actions(driver)
                .click(currentAddressInput)
                .sendKeys(address)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .sendKeys("c")
                .keyUp(Keys.CONTROL)
                .perform();

        final var permanentAddressInput = driver.findElement(By.id("permanentAddress"));

        Logs.info("Dando focus y pegando el contenido");
        new Actions(driver)
                .click(permanentAddressInput)
                .keyDown(Keys.CONTROL)
                .sendKeys("v")
                .keyUp(Keys.CONTROL)
                .perform();

        Logs.info("Verificando que ambos input tengan el mismo texto");
        Assert.assertEquals(
                currentAddressInput.getAttribute("value"),
                permanentAddressInput.getAttribute("value")
        );
    }

    @Test
    public void mouse1Test() {
        Logs.info("Navegamos a la pagina indicada");
        driver.get("https://demoqa.com/droppable");

        final var figuraOrigen = driver.findElement(By.id("draggable"));
        final var figuraDestino = driver.findElement(By.id("droppable"));

        Logs.info("Arrastrando la figura origen a la de destino");
        new Actions(driver)
                .dragAndDrop(figuraOrigen, figuraDestino) //arrastro de Origen a Destino
                .perform();

        Logs.info("Verificando que el label de dropped sea visible");
        Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Dropped!']")).isDisplayed());
    }

    @Test
    public void mouse2Test() {
        Logs.info("Navegando a la pagina indicada");
        driver.get("https://demoqa.com/tool-tips");

        final var greenButton = driver.findElement(By.id("toolTipButton"));

        Logs.info("Poniendo el puntero encima del boton verde");
        new Actions(driver)
                .moveToElement(greenButton)// puntero encima del boton verde
                .pause(1500)// pauso por un segundo y medio
                .perform();

        Logs.info("Verificando el texto del hover");
        Assert.assertEquals(
                greenButton.getAttribute("aria-describedby"),
                "buttonToolTip"
        );
    }

    @Test
    public void alert1Test() {
        Logs.info("Navegando a la pagina");
        driver.get("https://demoqa.com/alerts");

        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        Logs.info("Esperando que la pagina cargue");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Alerts']")));

        Logs.info("Haciendo click en el boton para que aparezca el alert");
        driver.findElement(By.id("alertButton")).click();

        Logs.info("Obteniendo alert");
        final var alert = (Alert) wait.until(ExpectedConditions.alertIsPresent());

        Logs.info("Verificando que el texto sea el correcto");
        Assert.assertEquals(alert.getText(), "You clicked a button");

        Logs.info("Presionando el boton del alert");
        alert.accept();
    }

    @Test
    public void alert2Test() {
        Logs.info("Navegando a la pagina");
        driver.get("https://demoqa.com/alerts");

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        Logs.info("Esperando que la pagina cargue");
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//h1[text()='Alerts']")));

        Logs.info("Haciendo click en el boton para que aparezca el confirm");
        driver.findElement(By.id("confirmButton")).click();

        Logs.info("obteniendo el alert");
        final var alert = (Alert) wait.until(ExpectedConditions.alertIsPresent());

        Logs.info("Haciendo click en cancel en el alert/confirm");
        alert.dismiss();

        Logs.info("Verificando que el div cancel sea visible");
        Assert.assertTrue(driver.
                findElement(By.xpath("//span[text()='Cancel']")).isDisplayed());
    }

    @Test
    public void alert3Test() {
        Logs.info("Navegando a la pagina");
        driver.get("https://demoqa.com/alerts");

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        Logs.info("Esperando que la pagina cargue");
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//h1[text()='Alerts']")));

        Logs.info("Haciendo click en el boton para que aparezca el confirm");
        driver.findElement(By.id("promtButton")).click();

        Logs.info("obteniendo el alert");
        final var alert = (Alert) wait.until(ExpectedConditions.alertIsPresent());

        final var faker = new Faker();
        final var randomName = faker.name().firstName();

        Logs.info("Escribiendo el nombre en el prompt");
        alert.sendKeys(randomName);

        Logs.info("Presionando el accept en el alert");
        alert.accept();

        final var dynamicLocator = String.format("//span[text()='%s']", randomName);

        Logs.info("Verificando que el nombre sea visible");
        Assert.assertTrue(driver.findElement(By.xpath(dynamicLocator)).isDisplayed());
    }


}
