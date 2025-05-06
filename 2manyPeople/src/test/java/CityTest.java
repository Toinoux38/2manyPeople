import com.citybuilder.CityBuilder;
import com.citybuilder.CityFactory;
import com.citybuilder.modelBis.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityTest {

    @Test
    @DisplayName("City Factory & Builder Test")
    public void testFactoryBuilder(){
        City city = new CityBuilder()
                .setName("Paris")
                .setMap(20,30)
                .setHazardRate(2)
                .setLoans(new ArrayList<>())
                .setMoney(10000)
                .build();
        assertEquals(city.getName(), "Paris");
        assertEquals(city.getMoney(), 10000);
        assertEquals(city.getHazardRate(), 2);
    }

}
