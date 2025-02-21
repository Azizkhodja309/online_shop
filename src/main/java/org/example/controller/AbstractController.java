package org.example.controller;

public abstract class AbstractController<S> {
    protected final S service;

    protected AbstractController(S s) {
        this.service = s;
    }
}
