package cl.duoc.bancoxyz.bff.mobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de transferencia o pago rápido móvil")
public class MobileTransferRequest {

    @Schema(description = "Monto a transferir", example = "15000.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double monto;

    @Schema(description = "Cuenta destino", example = "105", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long cuentaDestinoId;

    @Schema(description = "Comentario opcional", example = "Pago almuerzo")
    private String mensaje;

    public MobileTransferRequest() {}

    public MobileTransferRequest(Double monto, Long cuentaDestinoId, String mensaje) {
        this.monto = monto;
        this.cuentaDestinoId = cuentaDestinoId;
        this.mensaje = mensaje;
    }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public Long getCuentaDestinoId() { return cuentaDestinoId; }
    public void setCuentaDestinoId(Long cuentaDestinoId) { this.cuentaDestinoId = cuentaDestinoId; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
