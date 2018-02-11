package com.rks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class CartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartDetailsId;

    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "product_id")
    private Integer productId;
    private Integer quantity;

    public CartDetails(Integer productId, Integer quantity){
        this.productId = productId;
        this.quantity = quantity;
    }
}
