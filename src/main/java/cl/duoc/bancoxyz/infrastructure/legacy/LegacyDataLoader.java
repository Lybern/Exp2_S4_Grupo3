package cl.duoc.bancoxyz.infrastructure.legacy;

import cl.duoc.bancoxyz.domain.model.Cuenta;
import cl.duoc.bancoxyz.domain.model.MovimientoAnual;
import cl.duoc.bancoxyz.domain.model.Transaccion;
import cl.duoc.bancoxyz.domain.repository.BankRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegacyDataLoader {

    private final BankRepository bankRepository;
    private final ResourceLoader resourceLoader;

    @PostConstruct
    public void init() {
        log.info("Cargando datos legacy del Banco XYZ...");
        cargarCuentas();
        cargarTransacciones();
        cargarCuentasAnuales();
        log.info("Carga de datos legacy completada con éxito. Total cuentas: {}", bankRepository.findAllCuentas().size());
    }

    private void cargarCuentas() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data/intereses.csv");
            if (!resource.exists()) return;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String line = br.readLine(); // encabezado: cuenta_id,nombre,saldo,edad,tipo
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",", -1);
                    if (parts.length < 5) continue;
                    try {
                        String idStr = parts[0].trim();
                        if (idStr.isEmpty()) continue;
                        Long cuentaId = Long.parseLong(idStr);

                        String nombre = parts[1].trim();
                        if (nombre.isEmpty() || "Unknown".equalsIgnoreCase(nombre)) {
                            nombre = "Cliente " + cuentaId;
                        }

                        double saldo = 0.0;
                        if (!parts[2].trim().isEmpty()) {
                            saldo = Math.max(0.0, Double.parseDouble(parts[2].trim()));
                        }

                        int edad = 30;
                        if (!parts[3].trim().isEmpty()) {
                            try {
                                int parsedEdad = Integer.parseInt(parts[3].trim());
                                if (parsedEdad >= 18 && parsedEdad <= 100) {
                                    edad = parsedEdad;
                                }
                            } catch (NumberFormatException ignored) {}
                        }

                        String tipo = parts[4].trim().toLowerCase();
                        if (tipo.equals("-1") || tipo.isEmpty()) {
                            tipo = "cuenta_corriente";
                        }

                        double sobregiro = tipo.contains("corriente") ? 300000.0 : 0.0;
                        double tasaInteres = tipo.contains("ahorro") ? 3.8 : 0.5;

                        Cuenta cuenta = Cuenta.builder()
                                .cuentaId(cuentaId)
                                .nombreTitular(nombre)
                                .saldo(saldo)
                                .edad(edad)
                                .tipo(tipo)
                                .lineaSobregiro(sobregiro)
                                .tasaInteres(tasaInteres)
                                .estado("ACTIVA")
                                .build();

                        bankRepository.saveCuenta(cuenta);
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
                String line = br.readLine(); // encabezado: id,fecha,monto,tipo
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",", -1);
                    if (parts.length < 4) continue;
                    try {
                        Long id = Long.parseLong(parts[0].trim());
                        String fecha = parts[1].trim();
                        double monto = Math.abs(Double.parseDouble(parts[2].trim()));
                        String tipo = parts[3].trim().toLowerCase();

                        // Asociar transacción de forma distribuida entre las cuentas existentes
                        Long cuentaId = (id % 150) + 100;

                        Transaccion tx = Transaccion.builder()
                                .id(id)
                                .cuentaId(cuentaId)
                                .fecha(fecha)
                                .monto(monto)
                                .tipo(tipo)
                                .descripcion("Operación registrada: " + tipo)
                                .canalOrigen("LEGACY_BATCH")
                                .build();

                        bankRepository.saveTransaccion(tx);
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
                String line = br.readLine(); // encabezado: cuenta_id,fecha,transaccion,monto,descripcion
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",", -1);
                    if (parts.length < 5) continue;
                    try {
                        Long cuentaId = Long.parseLong(parts[0].trim());
                        String fecha = parts[1].trim();
                        String transaccion = parts[2].trim();
                        double monto = Math.abs(Double.parseDouble(parts[3].trim()));
                        String descripcion = parts[4].trim();
                        if (descripcion.isEmpty()) {
                            descripcion = "Movimiento histórico anual";
                        }

                        MovimientoAnual mov = MovimientoAnual.builder()
                                .cuentaId(cuentaId)
                                .fecha(fecha)
                                .transaccion(transaccion)
                                .monto(monto)
                                .descripcion(descripcion)
                                .build();

                        bankRepository.saveMovimientoAnual(mov);
                    } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            log.error("Error al cargar cuentas_anuales.csv: {}", e.getMessage());
        }
    }
}
