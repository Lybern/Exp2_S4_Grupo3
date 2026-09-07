package cl.duoc.bancoxyz.bff.cajero.service;

import cl.duoc.bancoxyz.bff.cajero.dto.RespuestaRetiroDto;
import cl.duoc.bancoxyz.bff.cajero.dto.SaldoCajeroDto;
import cl.duoc.bancoxyz.bff.cajero.dto.SolicitudDepositoDto;
import cl.duoc.bancoxyz.bff.cajero.dto.SolicitudRetiroDto;
import cl.duoc.bancoxyz.model.Cuenta;
import cl.duoc.bancoxyz.model.Transaccion;
import cl.duoc.bancoxyz.service.BancoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CajeroBffService {

    private static final double LIMITE_MAXIMO_GIRO_CAJERO = 200000.0;
    private final BancoService bancoService;

    public CajeroBffService(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    public SaldoCajeroDto consultarSaldoCajero(Long cuentaId, String terminalId) {
        Cuenta cuenta = bancoService.obtenerCuentaPorId(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarjeta o Cuenta no válida en cajero (ID: " + cuentaId + ")"));

        double saldoDisp = cuenta.getSaldo();
        double maxGiro = Math.min(LIMITE_MAXIMO_GIRO_CAJERO, saldoDisp);

        return SaldoCajeroDto.builder()
                .cuentaId(cuenta.getCuentaId())
                .saldoDisponible(saldoDisp)
                .montoMaximoRetiro(maxGiro)
                .codigoTerminal(terminalId != null ? terminalId : "ATM-TERMINAL-GENERIC")
                .fechaHora(LocalDateTime.now().toString())
                .build();
    }

    public RespuestaRetiroDto procesarRetiroCajero(Long cuentaId, SolicitudRetiroDto solicitud) {
        if (solicitud.getMonto() == null || solicitud.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a $0.");
        }

        // Regla física de cajero: billetes múltiplos de $5.000
        if (solicitud.getMonto() % 5000 != 0) {
            throw new IllegalArgumentException("El monto debe ser múltiplo de $5.000 para dispensación física de billetes.");
        }

        // Límite por giro
        if (solicitud.getMonto() > LIMITE_MAXIMO_GIRO_CAJERO) {
            throw new IllegalArgumentException("El monto supera el límite máximo permitido por giro en cajero ($" + LIMITE_MAXIMO_GIRO_CAJERO + ").");
        }

        // Validación de PIN
        if (solicitud.getPin() == null || solicitud.getPin().length() != 4) {
            throw new IllegalArgumentException("PIN de seguridad inválido. Debe contener exactamente 4 dígitos.");
        }

        String terminal = solicitud.getCodigoTerminal() != null ? solicitud.getCodigoTerminal() : "ATM-001";
        Transaccion tx = bancoService.procesarRetiro(cuentaId, solicitud.getMonto(), "CAJERO_ATM", terminal);
        Cuenta cuentaActualizada = bancoService.obtenerCuentaPorId(cuentaId).orElseThrow();

        String codigoAuth = "AUTH-ATM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return RespuestaRetiroDto.builder()
                .codigoAutorizacion(codigoAuth)
                .transaccionId(tx.getId())
                .cuentaId(cuentaId)
                .montoRetirado(solicitud.getMonto())
                .nuevoSaldo(cuentaActualizada.getSaldo())
                .dispensacionEfectivoPermitida(true)
                .mensajeRecibo("Retiro exitoso en terminal " + terminal + ". Retire su dinero y comprobante.")
                .build();
    }

    public RespuestaRetiroDto procesarDepositoCajero(Long cuentaId, SolicitudDepositoDto solicitud) {
        if (solicitud.getMonto() == null || solicitud.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a $0.");
        }

        String terminal = solicitud.getCodigoTerminal() != null ? solicitud.getCodigoTerminal() : "ATM-DEPOSIT-01";
        Transaccion tx = bancoService.procesarDeposito(cuentaId, solicitud.getMonto(), "CAJERO_DEPOSITO");
        Cuenta cuentaActualizada = bancoService.obtenerCuentaPorId(cuentaId).orElseThrow();

        String codigoAuth = "DEP-ATM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return RespuestaRetiroDto.builder()
                .codigoAutorizacion(codigoAuth)
                .transaccionId(tx.getId())
                .cuentaId(cuentaId)
                .montoRetirado(solicitud.getMonto())
                .nuevoSaldo(cuentaActualizada.getSaldo())
                .dispensacionEfectivoPermitida(false)
                .mensajeRecibo("Depósito recibido en terminal " + terminal + ". Fondos acreditados inmediatamente.")
                .build();
    }
}
