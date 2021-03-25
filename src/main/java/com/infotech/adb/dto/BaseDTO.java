package com.infotech.adb.dto;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.Serializable;

public interface BaseDTO<D, E> extends Serializable {

    public E convertToEntity() throws JsonProcessingException;

    public void convertToDTO(E entity, boolean partialFill);

    public D convertToNewDTO(E entity, boolean partialFill);

//	public BasicInfoBO convertToBasicInfoBO(E e);
//	public void clear();
//	public B convertToNewDTO(E e, boolean partialFill);

}

