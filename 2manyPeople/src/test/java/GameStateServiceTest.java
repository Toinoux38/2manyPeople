import com.citybuilder.CityBuilder;
import com.citybuilder.modelBis.Cell;
import com.citybuilder.modelBis.CellType;
import com.citybuilder.modelBis.purchasable.spacious.building.aoe.publicService.Hospital;
import com.citybuilder.service.GameStateService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateServiceTest {

    @Inject
    GameStateService gameStateService;

    @BeforeEach
    public void init(){
        gameStateService.setCity(new CityBuilder()
                .setMoney(500)
                .setLoans(new ArrayList<>())
                .setHazardRate(2)
                .setMap(20,30)
                .setName("Caen")
                .build());
    }

    @Test
    @DisplayName("Building Purchase Test")
    public void purchaseTest(){
        assertTrue(gameStateService.purchase(CellType.POLICE_STATION, new Cell(false, new Point(4, 5))),
                "Police Station should be purchased");
        assertEquals(gameStateService.getCity().getMoney(), 200,
                "Police station should have left us with 35000$");
        assertFalse(gameStateService.purchase(CellType.POWER_PLANT, new Cell(false, new Point(10, 5))),
                "Power Plant should cost more than we have");
    }

    @Test
    @DisplayName("Building Purchase Test")
    public void destroyTest(){
        Cell cell = new Cell(false, new Point(10, 5));
        cell.setType(CellType.RESIDENTIAL);
        assertTrue(gameStateService.destroy(cell),
                "cell content should be destroyed");
        assertEquals(gameStateService.getCity().getMoney(), 525,
                "we should have 525$");
        assertFalse(gameStateService.destroy(cell),
                "cell content should be destroyed");
    }
}
