package com.karol.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;

public class JsonMappingTest {

    private static ObjectMapper mapper;

    @BeforeClass
    public static void init() {
        mapper = new ObjectMapper();
    }

    @Test
    public void mapStructureToJsonObject() throws JsonProcessingException {
        Contractor contractor = new Contractor("Karol", "Stańczyk","Skorków 154");
        Contract contract = new Contract(Period.ANNUAL, "123", new Date(), new Date());
        contract.setContractor(contractor);
        contractor.getContractList().add(contract);
        mapper.writeValueAsString(contractor);
        assertTrue(true);
    }
}
