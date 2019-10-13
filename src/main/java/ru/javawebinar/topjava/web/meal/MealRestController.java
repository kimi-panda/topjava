package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getFiltered {}", startDate, endDate, startTime, endTime);
        List<Meal> mealList = service.getFiltered(meal -> {
                if (startDate != null || endDate != null) DateTimeUtil.isBetween(meal.getDate(), startDate, endDate);
                if (startTime != null || endTime != null) DateTimeUtil.isBetween(meal.getTime(), startTime, endTime);
            return true;});
        return MealsUtil.getTos(mealList, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

    public List<MealTo> getTos() {
        return MealsUtil.getTos(getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

}