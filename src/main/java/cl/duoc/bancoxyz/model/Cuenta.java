package cl.duoc.bancoxyz.model;

public class Cuenta {
    private Long cuentaId;
    private String nombreTitular;
    private Double saldo;
    private Integer edad;
    private String tipo;
    private Double lineaSobregiro;
    private Double tasaInteres;
    private String estado;

    public Cuenta() {}

    public Cuenta(Long cuentaId, String nombreTitular, Double saldo, Integer edad, String tipo, Double lineaSobregiro, Double tasaInteres, String estado) {
        this.cuentaId = cuentaId;
        this.nombreTitular = nombreTitular;
        this.saldo = saldo;
        this.edad = edad;
        this.tipo = tipo;
        this.lineaSobregiro = lineaSobregiro;
        this.tasaInteres = tasaInteres;
        this.estado = estado;
    }

    public static CuentaBuilder builder() {
        return new CuentaBuilder();
    }

    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }
    public String getNombreTitular() { return nombreTitular; }
    public void setNombreTitular(String nombreTitular) { this.nombreTitular = nombreTitular; }
    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Double getLineaSobregiro() { return lineaSobregiro; }
    public void setLineaSobregiro(Double lineaSobregiro) { this.lineaSobregiro = lineaSobregiro; }
    public Double getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(Double tasaInteres) { this.tasaInteres = tasaInteres; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public static class CuentaBuilder {
        private Long cuentaId;
        private String nombreTitular;
        private Double saldo;
        private Integer edad;
        private String tipo;
        private Double lineaSobregiro;
        private Double tasaInteres;
        private String estado;

        public CuentaBuilder cuentaId(Long cuentaId) { this.cuentaId = cuentaId; return this; }
        public CuentaBuilder nombreTitular(String nombreTitular) { this.nombreTitular = nombreTitular; return this; }
        public CuentaBuilder saldo(Double saldo) { this.saldo = saldo; return this; }
        public CuentaBuilder edad(Integer edad) { this.edad = edad; return this; }
        public CuentaBuilder tipo(String tipo) { this.tipo = tipo; return this; }
        public CuentaBuilder lineaSobregiro(Double lineaSobregiro) { this.lineaSobregiro = lineaSobregiro; return this; }
        public CuentaBuilder tasaInteres(Double tasaInteres) { this.tasaInteres = tasaInteres; return this; }
        public CuentaBuilder estado(String estado) { this.estado = estado; return this; }

        public Cuenta build() {
            return new Cuenta(cuentaId, nombreTitular, saldo, edad, tipo, lineaSobregiro, tasaInteres, estado);
        }
    }
}
