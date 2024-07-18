package dev.patika.vetmanagament.core.config.ModelMapper;

import org.modelmapper.ModelMapper;

public interface IModelMapperService  {
    ModelMapper forRequest ();
    ModelMapper forResponse();
}
