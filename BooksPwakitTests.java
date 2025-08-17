package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Logs;

public class BooksPwakitTests extends BaseTest {

    @Test
    public void shadowDoom1Test() {
        Logs.info("Navegamos a la pagina indicada");
        driver.get("https://books-pwakit.appspot.com/");

        Logs.info("Obteniendo el shadow root");
        final var shadowRoot = driver.findElement(
                By.cssSelector("book-app[apptitle='BOOKS']")).getShadowRoot();

        Logs.info("Obteniendo el footer a traves del shadow root");
        final var footer = shadowRoot.findElement(By.cssSelector("p"));

        Logs.info("Verificando que el texto sea el correcto");
        Assert.assertEquals(
                footer.getText(),
                "Made with <3 by the Polymer team."
        );
    }

    @Test
    public void shadowDoom2Test() {
        Logs.info("Navegamos a la pagina indicada");
        driver.get("https://books-pwakit.appspot.com/");

        Logs.info("Obteniendo el shadow root");
        final var shadowRoot = driver.findElement(
                By.cssSelector("book-app[apptitle='BOOKS']")).getShadowRoot();

        Logs.info("Obteniendo el input a traves del shadow root");
        final var input = shadowRoot.findElement(By.id("input"));

        Logs.info("Escribiendo hello world en el input");
        new Actions(driver)
                .click(input)
                .sendKeys("Hello World")
                .sendKeys(Keys.ENTER)
                .perform();
        // esperamos 1 seg para que renderice el shadow dom
        sleep(1000);

        Logs.info("Obteniendo el shadow root interno");
        final var shadowRootInterno = shadowRoot.findElement(By.cssSelector("book-explore")).getShadowRoot();

        final var listaLibros = shadowRootInterno.findElement(By.cssSelector("ul"));

        Logs.info("Verificando que la lista de libros sea visible");
        Assert.assertTrue(listaLibros.isDisplayed());
    }


}
