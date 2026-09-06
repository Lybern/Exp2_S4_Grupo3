package cl.duoc.bancoxyz.bff.mobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Resumen de cuenta optimizado para aplicación móvil (bajo consumo de datos)")
public class MobileAccountSummaryResponse {

    @Schema(description = "Número de cuenta bancaria", example = "101")
    private Long numeroCuenta;

    @Schema(description = "Nombre abreviado del titular", example = "Bob Johnson")
    private String titular;

    @Schema(description = "Saldo disponible para uso inmediato", example = "75000.50")
    private Double saldoDisponible;

    @Schema(description = "Tipo de cuenta", example = "AHORRO")
    private String tipoCuenta;

    @Schema(description = "Últimas transacciones recientes (máximo 3, para reducir payload)")
    private List<MobileTransactionResponse> ultimosMovimientos;

    @Schema(description = "Aviso o notificación importante para la app móvil", example = "Tu cuenta acumula 3.8% de interés anual")
    private String notificacion;

    public MobileAccountSummaryResponse() {}

    public MobileAccountSummaryResponse(Long numeroCuenta, String titular, Double saldoDisponible, String tipoCuenta, List<MobileTransactionResponse> ultimosMovimientos, String notificacion) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldoDisponible = saldoDisponible;
        this.tipoCuenta = tipoCuenta;
        this.ultimosMovimientos = ultimosMovimientos;
        this.notificacion = notificacion;
    }

    public static MobileAccountSummaryResponseBuilder builder() {
        return new MobileAccountSummaryResponseBuilder();
    }

    public Long getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(Long numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public Double getSaldoDisponible() { return saldoDisponible; }
    public void setSaldoDisponible(Double saldoDisponible) { this.saldoDisponible = saldoDisponible; }
    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }
    public List<MobileTransactionResponse> getUltimosMovimientos() { return ultimosMovimientos; }
    public void setUltimosMovimientos(List<MobileTransactionResponse> ultimosMovimientos) { this.ultimosMovimientos = ultimosMovimientos; }
    public String getNotificacion() { return notificacion; }
    public void setNotificacion(String notificacion) { this.notificacion = notificacion; }

    public static class MobileAccountSummaryResponseBuilder {
        private Long numeroCuenta;
        private String titular;
        private Double saldoDisponible;
        private String tipoCuenta;
        private List<MobileTransactionResponse> ultimosMovimientos;
        private String notificacion;

        public MobileAccountSummaryResponseBuilder numeroCuenta(Long numeroCuenta) { this.numeroCuenta = numeroCuenta; return this; }
        public MobileAccountSummaryResponseBuilder titular(String titular) { this.titular = titular; return this; }
        public MobileAccountSummaryResponseBuilder saldoDisponible(Double saldoDisponible) { this.saldoDisponible = saldoDisponible; return this; }
        public MobileAccountSummaryResponseBuilder tipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; return this; }
        public MobileAccountSummaryResponseBuilder ultimosMovimientos(List<MobileTransactionResponse> ultimosMovimientos) { this.ultimosMovimientos = ultimosMovimientos; return this; }
        public MobileAccountSummaryResponseBuilder notificacion(String notificacion) { this.notificacion = notificacion; return this; }

        public MobileAccountSummaryResponse build() {
            return new MobileAccountSummaryResponse(numeroCuenta, titular, saldoDisponible, tipoCuenta, ultimosMovimientos, notificacion);
        }
    }
}
