package com.jm.marketplace.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class CommentFilterTest {
    @Autowired
    private CommentFilter commentFilter;

    @Test
    void testFusedForbiddenWordToMethodFilter() {
        String testWord = "кожанныеублюдки сидите дома!";
        String exceptedWord = "кожанные******* сидите дома!";

        String testWordSecond = "кожанныеублюдкисидите дома!";
        String exceptedWordSecond = "кожанные*******сидите дома!";

        assertThat(commentFilter.filter(testWord)).isEqualTo(exceptedWord);
        assertThat(commentFilter.filter(testWordSecond)).isEqualTo(exceptedWordSecond);
    }

    @Test
    void testSplitForbiddenWordToMethodFilter() {
        String testWord = "кожанные ублюдки сидите дома!";
        String exceptedWord = "кожанные ******* сидите дома!";

        assertThat(commentFilter.filter(testWord)).isEqualTo(exceptedWord);
    }

    @Test
    void testToIgnoreCaseForbiddenWordToMethodFilter() {
        String testWord = "кожанные УБЛЮДКИ сидите дома!";
        String exceptedWord = "кожанные ******* сидите дома!";

        assertThat(commentFilter.filter(testWord)).isEqualTo(exceptedWord);
    }
}
