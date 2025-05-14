package com.campus.connect.participant.backend.payload.response;

import java.math.BigDecimal;

public class ExpensePerMonth {
    
    private String name;
    private BigDecimal total;

    public String getName() {
        return name;
    }

    public void setName(int month) {
        String name = "";
  if (month == 1) {
    name = "Jan";
} else if (month == 2) {
    name = "Feb";
} else if (month == 3) {
    name = "Mar";
} else if (month == 4) {
    name = "Apr";
} else if (month == 5) {
    name = "May";
} else if (month == 6) {
    name = "Jun";
} else if (month == 7) {
    name = "Jul";
} else if (month == 8) {
    name = "Aug";
} else if (month == 9) {
    name = "Sep";
} else if (month == 10) {
    name = "Oct";
} else if (month == 11) {
    name = "Nov";
} else {
    name = "Dec";
}
        this.name = name;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}