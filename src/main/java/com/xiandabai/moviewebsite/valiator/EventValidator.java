package com.xiandabai.moviewebsite.valiator;

import com.xiandabai.moviewebsite.domain.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class EventValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event) target;

        Date currentTime = new Date();
        System.out.println(currentTime);
        System.out.println(event.getEventTime());
        System.out.println(currentTime.getTime() - event.getEventTime().getTime());
        if(event.getEventTime() != null && currentTime.getTime() - event.getEventTime().getTime() > 0)
            errors.rejectValue("eventTime", "date.invalid", "Event time must later than current time");

        if(event.getEventTime() != null && event.getVoteStartTime() != null && event.getVoteEndTime().getTime() - event.getEventTime().getTime() > 0)
            errors.rejectValue("voteEndTime", "date.invalid", "Vote End Time must before than Event time");

        if(event.getVoteEndTime() != null && event.getVoteStartTime() != null && event.getVoteStartTime().getTime() - event.getVoteEndTime().getTime() > 0)
            errors.rejectValue("voteStartTime", "date.invalid", "Vote Start Time must before than Vote End Time");
    }
}
