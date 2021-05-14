package br.com.manager.core;

public interface ValidRequest<T> {
	public T getData();
    public boolean isValid();
}
