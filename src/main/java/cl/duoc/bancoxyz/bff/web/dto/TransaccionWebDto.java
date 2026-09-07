package cl.duoc.bancoxyz.bff.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalle completo de transacción para portal web")
public class TransaccionWebDto {

    @Schema(description = "ID único de la transacción", example = "105")
    private Long id;

    @Schema(description = "Fecha de la transacción", example = "2024-06-30")
    private String fecha;

    @Schema(description = "Monto de la transacción", example = "30000.0")
    private Double monto;

    @Schema(description = "Tipo de operación", example = "CREDITO")
    private String tipo;

    @Schema(description = "Descripción detallada", example = "Operación registrada: credito")
    private String descripcion;

    @Schema(description = "Canal de origen", example = "LEGACY_BATCH")
    private String canalOrigen;

    @Schema(description = "Categoría financiera calculada", example = "INGRESO_FONDOS")
    private String categoria;

    public TransaccionWebDto() {}

    public TransaccionWebDto(Long id, String fecha, Double monto, String tipo, String descripcion, String canalOrigen, String categoria) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.canalOrigen = canalOrigen;
        this.categoria = categoria;
    }

    public static TransaccionWebDtoBuilder builder() {
        return new TransaccionWebDtoBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCanalOrigen() { return canalOrigen; }
    public void setCanalOrigen(String canalOrigen) { this.canalOrigen = canalOrigen; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public static class TransaccionWebDtoBuilder {
        private Long id;
        private String fecha;
        private Double monto;
        private String tipo;
        private String descripcion;
        private String canalOrigen;
        private String categoria;

        public TransaccionWebDtoBuilder id(Long id) { this.id = id; return this; }
        public TransaccionWebDtoBuilder fecha(String fecha) { this.fecha = fecha; return this; }
        public TransaccionWebDtoBuilder monto(Double monto) { this.monto = monto; return this; }
        public TransaccionWebDtoBuilder tipo(String tipo) { this.tipo = tipo; return this; }
        public TransaccionWebDtoBuilder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public TransaccionWebDtoBuilder canalOrigen(String canalOrigen) { this.canalOrigen = canalOrigen; return this; }
        public TransaccionWebDtoBuilder categoria(String categoria) { this.categoria = categoria; return this; }

        public TransaccionWebDto build() {
            return new TransaccionWebDto(id, fecha, monto, tipo, descripcion, canalOrigen, categoria);
        }
    }
}
