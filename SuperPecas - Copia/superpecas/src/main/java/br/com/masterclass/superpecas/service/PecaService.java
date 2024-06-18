package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.Components.Pageable.CPageable;
import br.com.masterclass.superpecas.DTO.PecaDTO;
import br.com.masterclass.superpecas.Exceptions.ResourceNotFoundException;
import br.com.masterclass.superpecas.model.Peca;
import br.com.masterclass.superpecas.repository.ICarroRepository;
import br.com.masterclass.superpecas.repository.IPecaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PecaService {

    @Autowired
    private IPecaRepository pecaRepository;

    @Autowired
    private ICarroRepository carroRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PecaDTO> findAll(CPageable cPageable) {
        Pageable pageable = cPageable.getPageable();
        Page<Peca> pecaPage = pecaRepository.findAll(pageable);
        return pecaPage.map(peca -> modelMapper.map(peca, PecaDTO.class));
    }

    public PecaDTO findById(Integer id) {
        Optional<Peca> peca = pecaRepository.findById(id);
        return peca.map(value -> modelMapper.map(value, PecaDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Peça não encontrada com ID: " + id));
    }

    public PecaDTO save(PecaDTO pecaDTO) {
        if (!carroRepository.existsById(pecaDTO.getCarro().getId())) {
            throw new IllegalArgumentException("O ID do carro fornecido nao existe");
        }
        Peca peca = modelMapper.map(pecaDTO, Peca.class);
        peca = pecaRepository.save(peca);
        return modelMapper.map(peca, PecaDTO.class);
    }

    public PecaDTO update(Integer id, PecaDTO pecaDTO) {
        Optional<Peca> optionalPeca = pecaRepository.findById(id);

        if (optionalPeca.isPresent()) {
            Peca peca = optionalPeca.get();
            peca.setNome(pecaDTO.getNome());
            peca.setSerial_number(pecaDTO.getSerial_number());
            peca.setFabricante(pecaDTO.getFabricante());
            peca.setCarro(pecaDTO.getCarro());
            peca.setDescricao(pecaDTO.getDescricao());

            peca = pecaRepository.save(peca);
            return modelMapper.map(peca, PecaDTO.class);
        } else {
            throw new ResourceNotFoundException("Peça não encontrada com ID: " + id);
        }
    }

    public void delete(Integer id) {
        if (!pecaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Peça não encontrada com ID: " + id);
        }

        pecaRepository.deleteById(id);
    }

    public List<PecaDTO> findByNameContaining(String nome) {
        List<Peca> pecas = pecaRepository.findByNameContaining(nome);
        return pecas.stream()
                .map(peca -> modelMapper.map(peca, PecaDTO.class))
                .collect(Collectors.toList());
    }
}
