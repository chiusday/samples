package com.samples.common.converter.interfaces;

/***
 * This defines the source (F) and target (T) of conversion
 * @author chiusday
 *
 * @param <F> - conversion source type
 * @param <T> - conversion target type
 */
public interface IConvertible<F, T> {
	IConverter<F, T> getConverter();
}
