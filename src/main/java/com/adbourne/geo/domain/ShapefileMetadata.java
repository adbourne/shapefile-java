package com.adbourne.geo.domain;

import com.adbourne.geo.domain.geometry.BoundingBox;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShapefileMetadata {

    private GeometryType geometryType;

    private BoundingBox boundingBox;

    private int fileLength;

}
