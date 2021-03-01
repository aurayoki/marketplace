package com.jm.marketplace.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @since 11
 */
@Component
@Slf4j
@PropertySource(value = "classpath:application.properties")
public class CommentFilter {
    private List<String> forbiddenWords;

    @Value(value = "${forbidden_words.path}")
    private String pathFileForbiddenWords;

    /**
     * Вызывается после создания бина.
     *
     * Считывает строки из файла запрещённых слов, делит по запятым, убирая лишние пробелы с границ,
     * инициализирует переменную forbiddenWords запрещёнными словами.
     *
     * @throws  FileNotFoundException
     * @throws  URISyntaxException
     */
    @PostConstruct
    public void initialize() throws FileNotFoundException, URISyntaxException {
        pathFileForbiddenWords = pathFileForbiddenWords.startsWith("/") ? pathFileForbiddenWords : "/" + pathFileForbiddenWords;
        try {
            pathFileForbiddenWords = getClass().getResource(pathFileForbiddenWords).toURI().getPath();
        } catch (NullPointerException e) {
            log.error("NullPointerException с параметром: " + pathFileForbiddenWords + ", class CommentFilter, method: initialize", e);
            throw new NullPointerException(e.toString());
        } catch (URISyntaxException e) {
            log.error("URISyntaxException с параметром: " + pathFileForbiddenWords + ", class CommentFilter, method: initialize", e);
            throw new URISyntaxException(e.getInput(), e.getReason(), e.getIndex());
        }

        try (Scanner scanner = new Scanner(new FileInputStream(pathFileForbiddenWords))) {
            scanner.useDelimiter(",");
            forbiddenWords = new ArrayList<>();
            while (scanner.hasNext()) {
                forbiddenWords.add(scanner.next().trim().toLowerCase(Locale.ROOT));
            }
        }
    }
    /**
     * Проверяет на присутствие в комментарии запрещённые слова
     * если присутствуют заменяются на звёздочки
    */
    public String filter(String comment) {
        for (String forbiddenWord : forbiddenWords) {
            comment = replaceWithStars(comment, forbiddenWord);
        }
        return comment;
    }

    /**
     * @return Возвращает comment в котором были заменены все слова соответствующие параметру forbiddenWord на звёздочки.
     */
    private String replaceWithStars(String comment, String forbiddenWord) {
        Matcher matcher = Pattern.compile(forbiddenWord, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(comment);
        return matcher.replaceAll(getStarsByWordLength(forbiddenWord.length()));
    }

    /**
     * @since 11
     */

    private String getStarsByWordLength(int wordLength) {
        return "*".repeat(wordLength);
    }
}
