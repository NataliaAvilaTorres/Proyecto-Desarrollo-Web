package com.example.demo.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.AdministradorRepository;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.MedicamentoRepository;
import com.example.demo.repository.PropietarioRepository;
import com.example.demo.repository.TratamientoRepository;
import com.example.demo.repository.VeterinarioRepository;

import jakarta.transaction.Transactional;

@Controller
@Transactional
@Profile("default")
public class DataBaseInit implements ApplicationRunner {

        @Autowired
        MascotaRepository mascotaRepository;

        @Autowired
        PropietarioRepository propietarioRepository;

        @Autowired
        VeterinarioRepository veterinarioRepository;

        @Autowired
        AdministradorRepository administradorRepository;

        @Autowired
        TratamientoRepository tratamientoRepository;

        @Autowired
        MedicamentoRepository medicamentoRepository;

        @Override
        public void run(ApplicationArguments args) throws Exception {

                //VETERINARIOS
                
                veterinarioRepository.save(new Veterinario("100118790", "Vet. Juan", "cirujano","12345",
                                "juan@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118791", "Vet. Pedro", "cirujano","12345",
                                "pedro@p.com", "Inactivo"));
                veterinarioRepository.save(new Veterinario("100118792", "Vet. Ana", "cirujano", "12345",
                                "ana@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118793", "Vet. Maria", "dermatóloga", "12345",
                                "maria@p.com", "Inactivo"));
                veterinarioRepository.save(new Veterinario("100118794", "Vet. Carlos", "dentista", "12345",
                                "carlos@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118795", "Vet. Sofia", "oftalmóloga", "12345",
                                "sofia@p.com", "Inactivo"));
                veterinarioRepository.save(new Veterinario("100118796", "Vet. Luis", "pediatra", "12345",
                                "luis@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118797", "Vet. Camila", "cirujana", "12345",
                                "camila@p.com", "Inactivo"));
                veterinarioRepository.save(new Veterinario("100118798", "Vet. Andrés", "neurocirujano", "12345",
                                "andres@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118799", "Vet. Gabriela", "cirujana", "12345",
                                "gabriela@p.com", "Inactivo"));
                veterinarioRepository.save(new Veterinario("100118800", "Vet. David", "cardiólogo", "12345",
                                "david@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118801", "Vet. Laura", "dermatóloga", "12345",
                                "laura@p.com", "Inactivo"));
                veterinarioRepository.save(new Veterinario("100118802", "Vet. Javier", "oncólogo",  "12345",
                                "javier@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118803", "Vet. Carolina", "pediatra",  "12345",
                                "carolina@p.com", "Inactivo"));
                veterinarioRepository.save(new Veterinario("100118804", "Vet. Ricardo", "cirujano", "12345",
                                "ricardo@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118805", "Vet. Daniela", "dentista", "12345",
                                "daniela@p.com", "Inactivo"));
                veterinarioRepository.save(new Veterinario("100118806", "Vet. Mauricio", "neurocirujano",  "12345",
                                "mauricio@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118807", "Vet. Valeria", "oftalmóloga","12345",
                                "valeria@p.com", "Inactivo"));
                veterinarioRepository.save(new Veterinario("100118808", "Vet. Alejandro", "cardiólogo","12345",
                                "alejandro@p.com", "Activo"));
                veterinarioRepository.save(new Veterinario("100118809", "Vet. Patricia", "dermatóloga", "12345",
                                "patricia@p.com", "Inactivo"));

                Random random = new Random();


                // Lista de nombres de propietarios y correos electrónicos
                List<String> nombresPropietarios = Arrays.asList("Luis", "Pedro", "Ana", "Carlos", "María", "Jorge",
                                "Laura",
                                "Sofía", "Andrés", "Elena");
                List<String> correosPropietarios = Arrays.asList(
                                "luis1@p.com", "pedro1@p.com", "ana1@p.com", "carlos1@p.com", "maria1@p.com",
                                "jorge1@p.com", "laura1@p.com", "sofia1@p.com", "andres1@p.com", "elena1@p.com",
                                "luis2@p.com", "pedro2@p.com", "ana2@p.com", "carlos2@p.com", "maria2@p.com",
                                "jorge2@p.com", "laura2@p.com", "sofia2@p.com", "andres2@p.com", "elena2@p.com",
                                "luis3@p.com", "pedro3@p.com", "ana3@p.com", "carlos3@p.com", "maria3@p.com",
                                "jorge3@p.com", "laura3@p.com", "sofia3@p.com", "andres3@p.com", "elena3@p.com",
                                "luis4@p.com", "pedro4@p.com", "ana4@p.com", "carlos4@p.com", "maria4@p.com",
                                "jorge4@p.com", "laura4@p.com", "sofia4@p.com", "andres4@p.com", "elena4@p.com",
                                "luis5@p.com", "pedro5@p.com", "ana5@p.com", "carlos5@p.com", "maria5@p.com",
                                "jorge5@p.com", "laura5@p.com", "sofia5@p.com", "andres5@p.com", "elena5@p.com");

                // Lista de nombres de mascotas
                List<String> nombresMascotas = Arrays.asList("Coco", "Nala", "Luna", "Max", "Rocky", "Simba", "Milo",
                                "Bella",
                                "Duke", "Chloe");
                List<String> tiposMascotas = Arrays.asList("Perro", "Gato");

                // Lista de razas de perros y gatos
                List<String> razasPerros = Arrays.asList("Bulldog Francés", "Pastor Alemán", "Golden Retriever",
                                "Beagle", "Pug");
                List<String> razasGatos = Arrays.asList("Gato Persa", "Gato Siamés", "Gato Bengala", "Gato Maine Coon",
                                "Gato Sphynx");

                // Lista de enfermedades aleatorias
                List<String> enfermedades = Arrays.asList("Displasia de cadera", "Parvovirus", "Leucemia felina",
                                "Infección respiratoria", "Alergia alimentaria");

                // Lista de URLs para las fotos de perros
                List<String> fotosPerros = Arrays.asList(
                                "https://c.files.bbci.co.uk/48DD/production/_107435681_perro1.jpg",
                                "https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg",
                                "https://conecta.tec.mx/sites/default/files/styles/header_full/public/2018-11/Portada%20perros.jpg.webp?itok=84KeYznH",
                                "https://es.mypet.com/wp-content/uploads/sites/23/2021/03/razas-de-perros-pastor-aleman-570x455-1.jpg?w=300",
                                "https://imagenes.muyinteresante.com/files/image_414_276/uploads/2023/09/01/64f19def1a685.jpeg");

                // Lista de URLs para las fotos de gatos
                List<String> fotosGatos = Arrays.asList(
                                "https://static.nationalgeographic.es/files/styles/image_3200/public/75552.ngsversion.1422285553360.jpg?w=1900&h=1267",
                                "https://www.mirringo.com.co/Portals/mirringo/Images/articulos-actualidad-gatuna/ciclo-de-vida-de-los-gatos/Interna-1-ciclo-de-vida-de-los-gatos.jpg?ver=2024-03-20-160432-910",
                                "https://dam.ngenespanol.com/wp-content/uploads/2019/08/gato-exotico-garfield-2.jpg",
                                "https://s2.ppllstatics.com/lasprovincias/www/multimedia/202112/04/media/cortadas/GF0RO0L1-kQJH-U1601666042762sF-1248x770@Las%20Provincias.jpg",
                                "https://www.zooplus.es/magazine/wp-content/uploads/2022/01/Psicologia-felina.jpeg");

                // Crear 50 propietarios
                for (int i = 0; i < 50; i++) {
                        String nombrePropietario = nombresPropietarios.get(i % nombresPropietarios.size());
                        String correo = correosPropietarios.get(i);
                        String cedula = "100128609" + i;
                        String celular = "314371398" + i;
                        Propietario propietario = propietarioRepository
                                        .save(new Propietario(cedula, nombrePropietario, correo, celular, "1234"));

                        // Asignar dos mascotas a cada propietario
                        for (int j = 0; j < 2; j++) {
                                String nombreMascota = nombresMascotas.get((i * 2 + j) % nombresMascotas.size());
                                String tipo = tiposMascotas.get(j % tiposMascotas.size());
                                String raza = tipo.equals("Perro")
                                                ? razasPerros.get(random.nextInt(razasPerros.size()))
                                                : razasGatos.get(random.nextInt(razasGatos.size()));
                                int edad = 1 + random.nextInt(15); // Edad aleatoria entre 1 y 15 años
                                double peso = 1 + (15 * random.nextDouble()); // Peso aleatorio entre 1 y 15 kg
                                String enfermedad = enfermedades.get(random.nextInt(enfermedades.size()));

                                // Seleccionar la foto dependiendo si es perro o gato
                                String foto = tipo.equals("Perro")
                                                ? fotosPerros.get(random.nextInt(fotosPerros.size()))
                                                : fotosGatos.get(random.nextInt(fotosGatos.size()));

                                String estado = random.nextBoolean() ? "Activo" : "Inactivo"; // Estado aleatorio

                                // Crear la mascota con todos los campos
                                Mascota mascota = new Mascota(nombreMascota, raza, edad, peso, enfermedad, foto,
                                                estado);
                                mascota.setPropietario(propietario);
                                mascotaRepository.save(mascota);
                        }
                }

                List<Medicamento> medicamentos = new ArrayList<>();

                try (InputStream fis = new ClassPathResource("MEDICAMENTOS_VETERINARIA.xlsx").getInputStream();
                                Workbook workbook = new XSSFWorkbook(fis)) {
                        Sheet sheet = workbook.getSheetAt(0); // Asumiendo que los datos están en la primera hoja

                        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) { // Inicia desde 1 para
                                                                                                // saltar el encabezado
                                Row row = sheet.getRow(rowIndex);
                                if (row != null) {
                                        String nombre = row.getCell(0).getStringCellValue();
                                        float precioVenta = (float) row.getCell(1).getNumericCellValue();
                                        float precioCompra = (float) row.getCell(2).getNumericCellValue();
                                        int unidadesDisponibles = (int) row.getCell(3).getNumericCellValue();
                                        int unidadesVendidas = (int) row.getCell(4).getNumericCellValue();

                                        Medicamento medicamento = new Medicamento(nombre, precioCompra, precioVenta,
                                                        unidadesDisponibles, unidadesVendidas);
                                        medicamento.setUnidadesVendidas(unidadesVendidas); // Seteamos las unidades
                                                                                           // vendidas
                                        medicamentos.add(medicamento);
                                }
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }

                medicamentoRepository.saveAll(medicamentos); // Guarda todos los medicamentos leídos


                // Crear 10 tratamientos aleatorios
                List<Mascota> mascotas = mascotaRepository.findAll();
                List<Veterinario> veterinarios = veterinarioRepository.findAll();

                for (int i = 0; i < 10; i++) {
                        Mascota mascota = mascotas.get(i % mascotas.size()); // Selección de mascota ciclando la lista
                        Veterinario veterinario = veterinarios.get(i % veterinarios.size()); // Selección de veterinario ciclando la lista
                        Medicamento medicamento = medicamentos.get(i); // Obtener el medicamento en el índice i (primeros 10)
                        if (medicamento.getUnidadesDisponibles() > 0) {
                                // Actualizar unidades disponibles y vendidas
                                medicamento.setUnidadesDisponibles(medicamento.getUnidadesDisponibles() - 1);
                                medicamento.setUnidadesVendidas(medicamento.getUnidadesVendidas() + 1);
                                medicamentoRepository.save(medicamento);  // Guardar cambios en el medicamento
                        
                                Date fecha = new Date(System.currentTimeMillis() - i * 100000000); // Fecha aleatoria
                        
                                Tratamiento tratamiento = new Tratamiento(fecha, 1);
                                tratamiento.setMascota(mascota);
                                tratamiento.setVeterinario(veterinario);
                                tratamiento.setMedicamento(medicamento);
                                System.out.println("Tratamiento generado: Mascota ID = " + mascota.getId()
                                                   + ", Veterinario ID = " + veterinario.getId() + ", Medicamento = "
                                                   + medicamento.getNombre());
                        
                                tratamientoRepository.save(tratamiento);
                            } else {
                                System.out.println("El medicamento con ID " + medicamento.getId() + " no tiene unidades disponibles.");
                            }
                }

                // ADMINISTRADORES


                Administrador adminentity = Administrador.builder().cedula("1234567890").nombre("Juan Perez").contrasena("admin123").correo("admin@a.com").build();
                administradorRepository.save(adminentity);

                adminentity = Administrador.builder().cedula("1234567891").nombre("Maria Gomez").contrasena("admin123").correo("admin@b.com").build();
                administradorRepository.save(adminentity);

                adminentity = Administrador.builder().cedula("1234567892").nombre("Carlos Martinez").contrasena("admin123").correo("admin@c.com").build();
                administradorRepository.save(adminentity);

        }

}