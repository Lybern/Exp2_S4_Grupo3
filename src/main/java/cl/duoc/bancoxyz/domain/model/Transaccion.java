package cl.duoc.bancoxyz.domain.model;

public class Transaccion {
    private Long id;
    private Long cuentaId;
    private String fecha;
    private Double monto;
    private String tipo;
    private String descripcion;
    private String canalOrigen;

    public Transaccion() {}

    public Transaccion(Long id, Long cuentaId, String fecha, Double monto, String tipo, String descripcion, String canalOrigen) {
        this.id = id;
        this.cuentaId = cuentaId;
        this.fecha = fecha;
        this.monto = monto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.canalOrigen = canalOrigen;
    }

    public static TransaccionBuilder builder() {
        return new TransaccionBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }
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

    public static class TransaccionBuilder {
        private Long id;
        private Long cuentaId;
        private String fecha;
        private Double monto;
        private String tipo;
        private String descripcion;
        private String canalOrigen;

        public TransaccionBuilder id(Long id) { this.id = id; return this; }
        public TransaccionBuilder cuentaId(Long cuentaId) { this.cuentaId = cuentaId; return this; }
        public TransaccionBuilder fecha(String fecha) { this.fecha = fecha; return this; }
        public TransaccionBuilder monto(Double monto) { this.monto = monto; return this; }
        public TransaccionBuilder tipo(String tipo) { this.tipo = tipo; return this; }
        public TransaccionBuilder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public TransaccionBuilder canalOrigen(String canalOrigen) { this.canalOrigen = canalOrigen; return this; }

        public Transaccion build() {
            return new Transaccion(id, cuentaId, fecha, monto, tipo, descripcion, canalOrigen);
        }
    }
}
