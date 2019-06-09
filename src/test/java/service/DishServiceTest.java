package service;

import model.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import util.exception.IllegalRequestDataException;
import util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static testdata.DishTestData.*;
import static testdata.RestaurantTestData.STARDOGS;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    DishService dishService;

    @Autowired
    RestaurantService restaurantService;

    @Test
    void get() {
        assertThat(dishService.get(DISH118.getId())).isEqualToIgnoringGivenFields(DISH118, "restaurant");
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.get(NO_DISH));
    }

    @Test
    void save() {
        Dish created = dishService.save(TO_NEW_DISH);
        Dish actual = dishService.get(created.getId());
        assertThat(actual).isEqualToIgnoringGivenFields(created, "restaurant");
    }

    @Test
    void saveIllegalArgumentException() {
        assertThrows(IllegalRequestDataException.class, () -> dishService.save(TO_NEW_DISH_EX));
    }

    @Test
    void delete() {
        dishService.delete(DISH110.getId());
        assertMatch(restaurantService.getHistoryDishFromRestaurant(STARDOGS.getId()), STARDOGS_MENU_WITHOUT_DISH110);
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.delete(NO_DISH));
    }
}