
package Utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumericFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string == null) return;
        if (isNumeric(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text == null) return;
        if (isNumeric(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    // Logika pengecekan angka menggunakan regex
    private boolean isNumeric(String text) {
        // Gunakan "^[0-9]*$" untuk angka bulat saja
        // Gunakan "^[0-9.]*$" jika ingin mengizinkan titik desimal
        return text.matches("^[0-9]*$");
    }
}
