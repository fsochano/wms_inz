package com.sochanski.order;

import java.util.Optional;

public class OrderUpdateParams {
    public Optional<String> name = Optional.empty();
    public Optional<OrderStatus> status = Optional.empty();
}
