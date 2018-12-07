package com.bilev.exception.service;

public interface ServiceErrors {

    String USER_NOT_FOUND = "User not found";
    String TARIFF_NOT_FOUND = "Tariff not found";
    String ROLE_NOT_FOUND = "Role not found";
    String OPTION_NOT_FOUND = "Option not found";
    String CONTRACT_NOT_FOUND = "Contract not found";


    String EMAIL_NOT_UNIQUE = "Email is not unique";
    String TARIFF_NAME_NOT_UNIQUE = "Tariff name is not unique";
    String PHONE_NOT_UNIQUE = "Phone number is not unique";
    String OPTION_NAME_NOT_UNIQUE = "Option name is not unique";


    String UNABLE_TO_REMOVE = "Unable to remove";
    String UNABLE_TO_UPDATE = "Unable to update";
    String UNABLE_TO_SAVE = "Unable to save";
    String UNABLE_TO_FIND = "Unable to find";
    String UNABLE_TO_EDIT = "Unable to edit";
    String UNABLE_TO_SUBMIT = "Unable to submit basket";


    String VALIDATION = "Illegal argument";
    String ACCESS_DENIED = "Access denied";

    String TARIFF_VALID = "Unable to remove active tariff";
    String TARIFF_HAS_USERS = "Tariff still has users (Unable to remove)";
    String TARIFF_UNAVAILABLE = "Tariff is currently unavailable";
    String UNABLE_TO_CHANGE_TARIFF = "Unable to change tariff";


    String RELATED_OPTION_REMOVED = "One of the related options has been removed";
    String OPTION_ALREADY_ADDED = "Option's been already added";
    String UNABLE_TO_ADD_OPTION = "Unable to add option";
    String NOT_ENOUGH_MONEY = "Not enough money";
}
