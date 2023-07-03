package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaDbStorage;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

@Slf4j
@Component
public class MpaService {
    private final MpaDbStorage mpaDbStorage;

    @Autowired
    public MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    public List<MpaRating> getAllMpa() {
        log.info("MpaService getAllMpa - возрат информации из mpaDbStorage");
        return mpaDbStorage.getAllMpa();
    }

    public MpaRating getMpa(Integer id) {
        log.info("MpaService getMpa - возрат информации из mpaDbStorage");
        return mpaDbStorage.getMpa(id);
    }
}