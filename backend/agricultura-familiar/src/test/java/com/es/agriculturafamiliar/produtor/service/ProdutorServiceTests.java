package com.es.agriculturafamiliar.produtor.service;

import com.es.agriculturafamiliar.entity.produtor.Produtor;
import com.es.agriculturafamiliar.enums.TipoProdutor;
import com.es.agriculturafamiliar.exception.ResourceNotFoundException;
import com.es.agriculturafamiliar.repository.ProdutorRepository;
import com.es.agriculturafamiliar.service.ProdutorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProdutorServiceTests {

    @InjectMocks
    private static ProdutorService produtorService;

    @Mock
    private static ProdutorRepository produtorRepository;

    @Mock
    private static ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void saveProdutor_shouldReturnSavedProdutor_whenSuccessful(){
        Produtor produtor = new Produtor();
        produtor.setCpfOuCnpj("43292043742");
        produtor.setNome("Prod Teste");
        produtor.setNomeFantasia("Prod Teste");
        produtor.setEmail("prodteste@email.com");;
        produtor.setRegiaoDeProducao("região");
        produtor.setAtendeNoEnderecoDeProducao(true);
        produtor.setCadastroEntidade(true);
        produtor.setTipoProdutor(TipoProdutor.COLETIVO);
        produtor.setRegistroOuCertificacao(false);
        produtor.setAgroecologico(true);
        produtor.setCertificacaoAgroecologico(true);
        produtor.setOrganico("SIM");
        produtor.setGeolocalizacao("");
        produtor.getTelefones().addAll(Arrays.asList("1234512", "1574869"));

        Produtor returnedSavedProdutor = new Produtor();
        returnedSavedProdutor.setCpfOuCnpj("43292043742");
        returnedSavedProdutor.setNome("Prod Teste");
        returnedSavedProdutor.setNomeFantasia("Prod Teste");
        returnedSavedProdutor.setEmail("prodteste@email.com");;
        returnedSavedProdutor.setRegiaoDeProducao("região");
        returnedSavedProdutor.setAtendeNoEnderecoDeProducao(true);
        returnedSavedProdutor.setCadastroEntidade(true);
        returnedSavedProdutor.setTipoProdutor(TipoProdutor.COLETIVO);
        returnedSavedProdutor.setRegistroOuCertificacao(false);
        returnedSavedProdutor.setAgroecologico(true);
        returnedSavedProdutor.setCertificacaoAgroecologico(true);
        returnedSavedProdutor.setOrganico("SIM");
        returnedSavedProdutor.setGeolocalizacao("");
        returnedSavedProdutor.getTelefones().addAll(Arrays.asList("1234512", "1574869"));

        when(produtorRepository.save(any(Produtor.class)))
                .thenReturn(returnedSavedProdutor);

        var savedProdutor = produtorService.saveProdutor(produtor);

        assertEquals(produtor, savedProdutor);
    }

    @Test
    public void searchProdutorById_shouldThrowsObjectNotFoundException_whenResourceIsNotFound(){

        when(produtorRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> produtorService.findProdutorById(1L));
    }

    @Test
    public void findProdutorById_shouldReturnSavedProdutor_whenSuccessful(){
        Produtor produtor = new Produtor();
        produtor.setCpfOuCnpj("43292043742");
        produtor.setNome("Prod Teste");
        produtor.setNomeFantasia("Prod Teste");
        produtor.setEmail("prodteste@email.com");;
        produtor.setRegiaoDeProducao("região");
        produtor.setAtendeNoEnderecoDeProducao(true);
        produtor.setCadastroEntidade(true);
        produtor.setTipoProdutor(TipoProdutor.COLETIVO);
        produtor.setRegistroOuCertificacao(false);
        produtor.setAgroecologico(true);
        produtor.setCertificacaoAgroecologico(true);
        produtor.setOrganico("SIM");
        produtor.setGeolocalizacao("");
        produtor.getTelefones().addAll(Arrays.asList("1234512", "1574869"));

        when(produtorRepository.findById(any(Long.class))).thenReturn(Optional.of(produtor));

        var returnedProdutor = produtorService.findProdutorById(1L);

        assertEquals(produtor.getNome(), returnedProdutor.getNome());
        assertEquals(produtor.getNomeFantasia(), returnedProdutor.getNomeFantasia());
        assertEquals(produtor.getCpfOuCnpj(), returnedProdutor.getCpfOuCnpj());
        assertEquals(produtor.getTelefones(), returnedProdutor.getTelefones());
    }

    @Test
    public void deleteProdutorById_shouldThrowObjectNotFoundException_whenResourceIsNotFound(){
        when(produtorRepository.findById(any(Long.class)))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> produtorService.deleteProdutorById(1L));
    }

    @Test
    public void updateProdutor_shouldThrowObjectNotFoundException_whenResourceIsNotFound() {
        Produtor produtor = new Produtor();
        produtor.setCpfOuCnpj("43292043742");
        produtor.setNome("Atualizado");

        assertThrows(ResourceNotFoundException.class, () -> produtorService.updateProdutor(produtor, 1345L));
    }
}
