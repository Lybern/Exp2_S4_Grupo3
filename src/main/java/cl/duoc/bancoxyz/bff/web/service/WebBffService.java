package cl.duoc.bancoxyz.bff.web.service;

import cl.duoc.bancoxyz.bff.web.dto.WebAccountDetailResponse;
import cl.duoc.bancoxyz.bff.web.dto.WebDashboardResponse;
import cl.duoc.bancoxyz.bff.web.dto.WebTransactionDetailResponse;
import cl.duoc.bancoxyz.domain.model.Cuenta;
import cl.duoc.bancoxyz.domain.model.MovimientoAnual;
import cl.duoc.bancoxyz.domain.model.Transaccion;
import cl.duoc.bancoxyz.domain.service.BankCoreService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebBffService {

    private final BankCoreService bankCoreService;

    public WebBffService(BankCoreService bankCoreService) {
        this.bankCoreService = bankCoreService;
    }

    public WebAccountDetailResponse obtenerDetalleCompletoWeb(Long cuentaId) {
        Cuenta cuenta = bankCoreService.obtenerCuentaPorId(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta Web no encontrada con ID: " + cuentaId));

        List<Transaccion> transacciones = bankCoreService.obtenerTransaccionesPorCuenta(cuentaId);
        List<MovimientoAnual> anuales = bankCoreService.obtenerMovimientosAnuales(cuentaId);

        List<WebTransactionDetailResponse> txDtoList = transacciones.stream()
                .map(this::mapToWebTx)
                .collect(Collectors.toList());

        double sobregiro = cuenta.getLineaSobregiro() != null ? cuenta.getLineaSobregiro() : 0.0;
        double saldoTotal = cuenta.getSaldo() + sobregiro;
        double tasa = cuenta.getTasaInteres() != null ? cuenta.getTasaInteres() : 0.0;
        double interesEstimado = Math.round((cuenta.getSaldo() * (tasa / 100.0) / 12.0) * 100.0) / 100.0;

        Map<String, Object> metadata = Map.of(
                "canal", "WEB_DESKTOP",
                "fechaConsulta", LocalDateTime.now().toString(),
                "seguridadNivel", "TLS_1_3_WEB_SESSION",
                "soporteDescargaReporte", true
        );

        return WebAccountDetailResponse.builder()
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
                .totalTransacciones(txDtoList.size())
                .historialTransacciones(txDtoList)
                .historialAnual(anuales)
                .metadatosWeb(metadata)
                .build();
    }

    public List<WebTransactionDetailResponse> listarTodasTransaccionesWeb(Long cuentaId) {
        return bankCoreService.obtenerTransaccionesPorCuenta(cuentaId).stream()
                .map(this::mapToWebTx)
                .collect(Collectors.toList());
    }

    public WebDashboardResponse obtenerDashboardWeb() {
        List<Cuenta> todas = bankCoreService.obtenerTodasLasCuentas();
        int totalCuentas = todas.size();
        double capitalTotal = todas.stream().mapToDouble(Cuenta::getSaldo).sum();
        double promedio = totalCuentas > 0 ? capitalTotal / totalCuentas : 0.0;

        Map<String, Long> distribucion = todas.stream()
                .collect(Collectors.groupingBy(c -> c.getTipo().toUpperCase(), Collectors.counting()));

        return new WebDashboardResponse(
                totalCuentas,
                Math.round(capitalTotal * 100.0) / 100.0,
                Math.round(promedio * 100.0) / 100.0,
                distribucion
        );
    }

    private WebTransactionDetailResponse mapToWebTx(Transaccion tx) {
        String categoria = "debito".equalsIgnoreCase(tx.getTipo()) || "retiro".equalsIgnoreCase(tx.getTipo())
                ? "EGRESO_FONDOS" : "INGRESO_FONDOS";

        return WebTransactionDetailResponse.builder()
                .id(tx.getId())
                .fecha(tx.getFecha())
                .monto(tx.getMonto())
                .tipo(tx.getTipo().toUpperCase())
                .descripcion(tx.getDescripcion())
                .canalOrigen(tx.getCanalOrigen() != null ? tx.getCanalOrigen() : "SISTEMA")
                .categoria(categoria)
                .build();
    }
}
