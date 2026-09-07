package cl.duoc.bancoxyz.bff.cajero.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Comprobante de retiro exitoso en cajero automático")
public class RespuestaRetiroDto {

    @Schema(description = "ID de la cuenta", example = "101")
    private Long cuentaId;

    @Schema(description = "Monto entregado en pesos", example = "40000")
    private Long montoRetirado;

    @Schema(description = "Nuevo saldo remanente", example = "110000")
    private Long nuevoSaldo;

    @Schema(description = "Código de autorización bancaria", example = "AUTH-ATM-98231")
    private String codigoAutorizacion;

    @Schema(description = "Identificador de la transacción registrada", example = "5021")
    private Long transaccionId;

    @Schema(description = "Indica si el retiro fue autorizado exitosamente", example = "true")
    private Boolean retiroAutorizado;

    @Schema(description = "Texto formateado para el recibo físico", example = "Retiro exitoso por $40.000. Por favor retire su dinero.")
    private String mensajeTicket;

    public RespuestaRetiroDto() {
    }

    public RespuestaRetiroDto(Long cuentaId, Long montoRetirado, Long nuevoSaldo, String codigoAutorizacion, Long transaccionId, Boolean retiroAutorizado, String mensajeTicket) {
        this.cuentaId = cuentaId;
        this.montoRetirado = montoRetirado;
        this.nuevoSaldo = nuevoSaldo;
        this.codigoAutorizacion = codigoAutorizacion;
        this.transaccionId = transaccionId;
        this.retiroAutorizado = retiroAutorizado;
        this.mensajeTicket = mensajeTicket;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Long getMontoRetirado() {
        return montoRetirado;
    }

    public void setMontoRetirado(Long montoRetirado) {
        this.montoRetirado = montoRetirado;
    }

    public Long getNuevoSaldo() {
        return nuevoSaldo;
    }

    public void setNuevoSaldo(Long nuevoSaldo) {
        this.nuevoSaldo = nuevoSaldo;
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

    public Boolean getRetiroAutorizado() {
        return retiroAutorizado;
    }

    public void setRetiroAutorizado(Boolean retiroAutorizado) {
        this.retiroAutorizado = retiroAutorizado;
    }

    public String getMensajeTicket() {
        return mensajeTicket;
    }

    public void setMensajeTicket(String mensajeTicket) {
        this.mensajeTicket = mensajeTicket;
    }
}
