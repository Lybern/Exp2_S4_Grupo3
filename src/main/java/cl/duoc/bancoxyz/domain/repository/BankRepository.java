package cl.duoc.bancoxyz.domain.repository;

import cl.duoc.bancoxyz.domain.model.Cuenta;
import cl.duoc.bancoxyz.domain.model.MovimientoAnual;
import cl.duoc.bancoxyz.domain.model.Transaccion;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class BankRepository {

    private final Map<Long, Cuenta> cuentas = new ConcurrentHashMap<>();
    private final List<Transaccion> transacciones = Collections.synchronizedList(new ArrayList<>());
    private final List<MovimientoAnual> movimientosAnuales = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong transaccionIdSeq = new AtomicLong(5000);

    public void saveCuenta(Cuenta cuenta) {
        cuentas.put(cuenta.getCuentaId(), cuenta);
    }

    public Optional<Cuenta> findCuentaById(Long cuentaId) {
        return Optional.ofNullable(cuentas.get(cuentaId));
    }

    public List<Cuenta> findAllCuentas() {
        return new ArrayList<>(cuentas.values());
    }

    public void saveTransaccion(Transaccion transaccion) {
        if (transaccion.getId() == null) {
            transaccion.setId(transaccionIdSeq.incrementAndGet());
        }
        transacciones.add(transaccion);
    }

    public List<Transaccion> findTransaccionesByCuentaId(Long cuentaId) {
        return transacciones.stream()
                .filter(t -> t.getCuentaId() != null && t.getCuentaId().equals(cuentaId))
                .sorted(Comparator.comparing(Transaccion::getId, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    public void saveMovimientoAnual(MovimientoAnual movimiento) {
        movimientosAnuales.add(movimiento);
    }

    public List<MovimientoAnual> findMovimientosAnualesByCuentaId(Long cuentaId) {
        return movimientosAnuales.stream()
                .filter(m -> m.getCuentaId() != null && m.getCuentaId().equals(cuentaId))
                .collect(Collectors.toList());
    }

    public boolean existsCuenta(Long cuentaId) {
        return cuentas.containsKey(cuentaId);
    }
}
