package br.edu.utfpr.irigation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IrrigationLogDTO {
    private String message;
    private String userId;
}
