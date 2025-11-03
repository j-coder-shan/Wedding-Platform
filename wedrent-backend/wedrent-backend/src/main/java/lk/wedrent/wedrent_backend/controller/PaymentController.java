package lk.wedrent.wedrent_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lk.wedrent.wedrent_backend.dto.PaymentRequest;
import lk.wedrent.wedrent_backend.entity.Payment;
import lk.wedrent.wedrent_backend.entity.PaymentStatus;
import lk.wedrent.wedrent_backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Mock payment endpoints and webhook stubs for Sprint 2.
 */
@Validated
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    @Autowired
    public PaymentController(PaymentService paymentService) { this.paymentService = paymentService; }

    @Operation(summary = "Mock payment initiation (test only)")
    @PostMapping("/pay")
    public ResponseEntity<Payment> mockPay(@RequestBody PaymentRequest req) {
        var p = paymentService.processMockPayment(req);
        return ResponseEntity.ok(p);
    }

    @Operation(summary = "Webhook stub for provider callbacks")
    @PostMapping("/webhook/{provider}")
    public ResponseEntity<String> webhook(@PathVariable String provider, @RequestBody String payload) {
        // store/validate payload later in Sprint 3
        paymentService.handleWebhookStub(provider, payload);
        return ResponseEntity.ok("received");
    }
}
