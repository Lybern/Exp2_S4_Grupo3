package cl.duoc.bancoxyz.bff.web.service;

import cl.duoc.bancoxyz.bff.web.dto.DashboardWebDto;
import cl.duoc.bancoxyz.bff.web.dto.DetalleCuentaWebDto;
import cl.duoc.bancoxyz.bff.web.dto.TransaccionWebDto;
import cl.duoc.bancoxyz.model.Cuenta;
import cl.duoc.bancoxyz.model.MovimientoAnual;
import cl.duoc.bancoxyz.model.Transaccion;
import cl.duoc.bancoxyz.service.BancoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebBffService {

    private final BancoService bancoService;

    public WebBffService(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    public DetalleCuentaWebDto obtenerDetalleCompletoWeb(Long cuentaId) {
        Cuenta cuenta = bancoService.obtenerCuentaPorId(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta Web no encontrada con ID: " + cuentaId));

        List<Transaccion> transacciones = bancoService.obtenerTransaccionesPorCuenta(cuentaId);
        List<MovimientoAnual> anuales = bancoService.obtenerMovimientosAnuales(cuentaId);

        List<TransaccionWebDto> listaTxDto = transacciones.stream()
                .map(this::convertirTransaccionWeb)
                .collect(Collectors.toList());

        double sobregiro = cuenta.getLineaSobregiro() != null ? cuenta.getLineaSobregiro() : 0.0;
        double saldoTotal = cuenta.getSaldo() + sobregiro;
        double tasa = cuenta.getTasaInteres() != null ? cuenta.getTasaInteres() : 0.0;
        double interesEstimado = Math.round((cuenta.getSaldo() * (tasa / 100.0) / 12.0) * 100.0) / 100.0;

        Map<String, Object> metadata = Map.of(
                "canal", "WEB_DESKTOP",
                "fechaConsulta", LocalDateTime.now().toString(),
                "seguridadNivel", "TLS_1_3_WEB_SESSION",
                "soporteReportes", true
        );

        return DetalleCuentaWebDto.builder()
                .cuentaId(cuenta.getCuentaId())
                .nombreTitular(cuenta.getNombreTitular())
                .edadTitular(cuenta.getEdad())
                .tipoCuenta(cuenta.getTipo().toUpperCase())
                .saldoContable(cuenta.getSaldo())
                .lineaSobregiro(sobregiro)
                .saldoTotalDisponible(saldoTotal)
                .tasaInteresAnual(tasa)
                .interesMensualEstimado(interesEstimado)
                .estadoCuenta(cuenta.getEstado())
                .totalTransacciones(listaTxDto.size())
                .historialTransacciones(listaTxDto)
                .historialAnual(anuales)
                .metadatosWeb(metadata)
                .build();
    }

    public List<TransaccionWebDto> listarTodasTransaccionesWeb(Long cuentaId) {
        return bancoService.obtenerTransaccionesPorCuenta(cuentaId).stream()
                .map(this::convertirTransaccionWeb)
                .collect(Collectors.toList());
    }

    public DashboardWebDto obtenerDashboardWeb() {
        List<Cuenta> todas = bancoService.obtenerTodasLasCuentas();
        int totalCuentas = todas.size();
        double capitalTotal = todas.stream().mapToDouble(Cuenta::getSaldo).sum();
        double promedio = totalCuentas > 0 ? capitalTotal / totalCuentas : 0.0;

        Map<String, Long> distribucion = todas.stream()
                .collect(Collectors.groupingBy(c -> c.getTipo().toUpperCase(), Collectors.counting()));

        return new DashboardWebDto(
                totalCuentas,
                Math.round(capitalTotal * 100.0) / 100.0,
                Math.round(promedio * 100.0) / 100.0,
                distribucion
        );
    }

    private TransaccionWebDto convertirTransaccionWeb(Transaccion tx) {
        String categoria = "debito".equalsIgnoreCase(tx.getTipo()) || "retiro".equalsIgnoreCase(tx.getTipo())
                ? "EGRESO_FONDOS" : "INGRESO_FONDOS";

        return TransaccionWebDto.builder()
                .id(tx.getId())
                .fecha(tx.getFecha())
                .monto(tx.getMonto())
                .tipo(tx.getTipo().toUpperCase())
                .descripcion(tx.getDescripcion())
                .canalOrigen(tx.getCanal() != null ? tx.getCanal() : "SISTEMA")
                .categoria(categoria)
                .build();
    }
}
