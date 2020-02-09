package ru.maksimka.jb.web.controllers;


public interface Controller <REQ, RESP> {
    RESP execute(REQ request);
    Class getRequestClass();
}