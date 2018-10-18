package project.model.util;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Formats the money
 */
public class MoneyFormatterTag extends SimpleTagSupport {

    private Long money;

    public MoneyFormatterTag() {

    }

    public void setMoney(Long money) {
        this.money = money;
    }

    /**
     * Formats the money
     *
     * @throws IOException if something wrong with output stream
     */
    @Override
    public void doTag() throws IOException {
        Double result;
        result = (double) money / 100;
        getJspContext().getOut().write(String.valueOf(result).replace
                (".0", ""));
    }
}
