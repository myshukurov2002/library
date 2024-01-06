package com.company.library_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Setter
@Getter
public class FloorInfoDTO {
    int floorNumber;
    int countAllClosets;
    AtomicInteger countEmptyClosets;
    AtomicInteger countEmptyWardrobes;
}
