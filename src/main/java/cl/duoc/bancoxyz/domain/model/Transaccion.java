package cl.duoc.bancoxyz.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {
    private Long id;
    private Long cuentaId;
    private String fecha;
    private Double monto;
    private String tipo;
    private String descripcion;
    private String canalOrigen;
}
