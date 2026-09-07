package cl.duoc.bancoxyz.bff.movil.service;

import cl.duoc.bancoxyz.bff.movil.dto.ResumenCuentaMovilDto;
import cl.duoc.bancoxyz.bff.movil.dto.SolicitudTransferenciaMovilDto;
import cl.duoc.bancoxyz.bff.movil.dto.TransaccionMovilDto;
import cl.duoc.bancoxyz.model.Cuenta;
import cl.duoc.bancoxyz.model.Transaccion;
import cl.duoc.bancoxyz.service.BancoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovilBffService {

    private final BancoService bancoService;

    public MovilBffService(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    public ResumenCuentaMovilDto obtenerResumenMovil(Long cuentaId) {
        Cuenta cuenta = bancoService.obtenerCuentaPorId(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta móvil no encontrada con ID: " + cuentaId));

        // Solo las últimas 3 transacciones para minimizar datos en redes móviles
        List<Transaccion> transacciones = bancoService.obtenerTransaccionesPorCuenta(cuentaId);
        List<TransaccionMovilDto> ultimos3 = transacciones.stream()
                .limit(3)
                .map(this::convertirTransaccionMovil)
                .collect(Collectors.toList());

        String mensaje = cuenta.getTipo().contains("ahorro")
                ? "Tu cuenta genera " + cuenta.getTasaInteres() + "% de interés anual."
                : "Línea de sobregiro activa: $" + cuenta.getLineaSobregiro();

        return ResumenCuentaMovilDto.builder()
                .numeroCuenta(cuenta.getCuentaId())
                .titular(cuenta.getNombreTitular())
                .saldoDisponible(cuenta.getSaldo())
                .tipoCuenta(cuenta.getTipo().toUpperCase())
                .ultimosMovimientos(ultimos3)
                .mensajeInformativo(mensaje)
                .build();
    }

    public Double consultarSaldoMovil(Long cuentaId) {
        return bancoService.obtenerCuentaPorId(cuentaId)
                .map(Cuenta::getSaldo)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con ID: " + cuentaId));
    }

    public TransaccionMovilDto realizarTransferenciaMovil(Long cuentaOrigenId, SolicitudTransferenciaMovilDto solicitud) {
        Transaccion debito = bancoService.procesarRetiro(cuentaOrigenId, solicitud.getMonto(), "MOVIL_APP", null);
        bancoService.procesarDeposito(solicitud.getCuentaDestinoId(), solicitud.getMonto(), "MOVIL_TRANSFER");
        return convertirTransaccionMovil(debito);
    }

    private TransaccionMovilDto convertirTransaccionMovil(Transaccion tx) {
        boolean esCargo = "retiro".equalsIgnoreCase(tx.getTipo()) || "debito".equalsIgnoreCase(tx.getTipo());
        double montoFinal = esCargo ? -Math.abs(tx.getMonto()) : Math.abs(tx.getMonto());

        return TransaccionMovilDto.builder()
                .id(tx.getId())
                .fecha(tx.getFecha())
                .monto(montoFinal)
                .tipo(tx.getTipo().toUpperCase())
                .detalle(tx.getDescripcion())
                .build();
    }
}
