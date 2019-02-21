package com.vladmihalcea.hibernate.type.basic.internal;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.math.BigInteger;
import java.time.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author Vlad Mihalcea
 */
public class YearMonthEpochTypeDescriptor
        extends AbstractTypeDescriptor<YearMonth> {

    public static final YearMonth YEAR_MONTH_EPOCH = YearMonth.of(1970, 1);

    public static final LocalDate LOCAL_DATE_EPOCH = YEAR_MONTH_EPOCH.atDay(1);

    public static final YearMonthEpochTypeDescriptor INSTANCE = new YearMonthEpochTypeDescriptor();

    public YearMonthEpochTypeDescriptor() {
        super(YearMonth.class);
    }

    @Override
    public boolean areEqual(YearMonth one, YearMonth another) {
        return Objects.equals(one, another);
    }

    @Override
    public String toString(YearMonth value) {
        return value.toString();
    }

    @Override
    public YearMonth fromString(String string) {
        return YearMonth.parse(string);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <X> X unwrap(YearMonth value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        Number monthsSinceEpoch = Period.between(LOCAL_DATE_EPOCH, value.atDay(1)).toTotalMonths();
        if (Short.class.isAssignableFrom(type)) {
            return (X) (Short) (monthsSinceEpoch.shortValue());
        }
        if (Integer.class.isAssignableFrom(type)) {
            return (X) (Integer) (monthsSinceEpoch.intValue());
        }
        if (Long.class.isAssignableFrom(type)) {
            return (X) (Long) (monthsSinceEpoch.longValue());
        }
        if (BigInteger.class.isAssignableFrom(type)) {
            return (X) (BigInteger.valueOf(monthsSinceEpoch.longValue()));
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> YearMonth wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return YEAR_MONTH_EPOCH.plusMonths(((Number) value).intValue());
        }
        throw unknownWrap(value.getClass());
    }
}
