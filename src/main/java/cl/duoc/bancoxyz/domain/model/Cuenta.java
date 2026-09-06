package cl.duoc.bancoxyz.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    private Long cuentaId;
    private String nombreTitular;
    private Double saldo;
    private Integer edad;
    private String tipo;
    private Double lineaSobregiro;
    private Double tasaInteres;
    private String estado;
}
