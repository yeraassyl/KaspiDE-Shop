package com.company.items;

import java.time.LocalDate;

public interface Expirable {
    int getExpirationDays();
    LocalDate getProductionDate();
    default LocalDate getExpirationDate(){
        return getProductionDate().plusDays(getExpirationDays());
    }
}
