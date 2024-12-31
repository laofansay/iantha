package com.laofan.iantha.stripe;

import com.laofan.iantha.domain.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StrIpeApi {

    @Value("${stripe.api-key}")
    private String stripeApiKey;

    public Customer findOrCreateCustomer(String userId, String userEmail, String userName) throws StripeException {
        CustomerSearchParams params = CustomerSearchParams.builder().setQuery("email:'" + userEmail + "'").build();

        CustomerSearchResult result = Customer.search(params);

        Customer customer;

        // If no existing customer was found, create a new record
        if (result.getData().size() == 0) {
            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder().setName(userName).setEmail(userEmail).build();
            customer = Customer.create(customerCreateParams);
        } else {
            customer = result.getData().get(0);
        }
        return customer;
    }

    public String createOrder(String userId, String userName, String userEmail, Set<OrderItem> orderItems) throws StripeException {
        String clientBaseURL = System.getenv().get("CLIENT_BASE_URL");

        Customer customer = this.findOrCreateCustomer(userId, userEmail, userName);

        // Next, create a checkout session by adding the details of the checkout
        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setCustomer(customer.getId())
            .setSuccessUrl(clientBaseURL + "/success?session_id={CHECKOUT_SESSION_ID}")
            .setCancelUrl(clientBaseURL + "/failure");

        for (OrderItem item : orderItems) {
            paramsBuilder.addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(item.getCount().longValue())
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .putMetadata("app_id", item.getId() + "")
                                    .setName(item.getProduct().getTitle())
                                    .build()
                            )
                            .setCurrency("$")
                            .setUnitAmountDecimal(BigDecimal.valueOf(item.getPrice() * item.getCount()))
                            .build()
                    )
                    .build()
            );
        }
        Session session = Session.create(paramsBuilder.build());
        return session.getUrl();
    }
}
