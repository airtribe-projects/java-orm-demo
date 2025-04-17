package com.airtribe.orm.demo.controllers;


import com.airtribe.orm.demo.Exceptions.InvalidInputException;
import com.airtribe.orm.demo.Exceptions.ResourceNotFoundException;
import com.airtribe.orm.demo.constants.Constants;
import com.airtribe.orm.demo.enums.ResponseType;
import com.airtribe.orm.demo.models.requests.orders.CreateOrderRequest;
import com.airtribe.orm.demo.models.requests.orders.UpdateOrderRequest;
import com.airtribe.orm.demo.models.responses.base.GenericResponse;
import com.airtribe.orm.demo.models.responses.orders.OrderDetailsResponse;
import com.airtribe.orm.demo.services.orders.OrderService;
import com.airtribe.orm.demo.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/order")
public class OrderController {

    private final OrderService orderService;
    private final ValidationUtils validationUtils;

    public OrderController(OrderService orderService, ValidationUtils validationUtils) {
        this.orderService = orderService;
        this.validationUtils = validationUtils;
    }

    @GetMapping(path = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetch Order Details", description = "Fetch the details of an Order")
    public GenericResponse<OrderDetailsResponse> fetchOrderDetails(@RequestParam("user-id") String userId, @RequestParam("order-id") String orderId) {
        OrderDetailsResponse orderDetailsResponse = null;
        try {
            validationUtils.validateId(orderId);
            orderDetailsResponse = orderService.getOrder(userId, orderId);
        } catch (InvalidInputException e) {
            return GenericResponse.<OrderDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<OrderDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<OrderDetailsResponse>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(orderDetailsResponse)
                .build();
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "create order", description = "Add a new order")
    public GenericResponse<OrderDetailsResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        OrderDetailsResponse orderDetailsResponse = null;
        try {
            validationUtils.validateCreateOrderRequest(createOrderRequest);
            orderDetailsResponse = orderService.createOrder(createOrderRequest);

        } catch (InvalidInputException e) {
            return GenericResponse.<OrderDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<OrderDetailsResponse>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(orderDetailsResponse)
                .build();
    }

    @Operation(summary = "Update Order", description = "Update the details of an Order")
    @PutMapping(path = "/update/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<OrderDetailsResponse> updateOrderStatus(@RequestBody UpdateOrderRequest updateOrderRequest) {
        OrderDetailsResponse orderDetailsResponse = null;
        try {
            validationUtils.validateId(updateOrderRequest.getOrderId());
            validationUtils.validateId(updateOrderRequest.getUserId());
            orderDetailsResponse = orderService.updateOrderStatus(updateOrderRequest);

        } catch (InvalidInputException e) {
            return GenericResponse.<OrderDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<OrderDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<OrderDetailsResponse>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(orderDetailsResponse)
                .build();
    }

    @Operation(summary = "Delete Order", description = "delete the Order")
    @PutMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<OrderDetailsResponse> deleteProduct(@RequestParam("user-id") String userId, @RequestParam("order-id") String orderId) {
        OrderDetailsResponse orderDetailsResponse = null;
        try {
            validationUtils.validateId(userId);
            validationUtils.validateId(orderId);
            orderDetailsResponse = orderService.deleteOrder(userId, orderId);

        } catch (InvalidInputException e) {
            return GenericResponse.<OrderDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<OrderDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<OrderDetailsResponse>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(orderDetailsResponse)
                .build();
    }
}
