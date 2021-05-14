package br.com.manager.core;


public interface Command<T> {
    public CommandResult execute(ValidRequest<T> request);
}
