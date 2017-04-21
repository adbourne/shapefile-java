package com.adbourne.geo;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ShapefileParserTest {

    private DefaultShapefile shapefileParser;
    private GeometryFactory geometryFactory;


    @Before
    public void before() {
        this.shapefileParser = new DefaultShapefile();
        this.geometryFactory = new GeometryFactory();
    }


    @Test
    public void testParsePoint() throws Exception {
        File fileUnderTest = TestUtils.getFileFromResources("point/point.shp");

        List<Geometry> expected = Arrays.asList(new Geometry[]{
                geometryFactory.createPoint(new Coordinate(-69.95563226806375, 12.53481920223507))
        });
        List<Geometry> result = shapefileParser.parse(fileUnderTest);

        assertThat(result, is(expected));
    }

    @Test
    public void testParseLine() throws Exception {
        File fileUnderTest = TestUtils.getFileFromResources("line/line.shp");

        List<Geometry> expected = Arrays.asList(new Geometry[]{
                geometryFactory.createLineString(new Coordinate[]{
                        new Coordinate(-70.04222981949106, 12.552182670668463),
                        new Coordinate(-69.84002233900091, 12.568666976143204)
                }),
        });
        List<Geometry> result = shapefileParser.parse(fileUnderTest);

        assertThat(result, is(expected));
    }

    @Test
    public void testParsePolygon() throws Exception {
        File fileUnderTest = TestUtils.getFileFromResources("polygon/polygon.shp");

        List<Geometry> expected = Arrays.asList(new Geometry[]{
                geometryFactory.createPolygon(new Coordinate[]{
                        new Coordinate(-70.05475789165186, 12.571524255758826),
                        new Coordinate(-69.91519077196573, 12.608229309282581),
                        new Coordinate(-69.8090318447084, 12.517235943062014),
                        new Coordinate(-70.0329986084252, 12.465145537761835),
                        new Coordinate(-70.05475789165186, 12.571524255758826)
                }),
        });
        List<Geometry> result = shapefileParser.parse(fileUnderTest);

        // TODO: JTS uses != to compare coordinate instances
        // This results in a .equals failure for a polygon where
        // the coordinates may be the same but the coordinate instances
        // are different -- investigate why this is, but for for now
        // just use a toString to assert coordinate values are the same
        assertThat(result.toString(), is(expected.toString()));
    }

}
