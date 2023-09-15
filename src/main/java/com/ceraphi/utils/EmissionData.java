package com.ceraphi.utils;

import com.ceraphi.utils.Lcho.Current;
import com.ceraphi.utils.Lcho.Deep;
import com.ceraphi.utils.Lcho.Medium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmissionData {
private Current current;
private Deep deep;
private Medium medium;

}
