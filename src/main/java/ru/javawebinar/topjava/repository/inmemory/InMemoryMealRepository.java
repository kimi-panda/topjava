package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        if (!meal.getUserId().equals(userId)) {
            return null;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return get(id, userId) != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) throws NullPointerException {
        log.info("get {}", id);
        Meal meal = repository.get(id);
        return meal == null ? null : !meal.getUserId().equals(userId) ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getFiltered(null, null, null, null, userId);
        /*return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());*/
    }

    @Override
    public List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        log.info("getFiltered");
        List<Meal> mealList = repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(),startDate, endDate))
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(),startTime, endTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        return mealList;
    }
}

