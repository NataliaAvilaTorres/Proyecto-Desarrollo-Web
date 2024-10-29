package com.example.demo.e2e;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UseCaseTest2 {

    private final String BASE_URL = "http://localhost:4200";
    private WebDriver driver;
    private WebDriverWait wait;
    private String selectedMascotaNombre;
    private String selectedMascotaId;
    private int initialMedicamentoCount;
    private double initialGanancias;
    private double initialVentas;

    @BeforeEach
    public void init() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-cache");
        chromeOptions.addArguments("--disk-cache-size=0");
        chromeOptions.addArguments("--disable-application-cache");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-extensions");
        this.driver = new ChromeDriver(chromeOptions);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void AdministrarMedicamentoTest__MascotaDetails() throws InterruptedException {

        // Iniciar sesión como administrador y capturar datos iniciales
        driver.get(BASE_URL + "/login");
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement roleAdmin = driver.findElement(By.id("role-admin"));
        WebElement loginButton = driver.findElement(By.id("login-btn"));
        roleAdmin.click();
        emailInput.sendKeys("juan.admin@correo.com");
        passwordInput.sendKeys("admin123");
        loginButton.click();

        // Confirmar redirección al panel de administrador
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/adminPanel"));
        assertEquals(BASE_URL + "/adminPanel", driver.getCurrentUrl());

        // Obtener datos iniciales de "AEROFAR" (si no existe, inicializar en 0)
        WebElement medicamentoCountElement;
        try {
            medicamentoCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='medicamentos-table']//td[text()='AEROFAR']/following-sibling::td")));
            initialMedicamentoCount = Integer.parseInt(medicamentoCountElement.getText());
        } catch (Exception e) {
            initialMedicamentoCount = 0;  // AEROFAR no está en la lista, inicializar en 0
        }

        // Capturar datos iniciales de ventas y ganancias, eliminando caracteres innecesarios
        WebElement ventasElement = driver.findElement(By.cssSelector(".ventas-valor"));
        initialVentas = Double.parseDouble(ventasElement.getText().replace("$", "").replace(",", "").replace(".", ""));

        WebElement gananciasElement = driver.findElement(By.cssSelector(".ganancias-valor"));
        initialGanancias = Double.parseDouble(gananciasElement.getText().replace("$", "").replace(",", "").replace(".", ""));

        // Iniciar sesión como veterinario
        driver.get(BASE_URL + "/login");
        emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        passwordInput = driver.findElement(By.id("password"));
        WebElement roleVeterinario = driver.findElement(By.id("role-veterinario"));
        loginButton = driver.findElement(By.id("login-btn"));
        roleVeterinario.click();
        emailInput.sendKeys("pedro@p.com");
        passwordInput.sendKeys("12345");
        loginButton.click();

        // Verificar redirección a veterinarioPanel
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/veterinarioPanel"));
        assertEquals(BASE_URL + "/veterinarioPanel", driver.getCurrentUrl());

        // Navegar a administrar tratamientos
        WebElement addTratamientoLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-tratamiento-link")));
        addTratamientoLink.click();

        // Confirmar carga de selects para "mascota" y "medicamento"
        WebElement mascotaSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mascota")));
        Select selectMascota = new Select(mascotaSelectElement);
        wait.until(driver -> selectMascota.getOptions().size() > 1);

        WebElement medicamentoSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("medicamento")));
        Select selectMedicamento = new Select(medicamentoSelectElement);
        wait.until(driver -> selectMedicamento.getOptions().size() > 1);

        // Select the second mascota option
        selectMascota.selectByIndex(1);
        WebElement selectedMascotaOption = selectMascota.getOptions().get(1);
        System.out.println("Selected Mascota Option: " + selectedMascotaOption.getText());
        selectedMascotaNombre = selectedMascotaOption.getText().split("-")[1].trim(); // Ajustar de acuerdo al formato del texto
        System.out.println("Nombre de la mascota seleccionada: " + selectedMascotaNombre);


        // Extract ID from Value attribute
        String[] parts = selectedMascotaOption.getText().split("[() -]+");
        selectedMascotaId = parts[2].trim();
        System.out.println("ID de la mascota seleccionada: " + selectedMascotaId);      
        boolean aerofarFound = false;
        for (int i = 1; i < selectMedicamento.getOptions().size(); i++) {
            if (selectMedicamento.getOptions().get(i).getText().contains("AEROFAR")) {
                selectMedicamento.selectByIndex(i);
                aerofarFound = true;
                break;
            }
        }

        if (!aerofarFound) {
            fail("Medicamento 'AEROFAR' no se encuentra en la lista de opciones. Prueba cancelada.");
}
        String selectedMedicamentoNombre = "AEROFAR";

        WebElement unidadesInput = driver.findElement(By.id("unidades"));
        unidadesInput.sendKeys("1");

        WebElement fechaInput = driver.findElement(By.id("fecha"));
        String fechaTratamiento = "15/03/2024";
        fechaInput.sendKeys(fechaTratamiento);

        // Enviar formulario
        WebElement submitButton = driver.findElement(By.className("btn-administrar"));
        submitButton.click();

        // Confirmar alerta de éxito
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Tratamiento creado correctamente", alert.getText());
        alert.accept();

        System.out.println("Selected Mascota ID: " + selectedMascotaId);
        System.out.println("Selected Medicamento: " + selectedMedicamentoNombre);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increase to observe any delays
        String formattedFechaTratamiento = "15/03/2024";  // Assuming this format matches the table


        // Verificar que el tratamiento se muestra en la lista de tratamientos
        WebElement tratamientoTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tratamientosTable")));
        System.out.println("Table Content: " + tratamientoTable.getText());

        WebElement tratamientoRow = wait.until(ExpectedConditions.visibilityOf(
            tratamientoTable.findElement(By.xpath(".//tr[td[contains(text(),'" + selectedMascotaId + "')] and td[contains(text(), '" + formattedFechaTratamiento + "')]]"))
        ));
        assertEquals(selectedMascotaId, tratamientoRow.findElement(By.xpath(".//td[1]")).getText());
        assertEquals(fechaTratamiento, tratamientoRow.findElement(By.xpath(".//td[4]")).getText());
        assertEquals(selectedMedicamentoNombre, tratamientoRow.findElement(By.xpath(".//td[5]")).getText());

        // Ir a detalles de la mascota seleccionada
        WebElement mascotaList = wait.until(ExpectedConditions.elementToBeClickable(By.id("listado-mascotas-link")));
        mascotaList.click();

        WebElement verDetallesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-detalles-" + selectedMascotaId)));
        verDetallesButton.click();

        // Verificar detalles de la mascota en su perfil
        WebElement mascotaNombre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(@class, 'card-title') and text()='" + selectedMascotaNombre + "']")));
        WebElement mascotaID = driver.findElement(By.xpath("//p[contains(text(), 'ID:')]/span"));
        assertEquals(selectedMascotaNombre, mascotaNombre.getText());
        assertEquals(selectedMascotaId, mascotaID.getText());

        // Confirmar historial de tratamientos en detalles de la mascota
        WebElement tratamientoHistorialTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        WebElement tratamientoHistorialRow = wait.until(ExpectedConditions.visibilityOf(
            tratamientoHistorialTable.findElement(By.xpath(".//tr[td[contains(text(), 'AEROFAR')] and td[contains(text(), '15/03/2024')] and td[contains(text(), 'Vet. Pedro')]]"))
        ));

        // Validar detalles específicos del tratamiento en historial
        assertEquals(selectedMedicamentoNombre, tratamientoHistorialRow.findElement(By.xpath(".//td[1]")).getText());
        assertEquals(fechaTratamiento, tratamientoHistorialRow.findElement(By.xpath(".//td[2]")).getText());

        // Iniciar sesión nuevamente como administrador
        driver.get(BASE_URL + "/login");
        emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        passwordInput = driver.findElement(By.id("password"));
        roleAdmin = driver.findElement(By.id("role-admin"));
        loginButton = driver.findElement(By.id("login-btn"));
        roleAdmin.click();
        emailInput.sendKeys("juan.admin@correo.com");
        passwordInput.sendKeys("admin123");
        loginButton.click();

        // Confirmar redirección al panel de administrador
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/adminPanel"));
        assertEquals(BASE_URL + "/adminPanel", driver.getCurrentUrl());
        System.out.println("Admin Panel URL: " + driver.getCurrentUrl());
        driver.navigate().refresh();

        // Validar incremento en cantidad de tratamientos para "AEROFAR" y ganancias
        medicamentoCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='medicamentos-table']//td[text()='AEROFAR']/following-sibling::td")));
        int updatedMedicamentoCount = Integer.parseInt(medicamentoCountElement.getText());
        assertEquals(initialMedicamentoCount + 1, updatedMedicamentoCount);

        ventasElement = driver.findElement(By.cssSelector(".ventas-valor"));
        double updatedVentas = Double.parseDouble(ventasElement.getText().replace("$", "").replace(",", "").replace(".", ""));
        gananciasElement = driver.findElement(By.cssSelector(".ganancias-valor"));
        double updatedGanancias = Double.parseDouble(gananciasElement.getText().replace("$", "").replace(",", "").replace(".", ""));

        double medicamentoPrecio = 129000.0; // Precio de venta de AEROFAR
        double medicamentoGanancia = 64500.0; // Ganancia por venta de AEROFAR

        // Verificar que las ventas y ganancias hayan aumentado correctamente
        assertEquals(initialVentas + medicamentoPrecio, updatedVentas);
        assertEquals(initialGanancias + medicamentoGanancia, updatedGanancias);
    }
}
