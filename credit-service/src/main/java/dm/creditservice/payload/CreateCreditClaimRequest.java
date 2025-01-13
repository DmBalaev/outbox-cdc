package dm.creditservice.payload;

import java.math.BigDecimal;

public record CreateCreditClaimRequest(
        BigDecimal amount,
        Integer termInMonths
) {
}
