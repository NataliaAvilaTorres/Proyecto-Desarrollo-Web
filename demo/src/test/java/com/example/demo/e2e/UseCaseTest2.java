package com.example.demo.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.Select;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class UseCaseTest2 {

    private final String BASE_URL = "http://localhost:4200";
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void init() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-extensions");
        this.driver = new ChromeDriver(chromeOptions);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void AdministrarMedicamentoTest__MascotaDetails() {

        driver.get(BASE_URL + "/login");

        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement roleVeterinario = driver.findElement(By.id("role-veterinario"));
        WebElement loginButton = driver.findElement(By.id("login-btn"));

        roleVeterinario.click();
        emailInput.sendKeys("pedro@p.com");
        passwordInput.sendKeys("12345");
        loginButton.click();

        // Esperar a que la página de veterinario se cargue
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/veterinarioPanel"));
        assertEquals(BASE_URL + "/veterinarioPanel", driver.getCurrentUrl());

        // Paso 3: Navegar a la sección de administrar medicamentos
        WebElement addPropietarioLink = driver.findElement(By.id("add-tratamiento-link"));
        addPropietarioLink.click();

        // Esperar a que se cargue el formulario y sus datos
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mascota")));

        // Esperar a que las opciones del select de mascota estén cargadas
        wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(
                By.id("mascota"), By.tagName("option")));

        // Esperar a que las opciones del select de medicamento estén cargadas
        wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(
                By.id("medicamento"), By.tagName("option")));

        // Esperar a que el select de mascota sea clickeable
        wait.until(ExpectedConditions.elementToBeClickable(By.id("mascota")));

        // Verificar que hay opciones disponibles antes de seleccionar
        Select selectMascota = new Select(driver.findElement(By.id("mascota")));
        wait.until(driver -> selectMascota.getOptions().size() > 1); // Espera a que haya más de 1 opción (contando el
                                                                     // placeholder)

        // Ahora es seguro seleccionar la mascota
        selectMascota.selectByIndex(1);

        // Hacer lo mismo para el medicamento
        Select selectMedicamento = new Select(driver.findElement(By.id("medicamento")));
        wait.until(driver -> selectMedicamento.getOptions().size() > 1);
        selectMedicamento.selectByIndex(97);

        // Ingresar unidades a suministrar
        WebElement unidadesInput = driver.findElement(By.id("unidades"));
        unidadesInput.sendKeys("1");

        // Seleccionar la fecha de administración
        WebElement fechaInput = driver.findElement(By.id("fecha"));
        fechaInput.sendKeys("05-02-2024");

        // Enviar formulario
        WebElement submitButton = driver.findElement(By.className("btn-administrar"));
        submitButton.click();

        // Verificar que se muestra una alerta de confirmación
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Tratamiento creado correctamente", alert.getText());
        alert.accept();

        // Esperar a que el enlace de lista de mascotas esté presente y clickeable
        WebElement mascotaList = wait.until(ExpectedConditions.elementToBeClickable(By.id("listado-mascotas-link")));
        mascotaList.click();

        WebElement verDetallesButton = driver.findElement(By.id("btn-detalles-1"));
        verDetallesButton.click();

        driver.get(BASE_URL + "/login");

        WebElement emailAdminInput = driver.findElement(By.id("email"));
        WebElement passwordAdminInput = driver.findElement(By.id("password"));
        WebElement roleAdmin = driver.findElement(By.id("role-admin"));
        WebElement loginButtonAdmin = driver.findElement(By.id("login-btn"));

        roleAdmin.click();
        emailAdminInput.sendKeys("juan.admin@correo.com");
        passwordAdminInput.sendKeys("admin123");
        loginButtonAdmin.click();

        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/adminPanel"));
        assertEquals(BASE_URL + "/adminPanel", driver.getCurrentUrl());
    }
}