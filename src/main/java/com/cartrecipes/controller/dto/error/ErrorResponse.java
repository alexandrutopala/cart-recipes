package com.cartrecipes.controller.dto.error;

public record ErrorResponse(String title,
                            String details,
                            Integer statusCode) {
}
