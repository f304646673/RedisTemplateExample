package org.example.redistemplateexample.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoSearchStoreCommandArgs;
import org.springframework.data.redis.domain.geo.BoundingBox;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.data.redis.domain.geo.GeoShape;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GeoOperationTest {

    @Autowired
    private GeoOperation geoOperation;

    final String keyChina = "china";

    @BeforeEach
    public void Setup() {
        try {
            geoOperation.Add(keyChina, new GeoLocation<>("beijing", new Point(116.405285, 39.904989)));
            geoOperation.Add(keyChina, new GeoLocation<>("shanghai", new Point(121.472644, 31.231706)));
            geoOperation.Add(keyChina, new GeoLocation<>("guangzhou", new Point(113.280637, 23.125178)));
            geoOperation.Add(keyChina, new GeoLocation<>("shenzhen", new Point(114.057868, 22.543099)));
            geoOperation.Add(keyChina, new GeoLocation<>("chengdu", new Point(104.065735, 30.659462)));
            geoOperation.Add(keyChina, new GeoLocation<>("chongqing", new Point(106.551643, 29.562849)));
            geoOperation.Add(keyChina, new GeoLocation<>("wuhan", new Point(114.305392, 30.593098)));
            geoOperation.Add(keyChina, new GeoLocation<>("hangzhou", new Point(120.15507, 30.274085)));
            geoOperation.Add(keyChina, new GeoLocation<>("nanjing", new Point(118.796877, 32.060255)));
            geoOperation.Add(keyChina, new GeoLocation<>("suzhou", new Point(120.585315, 31.298886)));
            geoOperation.Add(keyChina, new GeoLocation<>("tianjin", new Point(117.190182, 39.125596)));
            geoOperation.Add(keyChina, new GeoLocation<>("changsha", new Point(112.982279, 28.19409)));
            geoOperation.Add(keyChina, new GeoLocation<>("zhengzhou", new Point(113.624931, 34.74725)));
            geoOperation.Add(keyChina, new GeoLocation<>("xian", new Point(108.940175, 34.341568)));
            geoOperation.Add(keyChina, new GeoLocation<>("kunming", new Point(102.832891, 24.880095)));
            geoOperation.Add(keyChina, new GeoLocation<>("nanning", new Point(108.366543, 22.817002)));
            geoOperation.Add(keyChina, new GeoLocation<>("haikou", new Point(110.198293, 20.044001)));
            geoOperation.Add(keyChina, new GeoLocation<>("lhasa", new Point(91.132212, 29.660361)));
            geoOperation.Add(keyChina, new GeoLocation<>("urumqi", new Point(87.617733, 43.792818)));
            geoOperation.Add(keyChina, new GeoLocation<>("hohhot", new Point(111.75199, 40.84149)));
            geoOperation.Add(keyChina, new GeoLocation<>("taiyuan", new Point(112.548879, 37.87059)));
            geoOperation.Add(keyChina, new GeoLocation<>("shijiazhuang", new Point(114.502461, 38.045474)));
            geoOperation.Add(keyChina, new GeoLocation<>("jinan", new Point(117.120497, 36.651216)));
            geoOperation.Add(keyChina, new GeoLocation<>("qingdao", new Point(120.38264, 36.067082)));
            geoOperation.Add(keyChina, new GeoLocation<>("changchun", new Point(125.323544, 43.817071)));
            geoOperation.Add(keyChina, new GeoLocation<>("harbin", new Point(126.5358, 45.80216)));
            geoOperation.Add(keyChina, new GeoLocation<>("shenyang", new Point(123.431475, 41.805698)));
            geoOperation.Add(keyChina, new GeoLocation<>("dalian", new Point(121.614682, 38.914003)));
            geoOperation.Add(keyChina, new GeoLocation<>("xining", new Point(101.778228, 36.617144)));
            geoOperation.Add(keyChina, new GeoLocation<>("lanzhou", new Point(103.834304, 36.061089)));
            geoOperation.Add(keyChina, new GeoLocation<>("hefei", new Point(117.227239, 31.820587)));
            geoOperation.Add(keyChina, new GeoLocation<>("fuzhou", new Point(119.296494, 26.074507)));
            geoOperation.Add(keyChina, new GeoLocation<>("nanchang", new Point(115.858197, 28.682892)));
            geoOperation.Add(keyChina, new GeoLocation<>("changzhou", new Point(119.973987, 31.810689)));
            geoOperation.Add(keyChina, new GeoLocation<>("wuxi", new Point(120.31191, 31.491169)));
            geoOperation.Add(keyChina, new GeoLocation<>("xuzhou", new Point(117.188107, 34.271553)));
            geoOperation.Add(keyChina, new GeoLocation<>("yantai", new Point(121.447935, 37.463822)));
            geoOperation.Add(keyChina, new GeoLocation<>("weihai", new Point(122.12042, 37.513068)));
            geoOperation.Add(keyChina, new GeoLocation<>("jining", new Point(116.587282, 35.414982)));
            geoOperation.Add(keyChina, new GeoLocation<>("linyi", new Point(118.356448, 35.104672)));
            geoOperation.Add(keyChina, new GeoLocation<>("zibo", new Point(118.054868, 36.813487)));
            geoOperation.Add(keyChina, new GeoLocation<>("weifang", new Point(119.161756, 36.706774)));
            geoOperation.Add(keyChina, new GeoLocation<>("dongying", new Point(118.674767, 37.434751)));
            geoOperation.Add(keyChina, new GeoLocation<>("binzhou", new Point(118.016974, 37.383542)));
            geoOperation.Add(keyChina, new GeoLocation<>("dezhou", new Point(116.357465, 37.434093)));
            geoOperation.Add(keyChina, new GeoLocation<>("heze", new Point(115.480656, 35.23375)));
            geoOperation.Add(keyChina, new GeoLocation<>("liaocheng", new Point(115.986869, 36.455829)));
            geoOperation.Add(keyChina, new GeoLocation<>("laiwu", new Point(117.676724, 36.213814)));
            geoOperation.Add(keyChina, new GeoLocation<>("liaoyang", new Point(123.172451, 41.273339)));
            geoOperation.Add(keyChina, new GeoLocation<>("anshan", new Point(123.007763, 41.118744)));
            geoOperation.Add(keyChina, new GeoLocation<>("fushun", new Point(123.923568, 41.846081)));
            geoOperation.Add(keyChina, new GeoLocation<>("benxi", new Point(123.766485, 41.311748)));
            geoOperation.Add(keyChina, new GeoLocation<>("dandong", new Point(124.356483, 40.000915)));
            geoOperation.Add(keyChina, new GeoLocation<>("yingkou", new Point(122.235418, 40.667012)));
            geoOperation.Add(keyChina, new GeoLocation<>("panjin", new Point(122.070714, 41.119997)));
            geoOperation.Add(keyChina, new GeoLocation<>("jinzhou", new Point(121.135742, 41.119269)));
            geoOperation.Add(keyChina, new GeoLocation<>("chaoyang", new Point(120.446163, 41.571828)));
            geoOperation.Add(keyChina, new GeoLocation<>("huludao", new Point(120.836932, 40.711052)));
            geoOperation.Add(keyChina, new GeoLocation<>("tieling", new Point(123.844279, 42.295585)));
            geoOperation.Add(keyChina, new GeoLocation<>("fuxin", new Point(121.676408, 42.01925)));
            geoOperation.Add(keyChina, new GeoLocation<>("liaoyuan", new Point(125.145349, 42.902692)));
            geoOperation.Add(keyChina, new GeoLocation<>("siping", new Point(124.350398, 43.16642)));
            geoOperation.Add(keyChina, new GeoLocation<>("baicheng", new Point(122.839024, 45.616764)));
            geoOperation.Add(keyChina, new GeoLocation<>("songyuan", new Point(124.825117, 45.141789)));
            geoOperation.Add(keyChina, new GeoLocation<>("baishan", new Point(126.427839, 41.942505)));
            geoOperation.Add(keyChina, new GeoLocation<>("tonghua", new Point(125.939697, 41.728401)));
            geoOperation.Add(keyChina, new GeoLocation<>("jilin", new Point(126.55302, 43.843577)));
            geoOperation.Add(keyChina, new GeoLocation<>("yanbian", new Point(129.513228, 42.904823)));
            geoOperation.Add(keyChina, new GeoLocation<>("hegang", new Point(130.277487, 47.332085)));
            geoOperation.Add(keyChina, new GeoLocation<>("jiamusi", new Point(130.360603, 46.808285)));
            geoOperation.Add(keyChina, new GeoLocation<>("qiqihar", new Point(123.918186, 47.354348)));
            geoOperation.Add(keyChina, new GeoLocation<>("mudanjiang", new Point(129.618602, 44.582962)));
            geoOperation.Add(keyChina, new GeoLocation<>("daqing", new Point(125.103784, 46.58931)));
            geoOperation.Add(keyChina, new GeoLocation<>("yichun", new Point(128.840492, 47.727536)));
            geoOperation.Add(keyChina, new GeoLocation<>("shuangyashan", new Point(131.159133, 46.646509)));
            geoOperation.Add(keyChina, new GeoLocation<>("wuhu", new Point(118.384108, 31.36602)));
            geoOperation.Add(keyChina, new GeoLocation<>("maanshan", new Point(118.507906, 31.689362)));
            geoOperation.Add(keyChina, new GeoLocation<>("huaibei", new Point(116.799349, 33.971707)));
            geoOperation.Add(keyChina, new GeoLocation<>("bengbu", new Point(117.285755, 33.861501)));
            geoOperation.Add(keyChina, new GeoLocation<>("huainan", new Point(117.018639, 32.642812)));
            geoOperation.Add(keyChina, new GeoLocation<>("tongling", new Point(117.812079, 30.945515)));
            geoOperation.Add(keyChina, new GeoLocation<>("anqing", new Point(117.043551, 30.50883)));
            geoOperation.Add(keyChina, new GeoLocation<>("huangshan", new Point(118.337481, 29.714655)));
            geoOperation.Add(keyChina, new GeoLocation<>("chuzhou", new Point(118.317325, 32.301556)));
            geoOperation.Add(keyChina, new GeoLocation<>("xuancheng", new Point(118.758816, 30.940718)));
            geoOperation.Add(keyChina, new GeoLocation<>("chizhou", new Point(117.489157, 30.656037)));
            geoOperation.Add(keyChina, new GeoLocation<>("fuyang", new Point(115.814205, 32.890124)));
            geoOperation.Add(keyChina, new GeoLocation<>("bozhou", new Point(115.778676, 33.844582)));
            geoOperation.Add(keyChina, new GeoLocation<>("xianyang", new Point(108.707509, 34.345373)));
            geoOperation.Add(keyChina, new GeoLocation<>("weinan", new Point(109.509786, 34.499995)));
            geoOperation.Add(keyChina, new GeoLocation<>("yanan", new Point(109.489727, 36.585455)));
            geoOperation.Add(keyChina, new GeoLocation<>("hanzhong", new Point(107.029827, 33.073798)));
            geoOperation.Add(keyChina, new GeoLocation<>("ankang", new Point(108.946465, 32.660444)));
            geoOperation.Add(keyChina, new GeoLocation<>("shangluo", new Point(109.939776, 33.868319)));
            geoOperation.Add(keyChina, new GeoLocation<>("yulin", new Point(110.154393, 22.63136)));
            geoOperation.Add(keyChina, new GeoLocation<>("baise", new Point(106.618201, 23.902333)));
            geoOperation.Add(keyChina, new GeoLocation<>("hezhou", new Point(111.573078, 24.40945)));
            geoOperation.Add(keyChina, new GeoLocation<>("hechi", new Point(107.982859, 24.256915)));
            geoOperation.Add(keyChina, new GeoLocation<>("laibin", new Point(109.761146, 23.84957)));
            geoOperation.Add(keyChina, new GeoLocation<>("chongzuo", new Point(107.357322, 22.415455)));
            geoOperation.Add(keyChina, new GeoLocation<>("guilin", new Point(110.290195, 25.273566)));
            geoOperation.Add(keyChina, new GeoLocation<>("liuzhou", new Point(109.415953, 24.325502)));
            geoOperation.Add(keyChina, new GeoLocation<>("nanning", new Point(108.366543, 22.817002)));
            geoOperation.Add(keyChina, new GeoLocation<>("beihai", new Point(109.119927, 21.481254)));
            geoOperation.Add(keyChina, new GeoLocation<>("fangchenggang", new Point(108.345478, 21.614631)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            geoOperation.Remove(keyChina, "beijing", "shanghai", "guangzhou", "shenzhen");
            geoOperation.Remove(keyChina, "chengdu", "chongqing", "wuhan", "hangzhou");
            geoOperation.Remove(keyChina, "nanjing", "suzhou", "tianjin", "changsha");
            geoOperation.Remove(keyChina, "zhengzhou", "xian", "kunming", "nanning");
            geoOperation.Remove(keyChina, "haikou", "lhasa", "urumqi", "hohhot");
            geoOperation.Remove(keyChina, "taiyuan", "shijiazhuang", "jinan", "qingdao");
            geoOperation.Remove(keyChina, "changchun", "harbin", "shenyang", "dalian");
            geoOperation.Remove(keyChina, "xining", "lanzhou", "hefei", "fuzhou");
            geoOperation.Remove(keyChina, "nanchang", "changzhou", "wuxi", "xuzhou");
            geoOperation.Remove(keyChina, "yantai", "weihai", "jining", "linyi");
            geoOperation.Remove(keyChina, "zibo", "weifang", "dongying", "binzhou");
            geoOperation.Remove(keyChina, "dezhou", "heze", "liaocheng", "laiwu");
            geoOperation.Remove(keyChina, "liaoyang", "anshan", "fushun", "benxi");
            geoOperation.Remove(keyChina, "dandong", "yingkou", "panjin", "jinzhou");
            geoOperation.Remove(keyChina, "chaoyang", "huludao", "tieling", "fuxin");
            geoOperation.Remove(keyChina, "liaoyuan", "siping", "baicheng", "songyuan");
            geoOperation.Remove(keyChina, "baishan", "tonghua", "jilin", "yanbian");
            geoOperation.Remove(keyChina, "hegang", "jiamusi", "qiqihar", "mudanjiang");
            geoOperation.Remove(keyChina, "daqing", "yichun", "shuangyashan", "wuhu");
            geoOperation.Remove(keyChina, "maanshan", "huaibei", "bengbu", "huainan");
            geoOperation.Remove(keyChina, "tongling", "anqing", "huangshan", "chuzhou");
            geoOperation.Remove(keyChina, "xuancheng", "chizhou", "fuyang", "bozhou");
            geoOperation.Remove(keyChina, "xianyang", "weinan", "yanan", "hanzhong");
            geoOperation.Remove(keyChina, "ankang", "shangludo", "yulin", "baise");
            geoOperation.Remove(keyChina, "hezhou", "hechi", "laibin", "chongzuo");
            geoOperation.Remove(keyChina, "guilin", "liuzhou", "nanning", "beihai");
            geoOperation.Remove(keyChina, "fangchenggang");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAdd() {
        assertDoesNotThrow(() -> {
            final String key = "testAdd";
            Point pointBeijing = new Point(116.405285, 39.904989);
            Long result = geoOperation.Add(key, pointBeijing, "beijing");
            assertEquals(1, result.longValue());
            geoOperation.Remove(key, "beijing");
        });
    }

    @Test
    public void testAddMap() {
        assertDoesNotThrow(() -> {
            final String key = "testAddMap";
            Point pointBeijing = new Point(116.405285, 39.904989);
            Point pointShanghai = new Point(121.472644, 31.231706);
            Long result = geoOperation.Add(key, Map.of("beijing", pointBeijing, "shanghai", pointShanghai));
            assertEquals(2, result.longValue());
            geoOperation.Remove(key, "beijing", "shanghai");
        });
    }

    @Test
    public void testAddGeoLocation() {
        assertDoesNotThrow(() -> {
            final String key = "testAddGeoLocation";
            Point pointBeijing = new Point(116.405285, 39.904989);
            GeoLocation<String> location = new GeoLocation<>("beijing", pointBeijing);
            Long result = geoOperation.Add(key, location);
            assertEquals(1, result.longValue());
            geoOperation.Remove(key, "beijing");
        });
    }

    @Test
    public void testDist() {
        assertDoesNotThrow(() -> {
            Distance distance = geoOperation.Dist(keyChina, "beijing", "shanghai");
            assertNotNull(distance);
        });
    }

    @Test
    public void testDistMetric() {
        assertDoesNotThrow(() -> {
            Distance distance = geoOperation.Dist(keyChina, "beijing", "shanghai", Metrics.KILOMETERS);
            assertNotNull(distance);
        });
    }

    @Test
    public void testHash() {
        assertDoesNotThrow(() -> {
            List<String> hashes = geoOperation.Hash(keyChina, "beijing");
            assertNotNull(hashes);
        });
    }

    @Test
    public void testPos() {
        assertDoesNotThrow(() -> {
            List<Point> points = geoOperation.Pos(keyChina, "beijing");
            assertNotNull(points);
        });
    }

    @Test
    public void testRadiusCircle() {
        assertDoesNotThrow(() -> {
            Circle circle = new Circle(new Point(116.405285, 39.904989), new Distance(100, Metrics.KILOMETERS));
            GeoResults<GeoLocation<String>> points = geoOperation.Radius(keyChina, circle);
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testRadiusCircleArgs() {
        assertDoesNotThrow(() -> {
            Circle circle = new Circle(new Point(116.405285, 39.904989), new Distance(100, Metrics.KILOMETERS));
            GeoResults<GeoLocation<String>> points = geoOperation.Radius(keyChina, circle,
                    GeoRadiusCommandArgs.newGeoRadiusArgs().limit(1));
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testRadius() {
        assertDoesNotThrow(() -> {
            GeoResults<GeoLocation<String>> points = geoOperation.Radius(keyChina, "beijing", 100);
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testRadiusDistance() {
        assertDoesNotThrow(() -> {
            GeoResults<GeoLocation<String>> points = geoOperation.Radius(keyChina, "beijing",
                    new Distance(300, Metrics.KILOMETERS));
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testRadiusDistanceArgs() {
        assertDoesNotThrow(() -> {
            GeoResults<GeoLocation<String>> points = geoOperation.Radius(keyChina, "beijing",
                    new Distance(300, Metrics.KILOMETERS), GeoRadiusCommandArgs.newGeoRadiusArgs().limit(1));
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testRemove() {
        assertDoesNotThrow(() -> {
            Long result = geoOperation.Remove(keyChina, "beijing", "shanghai", "guangzhou", "shenzhen");
            assertEquals(4, result.longValue());
        });
    }

    @Test
    public void testSearch() {
        assertDoesNotThrow(() -> {
            Circle circle = new Circle(new Point(116.405285, 39.904989), new Distance(100, Metrics.KILOMETERS));
            GeoResults<GeoLocation<String>> points = geoOperation.Search(keyChina, circle);
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testSearchReference() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            Distance distance = new Distance(100, Metrics.KILOMETERS);
            GeoResults<GeoLocation<String>> points = geoOperation.Search(keyChina, reference, distance);
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testSearchReferenceArgs() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            Distance distance = new Distance(100, Metrics.KILOMETERS);
            GeoResults<GeoLocation<String>> points = geoOperation.Search(keyChina, reference, distance,
                    GeoRadiusCommandArgs.newGeoRadiusArgs().limit(1));
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testSearchReferenceBoundingBox() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            BoundingBox boundingBox = new BoundingBox(new Distance(100, Metrics.KILOMETERS),
                    new Distance(200, Metrics.KILOMETERS));

            GeoResults<GeoLocation<String>> points = geoOperation.Search(keyChina, reference, boundingBox);
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testSearchReferenceBoundingBoxArgs() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            BoundingBox boundingBox = new BoundingBox(new Distance(100, Metrics.KILOMETERS),
                    new Distance(200, Metrics.KILOMETERS));

            GeoResults<GeoLocation<String>> points = geoOperation.Search(keyChina, reference, boundingBox,
                    GeoRadiusCommandArgs.newGeoRadiusArgs().limit(1));
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testSearchReferenceGeoShape() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            GeoShape geoShape = GeoShape.byRadius(new Distance(100, Metrics.KILOMETERS));

            GeoResults<GeoLocation<String>> points = geoOperation.Search(keyChina, reference, geoShape,
                    GeoRadiusCommandArgs.newGeoRadiusArgs().limit(1));
            assertNotNull(points);
            for (GeoResult<GeoLocation<String>> point : points) {
                assertNotNull(point.getContent());
            }
        });
    }

    @Test
    public void testSearchAndStoreCircle() {
        assertDoesNotThrow(() -> {
            Circle circle = new Circle(new Point(116.405285, 39.904989), new Distance(100, Metrics.KILOMETERS));
            Long result = geoOperation.SearchAndStore(keyChina, "testSearchAndStoreCircle", circle);
            assertEquals(1, result.longValue());
            geoOperation.Remove("testSearchAndStoreCircle", "beijing");
        });
    }

    @Test
    public void testSearchAndStoreReferenceDistance() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            Distance distance = new Distance(100, Metrics.KILOMETERS);
            Long result = geoOperation.SearchAndStore(keyChina, "testSearchAndStoreReferenceDistance", reference,
                    distance);
            assertEquals(1, result.longValue());
            geoOperation.Remove("testSearchAndStoreReferenceDistance", "beijing");
        });
    }

    @Test
    public void testSearchAndStoreReferenceDistanceArgs() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            Distance distance = new Distance(100, Metrics.KILOMETERS);
            Long result = geoOperation.SearchAndStore(keyChina, "testSearchAndStoreReferenceDistanceArgs", reference,
                    distance, GeoSearchStoreCommandArgs.newGeoSearchStoreArgs().limit(1));
            assertEquals(1, result.longValue());
            geoOperation.Remove("testSearchAndStoreReferenceDistanceArgs", "beijing");
        });
    }

    @Test
    public void testSearchAndStoreReferenceBoundingBox() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            BoundingBox boundingBox = new BoundingBox(new Distance(100, Metrics.KILOMETERS),
                    new Distance(200, Metrics.KILOMETERS));
            Long result = geoOperation.SearchAndStore(keyChina, "testSearchAndStoreReferenceBoundingBox", reference,
                    boundingBox);
            assertEquals(1, result.longValue());
            geoOperation.Remove("testSearchAndStoreReferenceBoundingBox", "beijing");
        });
    }

    @Test
    public void testSearchAndStoreReferenceBoundingBoxArgs() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            BoundingBox boundingBox = new BoundingBox(new Distance(100, Metrics.KILOMETERS),
                    new Distance(200, Metrics.KILOMETERS));
            Long result = geoOperation.SearchAndStore(keyChina, "testSearchAndStoreReferenceBoundingBoxArgs", reference,
                    boundingBox, GeoSearchStoreCommandArgs.newGeoSearchStoreArgs().limit(1));
            assertEquals(1, result.longValue());
            geoOperation.Remove("testSearchAndStoreReferenceBoundingBoxArgs", "beijing");
        });
    }

    @Test
    public void testSearchAndStoreReferenceGeoShape() {
        assertDoesNotThrow(() -> {
            GeoReference<String> reference = GeoReference.fromMember("beijing");
            GeoShape geoShape = GeoShape.byRadius(new Distance(100, Metrics.KILOMETERS));
            Long result = geoOperation.SearchAndStore(keyChina, "testSearchAndStoreReferenceGeoShape", reference,
                    geoShape, GeoSearchStoreCommandArgs.newGeoSearchStoreArgs().limit(1));
            assertEquals(1, result.longValue());
            geoOperation.Remove("testSearchAndStoreReferenceGeoShape", "beijing");
        });
    }

}
