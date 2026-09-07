package cl.duoc.bancoxyz.model;

public class MovimientoAnual {
    private Long cuentaId;
    private String fecha;
    private String transaccion;
    private Double monto;
    private String descripcion;

    public MovimientoAnual() {}

    public MovimientoAnual(Long cuentaId, String fecha, String transaccion, Double monto, String descripcion) {
        this.cuentaId = cuentaId;
        this.fecha = fecha;
        this.transaccion = transaccion;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public static MovimientoAnualBuilder builder() {
        return new MovimientoAnualBuilder();
    }

    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getTransaccion() { return transaccion; }
    public void setTransaccion(String transaccion) { this.transaccion = transaccion; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public static class MovimientoAnualBuilder {
        private Long cuentaId;
        private String fecha;
        private String transaccion;
        private Double monto;
        private String descripcion;

        public MovimientoAnualBuilder cuentaId(Long cuentaId) { this.cuentaId = cuentaId; return this; }
        public MovimientoAnualBuilder fecha(String fecha) { this.fecha = fecha; return this; }
        public MovimientoAnualBuilder transaccion(String transaccion) { this.transaccion = transaccion; return this; }
        public MovimientoAnualBuilder monto(Double monto) { this.monto = monto; return this; }
        public MovimientoAnualBuilder descripcion(String descripcion) { this.descripcion = descripcion; return this; }

        public MovimientoAnual build() {
            return new MovimientoAnual(cuentaId, fecha, transaccion, monto, descripcion);
        }
    }
}
