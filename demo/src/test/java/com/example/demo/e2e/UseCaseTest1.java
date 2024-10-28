package com.example.demo.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class UseCaseTest1 {

    private final String BASE_URL = "http://localhost:4200";
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void init() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-extensions");
        // chromeOptions.addArguments("--headless");

        this.driver = new ChromeDriver(chromeOptions);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void LoginTest_funcionalidadesVeterinario_Veterinario() {

        // Verificar que el usuario inicia sesion correctamente
        driver.get(BASE_URL + "/login");

        // Intento 1: Ingresar credenciales incorrectas
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement roleVeterinario = driver.findElement(By.id("role-veterinario"));
        WebElement loginButton = driver.findElement(By.id("login-btn"));

        // Seleccionar el rol de veterinario y completar campos incorrectamente
        roleVeterinario.click();
        emailInput.sendKeys("maria@p.com");
        passwordInput.sendKeys("incorrecta");
        loginButton.click();

        // Verificar que se muestra una alerta de error
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Credenciales inválidas para veterinario", alert.getText());
        alert.accept();

        // Intento 2: Ingresar credenciales correctas
        passwordInput.clear();
        passwordInput.sendKeys("12345");
        loginButton.click();

        // Esperar a que la página de veterinario se cargue (o redirigir al panel)
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/veterinarioPanel"));

        // Verificar que la URL actual es la del panel de veterinario
        assertEquals(BASE_URL + "/veterinarioPanel", driver.getCurrentUrl());

        // Paso 3: Navegar a la sección de registro de clientes
        WebElement addPropietarioLink = driver.findElement(By.id("add-propietario-link"));
        addPropietarioLink.click();

        // Paso 4: Completar el formulario de registro de cliente con error en la cédula
        WebElement cedulaInput = driver.findElement(By.id("cedula"));
        WebElement nombreInput = driver.findElement(By.id("nombre"));
        WebElement correoInput = driver.findElement(By.id("correo"));
        WebElement celularInput = driver.findElement(By.id("celular"));
        WebElement contrasenaInput = driver.findElement(By.id("contrasena"));
        WebElement guardarButton = driver.findElement(By.id("guardar-btn"));

        cedulaInput.sendKeys("1001186098");
        nombreInput.sendKeys("Juan Pérez");
        correoInput.sendKeys("Juan@"); // Correo incompleto
        celularInput.sendKeys("3001234567");
        contrasenaInput.sendKeys("password123");
        guardarButton.click();

        // Verificar que se muestra un mensaje de error por el correo
        WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-error")));
        assertEquals("Formato de correo inválido.", emailError.getText());

        // Paso 5: Corregir el campo cédula y volver a intentar el registro
        correoInput.clear();
        correoInput.sendKeys("Juan@p.com"); // Cédula corregida
        guardarButton.click();

    }
}