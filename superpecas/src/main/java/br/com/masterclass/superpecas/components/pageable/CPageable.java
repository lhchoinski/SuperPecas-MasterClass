package br.com.masterclass.superpecas.components.pageable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class CPageable {

    private int page = 1;
    private int size = 10;

    public int getPage() {
        return page <= 1 ? 0 : page - 1;
    }

    @JsonIgnore
    public Pageable getPageable() {

        return PageRequest.of(getPage(), getSize());
    }

}
