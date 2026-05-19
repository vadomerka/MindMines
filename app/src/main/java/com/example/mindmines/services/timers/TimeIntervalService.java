package com.example.mindmines.services.timers;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;

public class TimeIntervalService {
    public static OffsetDateTime plusFloat(OffsetDateTime t, float df) {
        double hf = (df - Math.floor(df)) * 24;
        double mf = (hf - Math.floor(hf)) * 60;

        Period days = Period.of(0, 0, (int) df);
        Duration hours = Duration.ofHours((int) hf);
        Duration minutes = Duration.ofMinutes((int) mf);

        return t.plus(days).plus(hours).plus(minutes);
    }

    public static int getMonths(float df) {
        return (int) (df / 30f);
    }

    public static int getWeeks(float df) {
        return (int) (df / 7f);
    }

    public static int getDays(float df) {
        return (int) df;
    }

    public static int getHours(float df) {
        return (int) (df * 24);
    }

    public static int getMinutes(float df) {
        return (int) (df * 24 * 60);
    }
}

//public class TimeInterval implements TemporalAmount {
//
//    @Override
//    public long get(TemporalUnit unit) {
//        return 0;
//    }
//
//    @Override
//    public List<TemporalUnit> getUnits() {
//        return null;
//    }
//
//    @Override
//    public Temporal addTo(Temporal temporal) {
//        return null;
//    }
//
//    @Override
//    public Temporal subtractFrom(Temporal temporal) {
//        return null;
//    }
//}
