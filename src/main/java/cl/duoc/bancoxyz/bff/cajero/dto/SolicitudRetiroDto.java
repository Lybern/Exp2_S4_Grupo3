package cl.duoc.bancoxyz.bff.cajero.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de giro/retiro de dinero en efectivo en cajero")
public class SolicitudRetiroDto {

    @Schema(description = "Monto en efectivo a retirar (múltiplo de $5.000)", example = "40000.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double monto;

    @Schema(description = "Código identificador del terminal ATM", example = "ATM-CENTRO-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoTerminal;

    @Schema(description = "PIN secreto de 4 dígitos", example = "1234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pin;

    public SolicitudRetiroDto() {}

    public SolicitudRetiroDto(Double monto, String codigoTerminal, String pin) {
        this.monto = monto;
        this.codigoTerminal = codigoTerminal;
        this.pin = pin;
    }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public String getCodigoTerminal() { return codigoTerminal; }
    public void setCodigoTerminal(String codigoTerminal) { this.codigoTerminal = codigoTerminal; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
}
