package com.mindhaven.demo.Services.Streak;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {

    private Boolean hasJournaledBefore;
    private Long streak;
    private Long resetStreakCount;
    private Long hoursSinceLastJournal;

}
