package com.coworking.coworking_technical_test.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworking_technical_test.services.interfaces.IUsuarioService;
import com.coworking.coworking_technical_test.shared.dto.UsuarioDTO;
import com.coworking.coworking_technical_test.shared.request.ActualizarUsuarioRequest;
import com.coworking.coworking_technical_test.shared.request.CrearOperadorRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios (solo ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @PostMapping("/operadores")
    @Operation(summary = "Crear operador", description = "Crea un nuevo usuario con rol OPERADOR")
    @ApiResponse(responseCode = "201", description = "Operador creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Email o documento ya registrado")
    public ResponseEntity<UsuarioDTO> crearOperador(@Valid @RequestBody CrearOperadorRequest request) {
        UsuarioDTO usuario = usuarioService.crearOperador(request);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna la información de un usuario específico")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna el listado de todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Listado de usuarios")
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza nombre, apellido, documento y email de un usuario")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @ApiResponse(responseCode = "400", description = "Email o documento ya registrado")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Integer id,
            @Valid @RequestBody ActualizarUsuarioRequest request) {
        UsuarioDTO usuario = usuarioService.actualizar(id, request);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
