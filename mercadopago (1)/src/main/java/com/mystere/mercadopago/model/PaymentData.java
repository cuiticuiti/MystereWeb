package com.mystere.mercadopago.model;

import lombok.Data;

@Data
public class PaymentData {
    private String title;
    private Integer quantity;
    private Float price;
}
