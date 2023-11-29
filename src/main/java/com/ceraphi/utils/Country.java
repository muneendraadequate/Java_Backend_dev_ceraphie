package com.ceraphi.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.Name;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    private List<String> name;

      }
