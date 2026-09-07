package cl.duoc.bancoxyz.repository;

import cl.duoc.bancoxyz.model.Cuenta;
import cl.duoc.bancoxyz.model.MovimientoAnual;
import cl.duoc.bancoxyz.model.Transaccion;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class CargadorDatosLegacy {

    private static final Logger log = LoggerFactory.getLogger(CargadorDatosLegacy.class);
    private final BancoRepository bancoRepository;
    private final ResourceLoader resourceLoader;

    public CargadorDatosLegacy(BancoRepository bancoRepository, ResourceLoader resourceLoader) {
        this.bancoRepository = bancoRepository;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void cargarDatos() {
        log.info("Iniciando carga de datos legacy del Banco XYZ...");
        cargarCuentas();
        cargarTransacciones();
        cargarCuentasAnuales();
        log.info("Datos cargados correctamente. Total cuentas registradas: {}", bancoRepository.obtenerTodasLasCuentas().size());
    }

    private void cargarCuentas() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data/intereses.csv");
            if (!resource.exists()) return;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String linea = br.readLine();
                while ((linea = br.readLine()) != null) {
                    if (linea.trim().isEmpty()) continue;
                    String[] datos = linea.split(",", -1);
                    if (datos.length < 5) continue;
                    try {
                        String idStr = datos[0].trim();
                        if (idStr.isEmpty()) continue;
                        Long cuentaId = Long.parseLong(idStr);

                        String nombre = datos[1].trim();
                        if (nombre.isEmpty() || "Unknown".equalsIgnoreCase(nombre)) {
                            nombre = "Cliente " + cuentaId;
                        }

                        double saldo = 0.0;
                        if (!datos[2].trim().isEmpty()) {
                            saldo = Math.max(0.0, Double.parseDouble(datos[2].trim()));
                        }

                        int edad = 30;
                        if (!datos[3].trim().isEmpty()) {
                            try {
                                int edadLeida = Integer.parseInt(datos[3].trim());
                                if (edadLeida >= 18 && edadLeida <= 100) edad = edadLeida;
                            } catch (NumberFormatException ignored) {}
                        }

                        String tipo = datos[4].trim().toLowerCase();
                        if (tipo.equals("-1") || tipo.isEmpty()) tipo = "cuenta_corriente";

                        double sobregiro = tipo.contains("corriente") ? 300000.0 : 0.0;
                        double tasaInteres = tipo.contains("ahorro") ? 3.8 : 0.5;

                        Cuenta cuenta = new Cuenta(
                                cuentaId,
                                nombre,
                                saldo,
                                edad,
                                tipo,
                                sobregiro,
                                tasaInteres,
                                "ACTIVA"
                        );

                        bancoRepository.guardarCuenta(cuenta);
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            log.error("Error al cargar intereses.csv: {}", e.getMessage());
        }
    }

    private void cargarTransacciones() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data/transacciones.csv");
            if (!resource.exists()) return;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String linea = br.readLine();
                while ((linea = br.readLine()) != null) {
                    if (linea.trim().isEmpty()) continue;
                    String[] datos = linea.split(",", -1);
                    if (datos.length < 4) continue;
                    try {
                        Long id = Long.parseLong(datos[0].trim());
                        String fecha = datos[1].trim();
                        double monto = Math.abs(Double.parseDouble(datos[2].trim()));
                        String tipo = datos[3].trim().toLowerCase();

                        Long cuentaId = (id % 150) + 100;

                        Transaccion tx = new Transaccion(
                                id,
                                cuentaId,
                                fecha,
                                monto,
                                tipo,
                                "Operación registrada: " + tipo,
                                "LEGACY_BATCH"
                        );

                        bancoRepository.guardarTransaccion(tx);
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            log.error("Error al cargar transacciones.csv: {}", e.getMessage());
        }
    }

    private void cargarCuentasAnuales() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data/cuentas_anuales.csv");
            if (!resource.exists()) return;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String linea = br.readLine();
                while ((linea = br.readLine()) != null) {
                    if (linea.trim().isEmpty()) continue;
                    String[] datos = linea.split(",", -1);
                    if (datos.length < 5) continue;
                    try {
                        Long cuentaId = Long.parseLong(datos[0].trim());
                        String fecha = datos[1].trim();
                        String transaccion = datos[2].trim();
                        double monto = Math.abs(Double.parseDouble(datos[3].trim()));
                        String descripcion = datos[4].trim();
                        if (descripcion.isEmpty()) descripcion = "Movimiento histórico anual";

                        MovimientoAnual mov = new MovimientoAnual(
                                cuentaId,
                                fecha,
                                transaccion,
                                monto,
                                descripcion
                        );

                        bancoRepository.guardarMovimientoAnual(mov);
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            log.error("Error al cargar cuentas_anuales.csv: {}", e.getMessage());
        }
    }
}
