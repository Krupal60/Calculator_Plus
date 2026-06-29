package com.plus.calculatorplus.domain.agedate

import org.koin.core.annotation.Singleton
import java.util.Calendar
import java.util.concurrent.TimeUnit

@Singleton
class AgeDateCalculationUseCase {

    data class AgeDateInput(
        // Epoch millis (UTC) as produced by Material3 DatePickerState.
        val fromMillis: Long,
        val toMillis: Long
    )

    data class AgeDateResult(
        val years: String,
        val months: String,
        val days: String,
        val totalDays: String
    )

    fun calculate(input: AgeDateInput): AgeDateResult {
        // Guarantee chronological order so the result is always non-negative.
        val startMillis = minOf(input.fromMillis, input.toMillis)
        val endMillis = maxOf(input.fromMillis, input.toMillis)

        val start = Calendar.getInstance().apply { timeInMillis = startMillis }
        val end = Calendar.getInstance().apply { timeInMillis = endMillis }

        var years = end.get(Calendar.YEAR) - start.get(Calendar.YEAR)
        var months = end.get(Calendar.MONTH) - start.get(Calendar.MONTH)
        var days = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH)

        if (days < 0) {
            months -= 1
            // Days in the month preceding the end date.
            val borrow = (end.clone() as Calendar).apply { add(Calendar.MONTH, -1) }
            days += borrow.getActualMaximum(Calendar.DAY_OF_MONTH)
        }
        if (months < 0) {
            years -= 1
            months += 12
        }

        val totalDays = TimeUnit.MILLISECONDS.toDays(endMillis - startMillis)

        return AgeDateResult(
            years = years.toString(),
            months = months.toString(),
            days = days.toString(),
            totalDays = totalDays.toString()
        )
    }
}
