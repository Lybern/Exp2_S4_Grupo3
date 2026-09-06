package cl.duoc.bancoxyz.bff.mobile.service;

import cl.duoc.bancoxyz.bff.mobile.dto.MobileAccountSummaryResponse;
import cl.duoc.bancoxyz.bff.mobile.dto.MobileTransactionResponse;
import cl.duoc.bancoxyz.bff.mobile.dto.MobileTransferRequest;
import cl.duoc.bancoxyz.domain.model.Cuenta;
import cl.duoc.bancoxyz.domain.model.Transaccion;
import cl.duoc.bancoxyz.domain.service.BankCoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobileBffService {

    private final BankCoreService bankCoreService;

    public MobileBffService(BankCoreService bankCoreService) {
        this.bankCoreService = bankCoreService;
    }

    public MobileAccountSummaryResponse obtenerResumenMovil(Long cuentaId) {
        Cuenta cuenta = bankCoreService.obtenerCuentaPorId(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta móvil no encontrada con ID: " + cuentaId));

        List<Transaccion> todas = bankCoreService.obtenerTransaccionesPorCuenta(cuentaId);
        List<MobileTransactionResponse> ultimos3 = todas.stream()
                .limit(3)
                .map(this::mapToMobileTx)
                .collect(Collectors.toList());

        String notificacion = cuenta.getTipo().contains("ahorro")
                ? "Tu cuenta genera " + cuenta.getTasaInteres() + "% de interés anual."
                : "Línea de sobregiro activa: $" + cuenta.getLineaSobregiro();

        return MobileAccountSummaryResponse.builder()
                .numeroCuenta(cuenta.getCuentaId())
                .titular(cuenta.getNombreTitular())
                .saldoDisponible(cuenta.getSaldo())
                .tipoCuenta(cuenta.getTipo().toUpperCase())
                .ultimosMovimientos(ultimos3)
                .notificacion(notificacion)
                .build();
    }

    public Double consultarSaldoRapido(Long cuentaId) {
        return bankCoreService.obtenerCuentaPorId(cuentaId)
                .map(Cuenta::getSaldo)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con ID: " + cuentaId));
    }

    public MobileTransactionResponse ejecutarTransferenciaRapida(Long cuentaOrigenId, MobileTransferRequest request) {
        Transaccion debito = bankCoreService.procesarRetiro(cuentaOrigenId, request.getMonto(), "MOBILE_APP", null);
        bankCoreService.procesarDeposito(request.getCuentaDestinoId(), request.getMonto(), "MOBILE_TRANSFER");
        return mapToMobileTx(debito);
    }

    private MobileTransactionResponse mapToMobileTx(Transaccion tx) {
        boolean esCargo = "retiro".equalsIgnoreCase(tx.getTipo()) || "debito".equalsIgnoreCase(tx.getTipo());
        double montoFinal = esCargo ? -Math.abs(tx.getMonto()) : Math.abs(tx.getMonto());

        return MobileTransactionResponse.builder()
                .id(tx.getId())
                .fecha(tx.getFecha())
                .monto(montoFinal)
                .tipo(tx.getTipo().toUpperCase())
                .detalle(tx.getDescripcion())
                .build();
    }
}
