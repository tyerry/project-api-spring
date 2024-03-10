package com.tyerryzanca.developerTest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsuarioRequestDTO {

    private String nome;

    private String email;

    private String senha;

}
