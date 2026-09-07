package cl.duoc.bancoxyz.bff.cajero.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Consulta de saldo rápida y segura para Cajeros Automáticos (ATM)")
public class SaldoCajeroDto {

    @Schema(description = "Número de cuenta", example = "101")
    private Long cuentaId;

    @Schema(description = "Saldo disponible para retiro inmediato", example = "70000.0")
    private Double saldoDisponible;

    @Schema(description = "Monto máximo permitido para retiro en esta operación", example = "200000.0")
    private Double montoMaximoRetiro;

    @Schema(description = "Identificador del cajero o terminal físico", example = "ATM-SANTIAGO-042")
    private String codigoTerminal;

    @Schema(description = "Marca temporal de la consulta", example = "2026-09-06T20:15:00")
    private String fechaHora;

    public SaldoCajeroDto() {}

    public SaldoCajeroDto(Long cuentaId, Double saldoDisponible, Double montoMaximoRetiro, String codigoTerminal, String fechaHora) {
        this.cuentaId = cuentaId;
        this.saldoDisponible = saldoDisponible;
        this.montoMaximoRetiro = montoMaximoRetiro;
        this.codigoTerminal = codigoTerminal;
        this.fechaHora = fechaHora;
    }

    public static SaldoCajeroDtoBuilder builder() {
        return new SaldoCajeroDtoBuilder();
    }

    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }
    public Double getSaldoDisponible() { return saldoDisponible; }
    public void setSaldoDisponible(Double saldoDisponible) { this.saldoDisponible = saldoDisponible; }
    public Double getMontoMaximoRetiro() { return montoMaximoRetiro; }
    public void setMontoMaximoRetiro(Double montoMaximoRetiro) { this.montoMaximoRetiro = montoMaximoRetiro; }
    public String getCodigoTerminal() { return codigoTerminal; }
    public void setCodigoTerminal(String codigoTerminal) { this.codigoTerminal = codigoTerminal; }
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public static class SaldoCajeroDtoBuilder {
        private Long cuentaId;
        private Double saldoDisponible;
        private Double montoMaximoRetiro;
        private String codigoTerminal;
        private String fechaHora;

        public SaldoCajeroDtoBuilder cuentaId(Long cuentaId) { this.cuentaId = cuentaId; return this; }
        public SaldoCajeroDtoBuilder saldoDisponible(Double saldoDisponible) { this.saldoDisponible = saldoDisponible; return this; }
        public SaldoCajeroDtoBuilder montoMaximoRetiro(Double montoMaximoRetiro) { this.montoMaximoRetiro = montoMaximoRetiro; return this; }
        public SaldoCajeroDtoBuilder codigoTerminal(String codigoTerminal) { this.codigoTerminal = codigoTerminal; return this; }
        public SaldoCajeroDtoBuilder fechaHora(String fechaHora) { this.fechaHora = fechaHora; return this; }

        public SaldoCajeroDto build() {
            return new SaldoCajeroDto(cuentaId, saldoDisponible, montoMaximoRetiro, codigoTerminal, fechaHora);
        }
    }
}
