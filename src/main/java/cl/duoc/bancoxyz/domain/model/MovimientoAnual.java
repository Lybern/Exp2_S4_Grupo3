package cl.duoc.bancoxyz.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoAnual {
    private Long cuentaId;
    private String fecha;
    private String transaccion;
    private Double monto;
    private String descripcion;
}
