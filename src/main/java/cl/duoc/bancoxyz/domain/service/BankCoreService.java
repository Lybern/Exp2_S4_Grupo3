package cl.duoc.bancoxyz.domain.service;

import cl.duoc.bancoxyz.domain.model.Cuenta;
import cl.duoc.bancoxyz.domain.model.MovimientoAnual;
import cl.duoc.bancoxyz.domain.model.Transaccion;
import cl.duoc.bancoxyz.domain.repository.BankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BankCoreService {

    private static final Logger log = LoggerFactory.getLogger(BankCoreService.class);
    private final BankRepository bankRepository;

    public BankCoreService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Optional<Cuenta> obtenerCuentaPorId(Long cuentaId) {
        return bankRepository.findCuentaById(cuentaId);
    }

    public List<Cuenta> obtenerTodasLasCuentas() {
        return bankRepository.findAllCuentas();
    }

    public List<Transaccion> obtenerTransaccionesPorCuenta(Long cuentaId) {
        return bankRepository.findTransaccionesByCuentaId(cuentaId);
    }

    public List<MovimientoAnual> obtenerMovimientosAnuales(Long cuentaId) {
        return bankRepository.findMovimientosAnualesByCuentaId(cuentaId);
    }

    public synchronized Transaccion procesarRetiro(Long cuentaId, Double monto, String canal, String codigoTerminal) {
        if (monto == null || monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a 0");
        }

        Cuenta cuenta = bankRepository.findCuentaById(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("La cuenta ID " + cuentaId + " no existe"));

        double saldoTotalDisponible = cuenta.getSaldo() + (cuenta.getLineaSobregiro() != null ? cuenta.getLineaSobregiro() : 0.0);
        if (saldoTotalDisponible < monto) {
            throw new IllegalStateException("Fondos insuficientes. Saldo disponible: $" + cuenta.getSaldo());
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto);
        bankRepository.saveCuenta(cuenta);

        Transaccion tx = Transaccion.builder()
                .cuentaId(cuentaId)
                .fecha(LocalDate.now().toString())
                .monto(monto)
                .tipo("RETIRO")
                .descripcion("Retiro de dinero vía " + canal + (codigoTerminal != null ? " (" + codigoTerminal + ")" : ""))
                .canalOrigen(canal)
                .build();

        bankRepository.saveTransaccion(tx);
        log.info("Retiro procesado: Cuenta={}, Monto={}, Canal={}, NuevoSaldo={}", cuentaId, monto, canal, cuenta.getSaldo());
        return tx;
    }

    public synchronized Transaccion procesarDeposito(Long cuentaId, Double monto, String canal) {
        if (monto == null || monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a 0");
        }

        Cuenta cuenta = bankRepository.findCuentaById(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("La cuenta ID " + cuentaId + " no existe"));

        cuenta.setSaldo(cuenta.getSaldo() + monto);
        bankRepository.saveCuenta(cuenta);

        Transaccion tx = Transaccion.builder()
                .cuentaId(cuentaId)
                .fecha(LocalDate.now().toString())
                .monto(monto)
                .tipo("DEPOSITO")
                .descripcion("Depósito de fondos vía " + canal)
                .canalOrigen(canal)
                .build();

        bankRepository.saveTransaccion(tx);
        log.info("Depósito procesado: Cuenta={}, Monto={}, Canal={}, NuevoSaldo={}", cuentaId, monto, canal, cuenta.getSaldo());
        return tx;
    }
}
