package br.com.masterclass.superpecas.service;

import br.com.masterclass.superpecas.Components.Pageable.CPageable;
import br.com.masterclass.superpecas.DTO.CarroDTO;
import br.com.masterclass.superpecas.model.Carro;
import br.com.masterclass.superpecas.repository.ICarroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CarroService {

    @Autowired
    private ICarroRepository carroRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<CarroDTO> findAll(CPageable cPageable) {
        Pageable pageable = cPageable.getPageable();
        Page<Carro> carroPage = carroRepository.findAll(pageable);
        return carroPage.map(carro -> modelMapper.map(carro, CarroDTO.class));
    }

    public CarroDTO findById(Integer id) {
        Optional<Carro> carro = carroRepository.findById(id);
        return carro.map(value -> modelMapper.map(value, CarroDTO.class)).orElse(null);
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
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (carroRepository.existsById(id)) {
            carroRepository.deleteById(id);
        }
        return HttpStatus.NOT_FOUND.value() == 404;
    }
}
