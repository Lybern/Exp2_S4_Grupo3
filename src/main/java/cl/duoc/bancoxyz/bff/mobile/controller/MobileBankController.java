package cl.duoc.bancoxyz.bff.mobile.controller;

import cl.duoc.bancoxyz.bff.mobile.dto.MobileAccountSummaryResponse;
import cl.duoc.bancoxyz.bff.mobile.dto.MobileTransactionResponse;
import cl.duoc.bancoxyz.bff.mobile.dto.MobileTransferRequest;
import cl.duoc.bancoxyz.bff.mobile.service.MobileBffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/mobile")
@Tag(name = "BFF Móvil", description = "Endpoints optimizados para dispositivos móviles (payloads compactos y bajo consumo de red)")
public class MobileBankController {

    private final MobileBffService mobileBffService;

    public MobileBankController(MobileBffService mobileBffService) {
        this.mobileBffService = mobileBffService;
    }

    @Operation(summary = "Obtener resumen de cuenta para móvil",
               description = "Entrega un payload ligero con el saldo disponible y las últimas 3 transacciones para minimizar transferencia de datos en redes móviles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumen móvil generado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/cuentas/{cuentaId}")
    public ResponseEntity<MobileAccountSummaryResponse> getResumenCuenta(
            @Parameter(description = "ID de la cuenta bancaria", example = "101")
            @PathVariable Long cuentaId) {
        return ResponseEntity.ok(mobileBffService.obtenerResumenMovil(cuentaId));
    }

    @Operation(summary = "Consulta rápida de saldo",
               description = "Devuelve únicamente el saldo disponible en formato clave-valor ultracompacto.")
    @GetMapping("/cuentas/{cuentaId}/saldo")
    public ResponseEntity<Map<String, Object>> getSaldoRapido(
            @Parameter(description = "ID de la cuenta bancaria", example = "101")
            @PathVariable Long cuentaId) {
        Double saldo = mobileBffService.consultarSaldoRapido(cuentaId);
        return ResponseEntity.ok(Map.of(
                "cuentaId", cuentaId,
                "saldoDisponible", saldo
        ));
    }

    @Operation(summary = "Ejecutar transferencia rápida móvil",
               description = "Permite enviar dinero de forma simplificada a otra cuenta desde la aplicación móvil.")
    @PostMapping("/cuentas/{cuentaId}/transferencia")
    public ResponseEntity<MobileTransactionResponse> transferir(
            @Parameter(description = "ID de la cuenta origen", example = "101")
            @PathVariable Long cuentaId,
            @RequestBody MobileTransferRequest request) {
        MobileTransactionResponse response = mobileBffService.ejecutarTransferenciaRapida(cuentaId, request);
        return ResponseEntity.ok(response);
    }
}
