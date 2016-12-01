package de.frickelbude.es.sandbox.rules;

import java.util.concurrent.TimeUnit;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.junit.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging / timing statement class for {@linkplain TimingWatcher}.
 * 
 * @author Ibragim Kuliev
 *
 */
class TimingStatement extends Statement {

    private static final PeriodFormatter formatter = PeriodFormat.getDefault();;
    private static final Logger log = LoggerFactory.getLogger(TimingWatcher.class);

    private final Statement baseStatement;
    private final Description description;

    public TimingStatement(final Statement baseStatement, final Description description) {
        this.baseStatement = baseStatement;
        this.description = description;
    }

    @Override
    public void evaluate() throws Throwable {

        long start = 0, nanoDuration = 0;
        try {

            log.info("Starting test case {}() in {}",
                description.getMethodName(), description.getTestClass().getSimpleName());

            start = System.nanoTime();
            baseStatement.evaluate();

        } catch (AssumptionViolatedException ex) {
            log.info("Skipped test case {}() in {} due to violated assumption",
                description.getMethodName(), description.getTestClass().getSimpleName());

        } catch (Throwable ex) {
            log.info("Failed test case {}() in {}",
                description.getMethodName(), description.getTestClass().getSimpleName());

        } finally {

            nanoDuration = System.nanoTime() - start;
            long microDuration = TimeUnit.NANOSECONDS.toMicros(nanoDuration);
            if (microDuration >= 1000L) {

                Period period = new Period(TimeUnit.NANOSECONDS.toMillis(nanoDuration));
                log.info("Finished test case {}() in {}, took {} (and {} microseconds)",
                    description.getMethodName(),
                    description.getTestClass().getSimpleName(),
                    formatter.print(period),
                    microDuration % 1000L);

            } else {
                log.info("Finished test case {}() in {}, took {} microseconds",
                    description.getMethodName(),
                    description.getTestClass().getSimpleName(),
                    microDuration);
            }
        }
    }

}
