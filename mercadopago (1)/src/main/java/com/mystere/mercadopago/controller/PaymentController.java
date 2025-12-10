package com.mystere.mercadopago.controller;
package com.mystere.mercadopago.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;

import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/pay")
@CrossOrigin("*")
public class PaymentController {

    private final String ACCESS_TOKEN = "APP_USR-ACA_TU_TOKEN";

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody Map<String, Object> data) {

        try {
            RestTemplate rest = new RestTemplate();

            Map<String, Object> body = new HashMap<>();
            body.put("transaction_amount", data.get("transaction_amount"));
            body.put("description", data.get("description"));
            body.put("payment_method_id", "visa");

            Map<String, Object> payer = new HashMap<>();
            payer.put("email", data.get("email"));

            body.put("payer", payer);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + ACCESS_TOKEN);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = rest.postForEntity(
                    "https://api.mercadopago.com/v1/payments",
                    request,
                    Map.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
