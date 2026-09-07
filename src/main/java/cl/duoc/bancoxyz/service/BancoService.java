package cl.duoc.bancoxyz.service;

import cl.duoc.bancoxyz.model.Cuenta;
import cl.duoc.bancoxyz.model.MovimientoAnual;
import cl.duoc.bancoxyz.model.Transaccion;
import cl.duoc.bancoxyz.repository.BancoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BancoService {

    private static final Logger log = LoggerFactory.getLogger(BancoService.class);
    private final BancoRepository bancoRepository;

    public BancoService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    public Optional<Cuenta> obtenerCuentaPorId(Long cuentaId) {
        return bancoRepository.buscarCuentaPorId(cuentaId);
    }

    public List<Cuenta> obtenerTodasLasCuentas() {
        return bancoRepository.obtenerTodasLasCuentas();
    }

    public List<Transaccion> obtenerTransaccionesPorCuenta(Long cuentaId) {
        return bancoRepository.buscarTransaccionesPorCuenta(cuentaId);
    }

    public List<MovimientoAnual> obtenerMovimientosAnuales(Long cuentaId) {
        return bancoRepository.buscarMovimientosAnualesPorCuenta(cuentaId);
    }

    public synchronized Transaccion procesarRetiro(Long cuentaId, Double monto, String canal, String detalleTerminal) {
        if (monto == null || monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a $0.");
        }

        Cuenta cuenta = bancoRepository.buscarCuentaPorId(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("La cuenta número " + cuentaId + " no existe."));

        double saldoDisponibleTotal = cuenta.getSaldo() + (cuenta.getLineaSobregiro() != null ? cuenta.getLineaSobregiro() : 0.0);
        if (saldoDisponibleTotal < monto) {
            throw new IllegalStateException("Fondos insuficientes. Saldo actual: $" + cuenta.getSaldo());
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto);
        bancoRepository.guardarCuenta(cuenta);

        Transaccion tx = new Transaccion(
                null,
                cuentaId,
                LocalDate.now().toString(),
                monto,
                "RETIRO",
                "Retiro de fondos por canal " + canal + (detalleTerminal != null ? " (" + detalleTerminal + ")" : ""),
                canal
        );

        bancoRepository.guardarTransaccion(tx);
        log.info("Retiro procesado: Cuenta={}, Monto=${}, Canal={}, NuevoSaldo=${}", cuentaId, monto, canal, cuenta.getSaldo());
        return tx;
    }

    public synchronized Transaccion procesarDeposito(Long cuentaId, Double monto, String canal) {
        if (monto == null || monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a $0.");
        }

        Cuenta cuenta = bancoRepository.buscarCuentaPorId(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("La cuenta número " + cuentaId + " no existe."));

        cuenta.setSaldo(cuenta.getSaldo() + monto);
        bancoRepository.guardarCuenta(cuenta);

        Transaccion tx = new Transaccion(
                null,
                cuentaId,
                LocalDate.now().toString(),
                monto,
                "DEPOSITO",
                "Depósito de fondos por canal " + canal,
                canal
        );

        bancoRepository.guardarTransaccion(tx);
        log.info("Depósito procesado: Cuenta={}, Monto=${}, Canal={}, NuevoSaldo=${}", cuentaId, monto, canal, cuenta.getSaldo());
        return tx;
    }
}
