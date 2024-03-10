package com.tyerryzanca.developerTest.service;

import com.tyerryzanca.developerTest.model.dto.UsuarioRequestDTO;
import com.tyerryzanca.developerTest.model.entity.Usuario;
import com.tyerryzanca.developerTest.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    public Usuario inserirUsuario(UsuarioRequestDTO usuario) throws Exception {
        Usuario usuarioEntidade = Usuario.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(passwordEncoder.encode(usuario.getSenha()))
                .build();

        try {
            Usuario usuarioCriado = usuarioRepository.save(usuarioEntidade);
            return usuarioCriado;
        } catch (Exception e) {
            throw new Exception("Error on insert user.");
        }

    }

    public Usuario encontrarUsuario(Integer id) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new Exception("User not found.");
        }
    }

    public List<Usuario> encontrarTodos() throws Exception {
        Optional<List<Usuario>> usuario = Optional.of(usuarioRepository.findAll());
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new Exception("Users not found.");
        }
    }

    public void deletarUsuario(Integer id) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new Exception("User not found, the delete operation was not completed.");
        }
    }

    public Usuario atualizarUsuario(Integer id, UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario usuarioPresente = usuario.get();
            usuarioPresente.setNome(usuarioRequestDTO.getNome() == null ? usuarioPresente.getNome() : usuarioRequestDTO.getNome());
            usuarioPresente.setEmail(usuarioRequestDTO.getEmail() == null ? usuarioPresente.getEmail() : usuarioRequestDTO.getEmail());
            usuarioPresente.setSenha(usuarioRequestDTO.getSenha() == null ? usuarioPresente.getSenha() : passwordEncoder.encode(usuarioRequestDTO.getSenha()));
            Usuario usuarioAtualizado = usuarioRepository.save(usuarioPresente);
            return usuarioAtualizado;
        } else {
            throw new Exception("User not found, the update operation was not completed.");
        }
    }
}
