package com.Encora.AmadeusBackend.Repository;

import com.Encora.AmadeusBackend.AmadeusBackendApplication;
import com.Encora.AmadeusBackend.Repo.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = AmadeusBackendApplication.class)
public class AmadeusRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @BeforeEach
    void setUbp(){}
}
