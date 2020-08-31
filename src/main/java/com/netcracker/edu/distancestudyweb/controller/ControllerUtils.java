package com.netcracker.edu.distancestudyweb.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    public static final String SERVICE_ERROR_MESSAGE = "Service is temporarily unavailable please try again later";
    public static final String SERVICE_ERROR = "serviceError";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String URL_DELIMITER = "/";
}
