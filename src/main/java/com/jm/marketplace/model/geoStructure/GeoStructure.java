package com.jm.marketplace.model.geoStructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 *
 * Сруктура для Json ответа с сервера Спутник Api
 */

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoStructure {

    @JsonProperty("result")
    public Result result;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {

        @JsonProperty("priority")
        public String priority;
        @JsonProperty("viewport")
        public Viewport viewport;
        @JsonProperty("address")
        public List<Address> address = null;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {

        @JsonProperty("type")
        public String type;
        @JsonProperty("features")
        public List<Feature> features = null;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {

        @JsonProperty("id")
        public Integer id;
        @JsonProperty("type")
        public String type;
        @JsonProperty("description")
        public String description;
        @JsonProperty("display_name")
        public String displayName;
        @JsonProperty("title")
        public String title;
        @JsonProperty("address_components")
        public List<AddressComponent> addressComponents = null;
        @JsonProperty("fias_id")
        public String fiasId;
        @JsonProperty("full_match")
        public Boolean fullMatch;
        @JsonProperty("display_type")
        public String displayType;
        @JsonProperty("poi_types")
        public Object poiTypes;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Geometry {
        @JsonProperty("type")
        public String type;
        @JsonProperty("geometries")
        public List<Coordinate> geometries = null;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Coordinate {
        @JsonProperty("type")
        public String type;
        @JsonProperty("coordinates")
        public List<Double> coordinates = null;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Feature {

        @JsonProperty("type")
        public String type;
        @JsonProperty("properties")
        public Properties properties;
        @JsonProperty("geometry")
        public Geometry geometry;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressComponent {

        @JsonProperty("type")
        public String type;
        @JsonProperty("value")
        public String value;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Viewport {

        @JsonProperty("TopLat")
        public Integer topLat;
        @JsonProperty("TopLon")
        public Integer topLon;
        @JsonProperty("BotLat")
        public Integer botLat;
        @JsonProperty("BotLon")
        public Integer botLon;
    }
}