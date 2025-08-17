package automation;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Logs;

public class SeleniumDevTests extends BaseTest {
    @Test
    public void scroll1Test() {
        Logs.info("Navegando a la pagina");
        driver.get("https://www.selenium.dev/selenium/web/scroll.html");

        Logs.info("Generando un numero entre 5 y 9");
        final var faker = new Faker();
        final var n = faker.number().numberBetween(5, 9);
        Logs.info("n: %d", n);

        final var dynamicId = String.format("line%d", n);//line5, line6, etc
        final var lineN = driver.findElement(By.id(dynamicId));

        Logs.info("haciendo scroll hacia el id: %s", dynamicId);
        new Actions(driver)
                .scrollToElement(lineN)//hago scroll hacia el elemento
                .pause(1000)
                .perform();

        Logs.info("Haciendo click en lineN");
        lineN.click();

        Logs.info("Verificando que el texto sea: %s", dynamicId);
        Assert.assertEquals(
                driver.findElement(By.id("clicked")).getText(), dynamicId
        );
    }

    @Test
    public void scroll2Test() {
        Logs.info("Navegando a la pagina");
        driver.get("https://www.selenium.dev/selenium/web/scrolling_tests/page_with_frame_out_of_view.html");

        final var iframe = driver.findElement(By.name("frame"));

        Logs.info("Haciendo scroll hacia el iframe");
        new Actions(driver)
                .scrollToElement(iframe)
                .pause(1000)
                .perform();

        Logs.info("Cambiamos el contexto al iframe");
        driver.switchTo().frame(iframe); // uso el web element

        final var checkBox = driver.findElement(By.name("checkbox"));

        Logs.info("Haciendo click en el checkbox");
        checkBox.click();

        Logs.info("Verificando que el checkbox este seleccionado");
        Assert.assertTrue(checkBox.isSelected());

    }
}
