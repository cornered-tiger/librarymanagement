package com.vestas.libraryManagement.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

// TODO: use records instead, records are not supported by modelMapper so try other mapping tools
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {
    private int id;
    private String isbn;
    @NotBlank
    private String title;
    private BigDecimal price;
    private String author;
    private boolean isAvailable = true;
}
