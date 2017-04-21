package com.adbourne.geo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DbfField {

    private String name;

    private byte fieldType;

    private byte[] address;

    private int size;

    private int precision;

    private byte[] padding;
}
