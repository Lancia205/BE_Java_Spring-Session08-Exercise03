package com.example.session08.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookUpdateStockDTO {

    @NotNull(message = "Stock không được để trống")
    @Min(value = 0, message = "Stock phải lớn hơn hoặc bằng 0")
    private Integer stock;
}