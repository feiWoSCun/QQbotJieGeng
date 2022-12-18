package cn.jiegeng.luobin.domain.vo;/**
 * 舞台再大，不上去永远是观众，静下心沉住气慢慢来
 *
 * @email: 2825097536@qq.com
 * @author: feiWoSCun
 * @creat: 2022/12/17
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *舞台再大，不上去永远是观众，静下心沉住气慢慢来
 *@email: 2825097536@qq.com
 *@author: feiWoSCun
 @creat: 2022/12/17
 */
@NoArgsConstructor
@Data
public class WeatherVo {

    @JsonProperty("results")
    private List<ResultsDTO> results;

    @NoArgsConstructor
    @Data
    public static class ResultsDTO {
        @JsonProperty("location")
        private LocationDTO location;
        @JsonProperty("now")
        private NowDTO now;
        @JsonProperty("last_update")
        private String lastUpdate;

        @NoArgsConstructor
        @Data
        public static class LocationDTO {
            @JsonProperty("id")
            private String id;
            @JsonProperty("name")
            private String name;
            @JsonProperty("country")
            private String country;
            @JsonProperty("path")
            private String path;
            @JsonProperty("timezone")
            private String timezone;
            @JsonProperty("timezone_offset")
            private String timezoneOffset;
        }

        @NoArgsConstructor
        @Data
        public static class NowDTO {
            @JsonProperty("text")
            private String text;
            @JsonProperty("code")
            private Integer code;
            @JsonProperty("temperature")
            private String temperature;
        }
    }
}
