package com.ets.filemanager.service;

import com.ets.filemanager.exception.BusinessException;

public interface Validator<T> {
    void validate(T t) throws BusinessException;
}
