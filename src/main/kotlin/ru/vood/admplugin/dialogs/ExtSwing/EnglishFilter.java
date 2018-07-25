package ru.vood.admplugin.dialogs.ExtSwing;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class EnglishFilter extends DocumentFilter {
    //private static final String DIGITS = "\\[abcdefg]+";
    //private static final String CHARACTER = "\\[qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM]+";
    private static final String CHARACTER_LOWER = "\\p{Lower}+";
    private static final String CHARACTER_UPPER = "\\p{Upper}+";
    private static final String CHARACTER_LATIN = "\\p{IsLatin}+";
    private static final String CHARACTER_UNDEL_LINE = "_";

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string.matches(CHARACTER_LATIN) || string.matches(CHARACTER_UNDEL_LINE)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
        if (string.matches(CHARACTER_LATIN) || string.matches(CHARACTER_UNDEL_LINE)) {
            super.replace(fb, offset, length, string, attrs);
        }
    }


}
