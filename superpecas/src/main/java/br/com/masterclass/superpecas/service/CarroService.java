package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.components.pageable.CPageable;
import br.com.masterclass.superpecas.DTO.CarroDTO;
import br.com.masterclass.superpecas.exceptions.CarroComPecasAssociadasException;
import br.com.masterclass.superpecas.exceptions.ResourceNotFoundException;
import br.com.masterclass.superpecas.entities.Carro;
import br.com.masterclass.superpecas.projections.CarroProjection;
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
public class CarroService {

    @Autowired
    private ICarroRepository carroRepository;

    @Autowired
    private IPecaRepository pecaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<CarroDTO> findAll(CPageable cPageable) {
        Pageable pageable = cPageable.getPageable();
        Page<Carro> carroPage = carroRepository.findAll(pageable);
        return carroPage.map(carro -> modelMapper.map(carro, CarroDTO.class));
    }

    public CarroDTO findById(Integer id) {
        Optional<Carro> carro = carroRepository.findById(id);
        return carro.map(value -> modelMapper.map(value, CarroDTO.class)).orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado com ID: " + id));
    }

    public CarroDTO save(CarroDTO carroDTO) {
        Carro carro = modelMapper.map(carroDTO, Carro.class);
        carro = carroRepository.save(carro);
        return modelMapper.map(carro, CarroDTO.class);
    }

    public CarroDTO update(Integer id, CarroDTO carroDTO) {
        Optional<Carro> optionalCarro = carroRepository.findById(id);

        if (optionalCarro.isPresent()) {
            Carro carro = optionalCarro.get();
            carro.setSerial(carroDTO.getSerial());
            carro.setNome(carroDTO.getNome());
            carro.setFabricante(carroDTO.getFabricante());

            carro = carroRepository.save(carro);
            return modelMapper.map(carro, CarroDTO.class);
        } else {
            throw new ResourceNotFoundException("Carro não encontrado com ID: " + id);
        }
    }

    public void delete(Integer id) {
        if (!carroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Carro não encontrado com ID: " + id);
        }

        boolean hasAssociatedParts = pecaRepository.existsByCarroId(id);
        if (hasAssociatedParts) {
            throw new CarroComPecasAssociadasException("Não é possível deletar o carro com ID: " + id + " pois ele possui peças associadas.");
        }

        carroRepository.deleteById(id);
    }

    public List<CarroDTO> findByNameContaining(String nome) {
        List<Carro> carros = carroRepository.findByNameContaining(nome);
        return carros.stream().map(carro -> modelMapper.map(carro, CarroDTO.class)).collect(Collectors.toList());
    }

    public List<CarroProjection> findProjectionsById(Integer id) {
        return carroRepository.findProjectionsById(id);
    }
}
