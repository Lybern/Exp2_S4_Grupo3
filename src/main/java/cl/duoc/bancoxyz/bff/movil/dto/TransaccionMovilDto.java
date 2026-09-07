package cl.duoc.bancoxyz.bff.movil.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Transacción simplificada para vista móvil")
public class TransaccionMovilDto {

    @Schema(description = "ID de la transacción", example = "105")
    private Long id;

    @Schema(description = "Fecha de la operación", example = "2024-06-30")
    private String fecha;

    @Schema(description = "Monto con signo (+ abono, - cargo)", example = "-15000.0")
    private Double monto;

    @Schema(description = "Tipo de operación", example = "DEBITO")
    private String tipo;

    @Schema(description = "Detalle breve", example = "Operación registrada: credito")
    private String detalle;

    public TransaccionMovilDto() {
    }

    public TransaccionMovilDto(Long id, String fecha, Double monto, String tipo, String detalle) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.tipo = tipo;
        this.detalle = detalle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
