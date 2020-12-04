package com.example.recruiting.course;

import com.example.recruiting.course.dto.CourseRegistrationDto;

import java.util.Collection;
import java.util.List;

import static com.example.recruiting.course.dto.CourseRegistrationDto.RegistrationStatus.ENROLLED;
import static java.util.Comparator.comparing;
import static java.util.function.BinaryOperator.maxBy;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author juergen.windhaber
 *
 * <p><p>
 * Evaluates which participants are currently valid.
 * As a course participant I can register to a course but I am also allowed to cancel whenever I want.
 * After a cancelation I can also register again, and so on and so forth.
 *
 * <p><p>
 * The current registration is the cronologicaly last registration a course participant sent.
 * <p>
 * A valid registrition is a registration which has the status:
 * <a href="#{@link}">{@link CourseRegistrationDto.RegistrationStatus#ENROLLED}</a>.
 *
 * <p><p>
 * The {@link CourseRegistrationEvaluator} holds a list of registrations. The list is of no particular order.
 *
 * <p><p>
 * The <a href="#{@link}">{@link com.example.recruiting.course.CourseRegistrationEvaluatorTest}</a> tests if the evaluator works correctly.
 *
 */
public class CourseRegistrationEvaluator {


    public static List<String> getAllCurrentValidCourseParticipantNames(List<CourseRegistrationDto> reservationEntries) {

        Collection<CourseRegistrationDto> currentRegistrations = reservationEntries.stream()
                .collect(toMap(CourseRegistrationDto::getCourseParticipantName, identity(), maxBy(comparing(CourseRegistrationDto::getRegistrationDate))))
                .values();

        List<String> currentValidRegistrations = currentRegistrations.stream()
                .filter(registrations -> registrations.getRegistrationStatus() == ENROLLED)
                .map(CourseRegistrationDto::getCourseParticipantName)
                .collect(toList());

        return currentValidRegistrations;
    }
}
