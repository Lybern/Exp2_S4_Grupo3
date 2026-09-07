package cl.duoc.bancoxyz.bff.cajero.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de depósito en efectivo en cajero")
public class SolicitudDepositoDto {

    @Schema(description = "Monto en efectivo a depositar", example = "50000.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double monto;

    @Schema(description = "Código del cajero automático", example = "ATM-CENTRO-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoTerminal;

    public SolicitudDepositoDto() {
    }

    public SolicitudDepositoDto(Double monto, String codigoTerminal) {
        this.monto = monto;
        this.codigoTerminal = codigoTerminal;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getCodigoTerminal() {
        return codigoTerminal;
    }

    public void setCodigoTerminal(String codigoTerminal) {
        this.codigoTerminal = codigoTerminal;
    }
}
