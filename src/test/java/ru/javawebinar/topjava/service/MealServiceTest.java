package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        Meal meal = service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL3, MEAL2);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> all = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID);
        assertMatch(all, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0),
                "Ужин", 1510);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), MEAL3, MEAL2, MEAL1, newMeal);
        assertMatch(created, newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        service.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                "Завтрак", 500), USER_ID);
    }
}