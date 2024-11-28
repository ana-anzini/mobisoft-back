package com.mobisoft.mobisoftapi.models;

import com.mobisoft.mobisoftapi.enums.costumer.NetworkType;
import com.mobisoft.mobisoftapi.enums.costumer.PersonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class CostumerTest {

    private Costumer costumer;
    private UserGroup userGroup;

    @BeforeEach
    void setUp() {
        userGroup = new UserGroup();
        costumer = new Costumer();
        costumer.setName("John Doe");
        costumer.setCpfOrCnpj("12345678901");
        costumer.setPhone("1234567890");
        costumer.setEmail("johndoe@example.com");
        costumer.setCep("12345-678");
        costumer.setAddress("123 Main St");
        costumer.setNumber(100);
        costumer.setNeighborhood("Downtown");
        costumer.setUserGroup(userGroup);
    }

    @Test
    void testCostumerCreation() {
        costumer.setRg("123456789");
        costumer.setBirthday(Calendar.getInstance());
        costumer.setNetworkType(NetworkType.STORE);
        costumer.setPersonType(PersonType.INDIVIDUAL);
        costumer.setNotes("Some notes");

        assertNotNull(costumer);
        assertEquals("John Doe", costumer.getName());
        assertEquals("12345678901", costumer.getCpfOrCnpj());
        assertEquals("1234567890", costumer.getPhone());
        assertEquals("johndoe@example.com", costumer.getEmail());
        assertEquals("12345-678", costumer.getCep());
        assertEquals("123 Main St", costumer.getAddress());
        assertEquals(100, costumer.getNumber());
        assertEquals("Downtown", costumer.getNeighborhood());
        assertNotNull(costumer.getUserGroup());
        assertEquals("123456789", costumer.getRg());
        assertNotNull(costumer.getBirthday());
        assertEquals(NetworkType.STORE, costumer.getNetworkType());
        assertEquals(PersonType.INDIVIDUAL, costumer.getPersonType());
        assertEquals("Some notes", costumer.getNotes());
    }

    @Test
    void testCostumerCreationWithoutRgAndNotes() {
        costumer.setRg(null);
        costumer.setNotes(null);

        assertNull(costumer.getRg());
        assertNull(costumer.getNotes());
    }
}
