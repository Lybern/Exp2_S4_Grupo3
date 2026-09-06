package cl.duoc.bancoxyz.bff.mobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representación compacta de una transacción para pantallas móviles")
public class MobileTransactionResponse {

    @Schema(description = "ID de la transacción", example = "105")
    private Long id;

    @Schema(description = "Fecha de operación", example = "2024-06-30")
    private String fecha;

    @Schema(description = "Monto con signo según tipo (+ para abono, - para cargo)", example = "-15000.0")
    private Double monto;

    @Schema(description = "Tipo de movimiento resumido", example = "DEBITO")
    private String tipo;

    @Schema(description = "Detalle corto", example = "Operación registrada: credito")
    private String detalle;

    public MobileTransactionResponse() {}

    public MobileTransactionResponse(Long id, String fecha, Double monto, String tipo, String detalle) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.tipo = tipo;
        this.detalle = detalle;
    }

    public static MobileTransactionResponseBuilder builder() {
        return new MobileTransactionResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public static class MobileTransactionResponseBuilder {
        private Long id;
        private String fecha;
        private Double monto;
        private String tipo;
        private String detalle;

        public MobileTransactionResponseBuilder id(Long id) { this.id = id; return this; }
        public MobileTransactionResponseBuilder fecha(String fecha) { this.fecha = fecha; return this; }
        public MobileTransactionResponseBuilder monto(Double monto) { this.monto = monto; return this; }
        public MobileTransactionResponseBuilder tipo(String tipo) { this.tipo = tipo; return this; }
        public MobileTransactionResponseBuilder detalle(String detalle) { this.detalle = detalle; return this; }

        public MobileTransactionResponse build() {
            return new MobileTransactionResponse(id, fecha, monto, tipo, detalle);
        }
    }
}
