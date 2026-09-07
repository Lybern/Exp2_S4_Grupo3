package cl.duoc.bancoxyz.bff.cajero.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Comprobante y autorización de dispensación de efectivo en cajero")
public class RespuestaRetiroDto {

    @Schema(description = "Código de autorización para el dispensador de billetes", example = "AUTH-ATM-982145")
    private String codigoAutorizacion;

    @Schema(description = "ID único de la transacción", example = "5001")
    private Long transaccionId;

    @Schema(description = "Número de cuenta", example = "101")
    private Long cuentaId;

    @Schema(description = "Monto retirado", example = "40000.0")
    private Double montoRetirado;

    @Schema(description = "Nuevo saldo remanente", example = "30000.0")
    private Double nuevoSaldo;

    @Schema(description = "Indica si el giro de dinero fue autorizado", example = "true")
    private Boolean retiroAutorizado;

    @Schema(description = "Mensaje impreso en el comprobante del cajero", example = "Retiro exitoso. Por favor retire su dinero y comprobante.")
    private String mensajeRecibo;

    public RespuestaRetiroDto() {
    }

    public RespuestaRetiroDto(String codigoAutorizacion, Long transaccionId, Long cuentaId, Double montoRetirado, Double nuevoSaldo, Boolean retiroAutorizado, String mensajeRecibo) {
        this.codigoAutorizacion = codigoAutorizacion;
        this.transaccionId = transaccionId;
        this.cuentaId = cuentaId;
        this.montoRetirado = montoRetirado;
        this.nuevoSaldo = nuevoSaldo;
        this.retiroAutorizado = retiroAutorizado;
        this.mensajeRecibo = mensajeRecibo;
    }

    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    public Long getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Long transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Double getMontoRetirado() {
        return montoRetirado;
    }

    public void setMontoRetirado(Double montoRetirado) {
        this.montoRetirado = montoRetirado;
    }

    public Double getNuevoSaldo() {
        return nuevoSaldo;
    }

    public void setNuevoSaldo(Double nuevoSaldo) {
        this.nuevoSaldo = nuevoSaldo;
    }

    public Boolean getRetiroAutorizado() {
        return retiroAutorizado;
    }

    public void setRetiroAutorizado(Boolean retiroAutorizado) {
        this.retiroAutorizado = retiroAutorizado;
    }

    public String getMensajeRecibo() {
        return mensajeRecibo;
    }

    public void setMensajeRecibo(String mensajeRecibo) {
        this.mensajeRecibo = mensajeRecibo;
    }
}
