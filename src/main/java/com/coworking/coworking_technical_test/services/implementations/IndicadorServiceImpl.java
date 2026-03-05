package com.coworking.coworking_technical_test.services.implementations;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.entities.Ingreso;
import com.coworking.coworking_technical_test.entities.Sede;
import com.coworking.coworking_technical_test.entities.Usuario;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.repositories.HistoricoRepository;
import com.coworking.coworking_technical_test.repositories.IngresoRepository;
import com.coworking.coworking_technical_test.repositories.SedeRepository;
import com.coworking.coworking_technical_test.repositories.UsuarioRepository;
import com.coworking.coworking_technical_test.services.interfaces.IIndicadorService;
import com.coworking.coworking_technical_test.shared.dto.IngresoEconomicoDTO;
import com.coworking.coworking_technical_test.shared.dto.PersonaPrimerIngresoDTO;
import com.coworking.coworking_technical_test.shared.dto.TopOperadorDTO;
import com.coworking.coworking_technical_test.shared.dto.TopPersonaDTO;
import com.coworking.coworking_technical_test.shared.dto.TopSedeFacturacionDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndicadorServiceImpl implements IIndicadorService {

    private final HistoricoRepository historicoRepository;
    private final IngresoRepository ingresoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SedeRepository sedeRepository;
    private final MessageSource messageSource;

    @Override
    public List<TopPersonaDTO> topPersonasConMasIngresos() {
        List<Object[]> resultados = historicoRepository.findTopPersonasConMasIngresos(PageRequest.of(0, 10));
        return resultados.stream()
                .map(r -> new TopPersonaDTO((String) r[0], (String) r[1], (Long) r[2]))
                .collect(Collectors.toList());
    }

    @Override
    public List<TopPersonaDTO> topPersonasConMasIngresosPorSede(Integer sedeId) {
        // Validar que la sede exista
        sedeRepository.findById(sedeId)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("SedeNotFound", null, Locale.getDefault())));

        List<Object[]> resultados = historicoRepository
                .findTopPersonasConMasIngresosPorSede(sedeId, PageRequest.of(0, 10));
        return resultados.stream()
                .map(r -> new TopPersonaDTO((String) r[0], (String) r[1], (Long) r[2]))
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonaPrimerIngresoDTO> personasPrimerIngreso() {
        List<Ingreso> ingresos = ingresoRepository.findPersonasPrimerIngreso();
        return ingresos.stream()
                .map(i -> {
                    PersonaPrimerIngresoDTO dto = new PersonaPrimerIngresoDTO();
                    dto.setDocumento(i.getPersona().getDocumento());
                    dto.setNombreCompleto(i.getPersona().getNombre() + " " + i.getPersona().getApellido());
                    dto.setSedeNombre(i.getSede().getNombre());
                    dto.setFechaHoraIngreso(i.getFechaHoraIngreso());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public IngresoEconomicoDTO ingresosEconomicos(String emailOperador) {
        // Buscar usuario operador
        Usuario operador = usuarioRepository.findByEmail(emailOperador)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("UsuarioNotFound", null, Locale.getDefault())));

        // Buscar sede asignada al operador
        Sede sede = sedeRepository.findByOperadorId(operador.getId())
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("SedeNotFound", null, Locale.getDefault())));

        LocalDate hoy = LocalDate.now();

        // Hoy
        LocalDateTime inicioHoy = hoy.atStartOfDay();
        LocalDateTime finHoy = hoy.atTime(LocalTime.MAX);
        BigDecimal ingresoHoy = historicoRepository.sumarIngresosPorSedeYRango(sede.getId(), inicioHoy, finHoy);

        // Semana (lunes a domingo)
        LocalDate inicioSemana = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = hoy.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        BigDecimal ingresoSemana = historicoRepository.sumarIngresosPorSedeYRango(
                sede.getId(), inicioSemana.atStartOfDay(), finSemana.atTime(LocalTime.MAX));

        // Mes
        LocalDate inicioMes = hoy.withDayOfMonth(1);
        LocalDate finMes = hoy.with(TemporalAdjusters.lastDayOfMonth());
        BigDecimal ingresoMes = historicoRepository.sumarIngresosPorSedeYRango(
                sede.getId(), inicioMes.atStartOfDay(), finMes.atTime(LocalTime.MAX));

        // Año
        LocalDate inicioAnio = hoy.withDayOfYear(1);
        LocalDate finAnio = hoy.with(TemporalAdjusters.lastDayOfYear());
        BigDecimal ingresoAnio = historicoRepository.sumarIngresosPorSedeYRango(
                sede.getId(), inicioAnio.atStartOfDay(), finAnio.atTime(LocalTime.MAX));

        return new IngresoEconomicoDTO(ingresoHoy, ingresoSemana, ingresoMes, ingresoAnio);
    }

    @Override
    public List<TopOperadorDTO> topOperadoresConMasIngresosSemana() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = hoy.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<Object[]> resultados = historicoRepository.findTopOperadoresConMasIngresos(
                inicioSemana.atStartOfDay(), finSemana.atTime(LocalTime.MAX), PageRequest.of(0, 3));

        return resultados.stream()
                .map(r -> new TopOperadorDTO((Integer) r[0], (String) r[1], (Long) r[2]))
                .collect(Collectors.toList());
    }

    @Override
    public List<TopSedeFacturacionDTO> topSedesConMayorFacturacionSemanal() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = hoy.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<Object[]> resultados = historicoRepository.findTopSedesConMayorFacturacion(
                inicioSemana.atStartOfDay(), finSemana.atTime(LocalTime.MAX), PageRequest.of(0, 3));

        return resultados.stream()
                .map(r -> new TopSedeFacturacionDTO((Integer) r[0], (String) r[1], (BigDecimal) r[2]))
                .collect(Collectors.toList());
    }

}
