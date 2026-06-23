package com.cl.duoc.service_deudas.service;

import com.cl.duoc.service_deudas.model.Deuda;
import com.cl.duoc.service_deudas.repository.DeudaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeudaServiceTest {

    @Mock
    DeudaRepository deudaRepository;

    @InjectMocks
    DeudaService deudaService;

    @Test
    void listar_returnsAll() {
        List<Deuda> expected = List.of(
                Deuda.builder().id(1L).rutFallecido("1-9").build(),
                Deuda.builder().id(2L).rutFallecido("2-7").build()
        );
        when(deudaRepository.findAll()).thenReturn(expected);

        List<Deuda> actual = deudaService.listar();

        assertThat(actual).isEqualTo(expected);
        verify(deudaRepository).findAll();
    }

    @Test
    void buscarPorId_found() {
        Deuda d = Deuda.builder().id(1L).rutFallecido("1-9").build();
        when(deudaRepository.findById(1L)).thenReturn(Optional.of(d));

        Optional<Deuda> res = deudaService.buscarPorId(1L);

        assertThat(res).isPresent().contains(d);
    }

    @Test
    void buscarPorId_notFound() {
        when(deudaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Deuda> res = deudaService.buscarPorId(1L);

        assertThat(res).isEmpty();
    }

    @Test
    void guardar_saves() {
        Deuda toSave = Deuda.builder().rutFallecido("1-9").tipoDeuda("Personal").institucion("Banco").monto(1000.0).build();
        Deuda saved = Deuda.builder().id(1L).rutFallecido("1-9").tipoDeuda("Personal").institucion("Banco").monto(1000.0).build();
        when(deudaRepository.save(toSave)).thenReturn(saved);

        Deuda res = deudaService.guardar(toSave);

        assertThat(res).isEqualTo(saved);
        verify(deudaRepository).save(toSave);
    }

    @Test
    void actualizar_existing() {
        Deuda existing = Deuda.builder().id(1L).rutFallecido("1-9").tipoDeuda("A").institucion("Old").monto(100.0).build();
        Deuda update = Deuda.builder().rutFallecido("1-9").tipoDeuda("B").institucion("New").monto(200.0).build();
        when(deudaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(deudaRepository.save(any(Deuda.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Deuda> res = deudaService.actualizar(1L, update);

        assertThat(res).isPresent();
        assertThat(res.get().getTipoDeuda()).isEqualTo("B");
        assertThat(res.get().getInstitucion()).isEqualTo("New");
        assertThat(res.get().getMonto()).isEqualTo(200.0);
    }

    @Test
    void eliminar_callsDeleteById() {
        doNothing().when(deudaRepository).deleteById(1L);

        deudaService.eliminar(1L);

        verify(deudaRepository).deleteById(1L);
    }

}
