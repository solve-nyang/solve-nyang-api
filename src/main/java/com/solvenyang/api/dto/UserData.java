package com.solvenyang.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    private Integer solvedCount;
    @JsonProperty("class")
    private Integer memberClass;
    private Integer tier;
    private Integer maxStreak;
}
