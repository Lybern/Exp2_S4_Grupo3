package cl.duoc.bancoxyz.bff.movil.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para realizar una transferencia rápida desde la app móvil")
public class SolicitudTransferenciaMovilDto {

    @Schema(description = "Monto a transferir", example = "15000.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double monto;

    @Schema(description = "Número de cuenta destinataria", example = "105", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long cuentaDestinoId;

    @Schema(description = "Comentario opcional", example = "Pago almuerzo")
    private String comentario;

    public SolicitudTransferenciaMovilDto() {}

    public SolicitudTransferenciaMovilDto(Double monto, Long cuentaDestinoId, String comentario) {
        this.monto = monto;
        this.cuentaDestinoId = cuentaDestinoId;
        this.comentario = comentario;
    }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public Long getCuentaDestinoId() { return cuentaDestinoId; }
    public void setCuentaDestinoId(Long cuentaDestinoId) { this.cuentaDestinoId = cuentaDestinoId; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
