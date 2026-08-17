package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SuppressedDiagnosticTest {

    @Test
    void 本体例外と終了例外の両方を診断できる() {
        String diagnostic = SuppressedDiagnostic.runAndDescribe();

        System.out.println("[evidence] diagnostic=" + diagnostic);

        assertEquals("body failure; suppressed=close failure", diagnostic);
    }
}
