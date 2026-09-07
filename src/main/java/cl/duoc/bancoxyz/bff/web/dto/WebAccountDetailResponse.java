package cl.duoc.bancoxyz.bff.web.dto;

import cl.duoc.bancoxyz.domain.model.MovimientoAnual;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

@Schema(description = "Vista enriquecida y completa de la cuenta para navegadores web")
public class WebAccountDetailResponse {

    @Schema(description = "Identificador único de la cuenta", example = "101")
    private Long cuentaId;

    @Schema(description = "Nombre completo del titular", example = "Bob Johnson")
    private String nombreTitular;

    @Schema(description = "Edad del cliente titular", example = "42")
    private Integer edadTitular;

    @Schema(description = "Tipo de producto o cuenta bancaria", example = "CUENTA_CORRIENTE")
    private String tipoCuenta;

    @Schema(description = "Saldo contable en cuenta", example = "7000.0")
    private Double saldoContable;

    @Schema(description = "Línea de sobregiro autorizada", example = "300000.0")
    private Double lineaSobregiro;

    @Schema(description = "Saldo total disponible (Saldo + Sobregiro)", example = "307000.0")
    private Double saldoTotalDisponible;

    @Schema(description = "Tasa de interés anual pactada (%)", example = "3.8")
    private Double tasaInteresAnual;

    @Schema(description = "Interés mensual proyectado estimado", example = "22.16")
    private Double interesMensualEstimado;

    @Schema(description = "Estado operativo de la cuenta", example = "ACTIVA")
    private String estadoCuenta;

    @Schema(description = "Total histórico de transacciones registradas", example = "15")
    private Integer totalTransacciones;

    @Schema(description = "Historial completo de transacciones")
    private List<WebTransactionDetailResponse> historialTransacciones;

    @Schema(description = "Historial consolidado de movimientos anuales")
    private List<MovimientoAnual> historialAnual;

    @Schema(description = "Metadatos técnicos para la interfaz web (paginación, timestamp, entorno)")
    private Map<String, Object> metadatosWeb;

    public WebAccountDetailResponse() {}

    public WebAccountDetailResponse(Long cuentaId, String nombreTitular, Integer edadTitular, String tipoCuenta, Double saldoContable, Double lineaSobregiro, Double saldoTotalDisponible, Double tasaInteresAnual, Double interesMensualEstimado, String estadoCuenta, Integer totalTransacciones, List<WebTransactionDetailResponse> historialTransacciones, List<MovimientoAnual> historialAnual, Map<String, Object> metadatosWeb) {
        this.cuentaId = cuentaId;
        this.nombreTitular = nombreTitular;
        this.edadTitular = edadTitular;
        this.tipoCuenta = tipoCuenta;
        this.saldoContable = saldoContable;
        this.lineaSobregiro = lineaSobregiro;
        this.saldoTotalDisponible = saldoTotalDisponible;
        this.tasaInteresAnual = tasaInteresAnual;
        this.interesMensualEstimado = interesMensualEstimado;
        this.estadoCuenta = estadoCuenta;
        this.totalTransacciones = totalTransacciones;
        this.historialTransacciones = historialTransacciones;
        this.historialAnual = historialAnual;
        this.metadatosWeb = metadatosWeb;
    }

    public static WebAccountDetailResponseBuilder builder() {
        return new WebAccountDetailResponseBuilder();
    }

    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }
    public String getNombreTitular() { return nombreTitular; }
    public void setNombreTitular(String nombreTitular) { this.nombreTitular = nombreTitular; }
    public Integer getEdadTitular() { return edadTitular; }
    public void setEdadTitular(Integer edadTitular) { this.edadTitular = edadTitular; }
    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }
    public Double getSaldoContable() { return saldoContable; }
    public void setSaldoContable(Double saldoContable) { this.saldoContable = saldoContable; }
    public Double getLineaSobregiro() { return lineaSobregiro; }
    public void setLineaSobregiro(Double lineaSobregiro) { this.lineaSobregiro = lineaSobregiro; }
    public Double getSaldoTotalDisponible() { return saldoTotalDisponible; }
    public void setSaldoTotalDisponible(Double saldoTotalDisponible) { this.saldoTotalDisponible = saldoTotalDisponible; }
    public Double getTasaInteresAnual() { return tasaInteresAnual; }
    public void setTasaInteresAnual(Double tasaInteresAnual) { this.tasaInteresAnual = tasaInteresAnual; }
    public Double getInteresMensualEstimado() { return interesMensualEstimado; }
    public void setInteresMensualEstimado(Double interesMensualEstimado) { this.interesMensualEstimado = interesMensualEstimado; }
    public String getEstadoCuenta() { return estadoCuenta; }
    public void setEstadoCuenta(String estadoCuenta) { this.estadoCuenta = estadoCuenta; }
    public Integer getTotalTransacciones() { return totalTransacciones; }
    public void setTotalTransacciones(Integer totalTransacciones) { this.totalTransacciones = totalTransacciones; }
    public List<WebTransactionDetailResponse> getHistorialTransacciones() { return historialTransacciones; }
    public void setHistorialTransacciones(List<WebTransactionDetailResponse> historialTransacciones) { this.historialTransacciones = historialTransacciones; }
    public List<MovimientoAnual> getHistorialAnual() { return historialAnual; }
    public void setHistorialAnual(List<MovimientoAnual> historialAnual) { this.historialAnual = historialAnual; }
    public Map<String, Object> getMetadatosWeb() { return metadatosWeb; }
    public void setMetadatosWeb(Map<String, Object> metadatosWeb) { this.metadatosWeb = metadatosWeb; }

    public static class WebAccountDetailResponseBuilder {
        private Long cuentaId;
        private String nombreTitular;
        private Integer edadTitular;
        private String tipoCuenta;
        private Double saldoContable;
        private Double lineaSobregiro;
        private Double saldoTotalDisponible;
        private Double tasaInteresAnual;
        private Double interesMensualEstimado;
        private String estadoCuenta;
        private Integer totalTransacciones;
        private List<WebTransactionDetailResponse> historialTransacciones;
        private List<MovimientoAnual> historialAnual;
        private Map<String, Object> metadatosWeb;

        public WebAccountDetailResponseBuilder cuentaId(Long cuentaId) { this.cuentaId = cuentaId; return this; }
        public WebAccountDetailResponseBuilder nombreTitular(String nombreTitular) { this.nombreTitular = nombreTitular; return this; }
        public WebAccountDetailResponseBuilder edadTitular(Integer edadTitular) { this.edadTitular = edadTitular; return this; }
        public WebAccountDetailResponseBuilder tipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; return this; }
        public WebAccountDetailResponseBuilder saldoContable(Double saldoContable) { this.saldoContable = saldoContable; return this; }
        public WebAccountDetailResponseBuilder lineaSobregiro(Double lineaSobregiro) { this.lineaSobregiro = lineaSobregiro; return this; }
        public WebAccountDetailResponseBuilder saldoTotalDisponible(Double saldoTotalDisponible) { this.saldoTotalDisponible = saldoTotalDisponible; return this; }
        public WebAccountDetailResponseBuilder tasaInteresAnual(Double tasaInteresAnual) { this.tasaInteresAnual = tasaInteresAnual; return this; }
        public WebAccountDetailResponseBuilder interesMensualEstimado(Double interesMensualEstimado) { this.interesMensualEstimado = interesMensualEstimado; return this; }
        public WebAccountDetailResponseBuilder estadoCuenta(String estadoCuenta) { this.estadoCuenta = estadoCuenta; return this; }
        public WebAccountDetailResponseBuilder totalTransacciones(Integer totalTransacciones) { this.totalTransacciones = totalTransacciones; return this; }
        public WebAccountDetailResponseBuilder historialTransacciones(List<WebTransactionDetailResponse> historialTransacciones) { this.historialTransacciones = historialTransacciones; return this; }
        public WebAccountDetailResponseBuilder historialAnual(List<MovimientoAnual> historialAnual) { this.historialAnual = historialAnual; return this; }
        public WebAccountDetailResponseBuilder metadatosWeb(Map<String, Object> metadatosWeb) { this.metadatosWeb = metadatosWeb; return this; }

        public WebAccountDetailResponse build() {
            return new WebAccountDetailResponse(cuentaId, nombreTitular, edadTitular, tipoCuenta, saldoContable, lineaSobregiro, saldoTotalDisponible, tasaInteresAnual, interesMensualEstimado, estadoCuenta, totalTransacciones, historialTransacciones, historialAnual, metadatosWeb);
        }
    }
}
