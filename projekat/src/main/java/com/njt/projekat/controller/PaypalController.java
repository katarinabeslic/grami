package com.njt.projekat.controller;

import com.njt.projekat.entity.*;
import com.njt.projekat.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
public class PaypalController {

	@Autowired
	private PaypalService paypalService;

	public static final String SUCCESS_URL = "paypal-successful";
	public static final String CANCEL_URL = "cancel";

	private static Order order;

	@PostMapping("/paypal-pay")
	public String paymentProcessing(@RequestBody String data) {
		JSONObject jsonObject = new JSONObject(data);
		String priceS = jsonObject.getString("price");
		String currency = jsonObject.getString("currency");
		String method = jsonObject.getString("method");
		String intent = jsonObject.getString("intent");
		String description = jsonObject.getString("description");
		double price = Double.parseDouble(priceS);
		order = new Order(price, currency, method, intent, description);
		ShopController.order = order;
		try {
			Payment payment = paypalService.createPayment(order.getTotalPrice(), order.getCurrency(), order.getPaymentMethod(),
					order.getIntent(), order.getDescription(), "http://localhost:8080/"+CANCEL_URL, "http://localhost:8080/"+SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					return link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return "fail";
	}
}
