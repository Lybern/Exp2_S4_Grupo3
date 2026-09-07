package cl.duoc.bancoxyz.bff.movil.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Resumen de cuenta optimizado para app móvil (payload ligero)")
public class ResumenCuentaMovilDto {

    @Schema(description = "Número de cuenta", example = "101")
    private Long numeroCuenta;

    @Schema(description = "Nombre del titular", example = "Bob Johnson")
    private String titular;

    @Schema(description = "Saldo disponible", example = "75000.50")
    private Double saldoDisponible;

    @Schema(description = "Tipo de producto bancario", example = "AHORRO")
    private String tipoCuenta;

    @Schema(description = "Últimos movimientos recientes (solo los 3 más nuevos para ahorrar datos)")
    private List<TransaccionMovilDto> ultimosMovimientos;

    @Schema(description = "Mensaje o alerta móvil", example = "Tu cuenta acumula 3.8% de interés anual")
    private String mensajeInformativo;

    public ResumenCuentaMovilDto() {
    }

    public ResumenCuentaMovilDto(Long numeroCuenta, String titular, Double saldoDisponible, String tipoCuenta, List<TransaccionMovilDto> ultimosMovimientos, String mensajeInformativo) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldoDisponible = saldoDisponible;
        this.tipoCuenta = tipoCuenta;
        this.ultimosMovimientos = ultimosMovimientos;
        this.mensajeInformativo = mensajeInformativo;
    }

    public Long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(Double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public List<TransaccionMovilDto> getUltimosMovimientos() {
        return ultimosMovimientos;
    }

    public void setUltimosMovimientos(List<TransaccionMovilDto> ultimosMovimientos) {
        this.ultimosMovimientos = ultimosMovimientos;
    }

    public String getMensajeInformativo() {
        return mensajeInformativo;
    }

    public void setMensajeInformativo(String mensajeInformativo) {
        this.mensajeInformativo = mensajeInformativo;
    }
}
