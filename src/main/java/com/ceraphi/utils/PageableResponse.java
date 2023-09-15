package com.ceraphi.utils;

import com.ceraphi.dto.ClientDetailsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PageableResponse  implements Serializable {
    private List<?> clients;
    @JsonProperty("total_items")
    private int totalElements;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("current_page")
    private int number;
    private boolean last;
}
