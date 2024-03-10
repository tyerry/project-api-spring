package com.tyerryzanca.developerTest.controller;

import com.tyerryzanca.developerTest.model.dto.ResponseErrorDTO;
import com.tyerryzanca.developerTest.model.dto.UsuarioRequestDTO;
import com.tyerryzanca.developerTest.model.dto.UsuarioResponseDTO;
import com.tyerryzanca.developerTest.model.entity.Usuario;
import com.tyerryzanca.developerTest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    public ResponseEntity<Object> returnError(Exception e) {
        HttpStatus httpStatus = null;

        if(e.getMessage().equals("User not found.")
                || e.getMessage().equals("User not found, the delete operation was not completed.")
                || e.getMessage().equals("User not found, the update operation was not completed.")) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ResponseErrorDTO responseErrorDTO = ResponseErrorDTO.builder()
                .description("Houston, we have a problem...")
                .errorMessage(e.getMessage())
                .build();
        return ResponseEntity.status(httpStatus).body(responseErrorDTO);
    }

    @PostMapping
    public ResponseEntity<Object> inserirUsuario(@RequestBody UsuarioRequestDTO usuario) throws Exception {
        try {
            Usuario usuarioCreated = usuarioService.inserirUsuario(usuario);
            UsuarioResponseDTO usuarioResponseDTO = UsuarioResponseDTO.builder()
                    .id(usuarioCreated.getId())
                    .nome(usuarioCreated.getNome())
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
        } catch (Exception e) {
            return returnError(e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> encontrarUsuario(@PathVariable("id") Integer id) throws Exception {
        try {
            Usuario usuario = usuarioService.encontrarUsuario(id);
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } catch (Exception e) {
            return returnError(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> encontrarTodos() throws Exception {
        try {
            List<Usuario> usuario = usuarioService.encontrarTodos();
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } catch (Exception e) {
            return returnError(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable("id") Integer id) throws Exception {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return returnError(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable("id") Integer id, @RequestBody UsuarioRequestDTO usuario) throws Exception {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id,usuario);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
        } catch (Exception e) {
            return returnError(e);
        }
    }
}
