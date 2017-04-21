package com.adbourne.geo.domain.geometry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoundingBox {

    private double minX;

    private double minY;

    private double maxX;

    private double maxY;

    private double minZ;

    private double maxZ;

    private double minM;

    private double maxM;

}
