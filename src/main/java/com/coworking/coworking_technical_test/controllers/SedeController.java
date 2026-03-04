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

import com.coworking.coworking_technical_test.services.interfaces.ISedeService;
import com.coworking.coworking_technical_test.shared.dto.SedeDTO;
import com.coworking.coworking_technical_test.shared.request.AsignarOperadorRequest;
import com.coworking.coworking_technical_test.shared.request.SedeRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sedes")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class SedeController {

    private final ISedeService sedeService;

    @PostMapping
    public ResponseEntity<SedeDTO> crear(@Valid @RequestBody SedeRequest request) {
        SedeDTO sede = sedeService.crear(request);
        return new ResponseEntity<>(sede, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SedeDTO> obtenerPorId(@PathVariable Integer id) {
        SedeDTO sede = sedeService.obtenerPorId(id);
        return ResponseEntity.ok(sede);
    }

    @GetMapping
    public ResponseEntity<List<SedeDTO>> obtenerTodas() {
        List<SedeDTO> sedes = sedeService.obtenerTodas();
        return ResponseEntity.ok(sedes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SedeDTO> actualizar(@PathVariable Integer id,
            @Valid @RequestBody SedeRequest request) {
        SedeDTO sede = sedeService.actualizar(id, request);
        return ResponseEntity.ok(sede);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        sedeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/operador")
    public ResponseEntity<SedeDTO> asignarOperador(@PathVariable Integer id,
            @Valid @RequestBody AsignarOperadorRequest request) {
        SedeDTO sede = sedeService.asignarOperador(id, request.getOperadorId());
        return ResponseEntity.ok(sede);
    }

}
