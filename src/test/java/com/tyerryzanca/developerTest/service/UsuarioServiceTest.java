package com.tyerryzanca.developerTest.service;

import com.tyerryzanca.developerTest.model.dto.UsuarioRequestDTO;
import com.tyerryzanca.developerTest.model.entity.Usuario;
import com.tyerryzanca.developerTest.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void inserirUsuario_Sucesso() throws Exception {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("Usuario Teste", "email@teste.com", "facil123");
        Usuario usuarioEsperado = new Usuario(1, "Usuario Teste", "email@teste.com", "senhaEncriptada");

        when(passwordEncoder.encode(anyString())).thenReturn("senhaEncriptada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEsperado);

        Usuario usuarioResultante = usuarioService.inserirUsuario(usuarioRequestDTO);

        assertNotNull(usuarioResultante);
        assertEquals(usuarioEsperado.getNome(), usuarioResultante.getNome());
        assertEquals(usuarioEsperado.getEmail(), usuarioResultante.getEmail());
        assertEquals("senhaEncriptada", usuarioResultante.getSenha());
    }

    @Test
    void encontrarUsuario_Encontrado() throws Exception {
        Integer usuarioId = 1;
        Optional<Usuario> optionalUsuario = Optional.of(new Usuario(usuarioId, "Usuario Teste", "email@teste.com", "senhaEncriptada"));

        when(usuarioRepository.findById(usuarioId)).thenReturn(optionalUsuario);

        Usuario usuarioResultante = usuarioService.encontrarUsuario(usuarioId);

        assertNotNull(usuarioResultante);
        assertEquals(optionalUsuario.get().getId(), usuarioResultante.getId());

        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    @Test
    void encontrarUsuario_NaoEncontrado() {
        Integer usuarioId = 1;
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.encontrarUsuario(usuarioId);
        });

        assertEquals("User not found.", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    @Test
    void encontrarTodos_Sucesso() throws Exception {
        List<Usuario> usuariosEsperados = List.of(
                new Usuario(1, "Usuario Um", "usuarioum@email.com", "senhaUm"),
                new Usuario(2, "Usuario Dois", "usuariodois@email.com", "senhaDois")
        );

        when(usuarioRepository.findAll()).thenReturn(usuariosEsperados);

        List<Usuario> resultUsers = usuarioService.encontrarTodos();

        assertNotNull(resultUsers);
        assertFalse(resultUsers.isEmpty());
        assertEquals(2, resultUsers.size());
        assertEquals(usuariosEsperados, resultUsers);

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void deletarUsuario_Sucesso() throws Exception {
        Integer usuarioId = 1;
        Usuario usuarioExistente = new Usuario(usuarioId,"Usuario Teste", "email@teste.com", "senhaEncriptada");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        doNothing().when(usuarioRepository).deleteById(usuarioId);

        usuarioService.deletarUsuario(usuarioId);

        verify(usuarioRepository, times(1)).deleteById(usuarioId);
    }

    @Test
    void deletarUsuario_NaoEncontrado() throws Exception {
        Integer usuarioId = 1;
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.deletarUsuario(usuarioId);
        });

        assertEquals("User not found, the delete operation was not completed.", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    @Test
    void atualizarUsuario_Sucesso() throws Exception {
        Integer usuarioId = 1;
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("Nome Atualizado", "emailatualizado@email.com", "novaSenha");
        Usuario usuarioExistente = new Usuario(usuarioId, "Nome Original", "emailoriginal@email.com", "senhaOriginal");
        Usuario usuarioAtualizado = new Usuario(usuarioId, usuarioRequestDTO.getNome(), usuarioRequestDTO.getEmail(), "novasenhaEncriptada");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(passwordEncoder.encode(usuarioRequestDTO.getSenha())).thenReturn("novasenhaEncriptada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioAtualizado);

        Usuario usuarioResultante = usuarioService.atualizarUsuario(usuarioId, usuarioRequestDTO);

        assertNotNull(usuarioResultante);
        assertEquals(usuarioId, usuarioResultante.getId());
        assertEquals(usuarioRequestDTO.getNome(), usuarioResultante.getNome());
        assertEquals(usuarioRequestDTO.getEmail(), usuarioResultante.getEmail());
        assertEquals("novasenhaEncriptada", usuarioResultante.getSenha());

        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(passwordEncoder, times(1)).encode(usuarioRequestDTO.getSenha());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void atualizarUsuario_NaoEncontrado() {
        Integer usuarioId = 1;
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("Updated Name", "updated@email.com", "newPassword");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.atualizarUsuario(usuarioId, usuarioRequestDTO);
        });

        assertEquals("User not found, the update operation was not completed.", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

}
