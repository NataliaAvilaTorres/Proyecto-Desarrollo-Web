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

        // Esperar a que se cargue el formulario
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mascota")));

        // Seleccionar mascota
        Select selectMascota = new Select(driver.findElement(By.id("mascota")));
        selectMascota.selectByIndex(1); // Selecciona la primera mascota disponible

        // Seleccionar medicamento
        Select selectMedicamento = new Select(driver.findElement(By.id("medicamento")));
        selectMedicamento.selectByIndex(3); // Selecciona el primer medicamento disponible

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

        WebElement mascotaList = driver.findElement(By.id("listado-mascotas-link"));
        mascotaList.click();

        WebElement verDetallesButton = driver.findElement(By.id("btn-detalles-1"));
        verDetallesButton.click();
    }

}
