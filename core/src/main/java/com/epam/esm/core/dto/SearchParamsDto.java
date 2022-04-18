package com.epam.esm.core.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class SearchParamsDto {
    private String tagName;
    private String name;
    private String description;

    @Pattern(regexp = "create_date|last_update_date|name", message = "sortBy must be 'create_date', 'last_update_date' or 'name'")
    private String sortBy;

    @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE, message = "sortOrder must be 'asc' or 'desc'(case insensitive)")
    private String sortType;
}
