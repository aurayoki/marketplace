package com.jm.marketplace.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.*;
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
     * @throws IOException
     */
    @PostConstruct
    public void initialize() throws IOException {
        try (Scanner scanner = new Scanner(new ClassPathResource(pathFileForbiddenWords).getInputStream())) {
            scanner.useDelimiter(",");
            forbiddenWords = new ArrayList<>();
            while (scanner.hasNext()) {
                forbiddenWords.add(scanner.next().trim().toLowerCase(Locale.ROOT));
            }
        } catch (IOException e) {
            log.error("Ошибка чтения файла, входные параметры: " + pathFileForbiddenWords);
            throw new IOException(e.getMessage(), e.getCause());
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
