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
        this.driver = new ChromeDriver(chromeOptions);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    public void teardown() {
       driver.quit();
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

        // Esperar a que la página de veterinario se cargue
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/veterinarioPanel"));
        assertEquals(BASE_URL + "/veterinarioPanel", driver.getCurrentUrl());

        // Paso 3: Navegar a la sección de registro de clientes
        WebElement addPropietarioLink = driver.findElement(By.id("add-propietario-link"));
        addPropietarioLink.click();

        // Paso 4: Completar el formulario de registro de cliente con error en el correo
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

        // Verificar mensaje de error
        WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-error")));
        assertEquals("Formato de correo inválido.", emailError.getText());

        // Corregir el campo correo y guardar
        correoInput.clear();
        correoInput.sendKeys("Juan@p.com");
        guardarButton.click();

        // Verificar redirección a la lista de propietarios
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/propietarios"));

        // Confirmar que el nuevo propietario aparece en la lista
        WebElement propietariosTable = driver.findElement(By.id("propietariosTable"));
        WebElement newOwnerRow = wait.until(ExpectedConditions.visibilityOf(propietariosTable.findElement(By.xpath(".//td[text()='1001186098']"))));
        assertEquals("1001186098", newOwnerRow.getText());

        // Navegar a agregar mascota
        // Espera explícita para asegurar que el enlace de "Agregar Mascota" está presente y visible
        WebElement addMascotaLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/mascotaForm/add']")));
        addMascotaLink.click();
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/mascotaForm/add"));

        // Llenar el formulario de agregar mascota
        WebElement nombreMascota = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        WebElement razaMascota = driver.findElement(By.id("breed"));
        WebElement edadMascota = driver.findElement(By.id("age"));
        WebElement pesoMascota = driver.findElement(By.id("weight"));
        WebElement enfermedadMascota = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='enfermedad']")));
        WebElement fotoMascota = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='fotoUrl']")));
        WebElement estadoMascota = driver.findElement(By.id("status"));
        WebElement propietarioSearch = driver.findElement(By.id("propietarioSearch"));
        WebElement guardarMascotaBtn = driver.findElement(By.cssSelector("button[type='submit']"));

        // Completar los campos de la mascota
        nombreMascota.sendKeys("Firulais");
        razaMascota.sendKeys("Golden Retriever");
        edadMascota.sendKeys("5");
        pesoMascota.sendKeys("30.5");
        enfermedadMascota.sendKeys("Ninguna");
        fotoMascota.sendKeys("https://example.com/firulais.jpg");
        estadoMascota.sendKeys("Activo");
        // Escribir la cédula en el campo de búsqueda del propietario
        propietarioSearch.sendKeys("1001186098");

        // Esperar a que aparezca el desplegable y seleccionar la primera opción
        WebElement dropdownResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'autocomplete-list')]/li[contains(text(), '1001186098')]")));
        dropdownResult.click(); // Hacer clic en la opción deseada del desplegable

        // Asegurarse de que el desplegable esté cerrado antes de intentar hacer clic en "Guardar"
        wait.until(ExpectedConditions.invisibilityOf(dropdownResult));
        // Usar JavaScript para hacer clic en el botón "Guardar"
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", guardarMascotaBtn);
        js.executeScript("arguments[0].click();", guardarMascotaBtn);
        
        // Esperar la alerta de éxito y aceptarla
        Alert successAlert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Mascota añadida correctamente", successAlert.getText()); // Verificar el texto del mensaje
        successAlert.accept(); // Aceptar la alerta

        // Hacer scroll hasta el enlace "Cerrar Sesión" para asegurarse de que esté visible
       // Establecer el zoom de la página al 80% para asegurar la visibilidad del botón
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='80%'");

        // Ahora ubicar el botón "Cerrar Sesión" y hacer clic
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/logout']")));
        logoutButton.click(); // Hacer clic en el botón

        // Verificar la redirección al login
        // Verificar la redirección a la página principal después de cerrar sesión
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/"));

        // Hacer clic en "Iniciar Sesión" en la página principal
        WebElement iniciarSesionLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
        iniciarSesionLink.click(); // Navegar a la página de login

        // Esperar a que la página de login esté cargada
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/login"));

        WebElement roleDueno = driver.findElement(By.id("role-dueno"));
        roleDueno.click();
        emailInput = driver.findElement(By.id("email"));
        passwordInput = driver.findElement(By.id("password"));
        loginButton = driver.findElement(By.id("login-btn"));

        emailInput.sendKeys("Juan@p.com");
        passwordInput.sendKeys("password123");
        loginButton.click();

        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/propietarioPanel"));

        // Navegar a la lista de mascotas del dueño
        // Buscar y hacer clic en el enlace "Mis Mascotas" usando el href directo
        WebElement misMascotasLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/propietarioMascotas']")));
        misMascotasLink.click();
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/propietarioMascotas"));

        // Verificar que la mascota "Firulais" está en la lista
        WebElement mascotasTable = driver.findElement(By.id("mascotasTable"));
        WebElement newPetRow = wait.until(ExpectedConditions.visibilityOf(mascotasTable.findElement(By.xpath(".//td[text()='Firulais']"))));
        assertEquals("Firulais", newPetRow.getText());

        // Ver detalles de la mascota
        // Localizar y hacer clic en el botón "Ver Detalles" usando su clase y el contexto correcto
        WebElement verDetallesLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn-detalles")));
        verDetallesLink.click();
        wait.until(ExpectedConditions.urlContains("/mascota/detail/"));


        // Verificar los detalles de la mascota usando selectores de clase más específicos
        WebElement mascotaNombre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(@class, 'card-title') and text()='Firulais']")));
        WebElement mascotaID = driver.findElement(By.xpath("//p[contains(text(), 'ID:')]/span"));
        WebElement mascotaRaza = driver.findElement(By.xpath("//p[contains(text(), 'Raza:')]/span"));
        WebElement mascotaEdad = driver.findElement(By.xpath("//p[contains(text(), 'Edad:')]/span"));
        WebElement mascotaPeso = driver.findElement(By.xpath("//p[contains(text(), 'Peso:')]/span"));
        WebElement mascotaEnfermedad = driver.findElement(By.xpath("//p[contains(text(), 'Enfermedad:')]/span"));
        WebElement mascotaEstado = driver.findElement(By.xpath("//p[contains(text(), 'Estado:')]/span"));

        // Validar los valores de cada campo
        assertEquals("Firulais", mascotaNombre.getText());
        assertEquals("101", mascotaID.getText());
        assertEquals("Golden Retriever", mascotaRaza.getText());
        assertEquals("5", mascotaEdad.getText());
        assertEquals("30.5", mascotaPeso.getText());
        assertEquals("Ninguna", mascotaEnfermedad.getText());
        assertEquals("Activo", mascotaEstado.getText());

    }
}
