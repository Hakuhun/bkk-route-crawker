package hu.oe.bakonyi.bkk.bkkroutecrawler.model;

import lombok.Data;

@Data
abstract class BkkBaseModel {
    protected int version;
    protected String status;
    protected int code;
    protected String text;
    protected long currentTime;

}
