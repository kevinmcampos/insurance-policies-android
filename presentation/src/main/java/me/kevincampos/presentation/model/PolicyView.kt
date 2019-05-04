package me.kevincampos.presentation.model

import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.*

class PolicyView(
    val policyId: String,
    val originalPolicyId: String?,
    private val startDate: String?,
    private val endDate: String?,
    val certificateUrl: String?,
    val termsUrl: String?,
    val cancellationType: String?,
    val newEndDate: String?
) {

    private val formatPattern = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    private val periodFormatter: PeriodFormatter = PeriodFormatterBuilder()
        .appendDays()
        .appendSuffix(" day", " days")
        .appendSeparator(", ")
        .appendHours()
        .appendSuffix(" hour", " hours")
        .appendSeparator(", ")
        .appendMinutes()
        .appendSuffix(" min", " mins")
        .toFormatter()

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatterBuilder()
        .appendDayOfWeekShortText()
        .appendLiteral(", ")
        .appendDayOfMonth(1)
        .appendLiteral(" ")
        .appendMonthOfYearShortText()
        .appendLiteral(" ")
        .appendYear(4, 4)
        .toFormatter()

    private val startDateFormatted: DateTime = DateTime.parse(startDate, formatPattern)
    private val endDateFormatted: DateTime = DateTime.parse(endDate, formatPattern)

    val readableRemainTime: String = {
        val remainTimeInMillis = endDateFormatted.minus(DateTime.now().millis)
        val period = Duration(remainTimeInMillis.millis).toPeriod()?.normalizedStandard()
        if (period != null) periodFormatter.print(period) else ""
    }()

    val readableDuration: String = {
        val duration = Duration(startDateFormatted, endDateFormatted).toPeriod().normalizedStandard()
        periodFormatter.print(duration)
    }()

    val isActive: Boolean = endDateFormatted.isAfterNow && startDateFormatted.isBeforeNow

    val readableStartDate: String = dateTimeFormatter.print(startDateFormatted).capitalize()

}