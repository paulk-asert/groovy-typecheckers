import static groovy.test.GroovyAssert.assertScript

assertScript ($/
import groovy.transform.TypeChecked

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static java.time.temporal.ChronoField.MONTH_OF_YEAR

var firstCommitLog = 'Date:   Thu Aug 28 18:48:39 2003 +0000'

@TypeChecked(extensions = 'groovy.typecheckers.RegexChecker')
def getMatcher(String text) {
    text =~ /Date:\s*(\w{3})\s(\w{3})\s(\d{2})\s(\d{2}):(\d{2}):(\d{2})\s(\d{4})/
}

@TypeChecked(extensions = 'groovy.typecheckers.FormatStringChecker')
def displayInfo(day, boolean afternoon, BigDecimal numYears) {
    printf 'Day: %s, Afternoon: %b, Years ago: %3.1f', day, afternoon, numYears
}

var (_, day, month, date, hour, _, _, year) = getMatcher(firstCommitLog)[0]

var afternoon = hour.toInteger() >= 12
int monthNumber = DateTimeFormatter.ofPattern('MMM').parse(month)[MONTH_OF_YEAR]
var now = LocalDate.now()
var firstCommitDate = LocalDate.of(year.toInteger(), monthNumber, date.toInteger())
var numYears = (now - firstCommitDate) / 365

displayInfo(day, afternoon, numYears)
/$)
