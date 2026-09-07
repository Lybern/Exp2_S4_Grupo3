package cl.duoc.bancoxyz.bff.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "Métricas consolidadas para el Dashboard Administrativo Web")
public class DashboardWebDto {

    @Schema(description = "Total de cuentas administradas", example = "100")
    private Integer totalCuentas;

    @Schema(description = "Monto total custodiado en el banco", example = "15000000.0")
    private Double capitalTotalCustodiado;

    @Schema(description = "Promedio de saldo por cuenta", example = "150000.0")
    private Double saldoPromedio;

    @Schema(description = "Distribución de cuentas por tipo")
    private Map<String, Long> distribucionPorTipo;

    public DashboardWebDto() {
    }

    public DashboardWebDto(Integer totalCuentas, Double capitalTotalCustodiado, Double saldoPromedio, Map<String, Long> distribucionPorTipo) {
        this.totalCuentas = totalCuentas;
        this.capitalTotalCustodiado = capitalTotalCustodiado;
        this.saldoPromedio = saldoPromedio;
        this.distribucionPorTipo = distribucionPorTipo;
    }

    public Integer getTotalCuentas() {
        return totalCuentas;
    }

    public void setTotalCuentas(Integer totalCuentas) {
        this.totalCuentas = totalCuentas;
    }

    public Double getCapitalTotalCustodiado() {
        return capitalTotalCustodiado;
    }

    public void setCapitalTotalCustodiado(Double capitalTotalCustodiado) {
        this.capitalTotalCustodiado = capitalTotalCustodiado;
    }

    public Double getSaldoPromedio() {
        return saldoPromedio;
    }

    public void setSaldoPromedio(Double saldoPromedio) {
        this.saldoPromedio = saldoPromedio;
    }

    public Map<String, Long> getDistribucionPorTipo() {
        return distribucionPorTipo;
    }

    public void setDistribucionPorTipo(Map<String, Long> distribucionPorTipo) {
        this.distribucionPorTipo = distribucionPorTipo;
    }
}
