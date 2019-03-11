package com.samples.common.converter.interfaces;

public interface IConverter<F, T> {
	
	T convertFrom(F from);
	F convertTo(T to);
}
