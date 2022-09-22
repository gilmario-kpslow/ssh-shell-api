package br.com.gilmariosoftware.ssh.generics;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import org.modelmapper.ModelMapper;

/**
 *
 * @author gilmario
 * @param <T>
 */
@Getter
public abstract class ServiceGeneric<T extends PanacheEntity> {

    private final ModelMapper modelMapper = new ModelMapper();

}
